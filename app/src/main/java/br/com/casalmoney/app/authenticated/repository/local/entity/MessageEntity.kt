package br.com.casalmoney.app.authenticated.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val isUser: Boolean,
    val text: String,
    var time: String
)