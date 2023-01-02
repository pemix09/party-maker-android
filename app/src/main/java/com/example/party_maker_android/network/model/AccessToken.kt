package com.example.party_maker_android.network.model

import android.util.Log
import com.example.party_maker_android.network.APIFormats.DateFormatDotNetAPI.Companion.dateFormat
import com.example.party_maker_android.network.Converters.DateConverter
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class AccessToken {


    var token: String? = null


    var created: Date? = null

    var expires: Date? = null


    override fun toString(): String {
        var gson = Gson()

        return gson.toJson(this)
    }

    fun isValid(): Boolean{
        val currentTime = Calendar.getInstance().time
        return expires?.after(currentTime)!!
    }
}