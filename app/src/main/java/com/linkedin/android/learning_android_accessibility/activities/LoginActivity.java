package com.linkedin.android.learning_android_accessibility.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linkedin.android.learning_android_accessibility.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView mErrorMessage;
    private TextInputLayout mEmailField;
    private TextInputLayout mPasswordField;


    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mErrorMessage = findViewById(R.id.login_error_message);
        mEmailField = findViewById(R.id.login_email_address);
        mPasswordField = findViewById(R.id.login_password);

        Button loginButton = findViewById(R.id.login_login_button);
        loginButton.setOnClickListener(this);

        ViewCompat.setAccessibilityLiveRegion(mErrorMessage,
                ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);
    }

    @Override
    public void onClick(View v) {
        if (!hasEmptyFields()) {
            mErrorMessage.setText(getString(R.string.login_invalid));
            mEmailField.setError(null);
            mPasswordField.setError(null);
        }
    }

    private boolean hasEmptyFields() {
        boolean hasEmptyFields = false;
        EditText emailEditText = mEmailField.getEditText();
        EditText passwordEditText = mPasswordField.getEditText();
        if (emailEditText != null && TextUtils.isEmpty(emailEditText.getText())) {
            mEmailField.setError(getString(R.string.login_email_required));
            mEmailField.requestFocus();
            mEmailField.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
            hasEmptyFields = true;
        } else if (passwordEditText != null && TextUtils.isEmpty(passwordEditText.getText())) {
            mPasswordField.setError(getString(R.string.login_password_required));
            mPasswordField.requestFocus();
            mPasswordField.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
            hasEmptyFields = true;
        }
        return hasEmptyFields;
    }
}
