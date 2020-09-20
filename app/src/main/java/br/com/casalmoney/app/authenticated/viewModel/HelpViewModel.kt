package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.interactor.HelpInteractor
import br.com.casalmoney.app.authenticated.interactor.IHelpInteractor
import br.com.casalmoney.app.unauthenticated.domain.Message
import br.com.casalmoney.app.unauthenticated.domain.MessageResult
import br.com.casalmoney.app.unauthenticated.view.adapter.ChatAdapter
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class HelpViewModel(val app: Application) : AndroidViewModel(app), IHelpInteractor {

    private val interactor = HelpInteractor()

    val currentMessage = MutableLiveData<String>()

    val messages = ArrayList<Message>()
    lateinit var adapter: ChatAdapter

    fun sendMessage() {

        val message = Message(true,
            currentMessage.value.toString(), getCurrentTimeFormatted())

        interactor.sendMessage(message, this)
        messages.add(message)

        adapter.notifyDataSetChanged()
        currentMessage.value = ""
    }

    fun getCurrentTimeFormatted() : String {
        val date = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return date.format(formatter)
    }

    override fun onSuccess(result: MessageResult?) {
        //converter o objeto
        //adicionar no array
        //atualizar a lista
        messages.add(Message(false,
            message = "", getCurrentTimeFormatted()))
        adapter.notifyDataSetChanged()
    }

    override fun onError(t: Throwable) {
        messages.add(Message(false,
            message = app.getString(R.string.generic_error_toast),
            getCurrentTimeFormatted()))
        adapter.notifyDataSetChanged()
    }
}