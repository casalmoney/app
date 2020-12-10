package br.com.casalmoney.app.authenticated.repository.service

import br.com.casalmoney.app.authenticated.domain.UserDetails
import com.google.firebase.auth.UserInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserInfoService {
    @GET("user/{id}")
    @Headers("Content-Type: application/json")
    fun getUser(@Path("id") id: String): Single<UserDetails>
}