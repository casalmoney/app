package br.com.casalmoney.app.authenticated.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val name: String,
    val initials: String
)