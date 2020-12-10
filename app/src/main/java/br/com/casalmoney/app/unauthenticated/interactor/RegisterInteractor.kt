package br.com.casalmoney.app.unauthenticated.interactor

import br.com.casalmoney.app.authenticated.domain.News
import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.domain.UserInfo
import br.com.casalmoney.app.unauthenticated.exception.SignupException
import br.com.casalmoney.app.unauthenticated.repository.RegisterRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

open class RegisterInteractor @Inject constructor(
    private val repo: RegisterRepository
) {
    fun register(user: User) : Observable<SignupException?> {
        return repo.register(user)
    }

    fun saveInfos(user: User): Single<UserInfo> {
        return repo.saveInfos(user)
    }
}