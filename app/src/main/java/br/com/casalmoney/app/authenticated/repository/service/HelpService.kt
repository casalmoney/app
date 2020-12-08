package br.com.casalmoney.app.authenticated.repository.service

import br.com.casalmoney.app.authenticated.domain.News
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface HelpService {
    @GET("news")
    @Headers("Content-Type: application/json")
    fun listNews(): Single<List<News>>
}