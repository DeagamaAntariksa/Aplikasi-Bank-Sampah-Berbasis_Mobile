package com.example.core.data.source.remote

import com.example.core.data.source.remote.response.ApiResponse
import com.example.core.data.source.remote.response.UserResponse
import com.example.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserDatasource {
    suspend fun registerUser(email: String, password: String): Flow<ApiResponse<Boolean>>
    suspend fun loginUser(email: String, password: String): Flow<ApiResponse<Boolean>>
    suspend fun insertUser(user: User): Flow<ApiResponse<Boolean>>
    suspend fun getUser(userId: String?): Flow<ApiResponse<UserResponse>>
    suspend fun isLogginedUser(): Boolean
    suspend fun isVerifiedEmail(): Boolean
    suspend fun sendEmailVerification()
    suspend fun signOut(): Boolean
}