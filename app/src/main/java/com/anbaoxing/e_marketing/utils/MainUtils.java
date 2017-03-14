package com.anbaoxing.e_marketing.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.anbaoxing.e_marketing.MyApplication;
import com.blankj.utilcode.utils.ToastUtils;


/**
 * author：kang
 * time:  2016-11-21
 */
public class MainUtils {

    private static ConnectivityManager manager;

    /** 隐藏键盘 */
    public static void concealKeyboard(EditText editText){
        InputMethodManager imm = (InputMethodManager) MyApplication.getAppContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isNetWorkEnable() {
        if (manager == null) {
            manager = (ConnectivityManager) MyApplication.getAppContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    /** 获取文本 */
    public static String getText(TextView textView){
        if (textView != null){
            return textView.getText().toString();
        }else {
            return "";
        }
    }

    /** 获取输入内容的 Md5 */
    public static String getMd5(EditText editText){
        return MD5Utils.getMd5(MainUtils.getText(editText));
    }

    /** 获取文本 */
    public static String getHint(TextView textView){
        if (textView != null){
            return textView.getHint().toString();
        }else {
            return "";
        }
    }

    /** 打电话 */
    public static void callUp(Activity activity, String phoneNumber){
        try {
            //判断手机号码是否为空
            if (!TextUtils.isEmpty(phoneNumber)){
                phoneNumber = phoneNumber.trim();
                // 判断是否是数字
                if (phoneNumber.matches("\\d+")) {
                    // 如果是数字，则创建隐式意图
                    Intent intentRingUp = new Intent(Intent.ACTION_CALL);
                    // 打电话
                    intentRingUp.setData(Uri.parse("tel:" + phoneNumber));
                    // 启动拨号界面
                    activity.startActivity(intentRingUp);
                } else {
                    // 输入的表示数字
                    ToastUtils.showShortToast("手机号码格式不正确");
                }
            } else {
                // 输入的表示数字
                ToastUtils.showShortToast("手机号码格式不正确");
            }
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    /** 调用系统短信界面 发送短信 */
    public static void sendSMS(Activity activity, String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)){
            phoneNumber = phoneNumber.trim();
            // 判断是否是数字
            if (phoneNumber.matches("\\d+")) {// && phoneNumber.length() == GlobalConstant.PHONE_NUMBER_LENGTH
                Uri smsToUri = Uri.parse("smsto:"+phoneNumber);
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                //intent.putExtra("sms_body", smsBody);  // 短信内容
                activity.startActivity(intent);
            }else {
                ToastUtils.showShortToast("手机号码格式不正确");
            }
        }else {
            ToastUtils.showShortToast("手机号码格式不正确");
        }
    }

    public static boolean textIsEmpty(String text){
        if (TextUtils.isEmpty(text) || "null".equals(text)
                || "(null)".equals(text)){
            return true;
        }else {
            return false;
        }
    }


}
