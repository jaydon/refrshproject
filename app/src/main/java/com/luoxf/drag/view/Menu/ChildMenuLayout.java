package com.luoxf.drag.view.Menu;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.luoxf.drag.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by luoxf on 2015/10/27.
 */
public class ChildMenuLayout extends RelativeLayout{
    private final String TAG = ChildMenuLayout.class.getSimpleName();
    private int screenHeight;
    private int screenWidth;
    private Context mContext;
    public ChildMenuLayout(Context context) {
        super(context);
        initView(context);
    }

    public ChildMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChildMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
                layoutParams.width = 4 * screenWidth / 5;
                setLayoutParams(layoutParams);
            }
        });
    }
}
