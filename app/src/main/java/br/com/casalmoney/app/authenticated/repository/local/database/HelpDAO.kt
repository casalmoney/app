package br.com.casalmoney.app.authenticated.repository.local.database

import android.os.Message
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import br.com.casalmoney.app.authenticated.repository.local.entity.MessageEntity
import io.reactivex.Single

@Dao
interface HelpDAO {

    @Transaction
    @Query("Select * from messages")
    fun getPreviousMessages(): Single<List<MessageEntity>>

    @Insert
    fun addMessage(message: MessageEntity)
}