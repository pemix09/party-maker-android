package com.example.party_maker_android.network.Requests

import com.google.gson.annotations.SerializedName

class LoginRequest {

    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null
}