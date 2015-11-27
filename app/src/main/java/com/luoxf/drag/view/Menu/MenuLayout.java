package com.luoxf.drag.view.Menu;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.luoxf.drag.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by luoxf on 2015/10/27.
 */
public class MenuLayout extends FrameLayout{
    private final String TAG = MenuLayout.class.getSimpleName();
    private int screenHeight;
    private int screenWidth;
    private Context mContext;
    private int mLastX;
    private int mLastY;
    private int touchSlop; //最小滑动距离
    private View mainLayout; //主页
    private View childMenuLayout; //菜单
    private View menuIV; //菜单图标
    private int childWidth; //菜单完度
    public MenuLayout(Context context) {
        super(context);
        initView(context);
    }

    public MenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;
        childWidth = 4 * screenWidth / 5;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mainLayout = findViewById(R.id.main_layout);
        childMenuLayout = findViewById(R.id.child_menu_layout);
        menuIV = findViewById(R.id.menu_iv);
        childTranslationX(-screenWidth/4);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                //认为是从左向右滑动
                if((deltaX > 0 && deltaX > touchSlop) || mainLayout.getX() > 0) {
                    return true;
                } else {
                    return false;
                }
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        mLastX = x;
        mLastY = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int deltaX = x - mLastX;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if(mainLayout.getX() + deltaX <= 0) {
                    mainTranslationX(0);
                    childTranslationX(-screenWidth / 4);
                } else if(mainLayout.getX() + deltaX >= childWidth) {
                    mainTranslationX(childWidth);
                    childTranslationX(0);
                }
                else if(mainLayout.getX() > 0) {
                    mainTranslationX(mainLayout.getX() + deltaX);
                    childTranslationX(childMenuLayout.getX() + deltaX / 3);
                } else {
                    //认为是从左向右滑动
                    if(deltaX > 0 && deltaX > touchSlop) {
                        mainTranslationX(mainLayout.getX() + deltaX);
                        childTranslationX(childMenuLayout.getX() + deltaX / 3);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if(mainLayout.getX() <= screenWidth / 4) {
                    mainTranslationX(0);
                    childTranslationX(-screenWidth / 4);
                } else if(mainLayout.getX() > screenWidth / 4 ) {
                    mainTranslationX(childWidth);
                    childTranslationX(0);
                }

                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    /**
     * 侧滑主菜单
     * @param x
     */
    private void mainTranslationX(float x) {
        ViewHelper.setAlpha(menuIV, 1- x / childWidth );
        ViewHelper.setTranslationX(mainLayout, x);
    }

    /**
     * 侧滑菜单选项
     * @param x
     */
    private void childTranslationX(float x) {
        ViewHelper.setTranslationX(childMenuLayout, x);
    }


    public void closeOrOpen() {
        if(mainLayout.getX() == 0) {
            mainTranslationX(childWidth);
            childTranslationX(0);
            ViewHelper.setAlpha(menuIV, 0);
        } else {
            mainTranslationX(0);
            childTranslationX(-screenWidth / 4);
            ViewHelper.setAlpha(menuIV, 100);
        }
    }

}
