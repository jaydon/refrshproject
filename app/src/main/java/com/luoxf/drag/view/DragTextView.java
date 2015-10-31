package com.luoxf.drag.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Jaydon on 2015/10/26.
 */
public class DragTextView extends TextView{
    private final String TAG = DragTextView.class.getSimpleName();
    private int mLastX;
    private int mLastY;
    public DragTextView(Context context) {
        super(context);
    }

    public DragTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                int translationX = (int) (ViewHelper.getTranslationX(this) + deltaX);
                int translationY = (int) (ViewHelper.getTranslationY(this) + deltaY);
                Log.e(TAG, "deltaX : " + deltaX + " deltaY : " + deltaY);
                Log.e(TAG, "translationX : " + ViewHelper.getTranslationX(this) + " translationY : " + ViewHelper.getTranslationY(this));
                Log.e(TAG, "left : " + getLeft() + " top : " + getTop());
                ViewHelper.setTranslationX(this, translationX);
                ViewHelper.setTranslationY(this, translationY);

                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}
