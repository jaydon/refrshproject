package com.luoxf.drag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DragActivity extends AppCompatActivity implements View.OnClickListener{
    private View dragDemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_layout);
        initView();
    }

    private void initView() {
        dragDemo = findViewById(R.id.drag_demo);
        dragDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
