package com.maxi182.android.mvpawesometvshow.api;

import com.maxi182.android.mvpawesometvshow.model.CharacterDetail;
import com.maxi182.android.mvpawesometvshow.model.TvShow;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public interface AwesomeTvShowEndpoints {

    @GET(ApiPaths.BBCHARACTERS)
    Call<RealmList<TvShow>> getCharacters();

    @GET(ApiPaths.BBDETAILS)
    Call<CharacterDetail> getCharactersDetail(@Path(ApiPaths.PARAM_ID) String secretCode);


}
