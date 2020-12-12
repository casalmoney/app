package br.com.casalmoney.app.authenticated.repository

import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.domain.UserDetails
import br.com.casalmoney.app.authenticated.repository.local.database.CasalmoneyDatabase
import br.com.casalmoney.app.authenticated.repository.local.database.HomeDAO
import br.com.casalmoney.app.authenticated.repository.local.database.UserDao
import br.com.casalmoney.app.authenticated.repository.local.entity.LocationTypeConverter
import br.com.casalmoney.app.authenticated.repository.local.entity.TransactionEntity
import br.com.casalmoney.app.authenticated.repository.local.entity.UserEntity
import br.com.casalmoney.app.authenticated.repository.service.HomeService
import br.com.casalmoney.app.authenticated.repository.service.UserInfoService
import br.com.casalmoney.app.unauthenticated.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService,
    private val userInfoService: UserInfoService,
    private val homeDAO: HomeDAO,
    private val userDAO: UserDao,
    ) {

    val mAuth = FirebaseAuth.getInstance()


    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    fun getTransactions(page: Int): Single<List<Transaction>> {
        return getLocalTransactions()
    }

    private fun getLocalTransactions(): Single<List<Transaction>> {
        return homeDAO.getTransactions()
            .map { transactions ->
                val arr = mutableListOf<Transaction>()
                transactions.map {it ->
                    arr.add(
                        Transaction(
                            it.id,
                            it.title,
                            it.explanation,
                            it.amount.toString(),
                            it.date,
                        LocationTypeConverter().stringToLocation(it.location)))
                }
                arr.toList()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveTransactions(transaction: TransactionEntity) {
        homeDAO.addTransaction(transaction)
    }

    fun updateTransaction(transaction: TransactionEntity?) {
        transaction?.let {
            homeDAO.updateTransaction(it)
        }
    }

    fun getUserInfo(): Single<UserDetails> {
        return userInfoService.getUser(mAuth.currentUser!!.uid).subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread())
    }

    fun getUserInfoLocale(): Single<List<UserEntity>> {
        return userDAO.getUser().subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread())
    }

    fun addUser(user: UserEntity) {
        userDAO.addUser(user)
    }


//    fun saveTransactions(transaction: Transaction) : Completable = Completable.fromCallable {
//        homeDAO.addTransaction(TransactionEntity(title = transaction.title, explanation = transaction.explanation, amount = transaction.amount, date = transaction.date))
//    }
}
