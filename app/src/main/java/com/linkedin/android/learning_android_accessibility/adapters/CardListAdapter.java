package com.linkedin.android.learning_android_accessibility.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
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

        if (item.isLiked()) {
            holder.mLikeButton.setColorFilter(Color.BLUE);
        } else {
            holder.mLikeButton.setColorFilter(null);
        }

        if (item.isFavorited()) {
            holder.mFavoriteButton.setColorFilter(Color.RED);
        } else {
            holder.mFavoriteButton.setColorFilter(null);
        }
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

            mLikeButton.setOnClickListener(this);
            mCommentButton.setOnClickListener(this);
            mFavoriteButton.setOnClickListener(this);
            mShareButton.setOnClickListener(this);
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
            }
        }
    }
}