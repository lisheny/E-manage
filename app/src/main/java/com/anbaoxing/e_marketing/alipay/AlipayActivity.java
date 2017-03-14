package com.anbaoxing.e_marketing.alipay;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.anbaoxing.e_marketing.MyApplication;
import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.activitys.BaseActivity;
import com.anbaoxing.e_marketing.beens.CodeBeen;
import com.anbaoxing.e_marketing.http.OkHttpClientManager;
import com.anbaoxing.e_marketing.http.OkHttpResponseHandler;
import com.anbaoxing.e_marketing.http.URL;
import com.anbaoxing.e_marketing.utils.Constant;
import com.anbaoxing.e_marketing.utils.MD5Utils;
import com.anbaoxing.e_marketing.widget.CommonTitle;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * TODO:支付宝支付
 * <p/>
 * Created: JustRush
 * Time:    2016/1/4 11:02
 */
public class AlipayActivity extends BaseActivity {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @BindView(R.id.alipay_aty_commontitle)
    CommonTitle alipayAtyCommontitle;
    @BindView(R.id.subject_tx)
    TextView subjectTx;
    @BindView(R.id.body_tx)
    TextView bodyTx;
    @BindView(R.id.price_tx)
    TextView priceTx;
    @BindView(R.id.pay_btn)
    Button payBtn;

    private CodeBeen codeBeen;
    //商品名称
    private String subject = "流量充值";
    //商品详情
    private String body = "充值了一分钱";
    //商品金额
    private String price = "0.01";
    //商品订单号
    //测试
    private String out_trade_no = MyApplication.getUseBeen().getId()+
            String.valueOf(System.currentTimeMillis());  //测试

    private String TAG = "AlipayActivity";
    /**
     * 支付取消
     */
    public static final int ALIPAY_CANCEL = 100;
    /**
     * 支付成功
     */
    public static final int ALIPAY_SUCCEED = 200;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    Logger.i("支付宝同步返回的数据：",resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(AlipayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //同步验签
                        try {
                            syncRequest(resultInfo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(AlipayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(AlipayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(AlipayActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        ButterKnife.bind(this);
        alipayAtyCommontitle.setTitle("支付宝充值");
        codeBeen = new CodeBeen();
//        Intent it = getIntent();
//        subject = it.getStringExtra("subject");
//        body = it.getStringExtra("body");
//        price = it.getStringExtra("price");
//        out_trade_no = it.getStringExtra("out_trade_no");

    }


    /**
     * 获取支付宝签名
     * command:1017
     */
    private void getSign(String order_no) throws JSONException {
        GetSignBean bean = new GetSignBean();
        bean.setCommand(Constant.CMD_GET_SIGN);
        bean.setId(MyApplication.getUseBeen().getId());
        bean.setUsername(MyApplication.getUseBeen().getUsername());
        bean.setPackmd5("");
        bean.setTimestamp(String.valueOf(System.currentTimeMillis()));
        bean.setOrder_no(order_no);
        bean.setOrdermoney("0.01");    //仅供测试

        JSONObject jsonObject = new JSONObject(new Gson().toJson(bean));
        //MD5加密请求包
        String mMD5 = MD5Utils.getPackmd5(jsonObject);
        bean.setPackmd5(mMD5);
        JSONObject newJsonObject = new JSONObject(new Gson().toJson(bean));

        OkHttpClientManager.okHttpPost(URL.HOST_URL, TAG, newJsonObject, new OkHttpResponseHandler() {
            @Override
            public void onSucceed(String response, int id) {
                Logger.i("支付回调：",response);
                SignResultBean b = new Gson().fromJson(response, SignResultBean.class);

//                if (b.getErrcode().equals(Constant.ERRCODE_NORMAL)) {
                final String payInfo = b.getSign();

                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(AlipayActivity.this);
                        // 调用支付接口，获取支付结果
                        Map<String, String> result = alipay.payV2(payInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
//                } else {
//                    Toast.makeText(AlipayActivity.this, b.getErrmsg(), Toast.LENGTH_SHORT).show();
//                }

            }

            @Override
            public void onFailure(Call call, Exception e, int id) {
                Toast.makeText(AlipayActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 同步支付结果给服务端
     * @param data  同步返回需要验证的信息
     */
     private void syncRequest(String data) throws JSONException {
        GetSignBean bean = new GetSignBean();
        bean.setCommand(Constant.CMD_SYNC_SIGN);
        bean.setPackmd5("");
        bean.setTimestamp(String.valueOf(System.currentTimeMillis()));
        bean.setVer(Constant.CMD_VERSION_1);
        bean.setSyncRequest(data);
        bean.setApptype(Constant.APP_TYPE_ANDROID);
        bean.setErrcode(Constant.ERRCODE);
        bean.setLanguage(Constant.LANGUAGE_CHINESE);

        JSONObject jsonObject = new JSONObject(new Gson().toJson(bean));
        //MD5加密请求包
        String mMD5 = MD5Utils.getPackmd5(jsonObject);
        bean.setPackmd5(mMD5);
        JSONObject newJsonObject = new JSONObject(new Gson().toJson(bean));

        OkHttpClientManager.okHttpPost(URL.HOST_URL, TAG, newJsonObject, new OkHttpResponseHandler() {
            @Override
            public void onSucceed(String response, int id) {
                Logger.d("同步验签服务器返回结果：",response);
                Gson gson = new Gson();
                Type type = new TypeToken<CodeBeen>() {
                }.getType();
                codeBeen = gson.fromJson(response,type);

                if (codeBeen.getErrcode().equals("1")){
                    Logger.i(codeBeen.getErrmsg());
                    ToastUtils.showShortToast(codeBeen.getErrmsg());
                    finish();
                }else {
                    ToastUtils.showShortToast(codeBeen.getErrmsg());
                }
            }

            @Override
            public void onFailure(Call call, Exception e, int id) {
                //dosomething
            }
        });

    }

//    @Override
//    public void onLeftLinearClick(LinearLayout linearLeft, ImageView imageLeft) {
//        //支付已取消
//        setResult(ALIPAY_CANCEL);
//        finish();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            setResult(102);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.pay_btn)
    public void onClick() {

        try {
            //在生产环境，必须将此代码注释！
            //EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
            //----------------------------------------
            getSign(out_trade_no);
//            测试同步验签
//            String mData ="{\"alipay_trade_app_pay_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"app_id\":\"2017022205812036\",\"auth_app_id\":\"2017022205812036\",\"charset\":\"utf-8\",\"timestamp\":\"2017-03-08 14:56:23\",\"total_amount\":\"0.01\",\"trade_no\":\"2017030821001004750256346949\",\"seller_id\":\"2088022809020717\",\"out_trade_no\":\"030814561010647\"},\"sign\":\"RdNQI47dGDtlVVM8sgSDHbE+U2rnTyQ6zZrYLIJftvgdC6xjQ/Ol4DD9HOPhzfiSQBXXPw0/+vYrn7kON0zRu2o3Py8SAKr8RtCafkEmmBnG6Mj/0oxw8NposjQNiwz0HDhVx0cMql6Z/wcPddxO4I5yeB60M8xLU+azAJR+CR9Rnw2OTR1Vu65bfQyKVzq8sd8wjWf51E5h2Q2N/NaWnbG6D45CmeraVulpocLdf24ulMv2qt6dzk/lTzjYikYPUh8tBD2mg1wT/p+aSiAWWnrgM5/bRNarrAu6fvBWUmDB4DImj3nikwqXlXk8ZpxPkdotzet0Dtt9MVrARDlqbw==\",\"sign_type\":\"RSA2\"}";
//             syncRequest(mData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
