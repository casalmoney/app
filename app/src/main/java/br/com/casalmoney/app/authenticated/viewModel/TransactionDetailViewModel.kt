package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import br.com.casalmoney.app.authenticated.domain.Transaction

class TransactionDetailViewModel @ViewModelInject constructor(
    val app: Application
) : AndroidViewModel(app){

    var transaction: Transaction? = null

    fun changeLocation() {

    }

}