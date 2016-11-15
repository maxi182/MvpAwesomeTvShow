package com.maxi182.android.mvpawesometvshow.presenter;

/**
 * Created by maximilianoferraiuolo on 14/11/2016.
 */

public interface IBasePresenter<V> {

    void attachView(V view);

    void detachView();

}
