package br.com.casalmoney.app.authenticated.interactor

import br.com.casalmoney.app.authenticated.repository.SearchLocationRepository
import java.lang.StringBuilder
import javax.inject.Inject

class SearchLocationInteractor @Inject constructor(
    private val repository: SearchLocationRepository
) {
    fun searchPlace(stringBuilder: StringBuilder) {

    }
}

