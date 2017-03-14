package com.anbaoxing.e_marketing.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity统一调度
 * Created by LENOVO on 2016/9/21.
 */
public class ActivityCollection {
    private static List<Activity> sList = new ArrayList<Activity>();

    public static void add(Activity activity){
        sList.add(activity);
    }

    public static void remove(Activity activity){
        sList.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity:sList){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
