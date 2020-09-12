package br.com.casalmoney.app.unauthenticated.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.casalmoney.app.R
import br.com.casalmoney.app.unauthenticated.exception.LoginException
import br.com.casalmoney.app.unauthenticated.interactor.RecoverPassworrdInterector

class RecoverPasswordViewModel (val app: Application) : AndroidViewModel(app) {
    private val interactor = RecoverPassworrdInterector()

    private val mRecoverPassword = MutableLiveData<Exception?>()
    val responseRecover: LiveData<Exception?> = mRecoverPassword

    val email  = MutableLiveData<String>()


    fun recoverPassword() {
        if(TextUtils.isEmpty(email.value?.toString())) {
            mRecoverPassword.value =  Exception(app.getString(R.string.email_required))
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email.value?.toString()).matches()) {
            mRecoverPassword.value =  Exception(app.getString(R.string.email_not_valid))
            return
        }

        val observer = interactor.recover(email.value?.toString()!!)
        observer.subscribe { result ->
            mRecoverPassword.value = result
        }
    }
}
