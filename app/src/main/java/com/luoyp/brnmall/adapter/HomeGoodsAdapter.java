package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.model.HomeGoods;

import java.util.List;

/**
 * Created by MnZi on 2016/5/6.
 */
public class HomeGoodsAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<HomeGoods> homeGoodsList;

    public HomeGoodsAdapter(Context context, List<HomeGoods> homeGoodsList) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.homeGoodsList = homeGoodsList;
    }

    @Override
    public int getCount() {
        return homeGoodsList.size();
    }

    @Override
    public HomeGoods getItem(int position) {
        return homeGoodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryGoodsAdapter.ViewHolder holder = null;
        TextView textView = new TextView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120);
        textView.setLayoutParams(params);
        textView.setTextSize(16);
        textView.setBackgroundResource(R.color.colorAccent);
        textView.setTextColor(mContext.getResources().getColorStateList(R.color.white));
        if ("33".equals(getItem(position).getItemType())) {
            textView.setText("情趣用品");
            return textView;
        }
        if ("34".equals(getItem(position).getItemType())) {
            textView.setText("保健品");
            return textView;
        }
        if ("35".equals(getItem(position).getItemType())) {
            textView.setText("保健食品");
            return textView;
        }
        if ("36".equals(getItem(position).getItemType())) {
            textView.setText("保健器材");
            return textView;
        }
        if ("37".equals(getItem(position).getItemType())) {
            textView.setText("化妆品");
            return textView;
        }
        if ("38".equals(getItem(position).getItemType())) {
            textView.setText("洗护用品");
            return textView;
        }
        if ("39".equals(getItem(position).getItemType())) {

            textView.setText("保健礼品");
            return textView;
        }
        convertView = mInflater.inflate(R.layout.item_home_goods, null);
        holder = new CategoryGoodsAdapter.ViewHolder();
        holder.goodsName = (TextView) convertView.findViewById(R.id.tv_goods_name);
        holder.goodsPrice = (TextView) convertView.findViewById(R.id.tv_goods_price);
        holder.goodsIcon = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
        holder.marketPrice = (TextView) convertView.findViewById(R.id.tv_market_price);
        holder.memberPrice = (TextView) convertView.findViewById(R.id.tv_member_price);
        convertView.setTag(holder);

        holder.goodsName.setText(getItem(position).getPname());
        holder.goodsPrice.setText("￥ " + getItem(position).getPrice());
        holder.marketPrice.setText(" ￥ " + getItem(position).getMarkiprice());
        holder.marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// 添加中划线

//        if (App.getPref("isLogin", false)) {
//            holder.memberPrice.setText("  ￥" + SysUtils.formatDouble((Double.valueOf(App.getPref("zhekou", "10")) * Double.valueOf(getItem(position).getShopPrice()) * 10 / 100)) + " (" + App.getPref("zhekoutitle", "") + ")");
//        } else {
//            holder.memberPrice.setText("  ￥ (未登陆)");
//        }
        App.getPicasso().load(getItem(position).getImg()).placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg).into(holder.goodsIcon);


        return convertView;
    }

    public static class ViewHolder {
        TextView goodsName;
        TextView goodsPrice;
        TextView marketPrice;
        TextView memberPrice;
        ImageView goodsIcon;
    }
}
