package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.luoyp.brnmall.R;
import com.luoyp.brnmall.model.MyAddressModel;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 * Created by lyp3314@gmail.com on 16/4/14.
 */
public class MyAddressAdapter extends BaseAdapter {

    private Context context;
    private List<MyAddressModel> dataList;
    private int index;

    public MyAddressAdapter(Context context, List<MyAddressModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public MyAddressModel getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        //  TLog.d("id:" + getItem(position).getCateId());
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_address_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.phone = (TextView) view.findViewById(R.id.phone);
            holder.address = (TextView) view.findViewById(R.id.address);
            holder.edit = (ImageButton) view.findViewById(R.id.editaddress);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(getItem(position).getName());
        holder.phone.setText(getItem(position).getMobile());
        holder.address.setText(getItem(position).getAddress());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(position, "editaddress");
            }
        });

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
        TextView phone;
        TextView address;
        ImageButton edit;
    }
}