package br.com.casalmoney.app.authenticated.repository

import br.com.casalmoney.app.authenticated.repository.service.BaseService
import br.com.casalmoney.app.unauthenticated.domain.Message
import br.com.casalmoney.app.unauthenticated.domain.MessageResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface HelpRepositoryInterface {
    @FormUrlEncoded
    @POST("message/text/send/")
    @Headers("content-type: application/x-www-form-urlencoded;charset=UTF-8")
    fun sendTextMessage(
        @Field("text") text: String,
        @Field("email") email: String,
        @Field("sessionId") sessionId: String): Call<MessageResult>

}

class HelpRepository(): BaseService() {

    private val service = retrofit.create(HelpRepositoryInterface::class.java)

    fun sentTextMessage(message: Message, email: String,
                        sessionId: String, callback: Callback<MessageResult>) {

        service.sendTextMessage(message.text, email, sessionId).enqueue(callback)
    }

}

