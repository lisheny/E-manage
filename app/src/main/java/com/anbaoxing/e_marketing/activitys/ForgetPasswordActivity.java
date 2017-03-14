package com.anbaoxing.e_marketing.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.beens.CodeBeen;
import com.anbaoxing.e_marketing.moudle.DoNetWork;
import com.anbaoxing.e_marketing.moudle.GetAuthCode;
import com.anbaoxing.e_marketing.moudle.Login;
import com.anbaoxing.e_marketing.moudle.MyOkHttpResponseHandler;
import com.anbaoxing.e_marketing.moudle.MyOkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.utils.MainUtils;
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

public class ForgetPasswordActivity extends BaseActivity {

    @BindView(R.id.forgetpassword_aty_commontitle)
    CommonTitle forgetpasswordAtyCommontitle;
    @BindView(R.id.aty_forgetpassword_number)
    EditText atyForgetpasswordNumber;
    @BindView(R.id.aty_forgetpassword_code)
    EditText atyForgetpasswordCode;
    @BindView(R.id.aty_forgetpassword_goto_getcode)
    TextView atyForgetpasswordGotoGetcode;
    @BindView(R.id.aty_forgetpassword_reset)
    Button atyForgetpasswordReset;

    private CodeBeen codeBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        forgetpasswordAtyCommontitle.setTitle("忘记密码");
        codeBeen = new CodeBeen();
    }

    /**
     * 获取验证码
     */
    private void getAuthCode(EditText editText) {
        GetAuthCode getAuthCode = new GetAuthCode(GetAuthCode.FOEGET_PASSWORD_COMMAND);
        getAuthCode.getAuthCode(MainUtils.getText(editText), Login.LOGIN_TYPE_PHONE_NUMBER,
                GetAuthCode.REQUEST_TYPE_ALL_DEFAULT, new GetAuthCode.OnGetAuthCodeListener() {
                    @Override
                    public void onVerifySucceed(String userName, String loginType, String requestType) {
                        String info = "onVerifySucceed()  -->  userName：" + userName +
                                "  loginType：" + loginType + "  requestType：" + requestType;
                        Logger.i(info);
                    }

                    @Override
                    public void onVerifyFailure(String userName, String loginType, String requestType, String errorMsg) {
                        String info = "onVerifyFailure()  -->  userName：" + userName +
                                "  loginType：" + loginType + "  requestType：" + requestType + "  errorMsg：" + errorMsg;
                        Logger.i(info);
                    }

                    @Override
                    public void onGetAuthCodeBefore(String userName, String loginType, String requestType, JSONObject requestPackage) {
                        String info = "onGetAuthCodeBefore()  -->  userName：" + userName +
                                "  loginType：" + loginType + "  requestType：" + requestType + "  requestPackage：" + requestPackage;
                        Logger.i(info);
                    }
                }, new MyOkHttpResponseHandler());
    }

    private void checkCode(){
        DoNetWork doNetWork = new DoNetWork(DoNetWork.CHECK_CODE);
        String mUsename = atyForgetpasswordNumber.getText().toString().trim();
        String mCode = atyForgetpasswordCode.getText().toString().trim();
        doNetWork.checkCode(mUsename, mCode,
                new DoNetWork.OnCheckCodeListener() {
                    @Override
                    public void onVerifySucceed(EditText editText, EditText etPassWord) {

                    }

                    @Override
                    public void onVerifyFailure(EditText editText, EditText etPassWord, String errorMsg) {

                    }

                    @Override
                    public void onCheckCodeBefore(String usename, String code, JSONObject requestPackage) {
                        Logger.d("usenaem: "+usename+" code: "+code+" requestPackage:"+requestPackage);
                    }
                },new MyOkHttpResponseHandlerImpl(){

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
                            startActivity(new Intent(ForgetPasswordActivity.this,ResetPasswordActivity.class));
                            finish();
                        }else {
                            ToastUtils.showShortToast(codeBeen.getErrmsg());
                        }

                    }
                });
    }

    @OnClick({R.id.aty_forgetpassword_goto_getcode, R.id.aty_forgetpassword_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aty_forgetpassword_goto_getcode:
                getAuthCode(atyForgetpasswordNumber);
                ToastUtils.showLongToast("获取验证码");
                break;
            case R.id.aty_forgetpassword_reset:
                checkCode();
                break;
        }
    }
}
