package com.app.homeworkkotlin.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.homeworkkotlin.domain.entity.MainActivityEvent
import com.app.homeworkkotlin.domain.entity.Image
import com.app.homeworkkotlin.domain.interactors.ImagesInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val imagesInteractor: ImagesInteractor) : ViewModel() {
    private val disposables = CompositeDisposable()
    val images = MutableLiveData<List<Image>>()
    val events = MutableLiveData<MainActivityEvent>()

    fun findImages(keyword: String){
        clearJobs()

        events.postValue(MainActivityEvent.SHOW_PROGRESS_DIALOG)

        imagesInteractor.search(keyword)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe {
                events.postValue(MainActivityEvent.HIDE_PROGRESS_DIALOG)
                images.postValue(it.images)
            }
            .addTo(disposables)
    }

    private fun clearJobs(){
        disposables.clear()
    }
}