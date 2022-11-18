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

    val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userName: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    private val dataChangeWatcher = MediatorLiveData<String>();

    init{
        dataChangeWatcher.addSource(email){
            validateEmail()
        }
        dataChangeWatcher.addSource(userName, Observer {
            validateUserName()
        })
    }

    private fun validateEmail(){
        this.emailValidationMessage.value = ";)"
    }

    private fun validateUserName(){
        this.userNameValidationMessage.value = ":)"
    }
}