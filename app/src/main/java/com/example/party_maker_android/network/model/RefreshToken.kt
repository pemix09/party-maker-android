package com.example.party_maker_android.network.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.util.Date

class RefreshToken {

    @SerializedName("token")
    var token: String? = null

    @SerializedName("created")
    var created: Date? = null

    @SerializedName("expires")
    var expires: Date? = null

    override fun toString(): String {
        var gson = Gson()

        return gson.toJson(this)
    }
}