package com.linkedin.android.learning_android_accessibility.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linkedin.android.learning_android_accessibility.R;
import com.linkedin.android.learning_android_accessibility.adapters.TabLayoutPagerAdapter;

public class TabLayoutActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, TabLayoutActivity.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // grab references
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.tab_layout_view_pager);

        // set up the adapter
        Resources res = getResources();
        TabLayoutPagerAdapter mViewPagerAdapter = new TabLayoutPagerAdapter(
                getSupportFragmentManager(), res.getStringArray(R.array.tab_layout_tabs_content));

        // set up view pager
        viewPager.setAdapter(mViewPagerAdapter);

        // set up tab layout
        tabLayout.setupWithViewPager(viewPager);

        // configure tabs
        String[] tabNames = res.getStringArray(R.array.tab_layout_tabs);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);

            // set tab nameView and content description to announce role and value
            if (tab != null && tabNames[i] != null) {
                tab.setText(tabNames[i]);
            }
        }
    }
}
