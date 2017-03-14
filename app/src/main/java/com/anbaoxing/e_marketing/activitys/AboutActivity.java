package com.anbaoxing.e_marketing.activitys;

import android.os.Bundle;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.widget.CommonTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.about_aty_commontitle)
    CommonTitle aboutAtyCommontitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        aboutAtyCommontitle.setTitle("关于我们");
    }
}
