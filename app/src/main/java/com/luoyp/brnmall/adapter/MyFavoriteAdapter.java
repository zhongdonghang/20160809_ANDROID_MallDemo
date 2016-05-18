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
import com.luoyp.brnmall.model.MyFavoriteModel;

import java.util.List;

/**
 * Created by MnZi on 2016/5/18.
 */
public class MyFavoriteAdapter extends BaseAdapter {

    private Context context;
    private List<Object> mList;
    private String mType;

    public MyFavoriteAdapter(Context context,List<Object> list,String type) {
        this.context = context;
        mList = list;
        mType = type;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_my_favorite, null);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_name);
            holder.price = (TextView) view.findViewById(R.id.tv_price);
            holder.icon = (ImageView) view.findViewById(R.id.iv_icon);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (mType.equals("1")){
            MyFavoriteModel.GoodsBean goodsBean = (MyFavoriteModel.GoodsBean) getItem(position);
            holder.name.setText(goodsBean.getName());
            holder.price.setText(goodsBean.getShopprice());
            App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + goodsBean.getStoreid()
                    + BrnmallAPI.BaseImgUrl2 + goodsBean.getShowimg())
                    .placeholder(R.mipmap.logo).error(R.mipmap.logo).into(holder.icon);
        } else if(mType.equals("2")) {
            MyFavoriteModel.StoreBean storeBean = (MyFavoriteModel.StoreBean) getItem(position);
            holder.name.setText(storeBean.getName());
            App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + storeBean.getStoreid()
                    + BrnmallAPI.BaseImgUrl2 + storeBean.getLogo())
                    .placeholder(R.mipmap.logo).error(R.mipmap.logo).into(holder.icon);
        }
        return view;
    }

    private static class ViewHolder {
        TextView name;
        TextView price;
        ImageView icon;
    }
}
