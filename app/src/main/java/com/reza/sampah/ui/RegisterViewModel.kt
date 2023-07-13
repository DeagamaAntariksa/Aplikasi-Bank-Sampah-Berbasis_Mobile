package com.reza.sampah.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.User
import com.example.core.domain.usecase.UserUsecase
import kotlinx.coroutines.launch

class RegisterViewModel(private val userUsecase: UserUsecase): ViewModel() {

    private val _statusSignUp = MutableLiveData<Resource<Boolean>>()
    val statusSignUp : LiveData<Resource<Boolean>> = _statusSignUp

    fun signUp (user : User, password: String){
        viewModelScope.launch {
            userUsecase.register(user , password).collect{
                _statusSignUp.postValue(it)
            }
        }
    }

}