package com.example.party_maker_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.party_maker_android.Services.UserService
import com.example.party_maker_android.network.model.AccessToken
import com.example.party_maker_android.ui.map.MapActivity
import com.example.party_maker_android.ui.welcome.WelcomeActivity

class MainActivity : Activity() {

    var isUserSignedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(isUserSignedIn == true){
            var mapIntent = Intent(this, MapActivity().javaClass)
            this.startActivity(mapIntent)
        }
        else{
            var welcomeIntent = Intent(this, WelcomeActivity().javaClass)
            this.startActivity(welcomeIntent)
        }

        finish()
    }

}