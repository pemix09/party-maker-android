package com.example.party_maker_android.presentation.activities.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.services.UserService
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.data.requests.LoginRequest
import com.example.party_maker_android.data.responses.LoginResponse
import com.example.party_maker_android.presentation.activities.models.LoginModel
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loginSuccess = MutableLiveData<Boolean>()
    var loginFeedBackMessage = MutableLiveData<String>()
    var passwordValidationMessage: String? = null
    var emailValidationMessage: String? = null
    var isPasswordInputValid: Boolean = false
    var isEmailInputValid: Boolean = false
    var areInputsValid: Boolean = false
        get() = isPasswordInputValid && isEmailInputValid

    private val context = application.applicationContext
    private val loginModel: LoginModel = LoginModel(application)


    //only available, when the input fields are valid
    fun login(){
        if(areInputsValid){
            viewModelScope.launch{
                try{
                    loginModel.login()
                    loginSuccess.value = true
                    loginFeedBackMessage.value = "Login successful!"
                }
                catch(error: Error){
                    loginSuccess.value = false
                    loginFeedBackMessage.value = "Login failed, details: ${error.message}"
                }
            }
        }
    }

    fun validatePassword(password: String){
        val passMinLen = 8

        if(password.length < passMinLen){
            this.isPasswordInputValid = false
            passwordValidationMessage = context.getString(R.string.password_min_length_error_message, passMinLen)
        }
        else{
            this.passwordValidationMessage = null
            this.isPasswordInputValid = true
            this.loginModel.password = password
        }
    }
    fun validateEmail(email: String){
        if(email.isEmpty()){
            this.isEmailInputValid = false
            this.emailValidationMessage = context.getString(R.string.email_required_error_message)
        }
        else if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() == false){
            this.isEmailInputValid = false
            this.emailValidationMessage = context.getString(R.string.email_not_valid_error_message)
        }
        else{
            this.emailValidationMessage = null
            this.isEmailInputValid = true
            this.loginModel.email = email
        }
    }
}