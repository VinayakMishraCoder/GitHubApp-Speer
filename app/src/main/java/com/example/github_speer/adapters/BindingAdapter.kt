package com.example.github_speer.adapters

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.example.github_speer.R
import com.squareup.picasso.Picasso

object BindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: AppCompatImageView, url:String?){
        if(url.isNullOrEmpty() == true) {
            view.setImageResource(R.drawable.baseline_cloud_circle_24)
        } else {
            Picasso.get().load(url)
                .placeholder(R.drawable.baseline_cloud_circle_24)
                .error(R.drawable.baseline_cloud_circle_24)
                .into(view)
        }
    }

    @BindingAdapter("setVisibility")
    @JvmStatic
    fun visibility(view: View, visibilityStatus: Boolean){
        if(visibilityStatus) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}