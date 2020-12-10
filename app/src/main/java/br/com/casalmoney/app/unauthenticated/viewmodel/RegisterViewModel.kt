package br.com.casalmoney.app.unauthenticated.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.interactor.HelpInteractor
import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.SignupException
import br.com.casalmoney.app.unauthenticated.interactor.RegisterInteractor
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(
    val app: Application,
    private val interactor: RegisterInteractor) : AndroidViewModel(app) {

    private val mCreateUser = MutableLiveData<SignupException?>()
    val responseCreateUser: LiveData<SignupException?> = mCreateUser
    val user = MutableLiveData(User())
    private var disposable: Disposable? = null

    fun register() {
        if(!inputsRolesIsOk()) {
            return
        }

        val obeserver = interactor.register(user.value!!)
        disposable = obeserver.subscribe { result ->
            mCreateUser.value = result
        }


    }

    fun saveInfos() {
        disposable = interactor.saveInfos(user.value!!).subscribe{list, error ->

        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    private fun inputsRolesIsOk() : Boolean{
        if(TextUtils.isEmpty(user.value?.email)) {
            mCreateUser.value =  SignupException(message = app.getString(R.string.email_required), code = 1)
            return false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user.value?.email).matches()) {
            mCreateUser.value = SignupException(message = app.getString(R.string.email_not_valid), code = 2)
            return false
        }

        if(TextUtils.isEmpty(user.value?.password)) {
            mCreateUser.value =  SignupException(message = app.getString(R.string.password_required), code = 3)
            return false
        }

        if(TextUtils.isEmpty(user.value?.name)) {
            mCreateUser.value =  SignupException(message = app.getString(R.string.name_required), code = 4)
            return false
        }

        return true
    }
}
