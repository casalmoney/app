package br.com.casalmoney.app.authenticated.repository

import android.app.Application
import android.content.Context
import br.com.casalmoney.app.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseService() {

    val gson: Gson
    val retrofit: Retrofit

    init {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
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

    fun toJson(obj: Any): String = gson.toJson(obj)
    inline fun <reified T> fromJson(json: String) = gson.fromJson(json, T::class.java)
}