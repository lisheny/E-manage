package com.anbaoxing.e_marketing;

import android.app.Application;

import com.anbaoxing.e_marketing.beens.UseBeen;
import com.blankj.utilcode.utils.Utils;

import cn.jpush.android.api.JPushInterface;
import me.shaohui.shareutil.ShareConfig;
import me.shaohui.shareutil.ShareManager;

/**
 * Created by LENOVO on 2017/2/7.
 */

public class MyApplication extends Application {

    private final String QQ_ID = "1105912429";
    private final String WX_ID = "";

    private static UseBeen useBeen;
    private static MyApplication appContext;
    public static MyApplication getAppContext(){
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        //工具库初始化
        Utils.init(appContext);

        ShareConfig config = ShareConfig.instance()
                .qqId(QQ_ID)
                .wxId(WX_ID);
        ShareManager.init(config);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static UseBeen getUseBeen() {
        return useBeen;
    }

    public static void setUseBeen(UseBeen useBeen) {
        MyApplication.useBeen = useBeen;
    }
}

