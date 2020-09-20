package br.com.casalmoney.app.authenticated.interactor

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.casalmoney.app.authenticated.repository.HelpRepository
import br.com.casalmoney.app.unauthenticated.domain.Message
import br.com.casalmoney.app.unauthenticated.domain.MessageResult
import br.com.casalmoney.app.unauthenticated.exception.LoginException
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

open class HelpInteractor () {
    private val repository = HelpRepository()
    val gson = Gson()

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
}

open interface IHelpInteractor {
    fun onSuccess(result: MessageResult?)
    fun onError(t: Throwable)
}