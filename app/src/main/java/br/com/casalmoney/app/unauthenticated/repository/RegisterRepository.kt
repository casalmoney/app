package br.com.casalmoney.app.unauthenticated.repository

import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.SignupException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class RegisterRepository {

    suspend fun register (user: User): Pair<AuthResult?, SignupException?>{
        val mAuth = FirebaseAuth.getInstance()
        return try {
            val data = mAuth.createUserWithEmailAndPassword(user.password, user.email).await()
            return Pair(data, null)
        } catch (e : Exception) {
            return return Pair(null, SignupException(-1001, e.localizedMessage))
        }
    }
}