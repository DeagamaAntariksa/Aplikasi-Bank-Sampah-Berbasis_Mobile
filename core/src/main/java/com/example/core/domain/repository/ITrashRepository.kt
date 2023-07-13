package com.example.core.domain.repository

import com.example.core.data.Resource
import com.example.core.domain.model.Trash
import kotlinx.coroutines.flow.Flow

interface ITrashRepository {
    suspend fun createRequestPickupTrash(request: Trash): Flow<Resource<Boolean>>
    suspend fun setStatusRequestTrash(
        userId: String,
        trashId: String,
        status: Boolean
    ): Flow<Resource<Boolean>>

    suspend fun getHistoryPickupTrash(userId: String): Flow<Resource<List<Trash>>>
    suspend fun removeHistoryPickupTrash(
        userId: String,
        trashId: String
    ): Flow<Resource<Boolean>>
    suspend fun getAllRequestPickupTrash(): Flow<Resource<List<Trash>>>
}