package com.thiagoaranha.googlenews.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Thiago on 21/01/18.
 */
class RetrofitInitializer {
    private val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    fun service(): Service{
        return retrofit.create(Service::class.java)
    }
}