package com.wcs.ebreedings.others

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.ULocale

@SuppressLint("SimpleDateFormat")
object DateConverter {

    fun convertDate(calendar: Calendar): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(calendar).toString()
    }

    fun getFormattedDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Calendar.getInstance(ULocale("ID")))

    }

    fun getFormattedDateTime2(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
        return sdf.format(Calendar.getInstance(ULocale("ID")))

    }

}
