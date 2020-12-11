package br.com.casalmoney.app.authenticated.view.activities

import android.app.SearchManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.domain.Location
import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.repository.service.HomeService
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    var selectedTransaction: Transaction? = null

    var searchView: SearchView? = null
    var iSearchView: SearchView.OnQueryTextListener? = null

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
                R.id.transactionDetailFragment,
                R.id.chatFragment,
                R.id.searchLocationFragment -> {
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

    override fun onBackPressed() {
        when(navController.currentDestination?.id) {
            R.id.homeFragment-> {
                AlertDialog
                    .Builder(this)
                    .setMessage(getString(R.string.close_app))
                    .setPositiveButton(R.string.yes) { _, _ ->
                        finishAffinity()
                    }.setNegativeButton(R.string.no) {_, _ -> /* do nothing*/ }
                    .show()
            }
            R.id.searchLocationFragment -> {
                searchView?.let {
                    if (!it.isIconified) {
                        it.onActionViewCollapsed()
                    } else {
                        super.onBackPressed()
                    }
                }
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        when(navController.currentDestination?.id) {
            R.id.searchLocationFragment -> {
                searchStuffIn(menu)
            }
            else -> {
                //do nothing
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchStuffIn(menu: Menu) {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)

        searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView?.let {
            it.setOnCloseListener { true }

            val searchPlate = it.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
            searchPlate.hint = getString(R.string.search)
            val searchPlateView: View =
                it.findViewById(androidx.appcompat.R.id.search_plate)
            searchPlateView.setBackgroundColor(
                ContextCompat.getColor(this, android.R.color.transparent)
            )

            it.setOnQueryTextListener(iSearchView)

            val searchManager =
                getSystemService(Context.SEARCH_SERVICE) as SearchManager
            it.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
    }

    fun setLocationInSelectedTransaction(location: Location) {
        selectedTransaction?.location = location
    }


}