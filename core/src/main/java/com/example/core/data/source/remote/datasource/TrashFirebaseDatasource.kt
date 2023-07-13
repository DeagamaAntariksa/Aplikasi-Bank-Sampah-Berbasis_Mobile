package com.example.core.data.source.remote.datasource

import android.util.Log
import com.example.core.data.source.remote.ITrashDatasource
import com.example.core.data.source.remote.response.ApiResponse
import com.example.core.data.source.remote.response.TrashResponse
import com.example.core.domain.model.Trash
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TrashFirebaseDatasource(private val mDbRef: DatabaseReference) : ITrashDatasource {
    override suspend fun createRequestPickupTrash(request: Trash): Flow<ApiResponse<Boolean>> =
        callbackFlow {
            try {
                val key = mDbRef.child(TRASH_PATH).child(request.idClient).push().key
                val trasnformedRequest = request.copy(id = key.toString())
                mDbRef.child(TRASH_PATH).child(request.idClient).child(key.toString())
                    .setValue(trasnformedRequest)
                trySend(ApiResponse.Success(true))
            } catch (e: Exception) {
                Log.e(this.javaClass.name, e.message.toString())
                trySend(ApiResponse.Error(e.message.toString()))
            }
            awaitClose { close() }
        }

    override suspend fun setStatusRequestTrash(
        userId: String,
        trashId: String,
        status: Boolean
    ): Flow<ApiResponse<Boolean>> =
        callbackFlow {
            try {
                mDbRef.child(TRASH_PATH).child(userId).child(trashId)
                    .child("statusPickUp").setValue(status)
                trySend(ApiResponse.Success(true))
            } catch (e: Exception) {
                Log.e(this.javaClass.name, e.message.toString())
                trySend(ApiResponse.Error(e.message.toString()))
            }

            awaitClose { close() }
        }

    override suspend fun getHistoryPickupTrash(userId: String): Flow<ApiResponse<List<TrashResponse>>> =
        callbackFlow {
            mDbRef.child(TRASH_PATH).child(userId).addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        try {
                            val listHistory = arrayListOf<TrashResponse>()
                            snapshot.children.forEach {
                                val history = it.getValue(TrashResponse::class.java)
                                if (history != null) {
                                    listHistory.add(history)
                                }
                            }

                            if (listHistory.isNotEmpty()) trySend(ApiResponse.Success(listHistory))
                            else trySend(ApiResponse.Empty)
                        } catch (e: Exception){
                            Log.e("DEBUG", e.message.toString())
                            trySend(ApiResponse.Error(e.message.toString()))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e(this.javaClass.name, error.message)
                        trySend(ApiResponse.Error(error.message))
                    }

                }
            )
            awaitClose { close() }
        }

    override suspend fun removeHistoryPickupTrash(
        userId: String,
        trashId: String
    ): Flow<ApiResponse<Boolean>> = callbackFlow {

        mDbRef.child(TRASH_PATH).child(userId).child(trashId)
            .setValue(null)
            .addOnFailureListener {
                Log.e(this.javaClass.name, it.message.toString())
                trySend(ApiResponse.Error(it.message.toString()))
            }.addOnSuccessListener {
                trySend(ApiResponse.Success(true))
            }

        awaitClose { close() }
    }

    override suspend fun getAllRequestPickupTrash(): Flow<ApiResponse<List<TrashResponse>>> =
        callbackFlow {

            mDbRef.child(TRASH_PATH).addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                       try {
                           val listHistory = arrayListOf<TrashResponse>()
                           snapshot.children.forEach { user ->
                               user.children.forEach { response ->
                                   val history = response.getValue(TrashResponse::class.java)
                                   if (history != null) {
                                       listHistory.add(history)
                                   }
                               }
                           }

                           if (listHistory.isNotEmpty()) trySend(ApiResponse.Success(listHistory))
                           else trySend(ApiResponse.Empty)
                       } catch (e: Exception){
                           Log.e("DEBUG", e.message.toString())
                           trySend(ApiResponse.Error(e.message.toString()))

                       }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e(this.javaClass.name, error.message)
                        trySend(ApiResponse.Error(error.message))
                    }

                }
            )

            awaitClose { close() }
        }

    companion object {
        const val TRASH_PATH = "trash"
    }
}