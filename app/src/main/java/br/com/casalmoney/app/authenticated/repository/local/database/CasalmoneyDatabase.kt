package br.com.casalmoney.app.authenticated.repository.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.casalmoney.app.authenticated.repository.local.entity.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class CasalmoneyDatabase : RoomDatabase() {

    abstract fun homeDAO(): HomeDAO
}