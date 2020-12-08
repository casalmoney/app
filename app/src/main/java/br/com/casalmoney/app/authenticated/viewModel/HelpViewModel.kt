package br.com.casalmoney.app.authenticated.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.casalmoney.app.authenticated.domain.News
import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.authenticated.interactor.HelpInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


class HelpViewModel @ViewModelInject constructor(
    val app: Application,
    private val helpInteractor: HelpInteractor,
) : AndroidViewModel(app) {

    var isLoading: PublishSubject<Boolean> = PublishSubject.create()

    private val newsList = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = newsList
    var disposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun getNews() {
        disposable = helpInteractor.getNews().subscribe { list, error ->
            if (error == null && list != null) {
                newsList.value = list
            }
        }
    }

}