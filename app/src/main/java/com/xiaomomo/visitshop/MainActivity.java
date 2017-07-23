package com.xiaomomo.visitshop;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomomo.visitshop.activity.BaseActivity;
import com.xiaomomo.visitshop.fragment.HomeFragment;
import com.xiaomomo.visitshop.fragment.MeFragment;
import com.xiaomomo.visitshop.fragment.ShopFragment;
import com.xiaomomo.visitshop.fragment.TrainFragment;
import com.xiaomomo.visitshop.fragment.VisitFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView homeTabImage;//首页
    private ImageView shopTabImage;//巡店
    private ImageView visitTabImage;//拜访
    private ImageView trainTabImage;//培训
    private ImageView meTabImage;//个人中心
    private ViewPager viewPager;
    private TextView titleNameText;
    private ImageView titleBackImage;
    private ImageView titleAddImage;
    private ImageView titleSaveImage;

    private HomeFragment homeFragment;
    private ShopFragment shopFragment;
    private VisitFragment visitFragment;
    private TrainFragment trainFragment;
    private MeFragment meFragment;

    private static final int TAB_HOME = 0;
    private static final int TAB_SHOP = 1;
    private static final int TAB_VISIT = 2;
    private static final int TAB_TRAIN = 3;
    private static final int TAB_ME = 4;

    private FragmentAdapter adapter;


    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        setSelect(TAB_HOME);

    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        homeFragment = new HomeFragment();
        shopFragment = new ShopFragment();
        visitFragment = new VisitFragment();
        trainFragment = new TrainFragment();
        meFragment = new MeFragment();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        homeTabImage = (ImageView) findViewById(R.id.home_image_view);
        shopTabImage = (ImageView) findViewById(R.id.shop_image_view);
        visitTabImage = (ImageView) findViewById(R.id.visit_image_view);
        trainTabImage = (ImageView) findViewById(R.id.train_image_view);
        meTabImage = (ImageView) findViewById(R.id.me_image_view);
        homeTabImage.setOnClickListener(this);
        shopTabImage.setOnClickListener(this);
        visitTabImage.setOnClickListener(this);
        trainTabImage.setOnClickListener(this);
        meTabImage.setOnClickListener(this);

        titleNameText = (TextView) findViewById(R.id.title_name_text);
        titleBackImage = (ImageView) findViewById(R.id.title_back_image);
        titleAddImage = (ImageView) findViewById(R.id.title_add_image);
        titleSaveImage = (ImageView) findViewById(R.id.title_save_image);

        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        fragmentManager = getSupportFragmentManager();
        adapter = new FragmentAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case TAB_HOME:
                        setSelect(TAB_HOME);
                        break;
                    case TAB_SHOP:
                        setSelect(TAB_SHOP);
                        break;
                    case TAB_VISIT:
                        setSelect(TAB_VISIT);
                        break;
                    case TAB_TRAIN:
                        setSelect(TAB_TRAIN);
                        break;
                    case TAB_ME:
                        setSelect(TAB_ME);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_image_view:
                setSelect(TAB_HOME);
                break;
            case R.id.shop_image_view:
                setSelect(TAB_SHOP);
                break;
            case R.id.visit_image_view:
                setSelect(TAB_VISIT);
                break;
            case R.id.train_image_view:
                setSelect(TAB_TRAIN);
                break;
            case R.id.me_image_view:
                setSelect(TAB_ME);
                break;
        }
    }

    /**
     * 根据当前的选择跳转到对应的fragment
     * @param position
     */
    public void setSelect(int position) {
        homeTabImage.setImageResource(R.drawable.menu_home_normal);
        shopTabImage.setImageResource(R.drawable.menu_shop_normal);
        visitTabImage.setImageResource(R.drawable.menu_visit_normal);
        trainTabImage.setImageResource(R.drawable.menu_train_normal);
        meTabImage.setImageResource(R.drawable.menu_me_normal);
        switch (position) {
            case TAB_HOME:
                viewPager.setCurrentItem(TAB_HOME,false);
                homeTabImage.setImageResource(R.drawable.menu_home_pressed);
                titleNameText.setText(getResources().getString(R.string.tab_main));
                titleAddImage.setVisibility(View.GONE);
                titleSaveImage.setVisibility(View.GONE);
                break;
            case TAB_SHOP:
                viewPager.setCurrentItem(TAB_SHOP,false);
                shopTabImage.setImageResource(R.drawable.menu_shop_pressed);
                titleNameText.setText(getResources().getString(R.string.tab_shop));
                titleAddImage.setVisibility(View.VISIBLE);
                titleSaveImage.setVisibility(View.VISIBLE);
                break;
            case TAB_VISIT:
                viewPager.setCurrentItem(TAB_VISIT,false);
                visitTabImage.setImageResource(R.drawable.menu_visit_pressed);
                titleNameText.setText(getResources().getString(R.string.tab_visit));
                titleAddImage.setVisibility(View.VISIBLE);
                titleSaveImage.setVisibility(View.GONE);
                break;
            case TAB_TRAIN:
                viewPager.setCurrentItem(TAB_TRAIN,false);
                trainTabImage.setImageResource(R.drawable.menu_train_pressed);
                titleNameText.setText(getResources().getString(R.string.tab_train));
                titleAddImage.setVisibility(View.GONE);
                titleSaveImage.setVisibility(View.GONE);
                break;
            case TAB_ME:
                viewPager.setCurrentItem(TAB_ME,false);
                meTabImage.setImageResource(R.drawable.menu_me_pressed);
                titleNameText.setText(getResources().getString(R.string.tab_me));
                titleAddImage.setVisibility(View.GONE);
                titleSaveImage.setVisibility(View.GONE);
                break;

        }
    }


    /**
     * viewpager的适配器
     */
    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case TAB_HOME:
                    return homeFragment;
                case TAB_SHOP:
                    return shopFragment;
                case TAB_VISIT:
                    return visitFragment;
                case TAB_TRAIN:
                    return trainFragment;
                case TAB_ME:
                    return meFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
