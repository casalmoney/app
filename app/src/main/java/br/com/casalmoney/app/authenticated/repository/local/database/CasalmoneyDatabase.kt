package br.com.casalmoney.app.authenticated.repository.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.casalmoney.app.authenticated.repository.local.entity.MessageEntity
import br.com.casalmoney.app.authenticated.repository.local.entity.TransactionEntity
import br.com.casalmoney.app.authenticated.repository.local.entity.UserEntity

@Database(entities = [TransactionEntity::class, MessageEntity::class, UserEntity::class], version = 3, exportSchema = false)
abstract class CasalmoneyDatabase : RoomDatabase() {

    abstract fun homeDAO(): HomeDAO
    abstract fun helpDAO(): HelpDAO
    abstract fun chatDAO(): ChatDAO
    abstract fun userDao(): UserDao
}