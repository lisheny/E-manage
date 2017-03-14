package com.anbaoxing.e_marketing.activitys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.anbaoxing.e_marketing.MyApplication;
import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.beens.CodeBeen;
import com.anbaoxing.e_marketing.moudle.ForgetPassword;
import com.anbaoxing.e_marketing.moudle.MyOkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.widget.CommonTitle;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ResetPasswordActivity extends BaseActivity {

    @BindView(R.id.resetpassword_aty_commontitle)
    CommonTitle resetpasswordAtyCommontitle;
    @BindView(R.id.aty_reserpassword_password00)
    EditText atyReserpasswordPassword00;
    @BindView(R.id.aty_reserpassword_password01)
    EditText atyReserpasswordPassword01;
    @BindView(R.id.aty_reserpassword_confirm)
    Button atyReserpasswordConfirm;

    private CodeBeen codeBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);

        resetpasswordAtyCommontitle.setTitle("重置密码");
        codeBeen = new CodeBeen();
    }

    private void resetPassword(){
        ForgetPassword forgetPassword = new ForgetPassword(ForgetPassword.FORGET_PASSWORD_COMMAND);
        forgetPassword.setUsername(MyApplication.getUseBeen().getUsername());
        forgetPassword.forgetPassword(atyReserpasswordPassword00, ForgetPassword.REQUEST_TYPE_RESET_PASSWORD,
                new ForgetPassword.OnForgetPasswordListener() {
                    @Override
                    public void onForgetPasswordBefore(EditText etPassWord, JSONObject requestPackage) {
                        Logger.d("etPassWord:"+etPassWord.getText().toString()+" requestPackage:"+requestPackage);
                    }

                    @Override
                    public void onVerifySucceed(EditText etPassWord) {

                    }

                    @Override
                    public void onVerifyFailure(EditText etPassWord, String errorMsg) {

                    }
                }, new MyOkHttpResponseHandlerImpl(){
                    @Override
                    public void onFailure(Call call, Exception e, int id) {
                        super.onFailure(call, e, id);
                        ToastUtils.showShortToast("网络请求失败");
                    }

                    @Override
                    public void onSucceed(String response, int id) {
                        super.onSucceed(response, id);

                        Gson gson = new Gson();
                        Type type = new TypeToken<CodeBeen>() {
                        }.getType();
                        codeBeen = gson.fromJson(response,type);

                        if (codeBeen.getError().equals("1")){
                            Logger.i(codeBeen.getErrmsg());
                             finish();
                        }else {
                            ToastUtils.showShortToast(codeBeen.getErrmsg());
                        }
                    }
                });
    }

    @OnClick(R.id.aty_reserpassword_confirm)
    public void onClick() {
        resetPassword();
        ToastUtils.showLongToast("重置密码中...");
    }
}
