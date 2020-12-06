package br.com.casalmoney.app.authenticated.repository

import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.repository.local.database.CasalmoneyDatabase
import br.com.casalmoney.app.authenticated.repository.local.database.HomeDAO
import br.com.casalmoney.app.authenticated.repository.local.entity.TransactionEntity
import br.com.casalmoney.app.authenticated.repository.service.HomeService
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService,
    private val homeDAO: HomeDAO,
) {

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    fun getTransactions(page: Int): Single<List<Transaction>> {
        return getLocalTransactions()
    }

    private fun getLocalTransactions(): Single<List<Transaction>> {
        return homeDAO.getTransactions()
            .map { transactios ->
                val arr = mutableListOf<Transaction>()
                transactios.map {it ->
                    arr.add(Transaction(it.title, it.explanation, it.amount.toString(), it.date))
                }
                //mock stuff
                arr.add(
                    Transaction(
                        "Compra online",
                        "Descrição do gasto",
                        "10,00",
                        "1343805819061"
                    )
                )
                arr.add(
                    Transaction(
                        "Compra loja fisica",
                        "Descrição do gasto",
                        "10,00",
                        "1343805819061"
                    )
                )
                arr.add(
                    Transaction(
                        "Compra débito",
                        "Descrição do gasto",
                        "10,00",
                        "1343805819061"
                    )
                )
                arr.toList()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveTransactions(transaction: TransactionEntity) {
        homeDAO.addTransaction(transaction)
    }

//    fun saveTransactions(transaction: Transaction) : Completable = Completable.fromCallable {
//        homeDAO.addTransaction(TransactionEntity(title = transaction.title, explanation = transaction.explanation, amount = transaction.amount, date = transaction.date))
//    }
}
