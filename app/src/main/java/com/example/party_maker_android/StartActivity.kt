package com.example.party_maker_android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.party_maker_android.domain.services.UserService
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.domain.models.AccessToken
import com.example.party_maker_android.domain.models.RefreshToken
import com.example.party_maker_android.data.requests.RefreshTokenRequest
import com.example.party_maker_android.data.responses.RefreshTokenResponse
import com.example.party_maker_android.presentation.activities.views.LoginActivity
import com.example.party_maker_android.presentation.activities.views.AppActivity
import com.example.party_maker_android.presentation.activities.views.WelcomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Response

class StartActivity : Activity() {

    var userService = UserService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var accessToken: AccessToken? = userService?.getAccessToken()
        var refreshToken: RefreshToken? = userService?.getRefreshToken()

        if(accessToken == null || refreshToken == null){
            var welcomeIntent = Intent(this, WelcomeActivity().javaClass)
            this.startActivity(welcomeIntent)
        }
        else if(accessToken?.isValid()!!){
            var mapIntent = Intent(this, AppActivity().javaClass)
            this.startActivity(mapIntent)
        }
        else if(refreshToken?.isValid()!!){
            var error = false
            var context: Context = this

            MainScope().launch(Dispatchers.IO) {
                var userHttpService = HttpClientsFactory(context).getUserClient()
                var refreshTokenRequest = RefreshTokenRequest(accessToken.token!!, refreshToken.token!!)
                val result: Response<RefreshTokenResponse> = userHttpService.RefreshToken(refreshTokenRequest)

                if(result.code() == 200){
                    userService.SaveRefreshedAccessToken(result.body()?.accessToken!!)
                }
                else{
                    error = true
                }
            }

            if(error == false)
            {
                var mapIntent = Intent(this, AppActivity().javaClass)
                this.startActivity(mapIntent)
            }
            else{
                //TODO - some error occured!!!
            }
        }
        else{
            //TODO - put some message here, like session expired
            var loginIntent = Intent(this, LoginActivity().javaClass)
            this.startActivity(loginIntent)
        }

        finish()
    }

}