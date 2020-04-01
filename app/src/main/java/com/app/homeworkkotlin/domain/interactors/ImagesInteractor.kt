package com.app.homeworkkotlin.domain.interactors

import com.app.homeworkkotlin.data.network.ImagesApi
import com.app.homeworkkotlin.domain.entity.ImagesResponse
import io.reactivex.Observable

class ImagesInteractor(private val api: ImagesApi) {
    fun search(keyword: String) : Observable<ImagesResponse>
    {
        return api.searchImagesByQuery(keyword)
    }
}