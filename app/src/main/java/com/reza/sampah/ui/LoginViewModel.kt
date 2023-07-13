package com.reza.sampah.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Session
import com.example.core.domain.usecase.UserUsecase
import kotlinx.coroutines.launch

class LoginViewModel(private val userUsecase: UserUsecase): ViewModel() {

    private val _statusLogin = MutableLiveData<Resource<Boolean>>()
    val statusLogin: LiveData<Resource<Boolean>> = _statusLogin

    fun login(email: String, password: String) {
        viewModelScope.launch {
            userUsecase.login(email, password).collect{
                _statusLogin.postValue(it)
            }
        }
    }
    fun isLoggined() {
        viewModelScope.launch {
            _statusLogin.postValue(Resource.Success(userUsecase.isLogginedUser()))
        }
    }
    fun getSession() : Session? {
        var session : Session? = null
        viewModelScope.launch {
            session = userUsecase.getSession()
        }
        return session
    }

}