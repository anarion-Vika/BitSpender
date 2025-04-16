package com.example.bitspender.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Long.toLocalDate(): Date {
    return Date(this)
}

fun Date.formatDateToUi(): String {
    val sdf = SimpleDateFormat("dd MMMM", Locale.getDefault())
    return sdf.format(this)
}
fun Date.formatTimeToUi(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(this)
}
