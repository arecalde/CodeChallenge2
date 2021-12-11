package com.example.codechallenge2.network

import retrofit2.http.GET
import com.example.codechallenge2.model.PageWrapper
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface APIService {
    @get:GET("/test/home")
    val cards: Call<PageWrapper?>?

    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://private-8ce77c-tmobiletest.apiary-mock.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}