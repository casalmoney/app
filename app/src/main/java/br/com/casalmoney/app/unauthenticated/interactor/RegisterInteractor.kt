package br.com.casalmoney.app.unauthenticated.interactor

import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.SignupException
import br.com.casalmoney.app.unauthenticated.repository.RegisterRepository
import io.reactivex.rxjava3.core.Observable

class RegisterInteractor {

    private val repo = RegisterRepository()

    fun register(user: User) : Observable<SignupException?> {
        return repo.register(user)
    }
}