package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.Session
import com.example.core.domain.model.User
import com.example.core.domain.repository.IUserRepository
import com.example.core.utils.ObjectMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserInteractor(private val userRepository: IUserRepository): UserUsecase {
    override suspend fun login(email: String, password: String): Flow<Resource<Boolean>> = flow {
        userRepository.login(email, password).collect {
            when(it) {
                is Resource.Success -> {
                    if (!userRepository.isVerifiedEmail()) {
                        emit(Resource.Error("Email ini belum melakukan verifikasi, silahkan verifikasi terlebih dahulu"))
                    } else {
                        userRepository.getUser().collect{ result ->
                            when(result){
                                is Resource.Error -> emit(Resource.Error(result.message.toString()))
                                is Resource.Loading -> emit(Resource.Loading())
                                is Resource.Success -> {
                                    result.data?.let { user -> userRepository.saveSession(
                                        ObjectMapper.mapUserToSession(user)) }
                                    emit(Resource.Success(true))
                                }
                            }
                        }
                    }
                }
                else -> emit(it)
            }
        }
    }

    override suspend fun register(user: User, password: String): Flow<Resource<Boolean>> = flow {
        userRepository.register(user.email, password).collect {
            when(it){
                is Resource.Success -> {
                    userRepository.sendEmailVerification()

                    userRepository.insertUser(user).collect { result ->
                        emit(result)
                    }
                }

                else -> emit(it)
            }
        }
    }

    override suspend fun getSession(): Session? = userRepository.getSession()

    override suspend fun isLogginedUser(): Boolean = userRepository.isLogginedUser() && userRepository.isVerifiedEmail()

    override suspend fun logout(): Boolean  = userRepository.signOut()


}