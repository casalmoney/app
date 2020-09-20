package br.com.casalmoney.app.authenticated.repository

import com.google.firebase.auth.FirebaseAuth

class HomeRepository {

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

}