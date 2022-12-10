package com.example.party_maker_android.network.requests

data class RefreshTokenRequest(var accessToken: String, var refreshToken: String) {
}