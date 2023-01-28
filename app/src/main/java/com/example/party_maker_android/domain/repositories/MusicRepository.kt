package com.example.party_maker_android.domain.repositories

import android.content.Context
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.services.UserService

class MusicRepository(private var context: Context) {
    private val userService = UserService(context)
    private val backEndAddress = context.getString(R.string.BackEndAddress)

    suspend fun getAllMusicGenres(){

    }
}