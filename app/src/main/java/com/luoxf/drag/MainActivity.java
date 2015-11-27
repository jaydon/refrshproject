package com.luoxf.drag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private View dragDemo; //拖动DEMO
    private View scrollerDemo; // ScrollerActivity
    private View pullDemo; //PullActivity
    private View refreshDemo; //RefreshActivity
    private View checkboxDemo; // CheckBox;
    private View menuDemo; // menuDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        dragDemo = findViewById(R.id.drag_demo);
        dragDemo.setOnClickListener(this);
        scrollerDemo = findViewById(R.id.scroller_demo);
        scrollerDemo.setOnClickListener(this);
        pullDemo = findViewById(R.id.pull_demo);
        pullDemo.setOnClickListener(this);
        refreshDemo = findViewById(R.id.refresh_demo);
        refreshDemo.setOnClickListener(this);
        checkboxDemo = findViewById(R.id.checkbox_demo);
        checkboxDemo.setOnClickListener(this);
        menuDemo = findViewById(R.id.menu_demo);
        menuDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.drag_demo:
                intent.setClass(this, DragActivity.class);
                startActivity(intent);
                break;
            case R.id.scroller_demo:
                intent.setClass(this, ScrollerActivity.class);
                startActivity(intent);
                break;
            case R.id.pull_demo:
                intent.setClass(this, PullActivity.class);
                startActivity(intent);
                break;
            case R.id.refresh_demo:
                intent.setClass(this, RefreshActivity.class);
                startActivity(intent);
                break;

            case R.id.checkbox_demo:
                intent.setClass(this, CheckBoxActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_demo:
                intent.setClass(this, MenuActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
