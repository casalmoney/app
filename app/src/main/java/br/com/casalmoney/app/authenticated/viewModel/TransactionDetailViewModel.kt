package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel

class TransactionDetailViewModel @ViewModelInject constructor(
    val app: Application
) : AndroidViewModel(app){
}