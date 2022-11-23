package com.example.party_maker_android.network.responses

import com.example.party_maker_android.network.model.AccessToken
import com.example.party_maker_android.network.model.RefreshToken
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("accessToken")
    var accessToken: AccessToken? = null

    @SerializedName("refreshToken")
    var refreshToken: RefreshToken? = null
}