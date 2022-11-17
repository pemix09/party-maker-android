package com.example.party_maker_android.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    lateinit var mapBinding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapBinding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(mapBinding.root)
    }
}