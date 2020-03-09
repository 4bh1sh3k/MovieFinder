package com.abhishek.moviefinder.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.abhishek.moviefinder.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewpager = findViewById<ViewPager2>(R.id.viewpager)
        viewpager.adapter = MainPagerAdapter(this)

        val tablayout = findViewById<TabLayout>(R.id.tablayout)
        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> "Search"
                1 -> "Favorite"
                else -> throw IllegalArgumentException("Position should be less than 2")
            }
        }
            .attach()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}

class MainPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchFragment()
            1 -> FavoriteFragment()
            else -> throw IllegalArgumentException("Position should be less than 2")
        }
    }
}


