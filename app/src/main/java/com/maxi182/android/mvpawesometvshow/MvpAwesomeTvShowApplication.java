package com.maxi182.android.mvpawesometvshow;

import android.app.Application;

import com.maxi182.android.mvpawesometvshow.api.RestClient;

import io.realm.Realm;

/**
 * Created by maximilianoferraiuolo on 07/11/2016.
 */

public class MvpAwesomeTvShowApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RestClient.init(this);
        Realm.init(this);

    }

}
