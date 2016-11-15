package com.maxi182.android.mvpawesometvshow.model;

import io.realm.RealmObject;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public class Character extends RealmObject {

    public int mCharacterId;
    public String Name;
    public int chapters;
    public int stars;
    public String occupation;
    public String ImgUrl;
    public boolean Alive;
    public boolean isFav;

}
