package com.luoyp.brnmall.adapter;

import android.content.Context;
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_home_goods, null);
            holder = new ViewHolder();
            holder.goodsName = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.tv_goods_price);
            holder.goodsIcon = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
            holder.marketPrice = (TextView) convertView.findViewById(R.id.tv_market_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.goodsName.setText(getItem(position).getPname());
        holder.goodsPrice.setText("￥ " + getItem(position).getPrice());
//        holder.marketPrice.setText("市场价 ￥ " + getItem(position).getMarketPrice());
        App.getPicasso().load(getItem(position).getImg()).placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg).into(holder.goodsIcon);
//        KLog.d(BrnmallAPI.BaseImgUrl1 + getItem(position).getStoreId()
//                +BrnmallAPI.BaseImgUrl2+ getItem(position).getShowImg());
        return convertView;
    }

    public static class ViewHolder {
        TextView goodsName;
        TextView goodsPrice;
        TextView marketPrice;
        ImageView goodsIcon;
    }
}
