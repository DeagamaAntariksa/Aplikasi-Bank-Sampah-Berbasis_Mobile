package com.example.core.data.source.remote.datasource

import android.util.Log
import com.example.core.data.source.remote.IUserDatasource
import com.example.core.data.source.remote.response.ApiResponse
import com.example.core.data.source.remote.response.UserResponse
import com.example.core.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserFirebaseDatasource(
    private val mAuth: FirebaseAuth,
    private val mFirestore: FirebaseFirestore,
): IUserDatasource {

    override suspend fun registerUser(email: String, password: String): Flow<ApiResponse<Boolean>> = callbackFlow {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(ApiResponse.Success(true))
            } else {
                trySend(ApiResponse.Error(task.exception?.message ?: "Gagal menambahkan user"))
            }
        }
        awaitClose { close() }
    }

    override suspend fun loginUser(email: String, password: String): Flow<ApiResponse<Boolean>> = callbackFlow {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(ApiResponse.Success(true))
            } else {
                trySend(ApiResponse.Error(task.exception?.message ?: "Gagal login"))
            }
        }
        awaitClose { close() }
    }

    override suspend fun insertUser(user: User): Flow<ApiResponse<Boolean>> = callbackFlow {
        try {
            val userWithId = mAuth.currentUser?.let { user.copy(id = it.uid) }

            userWithId?.let { mFirestore.collection(USERS_PATH).document(it.id).set(userWithId) }
            trySend(ApiResponse.Success(true))
        } catch (e: Exception) {
            trySend(ApiResponse.Error(e.message.toString()))
        }
        awaitClose { close() }
    }

    override suspend fun getUser(userId: String?): Flow<ApiResponse<UserResponse>> = callbackFlow {
        val id = userId ?: mAuth.uid
        mFirestore.collection(USERS_PATH).document(id.toString()).get()
            .addOnSuccessListener { snapshot ->
                val data = snapshot.toObject(UserResponse::class.java)

                if (data != null) trySend(ApiResponse.Success(data))
                else trySend(ApiResponse.Empty)
            }
            .addOnFailureListener { error ->
                trySend(ApiResponse.Error(error.message.toString()))
            }
        awaitClose { close() }
    }

    override suspend fun isLogginedUser(): Boolean = mAuth.currentUser != null

    override suspend fun isVerifiedEmail(): Boolean = mAuth.currentUser?.isEmailVerified ?: false

    override suspend fun sendEmailVerification() {
        mAuth.currentUser?.sendEmailVerification()
    }

    override suspend fun signOut(): Boolean = try {
        mAuth.signOut()
        true
    } catch (e: Exception) {
        Log.e("Error RemoteDataSource", e.message.toString())
        false
    }
    companion object {
        private const val USERS_PATH = "users"
    }
}