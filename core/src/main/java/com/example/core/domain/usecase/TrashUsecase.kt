package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.Trash
import kotlinx.coroutines.flow.Flow

interface TrashUsecase {
    suspend fun setStatusPickupTrash(
        userId: String,
        trashId: String,
        status: Boolean
    ): Flow<Resource<Boolean>>

    suspend fun createRequestPickupTrash(request: Trash): Flow<Resource<Boolean>>
    suspend fun getHistoryPickupTrash(): Flow<Resource<List<Trash>>>
    suspend fun removeHistoryPickupTrash(
        trashId: String
    ): Flow<Resource<Boolean>>
    suspend fun getAllRequestPickupTrash(): Flow<Resource<List<Trash>>>
}