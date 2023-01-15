package com.example.party_maker_android.presentation.activities.views

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider

import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.ActivityLoginBinding
import com.example.party_maker_android.presentation.activities.models.LoginModel

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var loginModel: LoginModel
    private var emailIputValid: Boolean = false
    private var passwordInputValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginModel = ViewModelProvider(this)[LoginModel::class.java]

        SetListeners()
    }

    private fun SetListeners(){
        loginBinding.loginButton.setOnClickListener {
            var email = loginBinding.loginEmailInput.text.toString()
            var password = loginBinding.loginPasswordInput.text.toString()
            loginModel.Login(email, password)
        }

        loginBinding.loginEmailInput.addTextChangedListener {
            loginBinding.loginEmailInputContainer.helperText = validateEmail()
            loginBinding.loginButton.isEnabled = emailIputValid && passwordInputValid
        }

        loginBinding.loginPasswordInput.addTextChangedListener {
            loginBinding.loginPasswordInputContainer.helperText = validatePassword()
            loginBinding.loginButton.isEnabled = emailIputValid && passwordInputValid
        }

        loginModel.loginSuccess.observe(this){
            if(it == true){
                var mapIntent = Intent(this, AppActivity::class.java)
                this.startActivity(mapIntent)
            }
            else{
                loginBinding.loginErrorMessage.visibility = TextView.VISIBLE
                loginBinding.loginErrorMessage.text = loginModel.loginFeedBackMessage.value
            }
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


}
