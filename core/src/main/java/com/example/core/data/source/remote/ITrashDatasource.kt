package com.example.core.data.source.remote

import com.example.core.data.source.remote.response.ApiResponse
import com.example.core.data.source.remote.response.TrashResponse
import com.example.core.domain.model.Trash
import kotlinx.coroutines.flow.Flow

interface ITrashDatasource {
    suspend fun createRequestPickupTrash(request: Trash): Flow<ApiResponse<Boolean>>
    suspend fun setStatusRequestTrash(
        userId: String,
        trashId: String,
        status: Boolean
    ): Flow<ApiResponse<Boolean>>

    suspend fun getHistoryPickupTrash(userId: String): Flow<ApiResponse<List<TrashResponse>>>
    suspend fun removeHistoryPickupTrash(
        userId: String,
        trashId: String
    ): Flow<ApiResponse<Boolean>>
    suspend fun getAllRequestPickupTrash(): Flow<ApiResponse<List<TrashResponse>>>
}