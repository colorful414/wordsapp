package cn.itfxq.wordsapp.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LooperPagerAdapter extends PagerAdapter {

    private List<Integer> mPics = null;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //无限轮播
        int mposition = position%mPics.size();

        ImageView imageView = new ImageView(container.getContext());
       // imageView.setBackgroundColor(mColors.get(position));
        //设置图片
        imageView.setImageResource(mPics.get(mposition));
        //设置颜色 添加到容器里面
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    //返回个数
    @Override
    public int getCount() {
        if (mPics != null) {
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

    public void setData(List<Integer> colors) {
        this.mPics = colors;
    }

    public int getDataSize(){
        if (mPics != null) {
            return mPics.size();
        }
        return 0;
    }
}
