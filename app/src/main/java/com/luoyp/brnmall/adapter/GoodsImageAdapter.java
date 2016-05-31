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
import com.socks.library.KLog;

/**
 * Created by MnZi on 2016/5/9.
 */
public class GoodsImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private GoodsDetailModel goodsDetailModel;

    public GoodsImageAdapter(Context context, GoodsDetailModel model) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        goodsDetailModel = model;
    }

    @Override
    public int getCount() {
        return goodsDetailModel.getImageBeanList().size();
    }

    @Override
    public GoodsDetailModel.ImageBean getItem(int position) {
        return goodsDetailModel.getImageBeanList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KLog.e(goodsDetailModel.getImageBeanList().get(0).getShowImg());
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
                .placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg).into(holder.imageView);
        return convertView;
    }

    public static class ViewHolder{
        ImageView imageView;
    }
}
