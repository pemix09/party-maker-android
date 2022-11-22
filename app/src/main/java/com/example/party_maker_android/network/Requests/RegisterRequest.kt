package com.example.party_maker_android.network.Requests

import com.google.gson.annotations.SerializedName

data class RegisterRequest(var email: String, var userName: String, var password: String) {

}