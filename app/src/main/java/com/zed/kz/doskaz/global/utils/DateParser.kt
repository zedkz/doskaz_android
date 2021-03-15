package com.zed.kz.doskaz.global.utils

import java.text.SimpleDateFormat
import java.util.*

object DateParser {

    fun yyyyMMddToDate(dateStr: String): Date{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return simpleDateFormat.parse(dateStr)
    }

    fun dateAndTimeToDate(dateStr: String): String{
        return if (dateStr.isNotEmpty()){
            val fromDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val toDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = fromDateFormat.parse(dateStr)
            toDateFormat.format(date)
        }else{
            ""
        }
    }

}