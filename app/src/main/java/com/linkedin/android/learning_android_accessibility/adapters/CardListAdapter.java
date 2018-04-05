package com.linkedin.android.learning_android_accessibility.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkedin.android.learning_android_accessibility.R;
import com.linkedin.android.learning_android_accessibility.models.CardItem;
import com.linkedin.android.learning_android_accessibility.utils.AccessibilityUtils;

import java.util.Calendar;
import java.util.List;

import static android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private List<CardItem> mData;
    private ItemClickListeners mClickListeners;

    public CardListAdapter(List<CardItem> cardData) {
        mData = cardData;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Context context = holder.itemView.getContext();
        final CardItem item = getItem(position);

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
        final Resources res = context.getResources();
        String name = item.getName();
        String moreOptionsDescription = String.format(res.getString(R.string
                .cards_card_more_options_button), name);
        final String commentDescription = String.format(res.getString(R.string
                .cards_card_comment_button), name);
        final String shareDescription = String.format(res.getString(R.string
                .cards_card_share_button), name);

        // content description for these buttons change based on state:
        final String likeDescription;
        final String favoriteDescription;

        if (item.isLiked()) {
            likeDescription = String.format(res.getString(R.string.cards_card_unlike_button), name);
            holder.mLikeButton.setColorFilter(Color.BLUE);
            holder.mLikeButton.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_thumb_up_24dp));
        } else {
            likeDescription = String.format(res.getString(R.string.cards_card_like_button), name);
            holder.mLikeButton.setColorFilter(null);
            holder.mLikeButton.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_thumb_up_border_24dp));
        }

        if (item.isFavorited()) {
            favoriteDescription = String.format(res.getString(R.string.cards_card_unfavorite_button), name);
            holder.mFavoriteButton.setColorFilter(Color.RED);
            holder.mFavoriteButton.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_favorite_24dp));
        } else {
            favoriteDescription = String.format(res.getString(R.string.cards_card_favorite_button), name);
            holder.mFavoriteButton.setColorFilter(null);
            holder.mFavoriteButton.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_favorite_border_24dp));
        }

        // assign all content descriptions to image buttons
//        holder.mMoreOptionsButton.setContentDescription(moreOptionsDescription);
//        holder.mLikeButton.setContentDescription(likeDescription);
//        holder.mCommentButton.setContentDescription(commentDescription);
//        holder.mFavoriteButton.setContentDescription(favoriteDescription);
//        holder.mShareButton.setContentDescription(shareDescription);

        holder.mMoreOptionsButton.setContentDescription(null);
        ViewCompat.setImportantForAccessibility(holder.mMoreOptionsButton, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);

        holder.mLikeButton.setContentDescription(null);
        ViewCompat.setImportantForAccessibility(holder.mLikeButton, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);

        holder.mCommentButton.setContentDescription(null);
        ViewCompat.setImportantForAccessibility(holder.mCommentButton, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);

        holder.mFavoriteButton.setContentDescription(null);
        ViewCompat.setImportantForAccessibility(holder.mFavoriteButton, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);

        holder.mShareButton.setContentDescription(null);
        ViewCompat.setImportantForAccessibility(holder.mShareButton, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);


        ViewCompat.setAccessibilityDelegate(holder.itemView, new AccessibilityDelegateCompat() {
            @Override
            public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onInitializeAccessibilityEvent(host, event);
                List<CharSequence> descriptions = AccessibilityUtils.getDescriptions(host);
                if (item.isLiked()) {
                    descriptions.add(res.getString(R.string.cards_card_liked));
                }
                if (item.isFavorited()) {
                    descriptions.add(res.getString(R.string.cards_card_favorited));
                }

                event.getText().addAll(descriptions);
            }

            @Override
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);

                List<CharSequence> descriptions = AccessibilityUtils.getDescriptions(host);
                if (item.isLiked()) {
                    descriptions.add(res.getString(R.string.cards_card_liked));
                }
                if (item.isFavorited()) {
                    descriptions.add(res.getString(R.string.cards_card_favorited));
                }
                info.setContentDescription(TextUtils.join(", ", descriptions));
                info.addAction(new AccessibilityActionCompat(R.id.action_card_like, likeDescription));
                info.addAction(new AccessibilityActionCompat(R.id.action_card_comment, commentDescription));
                info.addAction(new AccessibilityActionCompat(R.id.action_card_favorite, favoriteDescription));
                info.addAction(new AccessibilityActionCompat(R.id.action_card_share, shareDescription));
                info.addAction(new AccessibilityActionCompat(R.id.action_card_archive, res
                        .getString(R.string.cards_card_archive_this_post)));
                info.addAction(new AccessibilityActionCompat(R.id.action_card_remove, res
                        .getString(R.string.cards_card_remove_this_post)));
                info.addAction(new AccessibilityActionCompat(R.id.action_card_report, res
                        .getString(R.string.cards_card_report_this_post)));
            }

            @Override
            public boolean performAccessibilityAction(View host, int action, Bundle args) {

                switch (action) {
                    case R.id.action_card_like:
                        holder.onClick(holder.mLikeButton);
                        return true;
                    case R.id.action_card_comment:
                        holder.onClick(holder.mCommentButton);
                        return true;
                    case R.id.action_card_favorite:
                        holder.onClick(holder.mFavoriteButton);
                        return true;
                    case R.id.action_card_share:
                        holder.onClick(holder.mShareButton);
                        return true;
                    case R.id.action_card_archive:
                        removeItem(position);
                        return true;
                    case R.id.action_card_remove:
                        removeItem(position);
                        return true;
                    case R.id.action_card_report:
                        removeItem(position);
                        return true;
                }

                return super.performAccessibilityAction(host, action, args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getAvatarId();
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