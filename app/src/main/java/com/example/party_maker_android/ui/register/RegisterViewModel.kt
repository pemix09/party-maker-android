package com.example.party_maker_android.ui.register

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.party_maker_android.R

class RegisterViewModel : ViewModel(){

    val emailValidationMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userNameValidationMessage: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    fun validateEmail(email: String){
        this.emailValidationMessage.value = ";)"
    }
    fun validateUserName(userName: String){
        this.userNameValidationMessage.value = ":)"
    }
}