package br.com.casalmoney.app.authenticated.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.interactor.ChatInteractor
import br.com.casalmoney.app.authenticated.interactor.IChatInteractor
import br.com.casalmoney.app.unauthenticated.domain.Message
import br.com.casalmoney.app.unauthenticated.domain.MessageResult
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel @ViewModelInject constructor(
    val app: Application,
    val chatInteractor: ChatInteractor
) : AndroidViewModel(app), IChatInteractor {

    val currentMessage = MutableLiveData<String>()
    val messageList = MutableLiveData<ArrayList<Message>>()
    private var arrMessages = ArrayList<Message>()

    var isLoading = PublishSubject.create<Boolean>()
    var disposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun getPreviousMessages() {
        isLoading.onNext(true)
        disposable = chatInteractor.getPreviousMessages().subscribe { messages, error ->
            isLoading.onNext(false)
            if (error != null && messages != null) {
                arrMessages = messages as ArrayList<Message>
                messageList.value = arrMessages
            }
        }
    }

    fun sendMessage() {
        val message = Message(true,
            currentMessage.value.toString(), getCurrentTimeFormatted())

        chatInteractor.sendMessage(message, this)
        arrMessages.add(message)
        messageList.value = arrMessages

        currentMessage.value = ""
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTimeFormatted() : String {
        val formatter = SimpleDateFormat("HH:mm")
        return formatter.format(Date())
    }

    override fun onSuccess(result: MessageResult?) {

        result?.message?.let {
            Message(false, text = it, getCurrentTimeFormatted())
        }?.let {
            arrMessages.add(it)
            messageList.value = arrMessages
        }
    }

    override fun onError(t: Throwable) {
        val errorMessage = Message(false,
            text = app.getString(R.string.generic_error_toast),
            getCurrentTimeFormatted())

        arrMessages.add(errorMessage)
        messageList.value = arrMessages
    }

}