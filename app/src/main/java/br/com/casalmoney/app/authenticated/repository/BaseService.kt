package br.com.casalmoney.app.authenticated.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseService() {

    val gson: Gson
    val retrofit: Retrofit

    init {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().apply {
            connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            protocols(listOf(Protocol.HTTP_1_1))
        }
            .addInterceptor(logInterceptor)
            .build()

        gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://casalmoney.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}