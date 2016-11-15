package com.maxi182.android.mvpawesometvshow.api;

import android.content.Context;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by maximilianoferraiuolo on 08/11/2016.
 */

public class RestClient {

    private static AwesomeTvShowEndpoints sEndPoints;

    public static AwesomeTvShowEndpoints getApiService() {
        return sEndPoints;
    }

    public static void init(Context context) {
        sEndPoints = createService();
    }

    private static AwesomeTvShowEndpoints createService() {
        return getRetrofit().create(AwesomeTvShowEndpoints.class);
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://dl.dropboxusercontent.com/u/37464129/max/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();
    }
}
