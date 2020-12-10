package br.com.casalmoney.app.unauthenticated.repository

import br.com.casalmoney.app.authenticated.repository.service.BaseService
import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.domain.UserInfo
import br.com.casalmoney.app.unauthenticated.exception.SignupException
import br.com.casalmoney.app.unauthenticated.service.RegisterService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val registerService: RegisterService
) : BaseService() {
    val mAuth = FirebaseAuth.getInstance()

    fun register(user: User): Observable<SignupException?> {

        val task = mAuth.createUserWithEmailAndPassword(user.email, user.password)
        val userUpdate = UserProfileChangeRequest.Builder().setDisplayName(user.name).build()

        return Observable.create { emitter ->
            val currentUser = mAuth.currentUser
            currentUser?.updateProfile(userUpdate)

            task.addOnCompleteListener {
                emitter.onNext(
                    null ?: SignupException(
                        message = it.exception?.localizedMessage,
                        code = 1002
                    )
                )
                emitter.onComplete()
            }
        }
    }

    fun saveInfos(user: User): Single<UserInfo> {
        return registerService.register(mAuth.currentUser!!.uid, user.name, user.email)
            .subscribeOn(
                Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread())
    }
}