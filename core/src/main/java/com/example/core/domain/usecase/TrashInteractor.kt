package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.Trash
import com.example.core.domain.repository.ITrashRepository
import com.example.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrashInteractor(
    private val userRepository: IUserRepository,
    private val trashRepository: ITrashRepository
) : TrashUsecase {
    override suspend fun setStatusPickupTrash(
        userId: String,
        trashId: String,
        status: Boolean
    ): Flow<Resource<Boolean>> = trashRepository.setStatusRequestTrash(userId, trashId, status)

    override suspend fun createRequestPickupTrash(request: Trash): Flow<Resource<Boolean>> = flow {
        val session = userRepository.getSession()
        if (session != null) {
            val transformedRequest = request.copy(idClient = session.id)
            trashRepository.createRequestPickupTrash(transformedRequest).collect {
                emit(it)
            }
        } else {
            emit(Resource.Error("Sesi kamu telah habis, silahkan login kembali!"))
        }
    }

    override suspend fun getHistoryPickupTrash(): Flow<Resource<List<Trash>>> = flow {
        val session = userRepository.getSession()
        if (session != null) {
            trashRepository.getHistoryPickupTrash(session.id).collect {
                emit(it)
            }
        } else {
            emit(Resource.Error("Sesi kamu telah habis, silahkan login kembali!"))
        }
    }

    override suspend fun removeHistoryPickupTrash(
        trashId: String
    ): Flow<Resource<Boolean>> = flow {
        val session = userRepository.getSession()
        if (session != null) {
            trashRepository.removeHistoryPickupTrash(session.id, trashId).collect {
                emit(it)
            }
        } else {
            emit(Resource.Error("Sesi kamu telah habis, silahkan login kembali!"))
        }
    }

    override suspend fun getAllRequestPickupTrash(): Flow<Resource<List<Trash>>>
    = trashRepository.getAllRequestPickupTrash()

}