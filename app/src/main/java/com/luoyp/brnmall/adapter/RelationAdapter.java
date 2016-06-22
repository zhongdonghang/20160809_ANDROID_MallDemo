package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luoyp.brnmall.R;
import com.luoyp.brnmall.model.RelationModel;

import java.util.List;

/**
 * Created by lyp3314@gmail.com on 16/6/22.
 */

public class RelationAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<RelationModel> mList;

    public RelationAdapter(Context context, List<RelationModel> list) {
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public RelationModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        TextView textView = new TextView(mContext);
        ViewGroup.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120);
        textView.setLayoutParams(params);
        textView.setTextSize(15);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(25, 5, 5, 0);
        textView.setTextColor(mContext.getResources().getColorStateList(R.color.textColor));
        if ("-1".equals(getItem(position).getAddTime())) {
            textView.setText("我的介绍人");
            return textView;
        }
        if ("-2".equals(getItem(position).getAddTime())) {
            textView.setText("我介绍的会员");
            return textView;
        }

        convertView = mInflater.inflate(R.layout.item_relation, null);
        holder = new ViewHolder();
        holder.nickName = (TextView) convertView.findViewById(R.id.tv_nickname);
        holder.realName = (TextView) convertView.findViewById(R.id.tv_realname);
        holder.addTime = (TextView) convertView.findViewById(R.id.tv_addtime);

        convertView.setTag(holder);

        holder.nickName.setText(getItem(position).getNickName().replace(" ", ""));
        holder.realName.setText(getItem(position).getRealName().replace(" ", ""));
        holder.addTime.setText(getItem(position).getAddTime().replace(" ", ""));


        return convertView;
    }


    public static class ViewHolder {
        TextView nickName;
        TextView realName;
        TextView addTime;

    }
}
