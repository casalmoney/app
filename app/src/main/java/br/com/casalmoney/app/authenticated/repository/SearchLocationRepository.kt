package br.com.casalmoney.app.authenticated.repository

import br.com.casalmoney.app.authenticated.repository.service.BaseService
import io.reactivex.Single
import io.reactivex.Single.just
import java.lang.StringBuilder
import javax.inject.Inject

class SearchLocationRepository @Inject constructor(

) : BaseService() {

    fun searchPlaceUsing(query: String) : Single<List<String>> {
        return Single.just(null)
    }
}