package com.maxi182.android.mvpawesometvshow.presenter;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public interface CharacterListPresenter<V> extends IBasePresenter<V>{


    //void getCharactersFromServer();

    void getCharacters();
   // void getCharactersLocal();

    void handleFavorite(int id);





}
