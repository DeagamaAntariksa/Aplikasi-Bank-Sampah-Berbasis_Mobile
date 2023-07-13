package com.example.core.utils

import com.example.core.data.source.remote.response.TrashResponse
import com.example.core.data.source.remote.response.UserResponse
import com.example.core.domain.model.Session
import com.example.core.domain.model.Trash
import com.example.core.domain.model.User

object ObjectMapper {
    fun mapUserResponseToDomain(userResponse: UserResponse): User =
        User(
            id = userResponse.id ?: "",
            username = userResponse.username ?: "",
            email = userResponse.email ?: "",
            role = userResponse.role ?: "user"
        )

    fun mapUserToSession(user: User): Session =
        Session(
            id = user.id,
            username = user.username,
            role = user.role
        )

    fun mapTrashResponseToTrash(trashResponse: TrashResponse): Trash =
        Trash(
            id = trashResponse.id ?: "",
            idClient = trashResponse.idClient ?: "",
            name = trashResponse.name ?: "",
            category = trashResponse.category ?: "",
            weight = trashResponse.weight ?: 0.0,
            price = trashResponse.price ?: "0",
            address = trashResponse.address ?: "",
            datePickUp = trashResponse.datePickUp ?: "",
            statusPickUp = trashResponse.statusPickUp ?: false
        )
}