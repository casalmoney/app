package br.com.casalmoney.app.authenticated.interactor

import br.com.casalmoney.app.authenticated.repository.HelpRepository
import br.com.casalmoney.app.unauthenticated.domain.Message
import br.com.casalmoney.app.unauthenticated.domain.MessageResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

open class HelpInteractor @Inject constructor(
    private val repository: HelpRepository
) {


}

