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
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<String> imageIdList;

    private int size;
    private boolean isInfiniteLoop;

    public ImagePagerAdapter(Context context, List<String> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = 4;
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return 3;
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
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //holder.imageView.setBackgroundResource(imageIdList.get(getPosition(position)));
        KLog.d("ad pic:" + imageIdList.get(position) + "  pos=" + position);
        if (position == 0) {
            App.getPicasso().load(imageIdList.get(position)).placeholder(R.drawable.ad1).error(R.drawable.ad1).into(holder.imageView);
        }
        if (position == 1) {
            App.getPicasso().load(imageIdList.get(position)).placeholder(R.drawable.ad2).error(R.drawable.ad2).into(holder.imageView);
        }
        if (position == 2) {
            App.getPicasso().load(imageIdList.get(position)).placeholder(R.drawable.ad3).error(R.drawable.ad3).into(holder.imageView);
        }
        if (position == 3) {
            App.getPicasso().load(imageIdList.get(position)).placeholder(R.drawable.ad4).error(R.drawable.ad4).into(holder.imageView);
        }
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
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    private static class ViewHolder {

        ImageView imageView;
    }
}
