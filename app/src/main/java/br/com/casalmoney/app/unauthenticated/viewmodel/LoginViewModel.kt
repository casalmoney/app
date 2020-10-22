package br.com.casalmoney.app.unauthenticated.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.casalmoney.app.R
import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.LoginException
import br.com.casalmoney.app.unauthenticated.interactor.LoginInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

class LoginViewModel @ViewModelInject constructor (
    val app: Application,
    private val interactor: LoginInteractor
) : AndroidViewModel(app) {

    private val mLogin = MutableLiveData<LoginException?>()
    val responseLogin: LiveData<LoginException?> = mLogin
    val user = MutableLiveData(User())

    var isLoading: PublishSubject<Boolean> = PublishSubject.create()

    var disposable: Disposable? = null

    fun login() {
        if(!inputsRolesIsOk()) return

        val observerLogin = interactor.login(user.value!!)
        isLoading.onNext(true)
        disposable = observerLogin.subscribe { result ->
            isLoading.onNext(false)
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
        disposable?.dispose()
    }

}
