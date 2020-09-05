package br.com.casalmoney.app.unauthenticated.repository


import br.com.casalmoney.app.unauthenticated.domain.User
import br.com.casalmoney.app.unauthenticated.exception.SignupException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class RegisterRepository() {
    suspend fun register (user: User): Pair<AuthResult?, SignupException?>{
        val mAuth = FirebaseAuth.getInstance()

        return try {
            val data = mAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            val userUpdate =  UserProfileChangeRequest.Builder().setDisplayName(user.name).build()

            val currentUser = mAuth.currentUser
            currentUser?.updateProfile(userUpdate)

            return Pair(data, null)
        } catch (e : Exception) {
            return return Pair(null, SignupException(-1001, e.localizedMessage))
        }
    }
}