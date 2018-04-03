package com.linkedin.android.learning_android_accessibility.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.linkedin.android.learning_android_accessibility.R;
import com.linkedin.android.learning_android_accessibility.adapters.CardListAdapter;
import com.linkedin.android.learning_android_accessibility.models.CardItem;
import com.linkedin.android.learning_android_accessibility.utils.Utils;

import java.util.ArrayList;

public class CardsActivity extends BaseActivity implements CardListAdapter.ItemClickListeners {

    private RecyclerView mRecyclerView;
    private CardListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, CardsActivity.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_cards;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // populate some data
        ArrayList<CardItem> cardItems = new ArrayList<>();
        CardItem cardItem0 = new CardItem();
        cardItem0.setAvatarId(R.drawable.avatar_jane_doe);
        cardItem0.setName("Jane Doe");
        cardItem0.setDate(Utils.getNearDate());
        cardItem0.setCountry("Brazil");
        cardItem0.setShareText("Beautiful place!");
        cardItem0.setImageId(R.drawable.iguazu_falls);
        cardItems.add(cardItem0);

        CardItem cardItem1 = new CardItem();
        cardItem1.setAvatarId(R.drawable.avatar_john_doe);
        cardItem1.setName("John Doe");
        cardItem1.setDate(Utils.getNearDate());
        cardItem1.setCountry("United States");
        cardItem1.setShareText("New adventures last weekend and possibly one of the most beautiful places I've been!");
        cardItem1.setImageId(R.drawable.niagara_falls);
        cardItems.add(cardItem1);

        CardItem cardItem2 = new CardItem();
        cardItem2.setAvatarId(R.drawable.avatar_rebecca_williams);
        cardItem2.setName("Rebecca Williams");
        cardItem2.setDate(Utils.getNearDate());
        cardItem2.setCountry("United States");
        cardItem2.setShareText("No words to describe this place!");
        cardItem2.setImageId(R.drawable.yosemite);
        cardItems.add(cardItem2);

        // setup adapter
        mAdapter = new CardListAdapter(cardItems);
        mAdapter.setClickListener(this);

        // setup recycler view
        mRecyclerView = findViewById(R.id.cards_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        setUpItemTouchHelper();
    }

    @Override
    public void onLikeClicked(View view, int position) {
        CardItem item = mAdapter.getItem(position);
        item.setLiked(!item.isLiked());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCommentClicked(View view, int position) {
        Toast.makeText(this, "Comment is disabled!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavoriteClicked(View view, int position) {
        CardItem item = mAdapter.getItem(position);
        item.setFavorited(!item.isFavorited());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShareClicked(View view, int position) {
        String shareBody = mAdapter.getItem(position).getShareText();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        String chooserTitle = getResources().getString(R.string.cards_share_via);

        startActivity(Intent.createChooser(shareIntent, chooserTitle));
    }

    @Override
    public void onMoreOptionsClicked(View view, final int position) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.cards_card_more_options_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                mAdapter.removeItem(position);
                return true;
            }
        });

        popup.show();
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable archiveIcon;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                archiveIcon = ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_archive_24dp);
                initiated = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                CardListAdapter adapter = (CardListAdapter) mRecyclerView.getAdapter();
                adapter.removeItem(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = archiveIcon.getIntrinsicWidth();
                int intrinsicHeight = archiveIcon.getIntrinsicWidth();

                int left = itemView.getRight() - intrinsicWidth;
                int right = itemView.getRight();
                int top = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int bottom = top + intrinsicHeight;
                archiveIcon.setBounds(left, top, right, bottom);
                archiveIcon.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}
