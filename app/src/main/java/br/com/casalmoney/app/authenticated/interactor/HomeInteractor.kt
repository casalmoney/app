package br.com.casalmoney.app.authenticated.interactor

import br.com.casalmoney.app.authenticated.repository.HomeRepository

class HomeInteractor {

    private val repository = HomeRepository()

    fun logout() {
        repository.logout()
    }
}