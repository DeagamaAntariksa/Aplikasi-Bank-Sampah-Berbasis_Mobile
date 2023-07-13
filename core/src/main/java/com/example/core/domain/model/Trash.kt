package com.example.core.domain.model

data class Trash(
    val id: String = "",
    val idClient: String = "",
    val name: String, //required
    val category: String, // required
    val weight: Double, // required
    val price: String, // required
    val address: String, // required
    val datePickUp: String, // required
    val statusPickUp: Boolean = false
)
