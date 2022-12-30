package com.example.party_maker_android.network.model

import android.util.Log
import com.example.party_maker_android.network.APIFormats.DateFormatDotNetAPI.Companion.dateFormat
import com.example.party_maker_android.network.Converters.DateConverter
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class AccessToken {


    var token: String? = null

    var createdDate: Date? = null
        get() = DateConverter.stringToDate(created)
    var created: String? = null

    var expiresDate: Date? = null
        get() = DateConverter.stringToDate(expires)
    var expires: String? = null


    override fun toString(): String {
        var gson = Gson()

        return gson.toJson(this)
    }

    fun isValid(): Boolean{
        val currentTime = Calendar.getInstance().time
        val expiresTime = expiresDate
        return expiresTime?.after(currentTime)!!
    }
}