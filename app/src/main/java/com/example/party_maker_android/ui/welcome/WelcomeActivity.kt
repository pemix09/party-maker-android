package com.example.party_maker_android.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.party_maker_android.R
import com.example.party_maker_android.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {

    lateinit var registerButton: Button
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        //create the view
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //assign buttons to variables
        registerButton = findViewById(R.id.RegisterButton)
        loginButton = findViewById(R.id.LoginButton)

        //Register click actions:
        registerButton.setOnClickListener {

        }
        loginButton.setOnClickListener {
            var loginIntent: Intent = Intent(this, LoginActivity().javaClass)
            this.startActivity(loginIntent)
        }
    }
}