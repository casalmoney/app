package br.com.casalmoney.app.authenticated.interactor

import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.repository.HomeRepository
import io.reactivex.Single
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val repository: HomeRepository
) {

    fun logout() {
        repository.logout()
    }

    fun getTransactions(page: Int): Single<List<Transaction>> {
        return repository.getTransactions(page)
    }
}