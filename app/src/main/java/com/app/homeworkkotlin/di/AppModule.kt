package com.app.homeworkkotlin.di

import com.app.homeworkkotlin.utils.BASEURL
import com.app.homeworkkotlin.data.network.ImagesApi
import com.app.homeworkkotlin.domain.interactors.ImagesInteractor
import com.app.homeworkkotlin.presentation.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun createAppModule(baseUrl: String = BASEURL) = module {
    single { ImagesApi.create(baseUrl) }

    single { ImagesInteractor(get()) }

    viewModel { MainViewModel(get()) }

}

