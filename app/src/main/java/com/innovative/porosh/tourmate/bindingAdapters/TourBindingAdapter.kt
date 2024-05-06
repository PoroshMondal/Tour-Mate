package com.innovative.porosh.tourmate.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("app:setTimeStamp")
fun setFormattedDate(tv: TextView, timestamp: Timestamp) {
    val formattedDate = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        .format(timestamp.toDate())
    tv.text = "created on $formattedDate"
}

@BindingAdapter("app:setWeatherDate")
fun setFormattedWeatherDate(tv: TextView, date: Long) {
    val formattedDate = SimpleDateFormat("EEE, dd, yyyy", Locale.getDefault())
        .format(Date(date * 1000))
    tv.text = formattedDate
}

@BindingAdapter("app:setWeatherIcon")
fun setWeatherIcon(iv: ImageView, icon: String?) {
    icon?.let {
        val url = "http://openweathermap.org/img/wn/${icon}@2x.png"
        Glide.with(iv).load(url).into(iv)
    }
}
