package com.example.party_maker_android.network.Requests

import com.google.gson.annotations.SerializedName

class RegisterRequest(var _email: String, var _userName: String, var _password: String) {

    var email: String? = _email

    var userName: String? = _userName

    var password: String? = _password
}