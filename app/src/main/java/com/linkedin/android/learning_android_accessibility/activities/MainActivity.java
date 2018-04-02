package com.linkedin.android.learning_android_accessibility.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linkedin.android.learning_android_accessibility.R;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isUpEnabled() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = getBaseContext();

        // Login
        Button loginbutton = findViewById(R.id.main_login_button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.newIntent(context));
            }
        });

        // Settings
        Button settingsButton = findViewById(R.id.main_settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettingsActivity.newIntent(context));
            }
        });

        // TabLayout
        Button tabLayoutButton = findViewById(R.id.main_tablayout_button);
        tabLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TabLayoutActivity.newIntent(context));
            }
        });

        // Single Activity
        Button singleActivityButton = findViewById(R.id.main_single_button);
        singleActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SingleActivity.newIntent(context));
            }
        });

        // Cards
        Button cardsButton = findViewById(R.id.main_cards_button);
        cardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CardsActivity.newIntent(context));
            }
        });

        // Custom View
        Button customViewButton = findViewById(R.id.main_custom_button);
        customViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CustomViewActivity.newIntent(context));
            }
        });

        // Charts
        Button chartsButton = findViewById(R.id.main_charts_button);
        chartsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ChartsActivity.newIntent(context));
            }
        });
    }
}

