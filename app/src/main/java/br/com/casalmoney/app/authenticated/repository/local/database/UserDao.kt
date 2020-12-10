package br.com.casalmoney.app.authenticated.repository.local.database

import androidx.room.*
import br.com.casalmoney.app.authenticated.repository.local.entity.UserEntity
import io.reactivex.Single

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM user")
    fun getUser(): Single<List<UserEntity>>

    @Insert
    fun addUser(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)

    @Query("DELETE FROM user")
    fun delete()
}