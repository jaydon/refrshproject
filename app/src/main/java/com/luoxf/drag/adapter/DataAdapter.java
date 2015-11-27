package com.luoxf.drag.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luoxf.drag.R;
import com.luoxf.drag.bean.DataBean;
import java.util.List;

/**
 * Created by luoxf on 2015/10/28.
 */
public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private Activity mActivity;
    private List<DataBean> mDataSet; //数据集

    public DataAdapter(Activity activity) {
        super();
        this.mActivity = activity;
    }

    public void setData(List<DataBean> datas) {
        mDataSet = datas;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.data_item, viewGroup, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DataViewHolder dataViewHolder, final int i) {
        dataViewHolder.setData(mDataSet.get(i));
        dataViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, i + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}

class DataViewHolder extends RecyclerView.ViewHolder {
    public TextView content;

    public DataViewHolder(View itemView) {
        super(itemView);
        content = (TextView) itemView.findViewById(R.id.content);
    }

    public void setData(DataBean data) {
        content.setText(data.content);
    }
}

