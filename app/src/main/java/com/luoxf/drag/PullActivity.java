package com.luoxf.drag;

import com.luoxf.drag.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PullActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_layout);
        initView();
    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
