package br.com.casalmoney.app.unauthenticated.interactor

import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.LoginException
import br.com.casalmoney.app.unauthenticated.repository.LoginRepository
import com.google.firebase.auth.AuthResult

class LoginInteractor {

    private val repo = LoginRepository()

    suspend fun login (user: User): Pair<AuthResult?, LoginException?> {
        return repo.login(user)
    }
}