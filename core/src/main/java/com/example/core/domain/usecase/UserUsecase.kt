package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.Session
import com.example.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUsecase {
    suspend fun login(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun register(user: User, password: String): Flow<Resource<Boolean>>
    suspend fun getSession(): Session?
    suspend fun isLogginedUser(): Boolean
    suspend fun logout(): Boolean

}