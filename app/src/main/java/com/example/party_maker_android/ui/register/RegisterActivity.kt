package com.example.party_maker_android.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
    }
}