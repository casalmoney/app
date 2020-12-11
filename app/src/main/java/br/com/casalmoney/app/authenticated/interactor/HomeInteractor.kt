package br.com.casalmoney.app.authenticated.interactor

import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.domain.UserDetails
import br.com.casalmoney.app.authenticated.repository.HomeRepository
import br.com.casalmoney.app.authenticated.repository.local.entity.TransactionEntity
import br.com.casalmoney.app.authenticated.repository.local.entity.UserEntity
import br.com.casalmoney.app.unauthenticated.domain.User
import com.google.firebase.auth.UserInfo
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

    fun updateTransaction(transaction: TransactionEntity?) {
        return repository.updateTransaction(transaction)
    }

    fun saveTransaction(transaction: TransactionEntity) {
        repository.saveTransactions(transaction)
    }

    fun getUserInfo(): Single<UserDetails> {
       return repository.getUserInfo()
    }

    fun getUserInfoLocale(): Single<List<UserEntity>> {
        return repository.getUserInfoLocale()
    }

    fun addUser(user: UserEntity) {
        repository.addUser(user)
    }
}