package br.com.casalmoney.app.authenticated.repository

import br.com.casalmoney.app.authenticated.repository.local.database.HelpDAO
import br.com.casalmoney.app.authenticated.repository.service.BaseService
import br.com.casalmoney.app.authenticated.repository.service.HelpService
import br.com.casalmoney.app.unauthenticated.domain.Message
import br.com.casalmoney.app.unauthenticated.domain.MessageResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import javax.inject.Inject

interface HelpRepositoryInterface {
    @FormUrlEncoded
    @POST("message/text/send/")
    @Headers("content-type: application/x-www-form-urlencoded;charset=UTF-8")
    fun sendTextMessage(
        @Field("text") text: String,
        @Field("email") email: String,
        @Field("sessionId") sessionId: String): Call<MessageResult>

}

class HelpRepository @Inject constructor(
    private val helpService: HelpService,
    private val helpDAO: HelpDAO
): BaseService() {

    private val service = retrofit.create(HelpRepositoryInterface::class.java)

    fun sentTextMessage(message: Message, email: String,
                        sessionId: String, callback: Callback<MessageResult>) {

        service.sendTextMessage(message.text, email, sessionId).enqueue(callback)
    }

    fun getPreviousMessages(): Single<List<Message>> {
        return helpDAO.getPreviousMessages()
            .map { messages ->
                val arr = mutableListOf<Message>()
                messages.map {message ->
                    arr.add(Message(
                        isUser = message.isUser,
                        text = message.text,
                        time = message.time
                    ))
                }
                arr.toList()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

