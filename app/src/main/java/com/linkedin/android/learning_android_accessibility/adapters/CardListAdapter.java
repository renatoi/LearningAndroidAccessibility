package com.linkedin.android.learning_android_accessibility.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkedin.android.learning_android_accessibility.R;
import com.linkedin.android.learning_android_accessibility.models.CardItem;

import java.util.Calendar;
import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private List<CardItem> mData;
    private ItemClickListeners mClickListeners;

    public CardListAdapter(List<CardItem> cardData) {
        mData = cardData;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getAvatarId();
    }

    @NonNull
    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();
        CardItem item = getItem(position);

        holder.mAvatar.setImageResource(item.getAvatarId());
        holder.mName.setText(item.getName());

        // Time and Location
        long when = item.getDate().getTime();
        long now = Calendar.getInstance().getTimeInMillis();
        CharSequence time = DateUtils.getRelativeTimeSpanString(when, now, DateUtils.HOUR_IN_MILLIS);
        String country = item.getCountry();
        String timeLocFormat = context.getResources().getString(R.string.cards_timeloc_format);
        holder.mTimeLoc.setText(String.format(timeLocFormat, time, country));
        holder.mShareText.setText(item.getShareText());
        holder.mImage.setImageResource(item.getImageId());

        // content description for image buttons
        Resources res = context.getResources();
        String name = item.getName();
        String moreOptionsDescription = String.format(res.getString(R.string.cards_card_more_options_button), name);
        String commentDescription = String.format(res.getString(R.string.cards_card_comment_button), name);
        String shareDescription = String.format(res.getString(R.string.cards_card_share_button), name);

        // content description for these buttons change based on state
        String likeDescription;
        String favoriteDescription;

        if (item.isLiked()) {
            likeDescription = String.format(res.getString(R.string.cards_card_unlike_button), name);
            holder.mLikeButton.setColorFilter(Color.BLUE);
            holder.mLikeButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_thumb_up_24dp));
        } else {
            likeDescription = String.format(res.getString(R.string.cards_card_like_button), name);
            holder.mLikeButton.setColorFilter(null);
            holder.mLikeButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_thumb_up_border_24dp));
        }

        if (item.isFavorited()) {
            favoriteDescription = String.format(res.getString(R.string.cards_card_unfavorite_button), name);
            holder.mFavoriteButton.setColorFilter(Color.RED);
            holder.mFavoriteButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_24dp));
        } else {
            favoriteDescription = String.format(res.getString(R.string.cards_card_unfavorite_button), name);
            holder.mFavoriteButton.setColorFilter(null);
            holder.mFavoriteButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_24dp));
        }

        // assigning all content descriptions
//        holder.mMoreOptionsButton.setContentDescription(moreOptionsDescription);
//        holder.mLikeButton.setContentDescription(likeDescription);
//        holder.mCommentButton.setContentDescription(commentDescription);
//        holder.mFavoriteButton.setContentDescription(favoriteDescription);
//        holder.mShareButton.setContentDescription(shareDescription);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public CardItem getItem(int position) {
        return mData.get(position);
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void setClickListener(ItemClickListeners itemClickListeners) {
        this.mClickListeners = itemClickListeners;
    }

    // parent activity will implement these methods for click events
    public interface ItemClickListeners {
        void onLikeClicked(View view, int position);
        void onCommentClicked(View view, int position);
        void onFavoriteClicked(View view, int position);
        void onShareClicked(View view, int position);
        void onMoreOptionsClicked(View view, int position);
    }

    /* ----------------------------------------------------------------
     * View holder
     * ---------------------------------------------------------------- */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mAvatar;
        TextView mName;
        TextView mTimeLoc;
        TextView mShareText;
        ImageView mImage;
        ImageButton mLikeButton;
        ImageButton mCommentButton;
        ImageButton mFavoriteButton;
        ImageButton mShareButton;
        ImageButton mMoreOptionsButton;

        ViewHolder(View itemView) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.cards_card_avatar);
            mName = itemView.findViewById(R.id.cards_card_name);
            mTimeLoc = itemView.findViewById(R.id.cards_card_timeloc);
            mShareText = itemView.findViewById(R.id.cards_card_share_text);
            mImage = itemView.findViewById(R.id.cards_card_image);
            mLikeButton = itemView.findViewById(R.id.cards_card_like);
            mCommentButton = itemView.findViewById(R.id.cards_card_comment);
            mFavoriteButton = itemView.findViewById(R.id.cards_card_favorite);
            mShareButton = itemView.findViewById(R.id.cards_card_share);
            mMoreOptionsButton = itemView.findViewById(R.id.cards_card_more_options);

            ViewCompat.setImportantForAccessibility(mLikeButton, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
            mLikeButton.setContentDescription(null);

            ViewCompat.setImportantForAccessibility(mCommentButton, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
            mCommentButton.setContentDescription(null);

            ViewCompat.setImportantForAccessibility(mFavoriteButton, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
            mFavoriteButton.setContentDescription(null);

            ViewCompat.setImportantForAccessibility(mShareButton, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
            mShareButton.setContentDescription(null);

            ViewCompat.setImportantForAccessibility(mMoreOptionsButton, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
            mMoreOptionsButton.setContentDescription(null);

            mLikeButton.setOnClickListener(this);
            mCommentButton.setOnClickListener(this);
            mFavoriteButton.setOnClickListener(this);
            mShareButton.setOnClickListener(this);
            mMoreOptionsButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            switch (view.getId()) {
                case R.id.cards_card_like:
                    if (mClickListeners != null) {
                        mClickListeners.onLikeClicked(view, position);
                    }
                    break;
                case R.id.cards_card_comment:
                    if (mClickListeners != null) {
                        mClickListeners.onCommentClicked(view, position);
                    }
                    break;
                case R.id.cards_card_favorite:
                    if (mClickListeners != null) {
                        mClickListeners.onFavoriteClicked(view, position);
                    }
                    break;
                case R.id.cards_card_share:
                    if (mClickListeners != null) {
                        mClickListeners.onShareClicked(view, position);
                    }
                    break;
                case R.id.cards_card_more_options:
                    if (mClickListeners != null) {
                        mClickListeners.onMoreOptionsClicked(view, position);
                    }
                    break;
            }
        }
    }
}