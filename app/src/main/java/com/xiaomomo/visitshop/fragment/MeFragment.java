package com.xiaomomo.visitshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiaomomo.visitshop.R;
import com.xiaomomo.visitshop.view.SettingItemView;

/**
 * Created by lihuanxing on 2017/7/23.
 */

public class MeFragment extends BaseFragment {

    private SettingItemView meItemView;
    private SettingItemView clearItemView;
    private SettingItemView feedbackItemView;
    private SettingItemView aboutItemView;
    private SettingItemView checkItemView;
    private SettingItemView exitItemView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me,null);
        initView(view);
        setListener();
        return view;
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        meItemView.setOnItemClick(new SettingItemView.OnItemClick() {
            @Override
            public void onClick() {
                Toast.makeText(getContext(),getResources().getString(R.string.setting_item_me),Toast.LENGTH_LONG).show();
            }
        });

        clearItemView.setOnItemClick(new SettingItemView.OnItemClick() {
            @Override
            public void onClick() {
                Toast.makeText(getContext(),getResources().getString(R.string.setting_item_clear),Toast.LENGTH_LONG).show();
            }
        });

        feedbackItemView.setOnItemClick(new SettingItemView.OnItemClick() {
            @Override
            public void onClick() {
                Toast.makeText(getContext(),getResources().getString(R.string.setting_item_idea),Toast.LENGTH_LONG).show();
            }
        });

        aboutItemView.setOnItemClick(new SettingItemView.OnItemClick() {
            @Override
            public void onClick() {
                Toast.makeText(getContext(),getResources().getString(R.string.setting_item_about),Toast.LENGTH_LONG).show();
            }
        });

        checkItemView.setOnItemClick(new SettingItemView.OnItemClick() {
            @Override
            public void onClick() {
                Toast.makeText(getContext(),getResources().getString(R.string.setting_item_check),Toast.LENGTH_LONG).show();
            }
        });

        exitItemView.setOnItemClick(new SettingItemView.OnItemClick() {
            @Override
            public void onClick() {
                Toast.makeText(getContext(),getResources().getString(R.string.setting_item_exit),Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 初始化item控件
     * @param view
     */
    private void initView(View view) {
        meItemView = (SettingItemView) view.findViewById(R.id.setting_me);
        clearItemView = (SettingItemView) view.findViewById(R.id.setting_clear);
        feedbackItemView = (SettingItemView) view.findViewById(R.id.setting_feedback);
        aboutItemView = (SettingItemView) view.findViewById(R.id.setting_about);
        checkItemView = (SettingItemView) view.findViewById(R.id.setting_check);
        exitItemView = (SettingItemView) view.findViewById(R.id.setting_exit);
    }
}
