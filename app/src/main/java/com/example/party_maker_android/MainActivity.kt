package com.example.party_maker_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.party_maker_android.Services.UserService
import com.example.party_maker_android.network.model.AccessToken
import com.example.party_maker_android.network.model.RefreshToken
import com.example.party_maker_android.ui.map.MapActivity
import com.example.party_maker_android.ui.welcome.WelcomeActivity

class MainActivity : Activity() {

    var isUserSignedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var userService = UserService(this)
        var accessToken: AccessToken = userService?.getAccessToken()!!
        var refreshToken: RefreshToken = userService?.GetRefreshToken()!!

        if(accessToken.isValid()){
            var mapIntent = Intent(this, MapActivity().javaClass)
            this.startActivity(mapIntent)
        }
        else if(refreshToken.isValid()){
            //perform refresh token operation
        }
        else{
            var welcomeIntent = Intent(this, WelcomeActivity().javaClass)
            this.startActivity(welcomeIntent)
        }

        finish()
    }

}