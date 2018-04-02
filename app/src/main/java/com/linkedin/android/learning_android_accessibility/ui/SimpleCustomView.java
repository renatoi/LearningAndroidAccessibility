package com.linkedin.android.learning_android_accessibility.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.linkedin.android.learning_android_accessibility.utils.AccessibilityUtils;

public class SimpleCustomView extends View {

    private static final float TEXT_SIZE = AccessibilityUtils.spToPx(48);
    private static final String TEXT = "Custom view!";

    private Paint mTextPaint;
    private Rect mBounds;

    public SimpleCustomView(Context context) {
        super(context);
        init();
    }
    public SimpleCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBounds = new Rect();
        mTextPaint.setTextSize(TEXT_SIZE);
        mTextPaint.getTextBounds(TEXT, 0, TEXT.length(), mBounds);
        mBounds.offset(0, -mBounds.top);

        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(TEXT_SIZE);
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(TEXT, 0, mBounds.bottom, mTextPaint);
    }
}
