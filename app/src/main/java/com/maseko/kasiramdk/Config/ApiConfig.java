package com.maseko.kasiramdk.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    private static String BASE_URL = "https://maseko.000webhostapp.com/";

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
