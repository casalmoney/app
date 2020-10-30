package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import br.com.casalmoney.app.authenticated.interactor.HelpInteractor


class HelpViewModel @ViewModelInject constructor(
    val app: Application,
    private val helpInteractor: HelpInteractor,
) : AndroidViewModel(app) {


}