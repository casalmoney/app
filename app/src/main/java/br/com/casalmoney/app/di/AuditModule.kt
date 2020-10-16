package br.com.casalmoney.app.di

import br.com.casalmoney.app.Audit
import br.com.casalmoney.app.ViewAudit
import br.com.casalmoney.app.unauthenticated.BusinessAudit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AuditModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BusinessLogger

    @Provides
    @Singleton
    fun viewAuditProvides() : Audit = ViewAudit("VIEW")

    @Provides
    @Singleton
    @BusinessLogger
    fun businessAuditProvides() : Audit = BusinessAudit("BUSINESS")
}