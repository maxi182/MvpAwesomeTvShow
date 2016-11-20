package com.maxi182.android.mvpawesometvshow.model;

import io.realm.RealmObject;

/**
 * Created by maximilianoferraiuolo on 10/11/2016.
 */

public class Character extends RealmObject {

    public int mCharacterId;
    public String name;
    public int chapters;
    public int stars;
    public String occupation;
    public String imgUrl;
    public boolean alive;
    public boolean isFav;

}
