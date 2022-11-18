package com.example.party_maker_android.network.responses

import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("AccessToken")
    var accessToken: String? = null

}