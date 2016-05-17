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
import com.luoyp.brnmall.SysUtils;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.CategoryGoodsModel;
import com.socks.library.KLog;

/**
 * Created by MnZi on 2016/5/6.
 */
public class CategoryGoodsAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private CategoryGoodsModel categoryGoodsModel;

    public CategoryGoodsAdapter(Context context, CategoryGoodsModel model) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        categoryGoodsModel = model;
    }

    @Override
    public int getCount() {
        return categoryGoodsModel.getGoodsBeanList().size();
    }

    @Override
    public CategoryGoodsModel.GoodsBean getItem(int position) {
        return categoryGoodsModel.getGoodsBeanList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_category_goods, null);
            holder = new ViewHolder();
            holder.goodsName = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.tv_goods_price);
            holder.goodsIcon = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
            holder.marketPrice = (TextView) convertView.findViewById(R.id.tv_market_price);
            holder.memberPrice = (TextView) convertView.findViewById(R.id.tv_member_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.goodsName.setText(getItem(position).getName());
        holder.goodsPrice.setText("本店价 ￥ " + getItem(position).getShopPrice());
        holder.marketPrice.setText("市场价 ￥ " + getItem(position).getMarketPrice());

        if (App.getPref("isLogin", false)) {
            holder.memberPrice.setText("会员价 ￥" + SysUtils.formatDouble((Double.valueOf(App.getPref("zhekou", "10")) * Double.valueOf(getItem(position).getShopPrice()) * 10 / 100)) + " (" + App.getPref("zhekoutitle", "") + ")");
        } else {
            holder.memberPrice.setText("会员价 ￥ (未登陆)");
        }

        App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + getItem(position).getStoreId()
                + BrnmallAPI.BaseImgUrl2 + getItem(position).getShowImg())
                .placeholder(R.mipmap.logo).error(R.mipmap.logo).into(holder.goodsIcon);
        KLog.d(BrnmallAPI.BaseImgUrl1 + getItem(position).getStoreId()
                + BrnmallAPI.BaseImgUrl2 + getItem(position).getShowImg());
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
