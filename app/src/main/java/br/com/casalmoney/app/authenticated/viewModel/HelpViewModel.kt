package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.casalmoney.app.authenticated.interactor.HelpInteractor
import br.com.casalmoney.app.unauthenticated.domain.Message
import java.util.*
import kotlin.collections.ArrayList

class HelpViewModel(val app: Application) : AndroidViewModel(app) {

    private val interactor = HelpInteractor()

    val currentMessage = MutableLiveData<String>()

    val messages = ArrayList<Message>()

    fun sendMessage() {
        interactor.sendMessage(currentMessage.toString())
        currentMessage.value = ""
    }

    fun displayRandomMessage() {
        Handler(Looper.getMainLooper()).postDelayed({
            messages.add(Message(messages.size % 2 == 0,
                UUID.randomUUID().toString(), Date().toString()))

        }, 3000)
    }
}