package com.example.party_maker_android.domain.repositories

import android.content.Context
import android.util.Log
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.services.UserService
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.data.clients.IUserClient
import com.example.party_maker_android.domain.models.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository(private val dispatcher: CoroutineDispatcher, private val context: Context) {

    private val backEndAddress = context.getString(R.string.BackEndAddress)
    private val userHttpClient: IUserClient = HttpClientsFactory(backEndAddress).getUserClient()
    private val userService = UserService(context)
    private val TAG = "UserRepository"

    companion object{
        private var currentUser: UserEntity? = null
    }
    suspend fun getCurrentUser(): UserEntity?{
        if(currentUser != null) return currentUser
        else if(false){
            //TODO - user in local memory
        }
        else{
            fetchCurrentUser()
        }
        return currentUser
    }

    private suspend fun fetchCurrentUser(){
        var accessToken = userService.getAccessToken()
        var formattedAccessToken = "Bearer ${accessToken?.token!!}"
        var response: Response<UserEntity> = userHttpClient.getCurrentUser(formattedAccessToken)
        if(response.isSuccessful){
            Log.i(TAG ,"User data fetched successfully!")
            currentUser = response.body()
        }
        else{
            throw Error("User not fetched successfully, reason: ${response.errorBody().toString()}")
        }
    }
}