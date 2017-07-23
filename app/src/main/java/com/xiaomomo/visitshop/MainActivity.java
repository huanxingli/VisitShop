package com.xiaomomo.visitshop;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.xiaomomo.visitshop.activity.BaseActivity;
import com.xiaomomo.visitshop.fragment.HomeFragment;
import com.xiaomomo.visitshop.fragment.MeFragment;
import com.xiaomomo.visitshop.fragment.ShopFragment;
import com.xiaomomo.visitshop.fragment.TrainFragment;
import com.xiaomomo.visitshop.fragment.VisitFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private ImageView homeTabImage;//首页
    private ImageView shopTabImage;//巡店
    private ImageView visitTabImage;//拜访
    private ImageView trainTabImage;//培训
    private ImageView meTabImage;//个人中心
    private ViewPager viewPager;

    private HomeFragment homeFragment;
    private ShopFragment shopFragment;
    private VisitFragment visitFragment;
    private TrainFragment trainFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();

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

        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_image_view:
                break;
            case R.id.shop_image_view:
                break;
            case R.id.visit_image_view:
                break;
            case R.id.train_image_view:
                break;
            case R.id.me_image_view:
                break;
        }
    }
}
