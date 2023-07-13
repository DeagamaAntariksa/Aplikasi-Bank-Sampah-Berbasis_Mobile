package com.example.core.data.source

import android.util.Log
import com.example.core.data.Resource
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.IUserDatasource
import com.example.core.data.source.remote.response.ApiResponse
import com.example.core.domain.model.Session
import com.example.core.domain.model.User
import com.example.core.domain.repository.IUserRepository
import com.example.core.utils.ObjectMapper.mapUserResponseToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val localDataSource: LocalDataSource,
    private val userFirebaseDatasource: IUserDatasource
): IUserRepository {
    override suspend fun register(email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        userFirebaseDatasource.registerUser(email, password).collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Error("Gagal register akun"))
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(true))
            }
        }
    }

    override suspend fun login(email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        userFirebaseDatasource.loginUser(email, password).collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Error("Gagal login"))
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(it.data))
            }
        }
    }

    override suspend fun insertUser(user: User): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        userFirebaseDatasource.insertUser(user).collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Success(false))
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(true))
            }
        }
    }

    override suspend fun getUser(userId: String?): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        userFirebaseDatasource.getUser(userId).collect {
            when (it) {
                is ApiResponse.Empty -> emit(Resource.Error("Gagal mendapatkan data user"))
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Success -> emit(Resource.Success(mapUserResponseToDomain(it.data)))
            }
        }
    }

    override suspend fun isLogginedUser(): Boolean = userFirebaseDatasource.isLogginedUser()

    override suspend fun isVerifiedEmail(): Boolean = userFirebaseDatasource.isVerifiedEmail()

    override suspend fun saveSession(session: Session): Boolean {
        return try {
            localDataSource.saveSession(session)
            true
        } catch (e: Exception){
            Log.e("UserRepository", e.message.toString())
            false
        }
    }

    override fun getSession(): Session? = localDataSource.getSession()

    override suspend fun sendEmailVerification() {
        userFirebaseDatasource.sendEmailVerification()
    }

    override suspend fun signOut(): Boolean = userFirebaseDatasource.signOut() && localDataSource.clearSession()

}