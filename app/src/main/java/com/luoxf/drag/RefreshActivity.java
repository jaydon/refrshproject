package com.luoxf.drag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.luoxf.drag.adapter.DataAdapter;
import com.luoxf.drag.bean.DataBean;
import com.luoxf.drag.view.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class RefreshActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView dataRV;
    private LinearLayoutManager layoutManager; //线性列表
    private DataAdapter dataAdapter;
    private List<DataBean> dataBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_layout);
        initView();
        initData();
    }

    private void initView() {
        dataRV = (RecyclerView) findViewById(R.id.data_rv);
    }

    private void initData() {
        layoutManager = new FullyLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataAdapter = new DataAdapter(this);
        for(int i = 0; i < 15; i++) {
            DataBean dataBean = new DataBean();
            dataBean.content = "这是个测试" + i;
            dataBeanList.add(dataBean);

        }
        dataAdapter.setData(dataBeanList);
        dataRV.setLayoutManager(layoutManager);
        dataRV.setAdapter(dataAdapter);
        dataRV.setItemAnimator(new DefaultItemAnimator());
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
