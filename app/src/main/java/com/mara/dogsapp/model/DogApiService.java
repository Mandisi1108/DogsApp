package com.mara.dogsapp.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogApiService {
    private static final String BASE_URL = "https://raw.githubusercontent.com";
    private DogsApiInterface api;

    public DogApiService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DogsApiInterface.class);
    }
    public Single<List<DogBreed>> getDogs() {
        return api.getDogs();
    }
}
