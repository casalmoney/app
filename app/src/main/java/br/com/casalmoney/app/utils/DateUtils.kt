package br.com.casalmoney.app.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DateUtils {
    val datePattern = "dd/MM/yyyy"
    val fullDatePattern = "yyyy.MM.dd HH:mm"

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long, pattern: String = datePattern): String {
        val date = Date(time)
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateToLong(date: String, pattern: String = datePattern): Long {
        val df = SimpleDateFormat(pattern)
        return df.parse(date).time
    }
}