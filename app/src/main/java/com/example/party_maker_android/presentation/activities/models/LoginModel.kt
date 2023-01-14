package com.example.party_maker_android.presentation.activities.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.domain.services.UserService
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.data.requests.LoginRequest
import com.example.party_maker_android.data.responses.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginModel(application: Application) : AndroidViewModel(application) {
    val loginFeedBackMessage = MutableLiveData<String>()
    val loginSuccess = MutableLiveData<Boolean>()
    private val context = application.applicationContext

    //only available, when the input fields are valid
    fun Login(email: String, password: String){
        var userHttpService = HttpClientsFactory(context).getUserClient()
        var userService = UserService(context)
        var loginMessage: String = ""
        var success: Boolean = false

        viewModelScope.launch(Dispatchers.IO) {

            var loginRequest = LoginRequest(email, password)
            val result: Response<LoginResponse> = userHttpService.Login(loginRequest)

            if(result.code() == 200){
                userService.saveUserTokens(result.body()?.accessToken!!, result.body()?.refreshToken!!)
                success = true
            }
            else{
                loginMessage = result.errorBody().toString()
                success = false
            }
        }
        loginFeedBackMessage.value = loginMessage
        loginSuccess.value = success
    }
}