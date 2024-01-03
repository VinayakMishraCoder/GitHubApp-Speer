package com.example.github_speer.adapters

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.example.github_speer.R
import com.example.github_speer.databinding.ErrorEmptyLayoutBinding
import com.squareup.picasso.Picasso

object BindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun imageUrl(view: AppCompatImageView, url:String?){
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
    fun setVisibility(view: View, visibilityStatus: Boolean?){
        if(visibilityStatus==true) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @BindingAdapter("setVisibility")
    @JvmStatic
    fun setVisibility(view: ViewGroup, visibilityStatus: Boolean?){
        if(visibilityStatus==true) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}