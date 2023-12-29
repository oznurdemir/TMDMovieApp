package com.example.tmdmovieapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.centerCropTransform
import com.example.tmdmovieapp.R

// Her resim için tekrar tekrar kodların yazılmaması için bu fonksiyonu oluşturduk
fun ImageView.loadCircleImage(path: String) {
    Glide.with(this.context).load(Constants.IMAGE_BASE_URL + path)
        .apply(centerCropTransform().error(R.drawable.ic_error).circleCrop()).into(this)
}

fun ImageView.loadImage(path: String) {
    Glide.with(this.context).load(Constants.IMAGE_BASE_URL + path)
        .apply(centerCropTransform().error(R.drawable.ic_error)).into(this)
}