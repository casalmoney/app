package br.com.casalmoney.app.authenticated.view.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.domain.Transaction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    var selectedTransaction: Transaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        this.supportActionBar?.hide()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.authenticatedNavigationFragment) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavView.setupWithNavController(navHostFragment.navController)

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.homeFragment,
                R.id.statementFragment,
                R.id.helpFragment
            )
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.transactionDetailFragment, R.id.chatFragment -> {
                    this.supportActionBar?.show()
                    bottomNavView.visibility = View.GONE
                }
                else -> {
                    this.supportActionBar?.hide()
                    bottomNavView.visibility = View.VISIBLE
                }
            }
        }

        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}