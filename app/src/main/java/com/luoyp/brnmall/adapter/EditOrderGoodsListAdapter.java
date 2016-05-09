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
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.ShopCartModel;

/**
 * Created by MnZi on 2016/5/1.
 */
public class EditOrderGoodsListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ShopCartModel shopCartModel;

    public EditOrderGoodsListAdapter(Context context, ShopCartModel model) {
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        shopCartModel = model;
    }

    @Override
    public int getCount() {
        return shopCartModel.getCartGoodsBeanList().size();
    }

    @Override
    public ShopCartModel.CartGoodsBean getItem(int position) {
        return shopCartModel.getCartGoodsBeanList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.edit_oder_item, null);
            holder = new ViewHolder();
            holder.goodsName = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.goodsNum = (TextView) convertView.findViewById(R.id.tv_buy_count);
            holder.shopcarImg = (ImageView) convertView.findViewById(R.id.editorderImg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.goodsName.setText(getItem(position).getName());
        holder.goodsNum.setText("x " + getItem(position).getBuyCount());
        App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + getItem(position).getStoreId()
                + BrnmallAPI.BaseImgUrl2 + getItem(position).getShowImg())
                .placeholder(R.mipmap.logo).error(R.mipmap.logo).into(holder.shopcarImg);


        return convertView;
    }

    public static class ViewHolder {
        TextView goodsName;
        TextView goodsNum;
        ImageView shopcarImg;
    }
}
