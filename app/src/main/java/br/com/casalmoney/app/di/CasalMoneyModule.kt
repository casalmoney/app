package br.com.casalmoney.app.di

import android.content.Context
import br.com.casalmoney.app.Audit
import androidx.room.Room
import br.com.casalmoney.app.ViewAudit
import br.com.casalmoney.app.authenticated.repository.local.database.CasalmoneyDatabase
import br.com.casalmoney.app.authenticated.repository.service.ChatService
import br.com.casalmoney.app.authenticated.repository.service.HelpService
import br.com.casalmoney.app.authenticated.repository.service.HomeService
import br.com.casalmoney.app.unauthenticated.BusinessAudit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder

@Module
@InstallIn(ApplicationComponent::class)
object CasalMoneyModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BusinessLogger
    annotation class BaseUrl

    @Provides
    @Singleton
    fun viewAuditProvides() : Audit = ViewAudit("VIEW")

    @Provides
    @Singleton
    @BusinessLogger
    fun businessAuditProvides() : Audit = BusinessAudit("BUSINESS")

    @Provides
    fun provideRetrofit(@BaseUrl baseUrl: String): Retrofit {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().apply {
            connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            protocols(listOf(Protocol.HTTP_1_1))
        }
            .addInterceptor(logInterceptor)
            .build()

        val gsonConfig = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gsonConfig))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl() = "https://casalmoney.herokuapp.com/api/"

    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService = retrofit.create(HomeService::class.java)

    @Provides
    fun providesHelpService(retrofit: Retrofit): HelpService = retrofit.create(HelpService::class.java)

    @Provides
    fun providesChatService(retrofit: Retrofit): ChatService = retrofit.create(ChatService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CasalmoneyDatabase =
        Room.databaseBuilder(context, CasalmoneyDatabase::class.java, "CasalmoneyDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideTransactionDao(database: CasalmoneyDatabase) = database.homeDAO()

    @Singleton
    @Provides
    fun provideHelpDao(database: CasalmoneyDatabase) = database.helpDAO()

    @Singleton
    @Provides
    fun provideChatDao(database: CasalmoneyDatabase) = database.chatDAO()
}