package br.com.casalmoney.app.unauthenticated.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.casalmoney.app.R
import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.LoginException
import br.com.casalmoney.app.unauthenticated.interactor.LoginInteractor
import io.reactivex.rxjava3.disposables.CompositeDisposable

class LoginViewModel (val app: Application) : AndroidViewModel(app) {

    private val interector = LoginInteractor()
    private val compositeDisposable = CompositeDisposable()

    private val mLogin = MutableLiveData<LoginException?>()
    val responseLogin: LiveData<LoginException?> = mLogin
    val user = MutableLiveData(User())

    fun login() {
        if(!inputsRolesIsOk()) return

        val observerLogin = interector.login(user.value!!)
        observerLogin.subscribe { result ->
            mLogin.value = result
        }
    }

    private fun inputsRolesIsOk() : Boolean {
        if(TextUtils.isEmpty(user.value?.email)) {
            mLogin.value =  LoginException(message = app.getString(R.string.email_required), code = 1)
            return false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user.value?.email).matches()) {
            mLogin.value =  LoginException(message = app.getString(R.string.email_not_valid), code = 2)
            return false
        }

        if(TextUtils.isEmpty(user.value?.password)) {
            mLogin.value =  LoginException(message = app.getString(R.string.password_required), code = 3)
            return false
        }

        return true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
