package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.interactor.HomeInteractor
import br.com.casalmoney.app.authenticated.repository.local.entity.LocationTypeConverter
import br.com.casalmoney.app.authenticated.repository.local.entity.TransactionEntity
import br.com.casalmoney.app.utils.DateUtils
import br.com.casalmoney.app.utils.extensions.CurrencyUtils

class TransactionDetailViewModel @ViewModelInject constructor(
    val app: Application,
    private val homeInteractor: HomeInteractor
) : AndroidViewModel(app){

    var transaction: Transaction? = null

    fun updateTransaction() {
        homeInteractor.updateTransaction(transaction =
        transaction?.let {
            TransactionEntity(
                id = it.id,
                title = it.title,
                explanation = it.explanation,
                amount = CurrencyUtils().getAmountInDouble(it.amount),
                date = DateUtils().convertDateToLong(it.date).toString(),
                location = LocationTypeConverter().locationToString(it.location)
            )
        })
    }
}