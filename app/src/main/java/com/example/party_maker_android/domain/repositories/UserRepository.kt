package com.example.party_maker_android.domain.repositories

import android.content.Context
import android.util.Log
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.services.UserService
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.data.clients.IUserClient
import com.example.party_maker_android.data.requests.UpdateUserRequest
import com.example.party_maker_android.domain.models.UserEntity
import retrofit2.Response

class UserRepository(private val context: Context) {

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

    suspend fun updateUser(user: UserEntity){
        var accessToken = userService.getAccessToken()
        var formattedAccessToken = "Bearer ${accessToken?.token!!}"
        var request = UpdateUserRequest(user.userName!!, user.photo!!)
        var response: Response<Void> = userHttpClient.updateUser(formattedAccessToken, request)

        if(!response.isSuccessful){
            throw Error(response.errorBody()?.string())
        }
    }
    suspend fun getEventParticipants(userIds: List<String>): List<UserEntity>{
        var accessToken = userService.getAccessToken()
        var formattedAccessToken = "Bearer ${accessToken?.token!!}"
        var response: Response<List<UserEntity>> = userHttpClient.getManyByIds(formattedAccessToken, userIds)

        if(response.isSuccessful){
            return response.body()!!
        }
        else{
            throw Error(response.errorBody().toString())
        }
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