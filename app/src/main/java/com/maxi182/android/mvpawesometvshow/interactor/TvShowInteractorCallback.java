package com.maxi182.android.mvpawesometvshow.interactor;

import com.maxi182.android.mvpawesometvshow.model.TvShow;

import io.realm.RealmList;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public interface TvShowInteractorCallback  {


    void fetchShows(RequestCallback callback);

    void attachView();
    void detachView();

    void addToFavorite(int id);
    void removeFromFavorite(int id);

    interface RequestCallback {

        void onFetchDataSuccess(RealmList<TvShow> data);
        void onFetchDataFailed(String error);
        void onStoreCompleted(boolean isSuccess);

    }
}

