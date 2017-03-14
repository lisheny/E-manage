package com.anbaoxing.e_marketing.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anbaoxing.e_marketing.R;

/**
 * 公用标题栏
 * 使用：在xlm布局文件中引用这个控件，通过setTitle设定标题
 * Created by LENOVO on 2017/2/10.
 */

public class CommonTitle extends LinearLayout {

    private TextView commonTitle;
    private ImageView btnBack;

    public void setTitle(String title) {
        commonTitle.setText(title);
    }

    public void setGone(boolean isGone){
        if (isGone){
            btnBack.setVisibility(GONE);
        }else {
            btnBack.setVisibility(VISIBLE);
        }
    }

    public CommonTitle(Context context) {
        super(context);
    }

    public CommonTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.common_title, this);
        btnBack = (ImageView)findViewById(R.id.btn_back);
        commonTitle = (TextView)findViewById(R.id.common_title);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });
    }
}
