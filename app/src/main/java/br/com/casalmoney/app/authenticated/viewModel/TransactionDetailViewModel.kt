package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.interactor.HomeInteractor
import br.com.casalmoney.app.authenticated.repository.local.entity.LocationTypeConverter
import br.com.casalmoney.app.authenticated.repository.local.entity.TransactionEntity

class TransactionDetailViewModel @ViewModelInject constructor(
    val app: Application,
    private val homeInteractor: HomeInteractor
) : AndroidViewModel(app){

    var transaction: Transaction? = null

    fun updateTransaction() {
        homeInteractor.updateTransaction(transaction =
        transaction?.let {
            TransactionEntity(
                title = it.title,
                explanation = it.explanation,
                amount = getAmountInDouble(it.amount),
                date = it.date,
                location = LocationTypeConverter().locationToString(it.location)
            )
        })
    }

    fun getAmountInDouble(amount: String) : Double {
        return amount.toCharArray()
            .filter { it.isDigit() }
            .joinToString(separator = "")
            .toDouble()
    }
}