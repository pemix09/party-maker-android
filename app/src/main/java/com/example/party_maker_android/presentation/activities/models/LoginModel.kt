package com.example.party_maker_android.presentation.activities.models

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.R
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.data.requests.LoginRequest
import com.example.party_maker_android.data.responses.LoginResponse
import com.example.party_maker_android.domain.services.UserService
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginModel(private val context: Context) {
    var email: String? = null
    var password: String? = null

    fun login(){
        var backEndAddress = context.getString(R.string.BackEndAddress)
        var userHttpService = HttpClientsFactory(backEndAddress).getUserClient()
        var userService = UserService(context)

        //TODO
        /*viewModelScope.launch{

            var loginRequest = LoginRequest(email, password)
            val result: Response<LoginResponse> = userHttpService.Login(loginRequest)

            if(result.isSuccessful){
                userService.saveUserTokens(result.body()?.accessToken!!, result.body()?.refreshToken!!)
                loginSuccess.value = true
            }
            else{
                loginFeedBackMessage.value = result.errorBody().toString()
                loginSuccess.value = false
            }
        }*/
    }
}