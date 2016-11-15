package com.maxi182.android.mvpawesometvshow.ui;

import com.maxi182.android.mvpawesometvshow.model.Character;

import io.realm.RealmList;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public interface CharacterListView {

    void showListResponse(RealmList<Character> data);

    void onResponseFailed();

    void realmStoreCompleted();

    void realmStoreFailed();

    void showProgress();

    void hideProgress();
}
