package com.maxi182.android.mvpawesometvshow.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.animation.AnimatorSet;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maxi182.android.mvpawesometvshow.R;
import com.maxi182.android.mvpawesometvshow.model.Character;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

/**
 * Created by maximilianoferraiuolo on 09/11/2016.
 */

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersAdapterHolder> {

    private Context mContext;
    private AdapterCallbacks mCallbacks;
    private List<Character> mList;
    private boolean mAnimate;
    private boolean mState;
    private final Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();

    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    public CharactersAdapter(Context context, AdapterCallbacks callbacks, boolean state) {
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mState = state;
        this.mList = Collections.emptyList();

    }

    @Override
    public CharactersAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new CharactersAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CharactersAdapterHolder holder, final int position) {
        final Character user = mList.get(position);

        holder.text_name.setText(user.Name);
        holder.text_chapters.setText(String.valueOf(user.chapters));
        holder.text_ocupation.setText(user.occupation);
        holder.img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnimate = true;
                updateHeartButton(user, holder, true);
                mCallbacks.onFavPress(user, position);
            }
        });

        if (mAnimate) {
            updateHeartButton(user, holder, true);
        } else {
            handleFavIcon(holder, user);
        }

        Glide.with(holder.itemView.getContext())
                .load(user.ImgUrl)
                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_vector_profile_circle))
                .centerCrop()
                .dontAnimate()
                .into(holder.img_pic);

    }

    private void handleFavIcon(CharactersAdapterHolder holder, Character character) {
        if (character.isFav) {
            holder.img_fav.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_vector_favorite_filled_24dp));
        } else {
            holder.img_fav.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_vector_favorite_border_24dp));
        }
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

        void onFavPress(Character character, int pos);
    }

    private void updateHeartButton(final Character character, final CharactersAdapterHolder holder, boolean animated) {
        if (animated) {

            AnimatorSet animatorSet = new AnimatorSet();
            likeAnimations.put(holder, animatorSet);

            ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(holder.img_fav, "rotation", 0f, 360f);
            rotationAnim.setDuration(300);
            rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

            ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.img_fav, "scaleX", 0.2f, 1f);
            bounceAnimX.setDuration(300);
            bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.img_fav, "scaleY", 0.2f, 1f);
            bounceAnimY.setDuration(300);
            bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);

            bounceAnimY.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {

                    if (character.isFav) {
                        holder.img_fav.setImageResource(R.drawable.ic_vector_favorite_filled_24dp);
                    } else {
                        holder.img_fav.setImageResource(R.drawable.ic_vector_favorite_border_24dp);
                    }
                }
            });

            animatorSet.play(rotationAnim);
            animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAnimate = false;
                }
            });
            animatorSet.start();

        }
    }
}
