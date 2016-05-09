package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.GoodsDetailModel;

import java.util.List;

/**
 * Created by MnZi on 2016/5/9.
 */
public class GoodsImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<GoodsDetailModel.ImageBean> mList;

    public GoodsImageAdapter(Context context, List<GoodsDetailModel.ImageBean> list) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public GoodsDetailModel.ImageBean getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_goods_image,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GoodsDetailModel.ImageBean imageBean = getItem(position);
        App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + imageBean.getStoreId() + BrnmallAPI.BaseImgUrl3 + imageBean.getShowImg())
                .placeholder(R.mipmap.logo).error(R.mipmap.logo).into(holder.imageView);

        return convertView;
    }

    public static class ViewHolder{
        ImageView imageView;
    }
}
