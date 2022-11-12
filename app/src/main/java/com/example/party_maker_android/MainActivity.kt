package com.example.party_maker_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.party_maker_android.ui.map.MapActivity
import com.example.party_maker_android.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    val isUserSignedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(isUserSignedIn){
            var mapIntent = Intent(this, MapActivity().javaClass)
            this.startActivity(mapIntent)
        }
        else{
            var welcomeIntent = Intent(this, WelcomeActivity().javaClass)
            this.startActivity(welcomeIntent)
        }
    }
}