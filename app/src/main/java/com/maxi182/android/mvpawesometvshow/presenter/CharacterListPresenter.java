package com.maxi182.android.mvpawesometvshow.presenter;

import com.maxi182.android.mvpawesometvshow.model.Character;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public interface CharacterListPresenter<V> extends IBasePresenter<V>{

    void getCharacters();

    void getCharacterDetails(int id);

    void handleFavorite(Character character, int pos);


}
