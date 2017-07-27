package com.xiaomomo.visitshop.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by lihuanxing on 2017/7/25.
 * 首页图片轮播图的viewPager的adapter
 */

public class HomePictureAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{

    private static final Logger LOGGER = Logger.getLogger("HomePictureAdapter");

    private ArrayList<View> views; //数据源
    private LinearLayout layout; //提示点

    public HomePictureAdapter(ArrayList<View> views, LinearLayout layout){
        this.views = views;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        LOGGER.info("onPageSelected:" + position);
        for (int i = 0; i < layout.getChildCount(); i++){
            ImageView imageView = (ImageView) layout.getChildAt(i);
            if (i == position){
                imageView.setSelected(true);
            }else{
                imageView.setSelected(false);
            }

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }
}
