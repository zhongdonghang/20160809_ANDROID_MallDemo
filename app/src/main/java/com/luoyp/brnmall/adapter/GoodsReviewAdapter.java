package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luoyp.brnmall.R;
import com.luoyp.brnmall.model.GoodsReview;

import java.util.List;

/**
 * Created by MnZi on 2016/5/6.
 */
public class GoodsReviewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<GoodsReview> goodsList;

    public GoodsReviewAdapter(Context context, List<GoodsReview> goodsList) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.goodsList = goodsList;
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public GoodsReview getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_goods_review, null);
            holder = new ViewHolder();
            holder.userName = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.content = (TextView) convertView.findViewById(R.id.tv_content);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.userName.setText(getItem(position).getNickName());
        holder.time.setText(getItem(position).getReviewTime());
        holder.content.setText(getItem(position).getContent());

        return convertView;
    }

    public static class ViewHolder {
        TextView userName;
        TextView time;
        TextView content;
    }
}
