/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perlu_dilindungi.helper

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.perlu_dilindungi.R
import com.perlu_dilindungi.config.ApiStatus
import com.perlu_dilindungi.data.HealthCenter
import com.perlu_dilindungi.data.News
import com.perlu_dilindungi.ui.healthCenter.HealthCenterAdapter
import com.perlu_dilindungi.ui.news.NewsAdapter


/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listNewsData")
fun bindNewsRecyclerView(recyclerView: RecyclerView, data: List<News>?) {
    val adapter = recyclerView.adapter as NewsAdapter
    adapter.submitList(data)
}

/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listHealthCenter")
fun bindHealthCenterRecyclerView(recyclerView: RecyclerView, data: List<HealthCenter>?) {
    val adapter = recyclerView.adapter as HealthCenterAdapter
    adapter.submitList(data)
}

/**
 * Uses the Coil library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

/**
 * This binding adapter displays the [ApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("apiStatus")
fun bindStatusImage(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation_page)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

/**
 * This binding adapter displays the [ApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@SuppressLint("SetTextI18n")
@BindingAdapter("apiStatus")
fun bindStatusText(statusTextView: TextView, status: ApiStatus?) {
    when (status) {
        ApiStatus.INIT -> {
            statusTextView.visibility = View.VISIBLE
        }
        ApiStatus.LOADING -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.text = "Loading..."
        }
        ApiStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.text = "Connection Error"
        }
        ApiStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}
