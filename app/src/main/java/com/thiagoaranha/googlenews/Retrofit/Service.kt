package com.thiagoaranha.googlenews.Retrofit

import com.thiagoaranha.googlenews.ApiModel.Models
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Thiago on 06/01/18.
 */
interface Service {
    @GET("top-headlines")
    fun listTopNews(@Query("sources") sources: String, @Query("apiKey") key : String): Call<Models.Result>

}