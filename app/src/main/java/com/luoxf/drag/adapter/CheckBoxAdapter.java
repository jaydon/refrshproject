package com.luoxf.drag.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.luoxf.drag.R;
import com.luoxf.drag.bean.DataBean;

import java.util.List;

/**
 * Created by Jaydon on 2015/11/2.
 */
public class CheckBoxAdapter  extends RecyclerView.Adapter<CheckBoxViewHolder> {
    private final String TAG = CheckBoxAdapter.class.getSimpleName();
    private Activity mActivity;
    private List<DataBean> mDataSet; //数据集

    public CheckBoxAdapter(Activity activity) {
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
    public CheckBoxViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.checkbox_item, viewGroup, false);
        return new CheckBoxViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CheckBoxViewHolder checkBoxViewHolder, final int i) {
        checkBoxViewHolder.setData(mDataSet.get(i));
        checkBoxViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxViewHolder.checkBox.isChecked()) {
                    checkBoxViewHolder.checkBox.setChecked(false);
                } else {
                    checkBoxViewHolder.checkBox.setChecked(true);
                }
                Log.e(TAG, " checkBoxViewHolder.checkBox.isChecked() : " + checkBoxViewHolder.checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}

class CheckBoxViewHolder extends RecyclerView.ViewHolder {
    public TextView content;
    public CheckBox checkBox;

    public CheckBoxViewHolder(View itemView) {
        super(itemView);
        content = (TextView) itemView.findViewById(R.id.content);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
    }

    public void setData(DataBean data) {
        content.setText(data.content);
        checkBox.setChecked(data.check);
    }
}

