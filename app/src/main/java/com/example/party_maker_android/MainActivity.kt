package com.example.party_maker_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.party_maker_android.Services.UserService
import com.example.party_maker_android.network.model.AccessToken
import com.example.party_maker_android.network.model.RefreshToken
import com.example.party_maker_android.ui.login.LoginActivity
import com.example.party_maker_android.ui.map.MapActivity
import com.example.party_maker_android.ui.welcome.WelcomeActivity

class MainActivity : Activity() {

    var userService = UserService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var accessToken: AccessToken? = userService?.getAccessToken()
        var refreshToken: RefreshToken? = userService?.getRefreshToken()

        if(accessToken == null || refreshToken == null){
            var welcomeIntent = Intent(this, WelcomeActivity().javaClass)
            this.startActivity(welcomeIntent)
        }
        else if(accessToken?.isValid()!!){
            var mapIntent = Intent(this, MapActivity().javaClass)
            this.startActivity(mapIntent)
        }
        else if(refreshToken?.isValid()!!){
            //perform refresh token operation
        }
        else{
            //TODO - put some message here, like session expired
            var loginIntent = Intent(this, LoginActivity().javaClass)
            this.startActivity(loginIntent)
        }

        finish()
    }

}