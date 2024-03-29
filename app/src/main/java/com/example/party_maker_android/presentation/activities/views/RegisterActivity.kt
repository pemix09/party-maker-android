package com.example.party_maker_android.presentation.activities.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.party_maker_android.databinding.ActivityRegisterBinding
import androidx.core.widget.addTextChangedListener
import com.example.party_maker_android.presentation.activities.viewModels.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityRegisterBinding
    private val TAG = "RegisterActivity"
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
            viewModel.validateEmail(it.toString())
        }
        viewBinding.registerUserNameInput.addTextChangedListener{
            viewModel.validateUserName(it.toString())
        }
        viewBinding.registerPasswordInput.addTextChangedListener{
            viewModel.validatePassword(it.toString())
        }
        viewBinding.registerPasswordInput2.addTextChangedListener {
            var password = viewBinding.registerPasswordInput.text.toString()
            var passwordConfirmation = it.toString()
            viewModel.validatePasswordConfirmation(password, passwordConfirmation)
        }
        viewBinding.registerButton.setOnClickListener {
            var email = viewBinding.registerEmailInput.text.toString()
            var userName = viewBinding.registerUserNameInput.text.toString()
            var password = viewBinding.registerPasswordInput.text.toString()
            try{
                viewModel.register(email, userName, password)
            }
            catch(error: Error){
                Log.e(TAG, "Some error while registration: ${error.message}")
            }

        }
    }

    private fun setModelObservers(){
        viewModel.emailValidationMessage.observe(this){
            viewBinding.registerEmailInputContainer.helperText = it.toString()
        }

        viewModel.userNameValidationMessage.observe(this) {
            viewBinding.registerUserNameInputContainer.helperText = it.toString()
        }
        viewModel.passwordValidationMessage.observe(this){
            viewBinding.registerPasswordInputContainer.helperText = it.toString()
        }
        viewModel.passwordConfirmationValidationMessage.observe(this){
            viewBinding.registerPasswordInput2Container.helperText = it.toString()
        }
        viewModel.isFormValid.observe(this){
            viewBinding.registerButton.isEnabled = it
        }
        viewModel.didRegisterSuccessful.observe(this){
            if(it == true){
                var appIntent = Intent(this, AppActivity::class.java)
                this.startActivity(appIntent)
            }
            else{
                viewModel.registerActionFeedback.value?.let { it1 -> Log.e(TAG, it1) }
            }
        }
    }
}