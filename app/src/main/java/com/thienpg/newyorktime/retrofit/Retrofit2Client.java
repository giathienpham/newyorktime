package com.thienpg.newyorktime.retrofit;


import com.thienpg.newyorktime.BuildConfig;
import com.thienpg.newyorktime.service.NewYorkTimeService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thienpg.newyorktime.utility.Constant.API_URL;

/**
 * Created by ThienPG on 2/25/2017.
 */

public class Retrofit2Client {

    private static Retrofit2Client instance = null;
    private Retrofit retrofit;
    private OkHttpClient client;

    private NewYorkTimeService newYorkTimeService;

    public Retrofit2Client() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        client = okHttpBuilder.build();

        retrofit = new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        newYorkTimeService = retrofit.create(NewYorkTimeService.class);
    }

    public static Retrofit2Client getInstance() {
        if (instance == null) {
            instance = new Retrofit2Client();
        }

        return instance;
    }

    public NewYorkTimeService getNewYorkTimeService() {
        return newYorkTimeService;
    }
}