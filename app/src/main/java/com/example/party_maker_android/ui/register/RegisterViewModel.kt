package com.example.party_maker_android.ui.register

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.party_maker_android.R
import kotlin.coroutines.coroutineContext

class RegisterViewModel : ViewModel(){
    private var isEmailValid: Boolean = false
    private var isNickNameValid: Boolean = false
    private var isPasswordValid: Boolean = false
    private var isPasswordConfirmValid: Boolean = false

    val emailValidationMessage = MutableLiveData<String>()
    val userNameValidationMessage = MutableLiveData<String>()
    val passwordValidationMessage = MutableLiveData<String>()
    val passwordConfirmationValidationMessage = MutableLiveData<String>()
    val isFormValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun validateForm(){
        isFormValid.value = (isEmailValid && isNickNameValid && isPasswordValid && isPasswordConfirmValid)
    }

    fun validateEmail(email: String){
        if(email.isEmpty()){
            this.isEmailValid = false
            this.emailValidationMessage.value = ""
        }
        else if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() == false){
            this.isEmailValid = false
            this.emailValidationMessage.value = "Email not valid"
        }
        else{
            this.isEmailValid = true
            this.emailValidationMessage.value = ""
        }
        validateForm()
    }

    fun validateUserName(userName: String){
        this.userNameValidationMessage.value = ":)"
        this.isNickNameValid = true
        validateForm()
    }

    fun validatePassword(password: String){
        this.isPasswordValid = true
        this.passwordValidationMessage.value = "da"
        validateForm()
    }

    fun validatePasswordConfirmation(password: String, passwordConfirmation: String){
        this.isPasswordConfirmValid = true
        this.passwordConfirmationValidationMessage.value = "dsa"
        validateForm()
    }
}