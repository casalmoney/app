package br.com.casalmoney.app.unauthenticated.repository

import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.LoginException
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable

class LoginRepository() {
    private val mAuth = FirebaseAuth.getInstance()

    fun login (user: User): Observable<LoginException?> {
        val task = mAuth.signInWithEmailAndPassword(user.email, user.password)
        return Observable.create {emitter ->
            task.addOnCompleteListener {
                emitter.onNext(null?: LoginException(message = it.exception?.localizedMessage, code = 1001))
                emitter.onComplete()
            }
        }
    }

}