package com.xiaomomo.visitshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaomomo.visitshop.R;

/**
 * Created by lihuanxing on 2017/8/15.
 * 设置界面的自定义组合item_view
 */

public class SettingItemView  extends RelativeLayout{

    private View mView;

    private View rootLayout;
    private ImageView leftImageIcon;
    private TextView textView;
    private View dividerView;
    private ImageView rightImageIcon;

    private Drawable mleftIcon;
    private String mText;
    private int mTextColor;
    private int mTextSize;
    private boolean isShowDivider;
    private boolean isShowArrow;
    private OnItemClick onItemClick;

    public SettingItemView(Context context) {
        this(context, null, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        getCusAttrs(context, attrs);
        rootLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null){
                    onItemClick.onClick();
                }
            }
        });
    }

    /**
     * 初始化控件
     * @param context
     */
    private void initView(Context context) {
        mView = View.inflate(context, R.layout.view_item_setting, this);
        rootLayout = mView.findViewById(R.id.root_layout);
        leftImageIcon = (ImageView) mView.findViewById(R.id.setting_item_left_icon);
        textView = (TextView) mView.findViewById(R.id.setting_item_text);
        dividerView = mView.findViewById(R.id.setting_item_divide);
        rightImageIcon = (ImageView) mView.findViewById(R.id.setting_item_right_icon);
    }

    /**
     * 初始化自定义属性
     * @return
     */
    public void getCusAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        int num = typedArray.getIndexCount();
        for (int i = 0; i < num; i++){
            int array = typedArray.getIndex(i);
            switch (array){
                case R.styleable.SettingItemView_left_icon:
                    mleftIcon = typedArray.getDrawable(array);
                    leftImageIcon.setImageDrawable(mleftIcon);
                    break;
                case R.styleable.SettingItemView_item_text:
                    mText = typedArray.getString(array);
                    textView.setText(mText);
                    break;
                case R.styleable.SettingItemView_isShowDivide:
                    isShowDivider = typedArray.getBoolean(array, true);
                    if (isShowDivider){
                        dividerView.setVisibility(VISIBLE);
                    }else{
                        dividerView.setVisibility(GONE);
                    }
                    break;
                case R.styleable.SettingItemView_isShow_arrow:
                    isShowArrow = typedArray.getBoolean(array, true);
                    if (!isShowArrow){
                        rightImageIcon.setVisibility(GONE);
                    }
                    break;
            }
        }
        typedArray.recycle();
    }

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    /**
     * 感觉这个接口定义得有点多余，也许为了后面的拓展？？？
     */
    public interface OnItemClick{
        void onClick();
    }
}
