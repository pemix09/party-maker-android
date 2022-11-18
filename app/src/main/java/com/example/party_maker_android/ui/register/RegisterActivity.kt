package com.example.party_maker_android.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.ActivityRegisterBinding
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityRegisterBinding
    lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        setViewChangeListeners()
        setModelObservers()
    }

    private fun setViewChangeListeners(){
        viewBinding.registerEmailInput.addTextChangedListener{
            viewModel.email.value = viewBinding.registerEmailInput.text.toString()
        }
        viewBinding.registerUserNameInput.addTextChangedListener {
            viewModel.userName.value = viewBinding.registerUserNameInput.text.toString()
        }
    }

    private fun setModelObservers(){
        viewModel.emailValidationMessage.observe(this, Observer {
            viewBinding.registerEmailInputContainer.helperText = it.toString()
        })

    }
}