package com.maxi182.android.mvpawesometvshow.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maxi182.android.mvpawesometvshow.R;
import com.maxi182.android.mvpawesometvshow.model.Character;

import java.util.Collections;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by maximilianoferraiuolo on 09/11/2016.
 */

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersAdapterHolder> {

    private Context mContext;
    private AdapterCallbacks mCallbacks;
    private List<Character> mList;


    public CharactersAdapter(Context context, AdapterCallbacks callbacks) {
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mList = Collections.emptyList();

    }

    @Override
    public CharactersAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new CharactersAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CharactersAdapterHolder holder, int position) {
        final Character user = mList.get(position);

        holder.text_name.setText(user.Name);
        holder.text_chapters.setText(String.valueOf(user.chapters));
        holder.text_ocupation.setText(user.occupation);
        holder.img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onFavPress(user.mCharacterId);
            }
        });

        Glide.with(holder.itemView.getContext())
                .load(user.ImgUrl)
                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_vector_profile_circle))
                .centerCrop()
                .dontAnimate()
                .into(holder.img_pic);

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setCharacters(RealmList<Character> characters) {
        this.mList = characters;
    }


    public static class CharactersAdapterHolder extends RecyclerView.ViewHolder {
        public TextView text_name;
        public TextView text_ocupation;
        public TextView text_chapters;
        public ImageView img_pic;
        public ImageView img_fav;


        public CharactersAdapterHolder(View itemView) {
            super(itemView);

            text_name = (TextView) itemView.findViewById(R.id.text_name);
            text_ocupation = (TextView) itemView.findViewById(R.id.text_ocupation);
            text_chapters = (TextView) itemView.findViewById(R.id.text_chapters);
            img_pic = (ImageView) itemView.findViewById(R.id.img_pic);
            img_fav = (ImageView) itemView.findViewById(R.id.img_fav);
        }
    }

    public interface AdapterCallbacks {
        void onItemPress();

        void onFavPress(int id);
    }
}
