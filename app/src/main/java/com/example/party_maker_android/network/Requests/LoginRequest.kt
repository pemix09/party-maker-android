package com.example.party_maker_android.network.Requests

import com.google.gson.annotations.SerializedName

data class LoginRequest(val email: String, val password: String) {

}