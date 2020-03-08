package com.abhishek.moviefinder.view.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.abhishek.moviefinder.R
import com.abhishek.moviefinder.databinding.ActivityDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

const val EXTRA_ID = "id"

class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: DetailsViewModel

    private val disposables = CompositeDisposable()
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setTitle(R.string.title_details)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding =
            DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details)
                .apply { viewModel = this@DetailsActivity.viewModel }

        disposables += viewModel.getEvents()
            .subscribeBy { handleViewModelEvent(it) }

        viewModel.showDetailsById(intent.getStringExtra(EXTRA_ID))
    }

    private fun handleViewModelEvent(event: DetailsViewModel.Event) {
        when (event) {
            DetailsViewModel.Event.OnError ->
                Snackbar.make(
                        binding.root,
                        getString(R.string.label_no_details),
                        Snackbar.LENGTH_LONG
                    )
                    .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
