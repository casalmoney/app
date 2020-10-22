package br.com.casalmoney.app.authenticated.repository.service

import br.com.casalmoney.app.authenticated.repository.local.entity.TransactionEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface HomeService {

    @GET("transactions")
    @Headers("Content-Type: application/json")
    fun characterList(
        @Query("page") page: Int
    ): Single<List<TransactionEntity>>
}