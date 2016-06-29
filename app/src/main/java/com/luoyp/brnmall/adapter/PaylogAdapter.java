package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luoyp.brnmall.R;
import com.luoyp.brnmall.model.PayCreditLogModel;

import java.util.List;

/**
 * Created by lyp3314@gmail.com on 16/6/22.
 */

public class PaylogAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<PayCreditLogModel> mList;

    public PaylogAdapter(Context context, List<PayCreditLogModel> list) {
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PayCreditLogModel getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_paylog, null);
            holder = new ViewHolder();
            holder.ketixian = (TextView) convertView.findViewById(R.id.tv_ketixian);
            holder.dongjie = (TextView) convertView.findViewById(R.id.tv_dongjie);
            holder.addTime = (TextView) convertView.findViewById(R.id.tv_addtime);
            holder.des = (TextView) convertView.findViewById(R.id.tv_des);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.ketixian.setText("解冻:   " + getItem(position).getUserAmount());
        holder.dongjie.setText("冻结:   " + getItem(position).getFrozenAmount());
        holder.addTime.setText(getItem(position).getActionTime());

        holder.des.setText("资金明细: \n" + getItem(position).getActionDes());

        return convertView;
    }


    public static class ViewHolder {
        TextView ketixian;
        TextView dongjie;
        TextView addTime;
        TextView des;
    }
}
