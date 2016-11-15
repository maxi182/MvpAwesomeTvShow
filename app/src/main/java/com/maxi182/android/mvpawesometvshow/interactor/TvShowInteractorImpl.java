package com.maxi182.android.mvpawesometvshow.interactor;

import android.util.Log;

import com.maxi182.android.mvpawesometvshow.api.RestClient;
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
                    callback.onFetchDataSuccess(response.body());
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

    public void fetchShowsLocal(final RequestCallback callback) {
        RealmList<TvShow> list = new RealmList<>();
        list.addAll(getRealmData());
        callback.onFetchDataSuccess(list);

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
    public void addToFavorite(int id) {

    }

    @Override
    public void removeFromFavorite(int id) {

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
