package com.reza.sampah.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Trash
import com.example.core.domain.usecase.TrashUsecase
import kotlinx.coroutines.launch

class RiwayatViewModel(private val trashUsecase: TrashUsecase): ViewModel() {

    private val _listTrash = MutableLiveData<Resource<List<Trash>>>()
    val listTrash: LiveData<Resource<List<Trash>>> = _listTrash

    private val _statusRemove = MutableLiveData<Resource<Boolean>>()
    val statusRemove: LiveData<Resource<Boolean>> = _statusRemove

    fun getHistory(){
        viewModelScope.launch {
            trashUsecase.getHistoryPickupTrash().collect {
                _listTrash.postValue(it)
            }
        }
    }

    fun remove(trashId: String){
        viewModelScope.launch {
            trashUsecase.removeHistoryPickupTrash(trashId).collect {
                _statusRemove.postValue(it)
            }
        }
    }
}