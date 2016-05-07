package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.luoyp.brnmall.R;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.ShopCartModel;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.math.BigDecimal;

/**
 * Created by MnZi on 2016/5/1.
 */
public class ShopCarAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ShopCartModel shopCartModel;

    public ShopCarAdapter(Context context, ShopCartModel model) {
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
            convertView = mInflater.inflate(R.layout.item_shopcart, null);
            holder = new ViewHolder();
            holder.goodsName = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.tv_goods_price);
            holder.goodsNum = (TextView) convertView.findViewById(R.id.tv_goods_num);
            holder.down = (ImageButton) convertView.findViewById(R.id.iv_down);
            holder.up = (ImageButton) convertView.findViewById(R.id.iv_up);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.goodsName.setText(getItem(position).getName());
        BigDecimal bPrice = new BigDecimal(Double.toString(getItem(position).getShopPrice()));
        BigDecimal bBuyCount = new BigDecimal(Double.toString(getItem(position).getBuyCount()));
        double price = bPrice.multiply(bBuyCount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        holder.goodsPrice.setText("￥"+price);
        holder.goodsNum.setText(getItem(position).getBuyCount() + "");

        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(position).getBuyCount()>0){
                    downGoodsToCart(position,String.valueOf(getItem(position).getPid()),String.valueOf(getItem(position).getUid()),"-1");
                }

            }
        });

        // 增加商品数量
        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGoodsToCart(position,String.valueOf(getItem(position).getPid()),String.valueOf(getItem(position).getUid()),"1");
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView goodsName;
        TextView goodsPrice;
        TextView goodsNum;
        ImageButton down;
        ImageButton up;
    }

    // 增加商品数量
    private void addGoodsToCart(final int position, String pid, String uid, String count){
        BrnmallAPI.addProductToCart(pid,uid,count, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)){
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("true")){
                        getItem(position).setBuyCount(getItem(position).getBuyCount()+1);
                        BigDecimal bAmount = new BigDecimal(Double.toString(shopCartModel.getProductAmount()));
                        BigDecimal bPrice = new BigDecimal(Double.toString(getItem(position).getShopPrice()));
                        double amount = bAmount.add(bPrice).doubleValue();
                        shopCartModel.setProductAmount(amount);
                        notifyDataSetChanged();
                        // 发布事件，修改总金额
                        EventBus.getDefault().post(shopCartModel.getProductAmount(),"CartAdapter_tag");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downGoodsToCart(final int position, String pid, String uid, String count){
        BrnmallAPI.addProductToCart(pid,uid,count, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)){
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("true")){
                        getItem(position).setBuyCount(getItem(position).getBuyCount()-1);
                        BigDecimal bAmount = new BigDecimal(Double.toString(shopCartModel.getProductAmount()));
                        BigDecimal bPrice = new BigDecimal(Double.toString(getItem(position).getShopPrice()));
                        double amount = bAmount.subtract(bPrice).doubleValue();
                        shopCartModel.setProductAmount(amount);
                        notifyDataSetChanged();
                        // 发布事件，修改总金额
                        EventBus.getDefault().post(shopCartModel.getProductAmount(),"CartAdapter_tag");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
