package com.example.core.data.source

import com.example.core.data.Resource
import com.example.core.data.source.remote.ITrashDatasource
import com.example.core.data.source.remote.response.ApiResponse
import com.example.core.data.source.remote.response.TrashResponse
import com.example.core.domain.model.Trash
import com.example.core.domain.repository.ITrashRepository
import com.example.core.utils.ObjectMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrashRepository(private val trashDatasource: ITrashDatasource): ITrashRepository {
    override suspend fun createRequestPickupTrash(request: Trash): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        trashDatasource.createRequestPickupTrash(request).collect {
            when(it){
                ApiResponse.Empty -> emit(Resource.Error("Gagal membuat request jemput sampah"))
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(true))
            }
        }
    }

    override suspend fun setStatusRequestTrash(
        userId: String,
        trashId: String,
        status: Boolean
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        trashDatasource.setStatusRequestTrash(userId, trashId, status).collect {
            when(it){
                ApiResponse.Empty -> emit(Resource.Error("Gagal mengubah request jemput sampah"))
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(true))
            }
        }
    }

    override suspend fun getHistoryPickupTrash(userId: String): Flow<Resource<List<Trash>>> = flow {
        emit(Resource.Loading())
        trashDatasource.getHistoryPickupTrash(userId).collect {
            when(it){
                ApiResponse.Empty -> emit(Resource.Error("Gagal mengambil history request"))
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Success -> {
                    val transformedList = it.data.map { response -> ObjectMapper.mapTrashResponseToTrash(response) }
                    emit(Resource.Success(transformedList))
                }
            }
        }
    }

    override suspend fun removeHistoryPickupTrash(
        userId: String,
        trashId: String
    ): Flow<Resource<Boolean>> =  flow {
        emit(Resource.Loading())
        trashDatasource.removeHistoryPickupTrash(userId, trashId).collect {
            when(it){
                ApiResponse.Empty -> emit(Resource.Error("Gagal menghapus request jemput sampah"))
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(true))
            }
        }
    }

    override suspend fun getAllRequestPickupTrash(): Flow<Resource<List<Trash>>> = flow {
        emit(Resource.Loading())
        trashDatasource.getAllRequestPickupTrash().collect {
            when(it){
                ApiResponse.Empty -> emit(Resource.Error("Gagal mengambil semua request"))
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Success -> {
                    val transformedList = it.data.map { response -> ObjectMapper.mapTrashResponseToTrash(response) }
                    emit(Resource.Success(transformedList))
                }
            }
        }
    }

}