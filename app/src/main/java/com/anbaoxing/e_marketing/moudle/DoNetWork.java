package com.anbaoxing.e_marketing.moudle;

import android.support.annotation.NonNull;
import android.widget.EditText;

import com.anbaoxing.e_marketing.http.OkHttpResponseHandler;
import com.anbaoxing.e_marketing.http.OkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.http.URL;
import com.anbaoxing.e_marketing.utils.MD5Utils;
import com.anbaoxing.e_marketing.utils.MainUtils;
import com.blankj.utilcode.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 一般的网络请求
 * Created by LENOVO on 2017/2/17.
 */

public class DoNetWork extends RequestPackageImpl {
    private static final String TAG = DoNetWork.class.getSimpleName() + "  -->  ";

    public DoNetWork(String command) {
        super(command);
    }

    /**
     * 移动联通基站获取位置
     * @param mMnc
     * @param mLac
     * @param mCell
     * @param okHttpResponseHandlerImpl
     */
    public void getLocation01(String mMnc,String mLac,String mCell,OkHttpResponseHandlerImpl okHttpResponseHandlerImpl){
        try {
            requestPackage.put(mnc,mMnc);
            requestPackage.put(lac, mLac);
            requestPackage.put(cell, mCell);
            sendPostRequest(URL.HOST_URL,null,requestPackage,okHttpResponseHandlerImpl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 电信基站获取位置
     * @param mMnc
     * @param mSid
     * @param mNid
     * @param mBid
     * @param okHttpResponseHandlerImpl
     */
    public void getLocation02(String mMnc,String mSid,String mNid,String mBid,OkHttpResponseHandlerImpl okHttpResponseHandlerImpl){
        try {
            requestPackage.put(mnc,mMnc);
            requestPackage.put(sid, mSid);
            requestPackage.put(nid, mNid);
            requestPackage.put(bid, mBid);
            sendPostRequest(URL.HOST_URL,null,requestPackage,okHttpResponseHandlerImpl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据地域获取流量单价
     * @param mProvince
     * @param mCity
     * @param mOperator
     * @param okHttpResponseHandlerImpl
     */
    public void getPrice(String mProvince,String mCity,String mOperator,OkHttpResponseHandlerImpl okHttpResponseHandlerImpl){
        try {
            requestPackage.put("province",mProvince);
            requestPackage.put("city", mCity);
            requestPackage.put("operator", mOperator);
            sendPostRequest(URL.HOST_URL,null,requestPackage,okHttpResponseHandlerImpl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取列表消息
     * @param okHttpResponseHandler
     */
    public void getListMessage(OkHttpResponseHandler okHttpResponseHandler){

        sendPostRequest(URL.HOST_URL,null,requestPackage,okHttpResponseHandler);
    }

    /**
     * 问题反馈
     * @param user_name
     * @param tel
     * @param context
     * @param okHttpResponseHandler
     */
    public void feedback(String user_name, EditText tel, EditText context, OkHttpResponseHandler okHttpResponseHandler){

        try {
            //此处应加入校验！！！！！
            if (user_name.isEmpty()){
                ToastUtils.showShortToast("传入用户名为空");
                return;
            }
            if (MainUtils.getText(tel).isEmpty()){
                ToastUtils.showShortToast("请输入联系方式");
                return;
            }
            if (MainUtils.getText(context).isEmpty()){
                ToastUtils.showShortToast("请输入你要提交的内容");
                return;
            }

            requestPackage.put(username,user_name);
            requestPackage.put("tel", MainUtils.getText(tel));
            requestPackage.put("context", MainUtils.getText(context));


            sendPostRequest(URL.HOST_URL,null,requestPackage,okHttpResponseHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验手机号码验证码是否为空
     * @param usename
     * @param code
     * @return
     */
    private boolean checkEnter(String usename, String code) {
        if (usename.isEmpty()) {
            ToastUtils.showShortToast("手机号为空");
            return false;
        }
        if (code.isEmpty()) {
            ToastUtils.showShortToast("验证码为空");
            return false;
        }
        if (!isPhoneNumber(usename)) {
            ToastUtils.showShortToast("请输入正确的手机号");
            return false;
        }
        return true;
    }

    public void checkcode(String usename, String code, String url, Object tag,
                          OnCheckCodeListener onCheckCodeListener, OkHttpResponseHandler okHttpResponseHandler) {
        try {
            if (!checkEnter(usename, code)) {
                onCheckCodeListener.onVerifyFailure(null, null, "验证不通过");
                return;
            }

            requestPackage.put(username, usename);
            String mCode = MD5Utils.getMd5(code);
            requestPackage.put("code", mCode);


            if (onCheckCodeListener != null) {
                // 调用接口
                onCheckCodeListener.onCheckCodeBefore(usename, code, requestPackage);
            }
            // 发送请求
            this.sendPostRequest(url, tag, requestPackage, okHttpResponseHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证验证码
     *
     * @param usename
     * @param code
     * @param onCheckCodeListener
     * @param okHttpResponseHandlerImpl
     */
    public void checkCode(String usename, String code, OnCheckCodeListener onCheckCodeListener,
                          @NonNull OkHttpResponseHandlerImpl okHttpResponseHandlerImpl) {
        this.checkcode(usename, code, URL.HOST_URL, null, onCheckCodeListener, okHttpResponseHandlerImpl);
    }


    /**
     * 验证码监听器
     */
    public interface OnCheckCodeListener extends OnVerifyListener {

        void onCheckCodeBefore(String usename, String code, JSONObject requestPackage);
    }


}
