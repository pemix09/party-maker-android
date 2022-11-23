package com.example.party_maker_android.ui.register

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.party_maker_android.R
import com.example.party_maker_android.network.HttpClientsFactory
import com.example.party_maker_android.network.Requests.RegisterRequest
import com.example.party_maker_android.network.services.IUserService
import com.example.party_maker_android.ui.login.LoginActivity
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class RegisterViewModel(application: Application) : AndroidViewModel(application){
    private var isEmailValid: Boolean = false
    private var isNickNameValid: Boolean = false
    private var isPasswordValid: Boolean = false
    private var isPasswordConfirmValid: Boolean = false

    val emailValidationMessage = MutableLiveData<String>()
    val userNameValidationMessage = MutableLiveData<String>()
    val passwordValidationMessage = MutableLiveData<String>()
    val passwordConfirmationValidationMessage = MutableLiveData<String>()
    val isFormValid = MutableLiveData<Boolean>(false)
    val registerActionFeedback = MutableLiveData<String>()

    fun validateForm(){
        var formValidation = (isEmailValid && isNickNameValid && isPasswordValid && isPasswordConfirmValid)
        if(isFormValid.value != formValidation){
            isFormValid.value = formValidation
        }
    }

    fun validateEmail(email: String){
        val context = getApplication<Application>().applicationContext

        if(email.isEmpty()){
            this.isEmailValid = false
            this.emailValidationMessage.value = context.getString(R.string.email_required_error_message)
        }
        else if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() == false){
            this.isEmailValid = false
            this.emailValidationMessage.value = context.getString(R.string.email_not_valid_error_message)
        }
        else{
            this.isEmailValid = true
            this.emailValidationMessage.value = ""
        }
        validateForm()
    }

    fun validateUserName(userName: String){
        val context = getApplication<Application>().applicationContext

        if(userName.isEmpty()){
            this.isNickNameValid = false
            this.userNameValidationMessage.value = context.getString(R.string.username_required)
        }
        else{
            this.isNickNameValid = true
            this.userNameValidationMessage.value = ""
        }
        validateForm()
    }

    fun validatePassword(password: String){
        val context = getApplication<Application>().applicationContext
        val passMinLen = 8

        if(password.isEmpty()){
            this.isPasswordValid = false
            this.passwordValidationMessage.value = context.getString(R.string.login_password_input_required_message)
        }
        else if(password.length < passMinLen){
            this.isPasswordValid = false
            this.passwordValidationMessage.value = context.getString(R.string.password_min_length_error_message, passMinLen)
        }
        else{
            this.isPasswordValid = true
            this.passwordValidationMessage.value = ""
        }
        validateForm()
    }

    fun validatePasswordConfirmation(password: String, passwordConfirmation: String){
        val context = getApplication<Application>().applicationContext

        if(password != passwordConfirmation){
            this.isPasswordConfirmValid = false
            this.passwordConfirmationValidationMessage.value = context.getString(R.string.password_confirmation_not_match)
        }
        else if(passwordConfirmation.isEmpty()){
            this.isPasswordConfirmValid = false
            this.passwordConfirmationValidationMessage.value = context.getString(R.string.password_confirmation_required)
        }
        else{
            this.isPasswordConfirmValid = true
            this.passwordConfirmationValidationMessage.value = ""
        }
        validateForm()
    }

    fun register(email: String, userName: String, password: String){
        val context = getApplication<Application>().applicationContext
        var userHttpService = HttpClientsFactory(context).getUserClient()

        viewModelScope.launch(Dispatchers.IO) {

            var registerRequest = RegisterRequest(email, userName, password)

            val result: Response<Void> = userHttpService.Register(registerRequest)
            Log.i("HttpRequestInvoked", "response: ${result.message()}, code: ${result.code()}")

            if(result.code() == 200){
                Log.i("SuccessfulRegister", "User registered successfully")
                val loginIntent = Intent(context, LoginActivity::class.java)
                context.startActivity(loginIntent)
            }
            else{
                //posting value to view Model, as it's invoked on coroutine
                registerActionFeedback.postValue(result.errorBody().toString())
            }
        }

    }
}