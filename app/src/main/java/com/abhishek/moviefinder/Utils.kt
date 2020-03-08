package com.abhishek.moviefinder

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("isVisible")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if(visible) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun setImage(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}