package com.example.party_maker_android.network.model

import com.google.gson.annotations.SerializedName
import java.util.*

class AccessToken {

    @SerializedName("Token")
    var token: String? = null

    @SerializedName("Created")
    var created: Date? = null

    @SerializedName("Expires")
    var expires: Date? = null
}