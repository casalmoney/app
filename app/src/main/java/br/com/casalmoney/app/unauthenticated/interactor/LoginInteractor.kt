package br.com.casalmoney.app.unauthenticated.interactor

import br.com.casalmoney.app.Audit
import br.com.casalmoney.app.di.CasalMoneyModule
import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.LoginException
import br.com.casalmoney.app.unauthenticated.repository.LoginRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    @CasalMoneyModule.BusinessLogger private val audit: Audit
) {

    private val repo = LoginRepository()

    fun login(user: User) : Observable<LoginException?> {
        audit.auditString("Audit interactor")
        return repo.login(user)
    }
}