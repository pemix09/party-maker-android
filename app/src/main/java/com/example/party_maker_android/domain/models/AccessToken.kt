package com.example.party_maker_android.domain.models

import com.google.gson.Gson
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