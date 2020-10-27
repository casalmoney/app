package br.com.casalmoney.app.authenticated.interactor

import br.com.casalmoney.app.authenticated.repository.HelpRepository
import br.com.casalmoney.app.unauthenticated.domain.Message
import br.com.casalmoney.app.unauthenticated.domain.MessageResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

open class HelpInteractor @Inject constructor(
    private val repository: HelpRepository
) {

    fun sendMessage(message: Message, callback: IHelpInteractor) {
        val email = FirebaseAuth.getInstance().currentUser?.email
        val uuid = UUID.randomUUID().toString()

        repository.sentTextMessage(message, email!!, uuid, object : Callback<MessageResult> {
            override fun onResponse(call: Call<MessageResult>, response: Response<MessageResult>) {
                callback.onSuccess(response.body())
            }

            override fun onFailure(call: Call<MessageResult>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun getPreviousMessages(): Single<List<Message>> {
        return repository.getPreviousMessages()
    }
}

interface IHelpInteractor {
    fun onSuccess(result: MessageResult?)
    fun onError(t: Throwable)
}