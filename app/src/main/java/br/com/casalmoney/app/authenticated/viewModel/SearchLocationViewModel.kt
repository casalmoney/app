package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import br.com.casalmoney.app.authenticated.interactor.SearchLocationInteractor

class SearchLocationViewModel @ViewModelInject constructor(
    val app: Application,
    private val searchLocationInteracor: SearchLocationInteractor
) : AndroidViewModel(app) {

    private fun searchPlace() {

    }
}

