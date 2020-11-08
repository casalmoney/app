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
import java.text.NumberFormat
import java.text.SimpleDateFormat
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
                transactionList.value =  transactions.map {
                    Transaction(amount = formatToCurrency(it.amount), explanation = it.explanation, date = it.date)
                }
            }
        }
    }

    fun saveTransaction(amount: String, typeExpense: String) {
        val pattern = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(Date())

        val mAmount= amount.toString().replace("""[R$/\s/g.]""".toRegex(), "").replace(",", ".")
        homeInteractor.saveTransaction(
            Transaction(
                explanation = typeExpense,
                amount = mAmount,
                date = date
            )
        )
    }

    fun formatToCurrency(p0: CharSequence?): String {
        val cleanString: String = p0.toString().replace("""[R$,./\s/g]""".toRegex(), "")
        val parsed = cleanString.toDouble()
        val formatted = NumberFormat.getCurrencyInstance().format((parsed/100));
        return formatted
    }
}