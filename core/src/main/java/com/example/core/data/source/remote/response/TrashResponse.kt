package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TrashResponse(
    @field:SerializedName("id")
    val id: String? = "",
    @field:SerializedName("idClient")
    val idClient: String? = "",
    @field:SerializedName("name")
    val name: String? = "",
    @field:SerializedName("category")
    val category: String? = "",
    @field:SerializedName("weight")
    val weight: Double? = 0.0,
    @field:SerializedName("price")
    val price: String? = "0",
    @field:SerializedName("address")
    val address: String? = "",
    @field:SerializedName("datePickUp")
    val datePickUp: String? = "",
    @field:SerializedName("statusPickUp")
    val statusPickUp: Boolean? = false
)
