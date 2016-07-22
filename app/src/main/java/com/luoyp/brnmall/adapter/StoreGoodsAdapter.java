package com.luoyp.brnmall.adapter;

import android.content.Context;
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
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.CategoryGoodsModel;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 * Created by MnZi on 2016/5/6.
 */
public class StoreGoodsAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CategoryGoodsModel.GoodsBean> goodsList;

    public StoreGoodsAdapter(Context context, List<CategoryGoodsModel.GoodsBean> goodsList) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.goodsList = goodsList;
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public CategoryGoodsModel.GoodsBean getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_store_goods, null);
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
                    EventBus.getDefault().post(getItem(position).getPid()+"", "store_add_tocart");
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.goodsName.setText(getItem(position).getName());
        holder.goodsPrice.setText("商城价￥ " + getItem(position).getShopPrice());
        holder.marketPrice.setText("市场价￥ " + getItem(position).getMarketPrice());
        holder.marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// 添加中划线

        App.getPicasso().load(BrnmallAPI.BaseImgUrl1+getItem(position).getStoreId()+BrnmallAPI.BaseImgUrl2+getItem(position).getShowImg())
                .placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg).into(holder.goodsIcon);


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
