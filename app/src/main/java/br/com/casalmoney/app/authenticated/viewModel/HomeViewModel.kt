package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.interactor.HomeInteractor
import br.com.casalmoney.app.authenticated.view.adapters.TransactionAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

open class HomeViewModel(val app: Application) : AndroidViewModel(app) {

    private val homeInteractor = HomeInteractor()
    val currentUser = FirebaseAuth.getInstance().currentUser

    val transactionList = ArrayList<Transaction>()
    lateinit var adapter: TransactionAdapter

    fun logout() {
        homeInteractor.logout()
    }

    val nameInitials: String
        get() {
            val initials = currentUser?.displayName.toString()
                .split(' ')
                .mapNotNull { it.firstOrNull()?.toString() }
                .reduce { acc, s -> acc + s }
            return if (initials.length == 1) {
                initials + initials
            } else {
                initials.substring(0, 2)
            }
        }

    fun transactions(): ArrayList<Transaction> {
        transactionList.add(Transaction("Descrição do gasto", "10,00", "15 de outubro"))
        transactionList.add(       Transaction("Descrição do gasto", "10,00", "15 de outubro"))
        transactionList.add(Transaction("Descrição do gasto", "10,00", "15 de outubro"))
        return transactionList
    }
}