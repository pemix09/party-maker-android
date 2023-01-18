package com.example.party_maker_android.presentation.activities.models

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.R
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.data.requests.LoginRequest
import com.example.party_maker_android.data.responses.LoginResponse
import com.example.party_maker_android.domain.services.UserService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginModel(private val context: Context) {
    var email: String? = null
    var password: String? = null
    var loginSuccess: Boolean = false
    var loginFeedBackMessage: String? = null

    suspend fun login(defaultDispatcher: CoroutineDispatcher = Dispatchers.Default){
        var backEndAddress = context.getString(R.string.BackEndAddress)
        var userHttpService = HttpClientsFactory(backEndAddress).getUserClient()
        var userService = UserService(context)
        var loginRequest = LoginRequest(email!!, password!!)

        withContext(defaultDispatcher){
            val result: Response<LoginResponse> = userHttpService.Login(loginRequest)

            if(result.isSuccessful){
                userService.saveUserTokens(result.body()?.accessToken!!, result.body()?.refreshToken!!)
                loginSuccess = true
            }
            else{
                loginFeedBackMessage = result.errorBody().toString()
                loginSuccess = false
            }
        }
    }
}