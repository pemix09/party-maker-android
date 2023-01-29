package com.example.party_maker_android.data.responses

import com.example.party_maker_android.domain.models.AccessToken
import com.example.party_maker_android.domain.models.RefreshToken
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("accessToken")
    var accessToken: AccessToken? = null

    @SerializedName("refreshToken")
    var refreshToken: RefreshToken? = null
}