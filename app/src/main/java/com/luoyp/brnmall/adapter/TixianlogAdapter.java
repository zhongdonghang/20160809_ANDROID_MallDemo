package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luoyp.brnmall.R;
import com.luoyp.brnmall.SysUtils;
import com.luoyp.brnmall.model.TixianLogModel;

import java.util.List;

/**
 * Created by lyp3314@gmail.com on 16/6/22.
 */

public class TixianlogAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<TixianLogModel> mList;

    public TixianlogAdapter(Context context, List<TixianLogModel> list) {
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public TixianLogModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_tixianlog, null);
            holder = new ViewHolder();
            holder.jine = (TextView) convertView.findViewById(R.id.tv_tixianjine);
            holder.state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.addTime = (TextView) convertView.findViewById(R.id.tv_addtime);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.jine.setText("提现金额:   " + getItem(position).getApplyAmount());
        if (getItem(position).getState() == 1) {
            holder.state.setText("申请中");
        }
        if (getItem(position).getState() == 2) {
            holder.state.setText("不通过");
        }
        if (getItem(position).getState() == 3) {
            holder.state.setText("已通过");
        }

        holder.addTime.setText(SysUtils.getDate(getItem(position).getApplyTime()));

        holder.name.setText(getItem(position).getApplyRemark());

        return convertView;
    }


    public static class ViewHolder {
        TextView jine;
        TextView state;
        TextView addTime;
        TextView name;
    }
}
