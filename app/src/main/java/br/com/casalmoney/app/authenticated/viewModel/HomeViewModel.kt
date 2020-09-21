package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.casalmoney.app.authenticated.interactor.HomeInteractor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

open class HomeViewModel(val app: Application) : AndroidViewModel(app) {

    val homeInteractor = HomeInteractor()
    val currentUser = FirebaseAuth.getInstance().currentUser

    fun logout() {
        homeInteractor.logout()
    }

    val nameInitials: String
        get() {
            val initials = currentUser?.displayName.toString()
                .split(' ')
                .mapNotNull { it.firstOrNull()?.toString() }
                .reduce { acc, s -> acc + s }
            return if (initials.length == 1) {
                initials + initials
            } else {
                initials.substring(0, 2)
            }
        }
}