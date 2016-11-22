package com.maxi182.android.mvpawesometvshow.presenter;

import com.maxi182.android.mvpawesometvshow.interactor.TvShowInteractorCallback;
import com.maxi182.android.mvpawesometvshow.interactor.TvShowInteractorImpl;
import com.maxi182.android.mvpawesometvshow.model.Character;
import com.maxi182.android.mvpawesometvshow.model.CharacterDetail;
import com.maxi182.android.mvpawesometvshow.model.TvShow;
import com.maxi182.android.mvpawesometvshow.ui.CharacterListView;

import java.lang.ref.WeakReference;

import io.realm.RealmList;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public class CharacterListPresenterImpl implements CharacterListPresenter<CharacterListView>, TvShowInteractorCallback.RequestCallback {

    private TvShowInteractorCallback tvShowInteractorCallback;
    private WeakReference<CharacterListView> characterListView;


    public CharacterListPresenterImpl(CharacterListView characterListView) {
        this.tvShowInteractorCallback = new TvShowInteractorImpl();
        this.characterListView = new WeakReference<>(characterListView);

    }

    @Override
    public void getCharacters() {
        if (characterListView != null) {
            getView().showProgress();
        }
        tvShowInteractorCallback.fetchShows(this);
    }

    @Override
    public void getCharacterDetails(int id) {

        tvShowInteractorCallback.fetchCharacterDetail(this, String.valueOf(id));

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
            getView().showListResponse(data.get(0).results);
            getView().hideProgress();
        }
    }

    @Override
    public void onFetchDataDetailSuccess(CharacterDetail characterDetail) {
        if (characterListView != null) {

        }

    }

    @Override
    public void onFetchDataFailed(String error) {

        if (characterListView != null) {
            getView().hideProgress();
            getView().onResponseFailed();
        }
    }

    @Override
    public void onStoreCompleted(boolean isSuccess) {

        if (characterListView != null) {
            if (isSuccess) {
                getView().realmStoreCompleted();
            } else {
                getView().realmStoreFailed();

            }
        }
    }

    private CharacterListView getView() {
        return (characterListView != null) ? characterListView.get() : null;
    }

    @Override
    public void onFavChanged(int pos) {
        getView().onFavChanged(pos);
    }

    @Override
    public void onItemPress() {

        getView().onItemPress();
    }

}
