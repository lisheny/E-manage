package com.anbaoxing.e_marketing.moudle;

import android.widget.EditText;

/**
 * author：kang
 * time:  2016-11-22
 *
 * 验证成功或失败接口回调
 */
public interface OnVerifyListener {

    /**
     * 验证成功后调用
     * @param editText 用户名输入框，或原密码输入框
     * @param etPassWord 密码
     */
    void onVerifySucceed(EditText editText, EditText etPassWord);

    /**
     * 验证失败后调用
     * @param editText 用户名，或原密码输入框
     * @param etPassWord 密码
     * @param errorMsg 验证失败时的错误信息
     */
    void onVerifyFailure(EditText editText, EditText etPassWord, String errorMsg);

}
