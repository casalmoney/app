package br.com.casalmoney.app.unauthenticated.interactor

import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.LoginException
import br.com.casalmoney.app.unauthenticated.repository.LoginRepository
import io.reactivex.rxjava3.core.Observable

class LoginInteractor {

    private val repo = LoginRepository()

    fun login(user: User) : Observable<LoginException?> {
        return repo.login(user)
    }
}