package com.example.party_maker_android.presentation.activities.models

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.domain.services.UserService
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.data.requests.LoginRequest
import com.example.party_maker_android.data.responses.LoginResponse
import com.example.party_maker_android.presentation.activities.views.AppActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginModel(application: Application) : AndroidViewModel(application) {
    val loginFeedBackMessage = MutableLiveData<String>()
    val loginSuccess = MutableLiveData<Boolean>()
    private val context = application.applicationContext

    //only available, when the input fields are valid
    fun Login(email: String, password: String){
        var userHttpService = HttpClientsFactory(context).getUserClient()
        var userService = UserService(context)

        viewModelScope.launch{

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
        }
    }
}