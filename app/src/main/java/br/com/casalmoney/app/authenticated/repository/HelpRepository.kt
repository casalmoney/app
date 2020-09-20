package br.com.casalmoney.app.authenticated.repository

import android.app.Application
import android.content.Context
import br.com.casalmoney.app.unauthenticated.domain.Message
import br.com.casalmoney.app.unauthenticated.domain.MessageResult
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import java.util.*

interface HelpRepositoryInterface {

    @POST("message/text/send")
    @Headers("Content-Type: application/json")
    fun sendTextMessage(@Body request: Message): Call<MessageResult>

}

class HelpRepository(): BaseService() {

    private val service = retrofit.create(HelpRepositoryInterface::class.java)

    fun sentTextMessage(message: Message, email: String,
                        sessionId: String, callback: Callback<MessageResult>) {
        service.sendTextMessage(message).enqueue(callback)
    }
}

