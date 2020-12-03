package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import br.com.casalmoney.app.authenticated.interactor.SearchLocationInteractor
import java.lang.StringBuilder

class SearchLocationViewModel @ViewModelInject constructor(
    val app: Application,
    private val searchLocationInteractor: SearchLocationInteractor
) : AndroidViewModel(app) {

    fun searchPlaceUsing(query: String?) {
        query?.let {
            searchLocationInteractor.searchPlaceUsing(query)
        }
    }
}

