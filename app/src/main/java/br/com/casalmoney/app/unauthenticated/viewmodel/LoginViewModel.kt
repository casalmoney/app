package br.com.casalmoney.app.unauthenticated.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.casalmoney.app.R
import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.LoginException
import br.com.casalmoney.app.unauthenticated.interactor.LoginInteractor
import kotlinx.coroutines.launch

class LoginViewModel (val app: Application) : AndroidViewModel(app) {

    private val interector = LoginInteractor()

    private val mLogin = MutableLiveData<Pair<Boolean, LoginException?>>()
    val responseLogin: LiveData<Pair<Boolean, LoginException?>> = mLogin
    val user = MutableLiveData(User())

    fun login ()  {
        if(!inputsRolesIsOk()) {
            return
        }

        viewModelScope.launch {
            val data = interector.login(user.value!!)

            if (!data.second?.message.isNullOrEmpty() || !data.second?.message.isNullOrEmpty()) {
                mLogin.value = Pair(false, LoginException(message = data.second?.message, code = data.second?.code))
            } else {
                mLogin.value = Pair(true, null)
            }
        }

    }

    private fun inputsRolesIsOk() : Boolean {
        if(TextUtils.isEmpty(user.value?.email)) {
            mLogin.value =  Pair(false, LoginException(message = app.getString(R.string.email_required), code = 1))
            return false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user.value?.email).matches()) {
            mLogin.value =  Pair(false, LoginException(message = app.getString(R.string.email_not_valid), code = 2))
            return false
        }

        if(TextUtils.isEmpty(user.value?.password)) {
            mLogin.value =  Pair(false, LoginException(message = app.getString(R.string.password_required), code = 3))
            return false
        }

        return true
    }

}
