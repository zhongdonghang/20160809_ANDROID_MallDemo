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
import com.luoyp.brnmall.model.Brand;

import java.util.List;

/**
 * Created by MnZi on 2016/5/6.
 */
public class BrandAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Brand> brandList;

    public BrandAdapter(Context context, List<Brand> brandList) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.brandList = brandList;
    }

    @Override
    public int getCount() {
        return brandList.size();
    }

    @Override
    public Brand getItem(int position) {
        return brandList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_brand, null);
            holder = new ViewHolder();
            holder.brandIcon = (ImageView) convertView.findViewById(R.id.iv_brand_icon);
            holder.brandName = (TextView) convertView.findViewById(R.id.tv_brand_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.brandName.setText(getItem(position).getBrandName());
        App.getPicasso().load(getItem(position).getBrandImg()).placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg).into(holder.brandIcon);

        return convertView;
    }

    public static class ViewHolder {
        TextView brandName;
        TextView brandPrice;
        ImageView brandIcon;
    }
}
