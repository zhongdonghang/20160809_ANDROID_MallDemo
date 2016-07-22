package com.luoyp.brnmall.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.luoyp.brnmall.SysUtils;
import com.luoyp.brnmall.api.ApiCallback;
import com.luoyp.brnmall.api.BrnmallAPI;
import com.luoyp.brnmall.model.ShopCartModel;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

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
            holder.shopcarImg = (ImageView) convertView.findViewById(R.id.shopcarImg);
            holder.checkGoods = (CheckBox) convertView.findViewById(R.id.checkgoods);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.goodsName.setText(getItem(position).getName());
        if (getItem(position).isCheck()) {
            holder.checkGoods.setChecked(true);

        } else {
            holder.checkGoods.setChecked(false);
        }
        holder.checkGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(position, "onCheckedChanged");
            }
        });
        if (App.getPref("isLogin", false)) {
            holder.goodsPrice.setText("￥" + SysUtils.formatDouble((Double.valueOf(App.getPref("zhekou", "10")) * Double.valueOf(getItem(position).getShopPrice()) * 10 / 100)) + " (" + App.getPref("zhekoutitle", "") + ")");
        } else {
            holder.goodsPrice.setText("￥" + getItem(position).getShopPrice());
        }


        holder.goodsNum.setText(getItem(position).getBuyCount() + "");
        App.getPicasso().load(BrnmallAPI.BaseImgUrl1 + getItem(position).getStoreId()
                + BrnmallAPI.BaseImgUrl2 + getItem(position).getShowImg())
                .placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg).into(holder.shopcarImg);
        if (getItem(position).getBuyCount() == 1) {
            holder.down.setVisibility(View.GONE);
        } else {
            holder.down.setVisibility(View.VISIBLE);
        }
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(position).getBuyCount() > 1) {
                    downGoodsToCart(position, String.valueOf(getItem(position).getPid()), String.valueOf(getItem(position).getUid()), "-1");
                }

            }
        });

        // 增加商品数量
        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGoodsToCart(position, String.valueOf(getItem(position).getPid()), String.valueOf(getItem(position).getUid()), "1");
            }
        });

        return convertView;
    }

    // 增加商品数量
    private void addGoodsToCart(final int position, String pid, String uid, String count) {
        BrnmallAPI.addProductToCart(pid, uid, count, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("true")) {
                        getItem(position).setBuyCount(getItem(position).getBuyCount() + 1);
//                        BigDecimal bAmount = new BigDecimal(Double.toString(shopCartModel.getProductAmount()));
//                        BigDecimal bPrice = new BigDecimal(Double.toString(getItem(position).getShopPrice()));
//                        double amount = bAmount.add(bPrice).doubleValue();
//                     //   shopCartModel.setProductAmount(amount);
//                        notifyDataSetChanged();
                        // 发布事件，修改总金额
                        EventBus.getDefault().post("", "CartAdapter_tag");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downGoodsToCart(final int position, String pid, String uid, String count) {
        BrnmallAPI.addProductToCart(pid, uid, count, new ApiCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                if (TextUtils.isEmpty(response)) {
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("true")) {
                        getItem(position).setBuyCount(getItem(position).getBuyCount() - 1);
//                        BigDecimal bAmount = new BigDecimal(Double.toString(shopCartModel.getProductAmount()));
//                        BigDecimal bPrice = new BigDecimal(Double.toString(getItem(position).getShopPrice()));
//                        double amount = bAmount.subtract(bPrice).doubleValue();
//                        shopCartModel.setProductAmount(amount);
//                        notifyDataSetChanged();
                        // 发布事件，修改总金额
                        EventBus.getDefault().post("", "CartAdapter_tag");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static class ViewHolder {
        TextView goodsName;
        TextView goodsPrice;
        TextView goodsNum;
        ImageButton down;
        ImageButton up;
        ImageView shopcarImg;
        CheckBox checkGoods;
    }
}
