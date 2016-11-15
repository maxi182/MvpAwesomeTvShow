package com.maxi182.android.mvpawesometvshow.api;

import com.maxi182.android.mvpawesometvshow.model.TvShow;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public interface AwesomeTvShowEndpoints {

    @GET("BBCharacters.json")
        Call<RealmList<TvShow>> getCharacters();

}
