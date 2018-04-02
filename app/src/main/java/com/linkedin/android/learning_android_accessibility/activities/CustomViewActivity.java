package com.linkedin.android.learning_android_accessibility.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linkedin.android.learning_android_accessibility.R;

public class CustomViewActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, CustomViewActivity.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_custom_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
