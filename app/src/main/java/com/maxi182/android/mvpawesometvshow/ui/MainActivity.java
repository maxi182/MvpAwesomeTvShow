package com.maxi182.android.mvpawesometvshow.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.maxi182.android.mvpawesometvshow.R;
import com.maxi182.android.mvpawesometvshow.api.RestClient;
import com.maxi182.android.mvpawesometvshow.model.Character;
import com.maxi182.android.mvpawesometvshow.model.CharacterDetail;
import com.maxi182.android.mvpawesometvshow.model.TvShow;
import com.maxi182.android.mvpawesometvshow.presenter.CharacterListPresenter;
import com.maxi182.android.mvpawesometvshow.presenter.CharacterListPresenterImpl;
import com.maxi182.android.mvpawesometvshow.ui.adapter.CharactersAdapter;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CharacterListView, CharactersAdapter.AdapterCallbacks {

    private static final String STACK_KEY = "stack";

    private CharacterListPresenter presenter;
    private RecyclerView mRecycler;
    private CharactersAdapter mAdapter;
    private ImageView mImageCloud;
    private FrameLayout mProgress;

    public static Intent getNewIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecycler = (RecyclerView) findViewById(R.id.chars_recycler_view);
        mImageCloud = (ImageView) findViewById(R.id.img_cloud);
        mProgress = (FrameLayout) findViewById(R.id.progress);

        setupRecyclerView(mRecycler);
        presenter = new CharacterListPresenterImpl(this);
        presenter.attachView(this);

        presenter.getCharacters();

    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {

        mAdapter = new CharactersAdapter(this, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void showListResponse(RealmList<Character> data) {

        mAdapter.setCharacters(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDetaiResponse(RealmList<Character> data) {

    }

    @Override
    public void onResponseFailed() {

        mImageCloud.setVisibility(View.VISIBLE);

    }

    private void showSnackBar(boolean status) {

        String snackBarText = status ? getResources().getString(R.string.db_store_success) : getResources().getString(R.string.db_store_fail);

        Snackbar.make(findViewById(android.R.id.content), snackBarText, Snackbar.LENGTH_LONG)
                .show();

    }

    @Override
    public void realmStoreCompleted() {
        showSnackBar(true);
    }

    @Override
    public void realmStoreFailed() {
        showSnackBar(false);
    }

    @Override
    public void showProgress() {

        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onItemPress() {

    }


    @Override
    public void onFavChanged(int pos) {

        mAdapter.notifyItemChanged(pos);
    }

    @Override
    public void onItemPress(int pos) {


    }

    @Override
    public void onFavPress(Character character, int pos) {

        presenter.handleFavorite(character, pos);

    }

    private void changeFragment(Fragment fragment, boolean addToBackStack, boolean animate) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (animate) {
            ft.setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down);
        }
        ft.replace(R.id.container_body, fragment);

        if ((addToBackStack)) {
            ft.addToBackStack(STACK_KEY);
        }
        ft.commit();
    }
}
