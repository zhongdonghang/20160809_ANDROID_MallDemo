package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.model.HomeGoods;

import org.simple.eventbus.EventBus;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View head = mInflater.inflate(R.layout.home_item_head, null);
        TextView textView = (TextView) head.findViewById(R.id.item_head_name);
        View line = head.findViewById(R.id.line);
        Button btn = (Button) head.findViewById(R.id.item_head_more);
        btn.setTag(getItem(position).getItemType());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.cateIndex = getItem(position).getItemType();
                EventBus.getDefault().post(v.getTag().toString(), "homemoreclick");
            }
        });

        if ("33".equals(getItem(position).getItemType())) {
            textView.setText("情趣用品");
            line.setBackgroundColor(Color.parseColor("#A6D17A"));
            return head;
        }
        if ("34".equals(getItem(position).getItemType())) {
            line.setBackgroundColor(Color.parseColor("#FF6D69"));
            textView.setText("保健品");
            return head;
        }

        if ("35".equals(getItem(position).getItemType())) {
            line.setBackgroundColor(Color.parseColor("#A6D17A"));
            textView.setText("保健食品");
            return head;
        }
        if ("36".equals(getItem(position).getItemType())) {
            textView.setText("保健器材");
            line.setBackgroundColor(Color.parseColor("#FF6D69"));
            return head;
        }
        if ("37".equals(getItem(position).getItemType())) {
            line.setBackgroundColor(Color.parseColor("#A6D17A"));
            textView.setText("化妆品");
            return head;
        }
        if ("38".equals(getItem(position).getItemType())) {
            textView.setText("洗护用品");
            line.setBackgroundColor(Color.parseColor("#FF6D69"));
            return head;
        }
        if ("39".equals(getItem(position).getItemType())) {
            line.setBackgroundColor(Color.parseColor("#A6D17A"));
            textView.setText("保健礼品");
            return head;
        }

        convertView = mInflater.inflate(R.layout.item_home_goods, null);
        holder = new ViewHolder();
        holder.goodsName = (TextView) convertView.findViewById(R.id.tv_goods_name);
        holder.goodsPrice = (TextView) convertView.findViewById(R.id.tv_goods_price);
        holder.goodsIcon = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
        holder.marketPrice = (TextView) convertView.findViewById(R.id.tv_market_price);
        holder.memberPrice = (TextView) convertView.findViewById(R.id.tv_member_price);
        holder.addtocart = (Button) convertView.findViewById(R.id.home_add_tocart);
        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(getItem(position).getPid(), "home_add_tocart");
            }
        });
        convertView.setTag(holder);


        holder.goodsName.setText(getItem(position).getPname());
        holder.goodsPrice.setText("现价￥ " + getItem(position).getPrice());
        holder.marketPrice.setText("原价￥ " + getItem(position).getMarkiprice());
        holder.marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// 添加中划线

        App.getPicasso().load(getItem(position).getImg()).placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg).into(holder.goodsIcon);


        return convertView;
    }

    public static class ViewHolder {
        TextView goodsName;
        TextView goodsPrice;
        TextView marketPrice;
        TextView memberPrice;
        ImageView goodsIcon;
        Button addtocart;
    }
}
