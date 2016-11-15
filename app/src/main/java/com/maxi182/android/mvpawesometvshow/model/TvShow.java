package com.maxi182.android.mvpawesometvshow.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by maximilianoferraiuolo on 08/11/2016.
 */

public class TvShow extends RealmObject {

    public String SerieName;
    @PrimaryKey
    public String SerieId;
    public RealmList<Character> results;

}
