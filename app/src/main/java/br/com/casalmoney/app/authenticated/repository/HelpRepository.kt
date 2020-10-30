package br.com.casalmoney.app.authenticated.repository

import br.com.casalmoney.app.authenticated.repository.local.database.HelpDAO
import br.com.casalmoney.app.authenticated.repository.service.BaseService
import br.com.casalmoney.app.authenticated.repository.service.HelpService
import javax.inject.Inject

class HelpRepository @Inject constructor(
    private val helpService: HelpService,
    private val helpDAO: HelpDAO
): BaseService() {

}

