package com.anbaoxing.e_marketing.activitys;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.anbaoxing.e_marketing.utils.ActivityCollection;
import com.blankj.utilcode.utils.BarUtils;
import com.orhanobut.logger.Logger;

/**
 *
 * Created by LENOVO on 2017/2/10.
 */

public class BaseActivity extends Activity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("BaseActivity",getClass().getSimpleName());
        mContext = getApplicationContext();
        //设置透明状态栏
        BarUtils.setTransparentStatusBar(this);
        ActivityCollection.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollection.remove(this);
    }

    protected void finishAll() {
        ActivityCollection.finishAll();
    }
}
