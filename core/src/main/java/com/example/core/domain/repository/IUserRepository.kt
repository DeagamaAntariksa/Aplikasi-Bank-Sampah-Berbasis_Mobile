package com.example.core.domain.repository

import com.example.core.data.Resource
import com.example.core.domain.model.Session
import com.example.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun login(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun register(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun insertUser(user: User): Flow<Resource<Boolean>>
    suspend fun getUser(userId: String? = null): Flow<Resource<User>>
    suspend fun isLogginedUser(): Boolean
    suspend fun isVerifiedEmail(): Boolean
    suspend fun saveSession(session: Session): Boolean
    fun getSession(): Session?
    suspend fun sendEmailVerification()
    suspend fun signOut(): Boolean


}