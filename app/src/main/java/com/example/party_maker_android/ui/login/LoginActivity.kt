package com.example.party_maker_android.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

import com.example.party_maker_android.R
import com.example.party_maker_android.Services.UserService
import com.example.party_maker_android.databinding.ActivityLoginBinding
import com.example.party_maker_android.ui.map.MapActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private var emailIputValid: Boolean = false
    private var passwordInputValid: Boolean = false

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

        var httpRequest = LoginHttpRequest(this, email, password)
        var loginHttpResponse = httpRequest.execute().get()

        //successful login
        if(loginHttpResponse != null){
            var userService = UserService(this)
            userService.SaveUserTokens(loginHttpResponse.accessToken, loginHttpResponse.refreshToken)

            val mapIntent = Intent(this, MapActivity().javaClass)
            this.startActivity(mapIntent)
        }

    }
}
