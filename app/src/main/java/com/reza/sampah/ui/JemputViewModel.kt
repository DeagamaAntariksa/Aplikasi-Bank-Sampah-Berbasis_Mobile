package com.reza.sampah.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Trash
import com.example.core.domain.usecase.TrashUsecase
import kotlinx.coroutines.launch

class JemputViewModel(private val trashUsecase: TrashUsecase): ViewModel() {

    private val _statusUpload = MutableLiveData<Resource<Boolean>>()
    val statusUpload: LiveData<Resource<Boolean>> = _statusUpload

    fun inputJemput(trash: Trash){
        viewModelScope.launch {
            trashUsecase.createRequestPickupTrash(trash).collect {
                _statusUpload.postValue(it)
            }
        }
    }
}