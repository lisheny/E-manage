package com.anbaoxing.e_marketing.activitys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.beens.CodeBeen;
import com.anbaoxing.e_marketing.moudle.AmendPassword;
import com.anbaoxing.e_marketing.moudle.MyOkHttpResponseHandler;
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

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.changepassword_aty_commontitle)
    CommonTitle changepasswordAtyCommontitle;
    @BindView(R.id.aty_get_password00)
    EditText atyGetPassword00;
    @BindView(R.id.aty_get_password01)
    EditText atyGetPassword01;
    @BindView(R.id.aty_changepassword_confirm)
    Button atyChangepasswordConfirm;

    private CodeBeen codeBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        changepasswordAtyCommontitle.setTitle("修改密码");
        codeBeen = new CodeBeen();
    }

    /**
     * 修改密码
     */
    private void changePassword(){
        AmendPassword amendPassword = new AmendPassword(AmendPassword.AMEND_PASSWORD_COMMAND);
        amendPassword.amendPassword(atyGetPassword00, atyGetPassword01, new AmendPassword.OnAmendPasswordListener() {
            @Override
            public void onAmendPasswordBefore(EditText etOldPassWord, EditText etNewPassWord, JSONObject requestPackage) {

            }

            @Override
            public void onVerifySucceed(EditText editText, EditText etPassWord) {

            }

            @Override
            public void onVerifyFailure(EditText editText, EditText etPassWord, String errorMsg) {

            }
        }, new MyOkHttpResponseHandler(){
            @Override
            public void onSucceed(String response, int id) {
                super.onSucceed(response, id);

                Gson gson = new Gson();
                Type type = new TypeToken<CodeBeen>() {
                }.getType();
                codeBeen = gson.fromJson(response,type);

                if (codeBeen.getError().equals("1")){
                    Logger.i(codeBeen.getErrmsg());
                    ToastUtils.showShortToast(codeBeen.getErrmsg());
                    finish();
                }else {
                    ToastUtils.showShortToast(codeBeen.getErrmsg());
                }
            }


        });
    }

    @OnClick(R.id.aty_changepassword_confirm)
    public void onClick() {
//        ToastUtils.showLongToast("修改密码中...");
        changePassword();
    }
}
