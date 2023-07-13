package com.example.core.domain.model

data class User(
    val id: String = "",
//    val fullname: String,
    val username: String,
    val email: String,
    val role: String = "user"
)
