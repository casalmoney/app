package br.com.casalmoney.app.unauthenticated.repository

import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.LoginException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginRepository() {
    suspend fun login (user: User): Pair<AuthResult?, LoginException?>{
        val mAuth = FirebaseAuth.getInstance()

        return try {
            val data = mAuth.signInWithEmailAndPassword(user.email, user.password).await()
            return Pair(data, null)
        } catch (e : Exception) {
            return return Pair(null, LoginException(-1002, e.localizedMessage))
        }
    }

}