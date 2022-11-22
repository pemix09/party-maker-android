package com.example.party_maker_android.ui.login

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.example.party_maker_android.R
import com.example.party_maker_android.Services.UserService
import com.example.party_maker_android.databinding.ActivityLoginBinding
import com.example.party_maker_android.network.HttpClientsFactory
import com.example.party_maker_android.network.Requests.LoginRequest
import com.example.party_maker_android.network.Requests.RegisterRequest
import com.example.party_maker_android.network.responses.LoginResponse
import com.example.party_maker_android.ui.map.MapActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private var emailIputValid: Boolean = false
    private var passwordInputValid: Boolean = false
    val loginFeedBackMessage = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        SetListeners()
    }

    private fun SetListeners(){
        loginBinding.loginButton.setOnClickListener {
            Login()
        }

        loginBinding.loginEmailInput.addTextChangedListener {
            loginBinding.loginEmailInputContainer.helperText = validateEmail()
            loginBinding.loginButton.isEnabled = emailIputValid && passwordInputValid
        }

        loginBinding.loginPasswordInput.addTextChangedListener {
            loginBinding.loginPasswordInputContainer.helperText = validatePassword()
            loginBinding.loginButton.isEnabled = emailIputValid && passwordInputValid
        }
    }

    private fun validatePassword(): String?{
        val password = loginBinding.loginPasswordInput.text.toString()
        val passMinLen = 8

        if(password.length < passMinLen){
            this.passwordInputValid = false
            return getString(R.string.password_min_length_error_message, passMinLen)
        }

        this.passwordInputValid = true
        return null
    }
    private fun validateEmail(): String?{
        val email = loginBinding.loginEmailInput.text.toString()

        if(email.isEmpty()){
            this.emailIputValid = false
            return getString(R.string.email_required_error_message)
        }
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() == false){
            this.emailIputValid = false
            return getString(R.string.email_not_valid_error_message)
        }

        this.emailIputValid = true
        return null
    }

    //only available, when the input fields are valid
    private fun Login(){
        var email = loginBinding.loginEmailInput.text.toString()
        var password = loginBinding.loginPasswordInput.text.toString()

        var userHttpService = HttpClientsFactory(this).getUserClient()

        GlobalScope.launch(Dispatchers.IO) {

            var loginRequest = LoginRequest(email, password)

            val result: Response<LoginResponse> = userHttpService.Login(loginRequest)
            Log.i("HttpRequestInvoked", "response: ${result.message()}, code: ${result.code()}")

            if(result.code() == 200){
                Log.i("SuccessfulLogin", "User registered successfully")
                val mapIntent = Intent(this@LoginActivity, MapActivity::class.java)
                this@LoginActivity.startActivity(mapIntent)
            }
            else{
                //posting value to view Model, as it's invoked on coroutine
                loginFeedBackMessage.value = result.errorBody().toString()
            }
        }

    }
}
