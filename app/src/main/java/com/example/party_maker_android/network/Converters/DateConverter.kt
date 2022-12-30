package com.example.party_maker_android.network.Converters

import android.util.Log
import com.example.party_maker_android.network.APIFormats.DateFormatDotNetAPI
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object{

        fun stringToDate(dateString: String?): Date?{
            var sdf: SimpleDateFormat = SimpleDateFormat(DateFormatDotNetAPI.dateFormat, Locale.getDefault())

            try {
                return sdf.parse(dateString.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    }
}