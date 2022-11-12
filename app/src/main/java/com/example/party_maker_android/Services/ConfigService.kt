package com.example.party_maker_android.Services

import android.content.Context
import com.example.party_maker_android.R

class ConfigService(context: Context) {

    companion object{
        fun GetBackEndAddress(context: Context): String{
            return context.getString(R.string.backend_address)
        }
    }
}