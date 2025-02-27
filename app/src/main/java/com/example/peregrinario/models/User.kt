package com.example.peregrinario.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,  // ID do Firebase
    val name: String,
    val email: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val isLoggedIn: Boolean,
    val profilePicUrl: String?,
)