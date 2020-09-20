package br.com.casalmoney.app.unauthenticated.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.casalmoney.app.R
import com.google.firebase.auth.FirebaseAuth

class UnAuthenticatedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.hide()
        setContentView(R.layout.activity_unauthenticated)

    }
}
