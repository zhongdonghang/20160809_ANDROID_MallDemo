package com.luoyp.brnmall.adapter;

/**
 * Created by lyp3314@gmail.com on 15/10/21.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.luoyp.brnmall.App;
import com.luoyp.brnmall.R;
import com.socks.library.KLog;

import java.util.List;

/**
 * ImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class GoodsDetailImagePagerAdapter extends RecyclingPagerAdapter {

    public int size;
    private Context context;
    private List<String> imageIdList;
    private boolean isInfiniteLoop;
    private boolean isClick = false;

    public GoodsDetailImagePagerAdapter(Context context, List<String> imageIdList, boolean isClick) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = imageIdList.size();
        isInfiniteLoop = false;
        this.isClick = isClick;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return imageIdList.size();
    }


    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        holder = new ViewHolder();
        view = holder.imageView = new ImageView(context);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setTag(position);

        //holder.imageView.setBackgroundResource(imageIdList.get(getPosition(position)));
        KLog.d("ad pic:" + imageIdList.get(position) + "  pos=" + position);
        App.getPicasso().load(imageIdList.get(position)).placeholder(R.drawable.goodsdefaulimg).error(R.drawable.goodsdefaulimg).into(holder.imageView);


        return view;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public GoodsDetailImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    private static class ViewHolder {

        ImageView imageView;
    }
}
