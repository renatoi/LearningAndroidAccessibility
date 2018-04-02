package com.linkedin.android.learning_android_accessibility.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.linkedin.android.learning_android_accessibility.R;
import com.linkedin.android.learning_android_accessibility.fragments.DetailFragment;
import com.linkedin.android.learning_android_accessibility.fragments.ListFragment;

public class SingleActivity extends BaseActivity implements ListFragment.ItemClickListener,
        DetailFragment.AnimationListener {

    private Fragment mListFragment;
    private Fragment mDetailFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, SingleActivity.class);
    }

    @IdRes
    private static final int mFragmentContainerId = R.id.single_fragment_container;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_single;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // the fragment
        mListFragment = ListFragment.newInstance();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(mFragmentContainerId, mListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListItemClicked(View view, int position) {
        mDetailFragment = DetailFragment.newInstance();
        getSupportFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
            .add(mFragmentContainerId, mDetailFragment)
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void onAnimationEnded() {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(mListFragment)
                .commit();
    }
}
