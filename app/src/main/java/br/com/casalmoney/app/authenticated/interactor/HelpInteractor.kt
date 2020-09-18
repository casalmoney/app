package br.com.casalmoney.app.authenticated.interactor

import android.app.Application
import android.content.Context
import android.widget.Toast
import br.com.casalmoney.app.authenticated.repository.HelpRepository
import br.com.casalmoney.app.unauthenticated.domain.Message
import br.com.casalmoney.app.unauthenticated.domain.MessageResult
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

open class HelpInteractor () {
    private val repository = HelpRepository()

    fun sendMessage(message: String) {
        val request = Message(false, message, Date().toString())
        val email = FirebaseAuth.getInstance().currentUser?.email
        val uuid = UUID.randomUUID().toString()

        repository.sentTextMessage(request, email!!, uuid, object : Callback<MessageResult> {
            override fun onResponse(call: Call<MessageResult>, response: Response<MessageResult>) {

            }

            override fun onFailure(call: Call<MessageResult>, t: Throwable) {

            }
        })
    }

    fun receiveMessage(message: Message) {

    }
}