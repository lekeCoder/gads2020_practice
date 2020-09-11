package com.gads2020.practice.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


class BindUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("bind:badgeUrl")
        fun loadBadge(view: ImageView, badgeUrl: String?) {
            Picasso.get()
                .load(badgeUrl)
                .into(view)
        }

    }
}