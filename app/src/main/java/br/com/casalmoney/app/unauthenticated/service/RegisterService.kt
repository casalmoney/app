package br.com.casalmoney.app.unauthenticated.service

import br.com.casalmoney.app.unauthenticated.domain.UserInfo
import io.reactivex.Single
import retrofit2.http.*

interface RegisterService {
    @FormUrlEncoded
    @POST("user/register")
    @Headers("content-type: application/x-www-form-urlencoded;charset=UTF-8")
    fun register(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("email") email: String,
    ): Single<UserInfo>
}