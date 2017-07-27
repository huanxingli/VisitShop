package com.xiaomomo.visitshop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xiaomomo.visitshop.R;
import com.xiaomomo.visitshop.adapter.HomePictureAdapter;
import com.xiaomomo.visitshop.bean.AnnImageResult;
import com.xiaomomo.visitshop.bean.AnnImgs;
import com.xiaomomo.visitshop.okhttp.CommOkHttpClient;
import com.xiaomomo.visitshop.okhttp.OkHttpCallBack;
import com.xiaomomo.visitshop.utils.HttpUrl;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import okhttp3.Request;

/**
 * Created by lihuanxing on 2017/7/23.
 * 首页fragment
 */

public class HomeFragment extends BaseFragment {

    private static final Logger LOGGER = Logger.getLogger("HomeFragment");

    private ViewPager pictureViewPager;
    private LinearLayout pointLayout;
    private HomePictureAdapter adapter;
    private ArrayList<View> views; //轮播图片数据源
    private Context mContext;
    
    private int currentPage = 0;//当前选中那一页
    private static final int MSG_SCROOL = 1;
    private static final int SCROOL_TIME = 3000;//图片时间轮播间隔
    private Timer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        pictureViewPager = (ViewPager) view.findViewById(R.id.home_picture_viewpager);
        pointLayout = (LinearLayout) view.findViewById(R.id.home_viewpager_point_layout);
        initData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (views == null){
            views = new ArrayList<>();
        }
        //网络请求轮播图的数据
        CommOkHttpClient.getInstance().getRequest(HttpUrl.Announcement, new OkHttpCallBack() {
            @Override
            public void onSuccess(String response) {
                LOGGER.info("response");
                getPictureSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
//                getPictureFailure();
            }
        });
    }

    /**
     * 从服务器拿取数据不成功，从数据库中拿取
     */
    private void getPictureFailure() {
        List<AnnImgs> listAnnImgs = DataSupport.findAll(AnnImgs.class);
        updateView(listAnnImgs);
    }

    /**
     * 从服务器拿取数据成功
     * @param response
     */
    private void getPictureSuccess(String response) {
        if (response != null && !"".equals(response)){
            Gson gson = new Gson();
            AnnImageResult imageResult = gson.fromJson(response,AnnImageResult.class);
            List<AnnImgs> listAnnImgs = imageResult.getBody();
            //防止空指针
            if (listAnnImgs == null){
                listAnnImgs = new ArrayList<>();
            }
            //存进数据库
//            if (listAnnImgs.size() > 0){
//                for (int i = 0; i<listAnnImgs.size(); i++){
//                    DataSupport.deleteAll(AnnImgs.class,"id=?",listAnnImgs.get(i).getId() + "");
//                    listAnnImgs.get(i).save();
//                }
//            }
            updateView(listAnnImgs);
            initPoints();
            adapter = new HomePictureAdapter(views,pointLayout);
            pictureViewPager.setAdapter(adapter);
            pictureViewPager.addOnPageChangeListener(adapter);
            startTimer();

        }
    }

    /**
     * 开启定时器
     */
    private void startTimer() {
        if (timer == null){
            timer = new Timer();
            timer.schedule(timerTask,0,SCROOL_TIME);
        }
    }

    /**
     * 初始化指示点
     */
    private void initPoints() {
        pointLayout.removeAllViews();
        for (int i = 0; i < views.size(); i++){
            ImageView imagePoint = new ImageView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            imagePoint.setLayoutParams(layoutParams);
            imagePoint.setImageResource(R.drawable.home_viewpager_point_bg);
            //默认首张图片被选中
            if (i == 0) {
                imagePoint.setSelected(true);
            }
            pointLayout.addView(imagePoint);
        }
    }


    /**
     * 填充图片数据到Views
     * @param listAnnImgs
     */
    private void updateView(List<AnnImgs> listAnnImgs) {
        views.clear();
        for (int i = 0; i<listAnnImgs.size(); i++){
            ImageView imageView = new ImageView(mContext);
            Picasso.with(getActivity())
                    .load(HttpUrl.BASE_URL + listAnnImgs.get(i).getImgUrl())
                    .into(imageView);
            views.add(imageView);
        }
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (currentPage == views.size()){
                currentPage = 0;
            }else{
                currentPage++;
            }
            mHandler.sendEmptyMessage(MSG_SCROOL);
        }
    };

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SCROOL://接收消息，改变viewpager选定的item
                    pictureViewPager.setCurrentItem(currentPage);
                    break;
            }
        }
    };
}
