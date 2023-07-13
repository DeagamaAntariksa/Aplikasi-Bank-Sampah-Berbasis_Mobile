package com.reza.sampah.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.UserUsecase
import kotlinx.coroutines.launch

class MainViewModel(private val userUsecase: UserUsecase): ViewModel() {

    fun signOut() : Boolean {
        var signOut : Boolean = false
        viewModelScope.launch {
            signOut = userUsecase.logout()
        }
        return signOut
    }

}