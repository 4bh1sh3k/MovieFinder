package com.abhishek.moviefinder.view.details

import androidx.lifecycle.DefaultLifecycleObserver
import com.abhishek.moviefinder.repository.OmdbRepository
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    val repository: OmdbRepository
) : DefaultLifecycleObserver