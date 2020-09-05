package br.com.casalmoney.app.unauthenticated.interactor

import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.SignupException
import br.com.casalmoney.app.unauthenticated.repository.RegisterRepository
import com.google.firebase.auth.AuthResult


class RegisterInteractor {

    private val repo = RegisterRepository()

    suspend  fun register(user: User): Pair<AuthResult?, SignupException?> {
        return repo.register(user)
    }
}