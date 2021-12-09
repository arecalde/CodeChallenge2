package com.example.codechallenge2.network;

import com.example.codechallenge2.model.Page;
import com.example.codechallenge2.model.PageWrapper;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface APIService {
    @GET("/test/home")
    Call<PageWrapper> getCards();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://private-8ce77c-tmobiletest.apiary-mock.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
