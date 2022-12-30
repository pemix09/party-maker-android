package com.example.party_maker_android.network.model

import com.example.party_maker_android.network.Converters.DateConverter
import com.google.gson.Gson
import java.util.*

class RefreshToken {

    var token: String? = null

    var createdDate: Date? = null
    var created: String? = null
        set(value) {
            createdDate = DateConverter.stringToDate(value)
            field = createdDate.toString()
        }

    var expiresDate: Date? = null
    var expires: String? = null
        set(value){
            expiresDate = DateConverter.stringToDate(value)
            field = expiresDate.toString()
        }

    override fun toString(): String {
        var gson = Gson()

        return gson.toJson(this)
    }

    fun isValid(): Boolean{
        val currentTime = Calendar.getInstance().time
        return expiresDate?.after(currentTime)!!
    }
}