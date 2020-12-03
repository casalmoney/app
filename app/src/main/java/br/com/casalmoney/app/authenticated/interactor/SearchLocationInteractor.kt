package br.com.casalmoney.app.authenticated.interactor

import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.repository.SearchLocationRepository
import io.reactivex.Single
import java.lang.StringBuilder
import javax.inject.Inject

class SearchLocationInteractor @Inject constructor(
    private val repository: SearchLocationRepository
) {
    fun searchPlaceUsing(query: String): Single<List<String>> {
        return repository.searchPlaceUsing(query)
    }
}

