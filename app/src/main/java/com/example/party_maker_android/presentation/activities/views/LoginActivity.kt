package com.example.party_maker_android.presentation.activities.views

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider

import com.example.party_maker_android.databinding.ActivityLoginBinding
import com.example.party_maker_android.presentation.activities.viewModels.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        setViewListeners()
        setViewModelObservers()
    }

    private fun setViewListeners(){
        loginBinding.loginButton.setOnClickListener {
            loginViewModel.login()
        }

        loginBinding.loginEmailInput.addTextChangedListener {
            loginViewModel.validateEmail(it.toString())
            loginBinding.loginEmailInputContainer.helperText = loginViewModel.emailValidationMessage
            loginBinding.loginButton.isEnabled = loginViewModel.areInputsValid
        }

        loginBinding.loginPasswordInput.addTextChangedListener {
            loginViewModel.validatePassword(it.toString())
            loginBinding.loginPasswordInputContainer.helperText = loginViewModel.passwordValidationMessage
            loginBinding.loginButton.isEnabled = loginViewModel.areInputsValid
        }


    }

    private fun setViewModelObservers(){
        loginViewModel.loginSuccess.observe(this){
            if(it == true){
                var appIntent = Intent(this, AppActivity::class.java)
                this.startActivity(appIntent)
            }
            else{
                loginBinding.loginErrorMessage.visibility = TextView.VISIBLE
                loginBinding.loginErrorMessage.text = loginViewModel.loginFeedBackMessage.value
            }
        }
        loginViewModel.loginFeedBackMessage.observe(this){
            if(it != null){
                loginBinding.loginErrorMessage.text = it
            }
        }
    }
}
