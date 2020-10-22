package br.com.casalmoney.app.authenticated.repository.local.database

import androidx.room.*
import br.com.casalmoney.app.authenticated.repository.local.entity.TransactionEntity
import io.reactivex.Single

@Dao
interface HomeDAO {
    @Transaction
    @Query("Select * from transactions")
    fun getTransactions(): Single<List<TransactionEntity>>

    @Insert
    fun addTransaction(character: TransactionEntity)

    @Insert
    fun addTransactions(episodes: List<TransactionEntity>)

    @Update
    fun updateTransaction(transaction: TransactionEntity)
}