package br.com.casalmoney.app.unauthenticated.repository

import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.SignupException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.rxjava3.core.Observable

class RegisterRepository() {
    val mAuth = FirebaseAuth.getInstance()

    fun register (user: User): Observable<SignupException?> {


        val task = mAuth.createUserWithEmailAndPassword(user.email, user.password)
        val userUpdate =  UserProfileChangeRequest.Builder().setDisplayName(user.name).build()

        return Observable.create { emitter ->
            val currentUser = mAuth.currentUser

            currentUser?.updateProfile(userUpdate)

            task.addOnCompleteListener {
                emitter.onNext(null?: SignupException(message = it.exception?.localizedMessage, code = 1002))
                emitter.onComplete()
            }
        }
    }
}