package com.maxi182.android.mvpawesometvshow.interactor;

import android.util.Log;

import com.maxi182.android.mvpawesometvshow.api.RestClient;
import com.maxi182.android.mvpawesometvshow.model.Character;
import com.maxi182.android.mvpawesometvshow.model.CharacterDetail;
import com.maxi182.android.mvpawesometvshow.model.TvShow;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public class TvShowInteractorImpl extends RealmManager implements TvShowInteractorCallback {

    private RealmAsyncTask mTransaction;


    public void fetchShowsFromServer(final RequestCallback callback) {

        RestClient.getApiService().getCharacters().enqueue(new Callback<RealmList<TvShow>>() {

            @Override
            public void onResponse(Call<RealmList<TvShow>> call, Response<RealmList<TvShow>> response) {

                if (response.body() != null) {

                    storeLocal(callback, response.body());

                } else {

                    callback.onFetchDataFailed(response.message());

                }
            }

            @Override
            public void onFailure(Call<RealmList<TvShow>> call, Throwable t) {

                callback.onFetchDataFailed(t.getMessage());

            }
        });
    }

    @Override
    public void fetchCharacterDetail(final RequestCallback callback, String id) {

        RestClient.getApiService().getCharactersDetail(id).enqueue(new Callback<CharacterDetail>() {

            @Override
            public void onResponse(Call<CharacterDetail> call, Response<CharacterDetail> response) {

                if (response.body() != null) {

                    callback.onFetchDataDetailSuccess(response.body());

                }
            }
            @Override
            public void onFailure(Call<CharacterDetail> call, Throwable t) {

                callback.onFetchDataFailed(t.getMessage());

            }
        });
    }

    private void storeLocal(final RequestCallback callback, final RealmList<TvShow> data) {

        mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                bgRealm.copyToRealm(data);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onStoreCompleted(true);
                callback.onFetchDataSuccess(getRealmList());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onStoreCompleted(false);
            }
        });

    }

    private RealmResults<TvShow> getRealmData() {

        return mRealm.where(TvShow.class).findAll();

    }

    private boolean isRealmDBLoaded() {

        return (getRealmData() != null && !getRealmData().isEmpty()) ? true : false;

    }

    private RealmList<TvShow> getRealmList() {
        RealmList<TvShow> list = new RealmList<>();
        list.addAll(getRealmData());
        return list;

    }

    public void fetchShowsLocal(final RequestCallback callback) {
        callback.onFetchDataSuccess(getRealmList());
    }

    @Override
    public void fetchShows(RequestCallback callback) {

        if (isRealmDBLoaded()) {
            fetchShowsLocal(callback);
        } else {
            fetchShowsFromServer(callback);
        }
    }

    @Override
    public void handleFavorite(final RequestCallback callback, final Character character, final int pos) {

        final TvShow tvShow = getRealmList().get(0);

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                Character selected = tvShow.results.get(getCharacterId(tvShow, character));
                selected.isFav = !selected.isFav;
                mRealm.copyToRealmOrUpdate(tvShow);
                callback.onFavChanged(pos);

            }
        });
    }

    private int getCharacterId(TvShow tvShow, Character character) {
        int len = tvShow.results.size();
        for (int i = 0; i < len; i++) {
            if (character.equals(tvShow.results.get(i))) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void attachView() {

        Log.d("LOG", "Attach View");
        initRealm();

    }

    @Override
    public void detachView() {

        Log.d("LOG", "detachView");

        if (mTransaction != null && !mTransaction.isCancelled()) {
            mTransaction.cancel();
        }
        closeRealm();

    }
}
