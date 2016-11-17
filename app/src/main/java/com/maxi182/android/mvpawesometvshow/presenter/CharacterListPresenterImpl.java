package com.maxi182.android.mvpawesometvshow.presenter;

import com.maxi182.android.mvpawesometvshow.interactor.TvShowInteractorCallback;
import com.maxi182.android.mvpawesometvshow.interactor.TvShowInteractorImpl;
import com.maxi182.android.mvpawesometvshow.model.Character;
import com.maxi182.android.mvpawesometvshow.model.TvShow;
import com.maxi182.android.mvpawesometvshow.ui.CharacterListView;

import io.realm.RealmList;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public class CharacterListPresenterImpl implements CharacterListPresenter<CharacterListView>, TvShowInteractorCallback.RequestCallback {

    private TvShowInteractorCallback tvShowInteractorCallback;
    private CharacterListView characterListView;


    public CharacterListPresenterImpl(CharacterListView characterListView) {
        this.tvShowInteractorCallback = new TvShowInteractorImpl();
        this.characterListView = characterListView;
    }

    @Override
    public void getCharacters() {
        if (characterListView != null) {
            characterListView.showProgress();
        }
        tvShowInteractorCallback.fetchShows(this);
    }

    @Override
    public void handleFavorite(Character character, int pos) {

        tvShowInteractorCallback.handleFavorite(this, character, pos);
    }

    @Override
    public void attachView(CharacterListView view) {
        tvShowInteractorCallback.attachView();
    }


    @Override
    public void detachView() {
        tvShowInteractorCallback.detachView();
    }


    @Override
    public void onFetchDataSuccess(RealmList<TvShow> data) {
        if (characterListView != null) {
            characterListView.showListResponse(data.get(0).results);
            characterListView.hideProgress();
        }
    }

    @Override
    public void onFetchDataFailed(String error) {

        if (characterListView != null) {
            characterListView.hideProgress();
            characterListView.onResponseFailed();
        }
    }

    @Override
    public void onStoreCompleted(boolean isSuccess) {

        if (characterListView != null) {
            if (isSuccess) {
                characterListView.realmStoreCompleted();
            } else {
                characterListView.realmStoreFailed();

            }
        }
    }

    @Override
    public void onFavChanged(int pos) {
        characterListView.onFavChanged(pos);
    }

}
