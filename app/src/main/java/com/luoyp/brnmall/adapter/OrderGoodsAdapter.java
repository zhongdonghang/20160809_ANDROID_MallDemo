package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luoyp.brnmall.R;
import com.luoyp.brnmall.model.OrderDetailModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by MnZi on 2016/5/12.
 */
public class OrderGoodsAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<OrderDetailModel.OrderGoodsBean> mList;

    public OrderGoodsAdapter(Context context, List<OrderDetailModel.OrderGoodsBean> list) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public OrderDetailModel.OrderGoodsBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_order_goods,null);
            holder = new ViewHolder();
            holder.goodsName = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.goodsNum = (TextView) convertView.findViewById(R.id.tv_goods_num);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.tv_goods_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.goodsName.setText(getItem(position).getName());
        holder.goodsNum.setText("x"+getItem(position).getBuyCount());

        BigDecimal bNum = new BigDecimal(getItem(position).getBuyCount());
        BigDecimal bPrice = new BigDecimal(getItem(position).getShopPrice());
        String price = bPrice.multiply(bNum).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
        holder.goodsPrice.setText("￥" + price);

        return convertView;
    }

    public static class ViewHolder{
        TextView goodsName;
        TextView goodsNum;
        TextView goodsPrice;
    }
}
