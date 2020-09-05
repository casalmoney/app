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
import br.com.casalmoney.app.unauthenticated.exception.SignupException
import br.com.casalmoney.app.unauthenticated.interactor.RegisterInteractor
import kotlinx.coroutines.launch

class RegisterViewModel(val app: Application) : AndroidViewModel(app) {

    private val interactor = RegisterInteractor()

    private val mCreateUser = MutableLiveData<Pair<Boolean, SignupException?>>()
    val responseCreateUser: LiveData<Pair<Boolean, SignupException?>> = mCreateUser
    val user = MutableLiveData(User())

    fun register() {
        if(!inputsRolesIsOk()) {
            return
        }


        viewModelScope.launch {
            val resultRegister = interactor.register(user.value!!)

            if (!resultRegister.second?.message.isNullOrEmpty() || !resultRegister.second?.message.isNullOrEmpty()) {
                mCreateUser.value = Pair(false, SignupException(message = resultRegister.second?.message, code = resultRegister.second?.code))
            } else {
                mCreateUser.value = Pair(true, null)
            }
        }

    }

    private fun inputsRolesIsOk() : Boolean{
        if(TextUtils.isEmpty(user.value?.email)) {
            mCreateUser.value =  Pair(false, SignupException(message = app.getString(R.string.email_required), code = 1))
            return false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user.value?.email).matches()) {
            mCreateUser.value =  Pair(false, SignupException(message = app.getString(R.string.email_not_valid), code = 2))
            return false
        }

        if(TextUtils.isEmpty(user.value?.password)) {
            mCreateUser.value =  Pair(false, SignupException(message = app.getString(R.string.password_required), code = 3))
            return false
        }

        if(TextUtils.isEmpty(user.value?.name)) {
            mCreateUser.value =  Pair(false, SignupException(message = app.getString(R.string.name_required), code = 4))
            return false
        }

        return true
    }
}
