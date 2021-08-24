package com.samad_talukder.rokomariassessmenttest.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.samad_talukder.rokomariassessmenttest.R

object GlideUtils {

    private val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_placeholder_image)
        .error(R.drawable.ic_placeholder_image)

    fun showImage(context: Context, imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .apply(requestOptions)
            .into(imageView)
    }
}