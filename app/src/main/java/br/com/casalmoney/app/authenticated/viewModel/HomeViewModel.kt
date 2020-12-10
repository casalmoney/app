package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.interactor.HomeInteractor
import br.com.casalmoney.app.authenticated.repository.local.entity.TransactionEntity
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
    val transactionList = MutableLiveData<List<Transaction>>()

    val totalAmount = MutableLiveData<String>()
    val nameInitials = MutableLiveData<String>()
    val currentUser = MutableLiveData<String>()

    var disposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun logout() {
        homeInteractor.logout()
    }

    fun getUserDetails() {
        nameInitials.value = "--"
        currentUser.value = "--"
        disposable = homeInteractor.getUserInfo().subscribe { user, error ->
            if (user != null && error == null) {
                nameInitials.value = user.initials
                currentUser.value = user.name
            } else {
                nameInitials.value = "--"
                currentUser.value = "--"
            }
        }
    }


    fun getTransactions() {
        isLoading.onNext(true)

        val param = page.value ?: 1
        disposable = homeInteractor.getTransactions(param).subscribe { transactions, error ->
            if (error == null && transactions != null) {
                setTransactionList(transactions)
                setTotalAmout(transactions)
            }
            isLoading.onNext(false)
        }
    }

    fun saveTransaction(amount: String, typeExpense: String) {
        val mAmount = amount.replace("""[R$/\s/g.]""".toRegex(), "").replace(",", ".").toDouble()

        homeInteractor.saveTransaction(
            TransactionEntity(
                title = "",
                explanation = typeExpense,
                amount = mAmount,
                date = Date().time.toString()
            )
        )
    }

    fun formatToCurrency(p0: CharSequence?): String {
        val cleanString: String = p0.toString().replace("""[R$,./\s/g]""".toRegex(), "")
        val parsed = cleanString.toDouble()
        return NumberFormat.getCurrencyInstance().format((parsed / 100))
    }

    private fun setTransactionList(transactions: List<Transaction>) {
        val pattern = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)

        transactionList.value = transactions.map {
            Transaction(
                title = "",
                amount = formatToCurrency(it.amount),
                explanation = it.explanation,
                date = simpleDateFormat.format(Date(it.date.toLong()))
            )
        }
    }

    private fun setTotalAmout(transactions: List<Transaction>) {
        var total = 0.0
        val calendar = Calendar.getInstance()

        calendar.getActualMaximum(Calendar.DATE)
        val lastDay = calendar.getActualMaximum(Calendar.DATE)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)

        val maxDate = "$currentMonth/$lastDay/$currentYear"
        val minDate = "$currentMonth/01/$currentYear"

        val currentTransactions = transactions.filter { it ->
            Date(it.date.toLong()).after(Date(minDate)) && Date(it.date.toLong()).before(
                Date(
                    maxDate
                )
            )
        }
        currentTransactions.map { total += it.amount.toDouble() }
        totalAmount.value = formatToCurrency(total.toString()).replace("""[R$]""".toRegex(), "")
    }

    fun saveTransaction(title: String, amount: String, typeExpense: String) {
        homeInteractor.saveTransaction(
            TransactionEntity(
                title = title,
                explanation = typeExpense,
                amount = amount.toDouble(),
                date = Date().toString()
            )
        )
    }
}