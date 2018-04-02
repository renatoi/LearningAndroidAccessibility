package com.linkedin.android.learning_android_accessibility.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.linkedin.android.learning_android_accessibility.R;

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Activities that extend this class must provide a layout resource id so that it can set the
     * content view onCreate.
     * @return the layout resource id.
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * Child classes can override this method to prevent the up arrow to show up in the toolbar.
     * @return true to show the up arrow in the toolbar.
     */
    protected boolean isUpEnabled() {
        return true;
    }

    /**
     * The onCreate method for the activity. The base class implementation inflates the layout
     * provided in getLayoutResId() and sets the toolbar up for the activity.
     * @param savedInstanceState The bundle for saving instance data
     */
    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            // toolbar blocks focus by default, remove this behavior
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setTouchscreenBlocksFocus(false);
            }

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(isUpEnabled());
            }
        }
    }
}