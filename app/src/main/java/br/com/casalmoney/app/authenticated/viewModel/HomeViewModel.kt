package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.casalmoney.app.authenticated.interactor.HomeInteractor

open class HomeViewModel(val app: Application) : AndroidViewModel(app) {

    val homeInteractor = HomeInteractor()

    fun logout() {
        homeInteractor.logout()
    }

}