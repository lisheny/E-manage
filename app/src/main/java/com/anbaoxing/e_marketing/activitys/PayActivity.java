package com.anbaoxing.e_marketing.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.alipay.AlipayActivity;
import com.anbaoxing.e_marketing.widget.CommonTitle;
import com.blankj.utilcode.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends BaseActivity {

    @BindView(R.id.aty_pay_commontitle)
    CommonTitle atyPayCommontitle;
    @BindView(R.id.aty_pay_10rmb)
    TextView atyPay10rmb;
    @BindView(R.id.aty_pay_20rmb)
    TextView atyPay20rmb;
    @BindView(R.id.aty_pay_50rmb)
    TextView atyPay50rmb;
    @BindView(R.id.aty_pay_100rmb)
    TextView atyPay100rmb;
    @BindView(R.id.aty_pay_wx_rb)
    RadioButton atyPayWxRb;
    @BindView(R.id.aty_pay_wx_relativelayout)
    RelativeLayout atyPayWxRelativelayout;
    @BindView(R.id.aty_pay_zhifubao_rb)
    RadioButton atyPayZhifubaoRb;
    @BindView(R.id.aty_pay_zhifubao_relativelayout)
    RelativeLayout atyPayZhifubaoRelativelayout;
    @BindView(R.id.aty_pay_yinlian_rb)
    RadioButton atyPayYinlianRb;
    @BindView(R.id.aty_pay_yinlian_relativelayout)
    RelativeLayout atyPayYinlianRelativelayout;
    @BindView(R.id.aty_pay_comfirm)
    Button atyPayComfirm;

    public static final int WX_PAY = 1;
    public static final int ZHIFUBAO_PAY = 2;
    private int payType = 0;
    private int chooseRMB = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        initView();
        atyPayCommontitle.setTitle("余额充值");
    }

    private void initView(){
        changeTvBg(atyPay10rmb);
    }

    private void changeTvBg(TextView textView){
        atyPay10rmb.setBackgroundResource(R.drawable.pay_aty_choose_bg);
        atyPay20rmb.setBackgroundResource(R.drawable.pay_aty_choose_bg);
        atyPay50rmb.setBackgroundResource(R.drawable.pay_aty_choose_bg);
        atyPay100rmb.setBackgroundResource(R.drawable.pay_aty_choose_bg);
        textView.setBackgroundResource(R.mipmap.aty_pay_choose_bg);
    }

    @OnClick({R.id.aty_pay_10rmb, R.id.aty_pay_20rmb, R.id.aty_pay_50rmb, R.id.aty_pay_100rmb, R.id.aty_pay_wx_rb, R.id.aty_pay_wx_relativelayout, R.id.aty_pay_zhifubao_rb, R.id.aty_pay_zhifubao_relativelayout, R.id.aty_pay_yinlian_rb, R.id.aty_pay_yinlian_relativelayout, R.id.aty_pay_comfirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aty_pay_10rmb:
                changeTvBg(atyPay10rmb);
                chooseRMB = 10;
                break;
            case R.id.aty_pay_20rmb:
                changeTvBg(atyPay20rmb);
                chooseRMB = 20;
                break;
            case R.id.aty_pay_50rmb:
                changeTvBg(atyPay50rmb);
                chooseRMB = 50;
                break;
            case R.id.aty_pay_100rmb:
                changeTvBg(atyPay100rmb);
                chooseRMB = 100;
                break;
            case R.id.aty_pay_wx_rb:
                atyPayWxRb.setChecked(true);
                atyPayZhifubaoRb.setChecked(false);
                payType = WX_PAY;
                break;
            case R.id.aty_pay_wx_relativelayout:
                break;
            case R.id.aty_pay_zhifubao_rb:
                atyPayWxRb.setChecked(false);
                atyPayZhifubaoRb.setChecked(true);
                payType = ZHIFUBAO_PAY;
                break;
            case R.id.aty_pay_zhifubao_relativelayout:

                break;
            case R.id.aty_pay_yinlian_rb:
                break;
            case R.id.aty_pay_yinlian_relativelayout:
                break;
            case R.id.aty_pay_comfirm:
                if (payType == WX_PAY) {
                    //dosomething
                    ToastUtils.showShortToast("暂不支持");
                } else if (payType == ZHIFUBAO_PAY) {
                    Intent goAlipayAtyIntent = new Intent(this,AlipayActivity.class);
                    goAlipayAtyIntent.putExtra("chooseRMB",chooseRMB);
                    startActivity(goAlipayAtyIntent);
                    finish();
                }else {
                    ToastUtils.showShortToast("请选择支付方式");
                }
                break;
        }
    }
}
