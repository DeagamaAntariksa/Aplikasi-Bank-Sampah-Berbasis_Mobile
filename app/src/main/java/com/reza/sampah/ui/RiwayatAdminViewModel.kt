package com.reza.sampah.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Trash
import com.example.core.domain.usecase.TrashUsecase
import kotlinx.coroutines.launch

class RiwayatAdminViewModel(private val trashUsecase: TrashUsecase): ViewModel() {

    private val _listTrash = MutableLiveData<Resource<List<Trash>>>()
    val listTrash: LiveData<Resource<List<Trash>>> = _listTrash

    private val _statusSwitch = MutableLiveData<Resource<Boolean>>()
    val statusSwitch: LiveData<Resource<Boolean>> = _statusSwitch

    fun getListTrash(){
        viewModelScope.launch {
            trashUsecase.getAllRequestPickupTrash().collect {
                _listTrash.postValue(it)
            }
        }
    }

    fun updateTrash(trash: Trash){
        viewModelScope.launch {
            trashUsecase.setStatusPickupTrash(trash.idClient, trash.id, trash.statusPickUp).collect {
                _statusSwitch.postValue(it)
            }
        }
    }
}