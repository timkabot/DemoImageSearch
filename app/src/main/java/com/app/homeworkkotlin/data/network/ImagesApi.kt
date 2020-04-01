package com.app.homeworkkotlin.data.network

import com.app.homeworkkotlin.utils.APIKEY
import com.app.homeworkkotlin.utils.BASEURL
import com.app.homeworkkotlin.domain.entity.ImagesResponse
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ImagesApi {
    @GET("search")
    fun searchImagesByQuery(@Query("query") keyword: String?): Observable<ImagesResponse>

    companion object Factory {

        private fun createNetworkClient() =
            OkHttpClient.Builder()
                .addNetworkInterceptor(createAccessTokenProvidingInterceptor())
                .build()


        fun create(baseUrl: String = BASEURL): ImagesApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(createNetworkClient())
                .build()

            return retrofit.create(ImagesApi::class.java)
        }

        private fun createAccessTokenProvidingInterceptor() = Interceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $APIKEY")
                .build())
        }
    }


}