package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.interactor.HomeInteractor
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.*

open class HomeViewModel @ViewModelInject constructor(
    val app: Application,
    private val homeInteractor: HomeInteractor,
) : AndroidViewModel(app) {

    var isLoading: PublishSubject<Boolean> = PublishSubject.create()

    private val page = MutableLiveData(1)

    val currentUser = FirebaseAuth.getInstance().currentUser
    val transactionList = MutableLiveData<List<Transaction>>()

    var disposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun logout() {
        homeInteractor.logout()
    }

    val nameInitials: String
        get() {
//            val initials = currentUser?.displayName.toString()
//                .split(' ')
//                .mapNotNull { it.firstOrNull()?.toString() }
//                .reduce { acc, s -> acc + s }
//            return if (initials.length == 1) {
//                initials + initials
//            } else {
//                initials.substring(0, 2)
//            }
            return "Oi"
        }

    fun getTransactions() {
        isLoading.onNext(true)

        val param = page.value ?: 1
        disposable = homeInteractor.getTransactions(param).subscribe { transactions, error ->
            isLoading.onNext(false)
            if (error == null && transactions != null) {
                transactionList.value = transactions
            }
        }
    }

    fun saveTransaction(amount: String, typeExpense: String) {
        homeInteractor.saveTransaction(Transaction(explanation =  typeExpense, amount = amount, date = Date().toString()))
    }
}