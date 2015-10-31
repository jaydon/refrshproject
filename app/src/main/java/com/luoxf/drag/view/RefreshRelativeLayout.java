package com.luoxf.drag.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.luoxf.drag.R;

/**
 * Created by luoxf on 2015/10/27.
 */
public class RefreshRelativeLayout extends ScrollView{
    private final String TAG = RefreshRelativeLayout.class.getSimpleName();
    private int screenHeight;
    private int screenWidth;
    private Context mContext;
    private int mLastX;
    private int mLastY;
    private int touchSlop; //最小滑动距离
    private boolean isNeedToUp = false;
    private View refreshContent; //刷新的头部
    private int refreshContentHeight;
    private int x;
    private int y;
    private Scroller mScroller;
    public RefreshRelativeLayout(Context context) {
        super(context);
        initView(context);
    }

    public RefreshRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        this.mScroller = new Scroller(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;
    }

    //调用此方法滚动到目标位置
    public void scrollerSmoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void scrollerSmoothScrollBy(int dx, int dy) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        refreshContent = findViewById(R.id.refresh_content);
        refreshContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                refreshContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                refreshContentHeight = refreshContent.getHeight();
                scrollTo(0, refreshContentHeight);
                refreshContent.getLayoutParams().height = refreshContentHeight;
                Log.e(TAG, "refreshContentHeight : " + refreshContentHeight);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void fling(int velocityY) {
        int scrollerY = getScrollY();
        //刷新区域出现
        if(scrollerY <= refreshContentHeight) {
            super.fling(velocityY / 10);
        } else {
            super.fling(velocityY);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX();
        y = (int) event.getY();
        mLastX = x;
        mLastY = y;
        Log.e(TAG, "scrollerY : " + getScrollY());
        int scrollerY = getScrollY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //刷新区域出现
                if(scrollerY <= refreshContentHeight) {
                    int deltaY = y - mLastY;
                    smoothScrollBy(0, deltaY);
                    isNeedToUp = true;
                }
                else {
                }
                break;
            case MotionEvent.ACTION_UP:
                //刷新区域出现
                if(scrollerY <= refreshContentHeight) {
                    smoothScrollTo(0,refreshContentHeight);
                } else {
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
