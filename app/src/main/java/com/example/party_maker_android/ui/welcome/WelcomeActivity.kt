package com.example.party_maker_android.ui.welcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.party_maker_android.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_welcome)
    }
}