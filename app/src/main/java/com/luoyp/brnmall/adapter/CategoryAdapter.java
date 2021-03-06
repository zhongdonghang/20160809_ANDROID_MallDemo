package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luoyp.brnmall.R;
import com.luoyp.brnmall.model.CategoryModel;

import java.util.List;

/**
 * Created by lyp3314@gmail.com on 16/4/14.
 */
public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryModel.Category> categoryList;
    private int index;

    public CategoryAdapter(Context context, List<CategoryModel.Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public CategoryModel.Category getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        //  TLog.d("id:" + getItem(position).getCateId());
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(getItem(position).getCateName());
        if (position == index) {// 判断当前position是否为选中项
            view.setBackgroundColor(Color.WHITE);
        } else {
            view.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }
        return view;
    }

    /**
     * 设置选中的项 * * @param index
     */
    public void setMySelection(int index) {
        this.index = index;

        notifyDataSetChanged();

    }

    private static class ViewHolder {

        TextView name;
    }
}