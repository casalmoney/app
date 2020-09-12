package br.com.casalmoney.app.unauthenticated.repository

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.core.Observable


class RecoverPasswordRepository  {
    val mAuth = FirebaseAuth.getInstance()

    fun recover(email: String) : Observable<Exception?> {
        val task  = mAuth.sendPasswordResetEmail(email)
        return Observable.create { emitter ->
            task.addOnCompleteListener {
                emitter.onNext(null?: Exception(it.exception?.localizedMessage))
                emitter.onComplete()
            }
        }
    }

}