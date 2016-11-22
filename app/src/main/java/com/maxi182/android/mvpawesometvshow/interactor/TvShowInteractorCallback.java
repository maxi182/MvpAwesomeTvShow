package com.maxi182.android.mvpawesometvshow.interactor;

import com.maxi182.android.mvpawesometvshow.model.Character;
import com.maxi182.android.mvpawesometvshow.model.CharacterDetail;
import com.maxi182.android.mvpawesometvshow.model.TvShow;

import io.realm.RealmList;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public interface TvShowInteractorCallback {


    void fetchShows(RequestCallback callback);

    void fetchCharacterDetail(RequestCallback callback, String s);

    void attachView();

    void detachView();

    void handleFavorite(RequestCallback callback, Character character, int pos);


    interface RequestCallback {

        void onFetchDataSuccess(RealmList<TvShow> data);

        void onFetchDataDetailSuccess(CharacterDetail characterDetail);

        void onFetchDataFailed(String error);

        void onStoreCompleted(boolean isSuccess);

        void onFavChanged(int pos);

        void onItemPress();

    }
}

