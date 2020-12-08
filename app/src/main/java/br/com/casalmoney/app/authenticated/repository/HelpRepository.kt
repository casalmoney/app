package br.com.casalmoney.app.authenticated.repository

import br.com.casalmoney.app.authenticated.domain.News
import br.com.casalmoney.app.authenticated.repository.local.database.HelpDAO
import br.com.casalmoney.app.authenticated.repository.service.BaseService
import br.com.casalmoney.app.authenticated.repository.service.HelpService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HelpRepository @Inject constructor(
    private val helpService: HelpService,
    private val helpDAO: HelpDAO
) : BaseService() {
    fun getNews(): Single<List<News>> {
        return helpService.listNews().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}

