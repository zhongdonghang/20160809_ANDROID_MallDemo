package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.MyOrderModel;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 * Created by lyp3314@gmail.com on 16/4/14.
 */
public class MyOrderAdapter extends BaseAdapter {

    private Context context;
    private List<MyOrderModel> orderList;
    private int index;

    public MyOrderAdapter(Context context, List<MyOrderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public MyOrderModel getItem(int position) {
        return orderList.get(position);
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
            view = inflater.inflate(R.layout.my_oder_item, null);
            holder = new ViewHolder();
            holder.time = (TextView) view.findViewById(R.id.addtime);
            holder.state = (TextView) view.findViewById(R.id.state);
            holder.price = (TextView) view.findViewById(R.id.price);
            holder.payname = (TextView) view.findViewById(R.id.payname);
            holder.storename = (TextView) view.findViewById(R.id.storename);
            holder.paynow = (Button) view.findViewById(R.id.paynow);
            holder.cancelorder = (Button) view.findViewById(R.id.cancelorder);
            holder.imgll = (LinearLayout) view.findViewById(R.id.imgll);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        int count = getItem(position).getGoodsList().size();
        if (count >= 4) {
            count = 4;
        }
        holder.imgll.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(layoutParams);
            App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + getItem(position).getStoreid()
                    + BrnmallAPI.BaseImgUrl2 + getItem(position).getGoodsList().get(i).getImg())
                    .placeholder(R.mipmap.logo).error(R.mipmap.logo).into(imageView);
            holder.imgll.addView(imageView);
        }
        holder.time.setText(getItem(position).getAddtime());
        holder.price.setText("实付: $" + getItem(position).getOrderamount() + "  (共" + getItem(position).getGoodsList().size() + "件)");
        holder.payname.setText("支付方式: " + getItem(position).getPayfriendname());
        holder.storename.setText("备送店铺: " + getItem(position).getStorename());
        holder.cancelorder.setVisibility(View.GONE);
        holder.paynow.setVisibility(View.GONE);

        holder.paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("0", "orderbtn");
            }
        });
        holder.cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("1", "orderbtn");
            }
        });
        String state = getItem(position).getOrderstate();
        if ("10".equals(state)) {
            holder.state.setText("已提交");
        }
        if ("30".equals(state)) {
            holder.state.setText("等待付款");
            holder.paynow.setVisibility(View.VISIBLE);
            holder.cancelorder.setVisibility(View.VISIBLE);
        }
        if ("50".equals(state)) {
            holder.state.setText("确认中");
        }
        if ("70".equals(state)) {
            holder.state.setText("已确认");
        }
        if ("90".equals(state)) {
            holder.state.setText("备货中");
        }
        if ("110".equals(state)) {
            holder.state.setText("已发货");
        }
        if ("140".equals(state)) {
            holder.state.setText("已完成");
        }
        if ("160".equals(state)) {
            holder.state.setText("已退货");
        }
        if ("180".equals(state)) {
            holder.state.setText("已锁定");
        }
        if ("200".equals(state)) {
            holder.state.setText("已取消");
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

        TextView time;
        TextView state;
        TextView price;
        TextView payname;
        TextView storename;
        Button paynow;
        Button cancelorder;
        LinearLayout imgll;
    }
}