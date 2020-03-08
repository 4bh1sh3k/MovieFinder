package com.abhishek.moviefinder.view.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.abhishek.moviefinder.R
import com.abhishek.moviefinder.databinding.ActivityDetailsBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details)
            .apply { viewModel = this@DetailsActivity.viewModel }
    }
}
