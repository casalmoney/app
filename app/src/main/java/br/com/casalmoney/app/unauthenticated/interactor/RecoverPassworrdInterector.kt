package br.com.casalmoney.app.unauthenticated.interactor

import br.com.casalmoney.app.unauthenticated.repository.RecoverPasswordRepository
import io.reactivex.Observable

class RecoverPassworrdInterector {
    val repo = RecoverPasswordRepository()

    fun recover (email: String) : Observable<Exception?>{
        return repo.recover(email)
    }

}