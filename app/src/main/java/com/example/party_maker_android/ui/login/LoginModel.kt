package com.example.party_maker_android.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.Services.UserService
import com.example.party_maker_android.network.HttpClientsFactory
import com.example.party_maker_android.network.Requests.LoginRequest
import com.example.party_maker_android.network.responses.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginModel(application: Application) : AndroidViewModel(application) {
    val loginFeedBackMessage = MutableLiveData<String>()
    val context = application.applicationContext

    //only available, when the input fields are valid
    fun Login(email: String, password: String){
        var userService = UserService(context)
        var userHttpService = HttpClientsFactory(context).getUserClient()

        viewModelScope.launch(Dispatchers.IO) {

            var loginRequest = LoginRequest(email, password)

            val result: Response<LoginResponse> = userHttpService.Login(loginRequest)
            Log.i("HttpRequestInvoked", "response: ${result.message()}, code: ${result.code()}, body: ${result.body()?.accessToken}")

            if(result.code() == 200){
                Log.i("SuccessfulLogin", "User logged successfully")
                userService.saveUserTokens(result.body()?.accessToken!!, result.body()?.refreshToken!!)
            }
            else{
                //posting value to view Model, as it's invoked on coroutine
                loginFeedBackMessage.value = result.errorBody().toString()
            }
        }

    }
}