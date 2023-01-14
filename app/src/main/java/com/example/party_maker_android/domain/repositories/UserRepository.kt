package com.example.party_maker_android.domain.repositories

import android.content.Context
import android.util.Log
import com.example.party_maker_android.domain.services.UserService
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.data.clients.IUserClient
import com.example.party_maker_android.domain.models.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository(private val dispatcher: CoroutineDispatcher, private val context: Context) {

    private val userHttpClient: IUserClient = HttpClientsFactory(context).getUserClient()
    private val userService = UserService(context)
    private val TAG = "UserRepository"

    suspend fun getCurrentUser(): UserEntity?{
        var user: UserEntity? = null

        withContext(dispatcher){
            var accessToken = userService.getAccessToken()
            var formattedAccessToken = "Bearer ${accessToken?.token!!}"

            runBlocking {
                var response: Response<UserEntity> = userHttpClient.getCurrentUser(formattedAccessToken)

                if(response.isSuccessful){
                    Log.i(TAG ,"User data fetched successfully!")
                    user = response.body()
                }
                else{
                    throw Error("User not fetched successfully, reason: ${response.errorBody().toString()}")
                }
            }
        }

        return user
    }
}