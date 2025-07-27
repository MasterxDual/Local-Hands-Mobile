package com.undef.localhandsbrambillafunes.data.entity

import androidx.room.Entity

@Entity(tableName = "SellerEntity")
data class Seller(
    val id: Int,
    val name: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val entrepreneurship: String,
    val address: String
)