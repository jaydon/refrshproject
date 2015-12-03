package com.luoxf.drag.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import com.luoxf.drag.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by luoxf on 2015/10/27.
 */
public class PullRelativeLayout extends RelativeLayout{
    private final int BEFOREREFRESH = 1;
    private final int UPREFRESH = 2;
    private final int REFRESHING = 3;
    private int flag = 0;
    private final String TAG = PullRelativeLayout.class.getSimpleName();
    private int screenHeight;
    private int screenWidth;
    private Context mContext;
//    private ScrollerCompat mScroller;
    private Scroller mScroller;
    private int mLastX;
    private int mLastY;
    private int touchSlop; //最小滑动距离
    private boolean isNeedToUp;
    private View refreshContent; //刷新内容
    private int refreshContentHeight; //刷新高度
    private View content;
    private TextView refreshText; //刷新文本
    private View refreshIcon; //刷新图标
    private Handler handler;
    private VelocityTracker velocityTracker; // 速度捕捉器
    public PullRelativeLayout(Context context) {
        super(context);
        initView(context);
    }

    public PullRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * Interpolator defining the animation curve for mScroller
     */
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    private void initView(Context context) {
        this.mContext = context;
        this.mScroller = new Scroller(context, sInterpolator);
//        this.mScroller = ScrollerCompat.create(context, sInterpolator);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;
        handler = new Handler() {
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what) {
                    //正在刷新
                    case REFRESHING:
                        if(refreshText.getText().toString().equals(mContext.getString(R.string.refresh_three))) {
                            flag = 0;
                            smoothScrollTo(0, refreshContentHeight);
                        }
                        break;
                }
            }
        };
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        refreshContent = findViewById(R.id.refresh_content);
        content = findViewById(R.id.content);
        refreshText = (TextView) findViewById(R.id.refresh_text);
        refreshIcon = findViewById(R.id.refresh_icon);
        refreshContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                refreshContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                refreshContentHeight = refreshContent.getHeight();
                mScroller.setFinalY(refreshContentHeight);
                scrollTo(0, refreshContentHeight);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                layoutParams.height = getHeight() + refreshContentHeight;
                layoutParams.width = getWidth();
                setLayoutParams(layoutParams);
            }
        });
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
                int deltaY = y - mLastY;
                //子view需要
                if(mScroller.getFinalY() == refreshContentHeight && (deltaY < 0 || content.getScrollY() != 0)) {
                    return false;
                } else {
                    velocityTracker = VelocityTracker.obtain();
                    velocityTracker.addMovement(ev);
                    return true;
                }
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }

        mLastX = x;
        mLastY = y;
        return intercepted;
    }

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {
        velocityTracker.computeCurrentVelocity(1000);
        if(Math.abs(dy) < refreshContentHeight * 2) {
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, 500);
        } else {
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, 2000);
        }
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
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastY;
                //改变刷新图标和文字
                //改变刷新图标和文字
                if(mScroller.getFinalY() <= 0) {
                    //变成松开刷新
                    if(flag == 0) {
                        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_180_animation);
                        refreshIcon.startAnimation(animation);
                        flag = 1;
                        refreshText.setText(mContext.getString(R.string.refresh_two));
                    }
                } else {
                    //变成下拉刷新
                    if(flag == 1) {
                        refreshText.setText(mContext.getString(R.string.refresh_first));
                        flag = 0;
                        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_360_animation);
                        refreshIcon.startAnimation(animation);
                    }
                }

                //滑到顶部
                if(mScroller.getFinalY() - deltaY > refreshContentHeight) {
                    smoothScrollTo(0, refreshContentHeight);
                    isNeedToUp = false;
                }
                //下滑大于屏幕三分之一
                else if(mScroller.getFinalY() - deltaY < -screenHeight / 3) {
                    smoothScrollTo(0, -screenHeight / 3);
                    isNeedToUp = true;
                } else {
                    smoothScrollBy(0, -deltaY);
                    isNeedToUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //自动滑到顶部
                if(isNeedToUp) {
                    //正在刷新
                    if(mScroller.getFinalY() <= 0) {
                        smoothScrollTo(0, 0);
                        refreshText.setText(mContext.getString(R.string.refresh_three));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    handler.sendEmptyMessage(REFRESHING);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {
                        smoothScrollTo(0, refreshContentHeight);
                    }
                }
                //清除速度
                velocityTracker.clear();
                velocityTracker.recycle();
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}
