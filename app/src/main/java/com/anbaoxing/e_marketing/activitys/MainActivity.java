package com.anbaoxing.e_marketing.activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anbaoxing.e_marketing.MyApplication;
import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.beens.MessageEvent;
import com.anbaoxing.e_marketing.fragments.GroupManageFragment;
import com.anbaoxing.e_marketing.fragments.GroupMessageFragment;
import com.anbaoxing.e_marketing.fragments.GroupNewsFragment;
import com.anbaoxing.e_marketing.fragments.GroupTabFragment;
import com.anbaoxing.e_marketing.fragments.MainTabFragment;
import com.anbaoxing.e_marketing.fragments.SettingTabFragment;
import com.anbaoxing.e_marketing.http.OkHttpResponseHandlerImpl;
import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.moudle.DoNetWork;
import com.anbaoxing.e_marketing.utils.ActivityCollection;
import com.anbaoxing.e_marketing.utils.DialogUtils;
import com.blankj.utilcode.utils.BarUtils;
import com.blankj.utilcode.utils.LocationUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.PhoneUtils;
import com.orhanobut.logger.Logger;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.anbaoxing.e_marketing.fragments.GroupTabFragment.MANAGE_FRAGMENT;
import static com.anbaoxing.e_marketing.fragments.GroupTabFragment.MASSAGE_FRAGMENT;
import static com.anbaoxing.e_marketing.fragments.GroupTabFragment.NEWS_FRAGMENT;
import static com.blankj.utilcode.utils.SizeUtils.dp2px;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.tab_main)
    RadioButton tabMain;
    @BindView(R.id.tab_group)
    RadioButton tabGroup;
    @BindView(R.id.tab_setting)
    RadioButton tabSetting;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    private static final int REQUECT_CODE_SDCARD = 1;

    private MainTabFragment mMainTabFragment;
    private GroupTabFragment mGroupTabFragment;
    private GroupManageFragment mGroupManageFragment;
    private GroupMessageFragment mGroupMessageFragment;
    private GroupNewsFragment mGroupNewsFragment;
    private SettingTabFragment mSettingTabFragment;
    private FragmentManager mFragmentManager;
    private Fragment fromfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollection.add(this);
        ButterKnife.bind(this);
        //设置透明状态栏
        BarUtils.setTransparentStatusBar(this);

        initView();
        initFragment();

        //请求权限
        MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD,
                Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Logger.d("手机运营商：", PhoneUtils.getSimOperatorName());

        Logger.d("根据经纬度获取地区：", LocationUtils.getAddress(22.695762,113.910091).getAdminArea()+" "+
                LocationUtils.getAddress(22.695762,113.910091).getLocality());

        if (MyApplication.getUseBeen() == null){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        DialogUtils.closeDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollection.remove(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess() {
        Toast.makeText(this, "已获取位置权限！", Toast.LENGTH_SHORT).show();
        getLacAndCID();
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed() {
        Toast.makeText(this, "授权失败！", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        initDrawable(tabMain);
        initDrawable(tabGroup);
        initDrawable(tabSetting);

        tabMain.setChecked(true);
    }

    /**
     * 指定tab 导航栏图标大小
     *
     * @param radioButton RadioButton
     */
    private void initDrawable(RadioButton radioButton) {
        Drawable drawable = radioButton.getCompoundDrawables()[1];
        drawable.setBounds(0, 0, dp2px(100), dp2px(45));
        radioButton.setCompoundDrawables(null, drawable, null, null);
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mMainTabFragment = new MainTabFragment();
        changeFragment(mMainTabFragment);
        fromfragment = mMainTabFragment;
    }

    private void changeFragment(Fragment f) {
        android.support.v4.app.FragmentTransaction beginTransaction = mFragmentManager.beginTransaction();
        beginTransaction.add(R.id.content, f, f.getClass().getSimpleName()).commit();
        Log.i("add1", f.getClass().getSimpleName());
    }

    private void changeFragment(Fragment from, Fragment to) {
        if (fromfragment != to) {
            fromfragment = to;
            android.support.v4.app.FragmentTransaction transaction = mFragmentManager.beginTransaction();

            if (!to.isAdded()) {                                          // 先判断是否被add过
                transaction.hide(from).add(R.id.content, to).commit();   // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit();                  // 隐藏当前的fragment，显示下一个
            }
        }
        Log.i("add2", to.getClass().getSimpleName());
    }

    /**
     * 获取基站信息
     */
    private void getLacAndCID() {
        //仅供测试
        getLocationOnNet("1",String.valueOf(9541),String.valueOf(101950721));

        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        // 返回值MCC + MNC
//        String operator = mTelephonyManager.getNetworkOperator();
//        int mcc = Integer.parseInt(operator.substring(0, 3));
//        int mnc = Integer.parseInt(operator.substring(3));

        if (!NetworkUtils.getDataEnabled()) {
            Logger.d("未使用移动数据网络");
            return;
        }
        if (NetworkUtils.NetworkType.NETWORK_WIFI.equals(NetworkUtils.getNetworkType())) {
            Logger.d("当前正使用WiFi上网");
            return;
        }
        Logger.d("判断移动数据是否打开：", String.valueOf(NetworkUtils.getDataEnabled()));
        Logger.d("当前网络类型：", String.valueOf(NetworkUtils.getNetworkType()));
        // MCC：Mobile Country Code，移动国家码，共3位，中国为460;
        //MNC:Mobile Network Code，在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03
        Logger.d("移动网络运营商代号：", String.valueOf(mTelephonyManager.getNetworkOperator()));
        Logger.d("网络运营商：", String.valueOf(mTelephonyManager.getSimOperatorName()));
        if ("46003".equals(mTelephonyManager.getNetworkOperator())) {
            // 中国电信获取LAC、CID的方式
            CdmaCellLocation location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
            int lac = location1.getNetworkId();
            int cellId = location1.getBaseStationId();
            Logger.d("基站信息", "LAC: " + lac + " CID: " + cellId);

        } else {
            // 中国移动和中国联通获取LAC、CID的方式
            GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
            int lac = location.getLac();
            int cellId = location.getCid();
            Logger.d("基站信息", "LAC: " + lac + " CID: " + cellId);
        }
    }

    /**
     * 基站获取位置
     * @param mMnc
     * @param mLac
     * @param mCell
     */
    private void getLocationOnNet(String mMnc,String mLac,String mCell){
        DoNetWork doNetWork = new DoNetWork(RequestPackage.GET_LOCATION);
//        doNetWork.getLocation02("2", "12355", "12355","12355", new OkHttpResponseHandlerImpl() {
        doNetWork.getLocation01(mMnc, mLac, mCell, new OkHttpResponseHandlerImpl() {
            @Override
            public void onBefore(Request request, int id) {

            }

            @Override
            public void onAfter(int id) {

            }

            @Override
            public void onSucceed(String response, int id) {
                Logger.d("getLocationOnNet",response);
            }

            @Override
            public void onFailure(Call call, Exception e, int id) {

            }
        });

    }

    @OnClick({R.id.tab_main, R.id.tab_group, R.id.tab_setting})
    public void onClick(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();
        switch (radioButton.getId()) {
            case R.id.tab_main:
                if (checked) {
                    if (mMainTabFragment == null) {
                        mMainTabFragment = new MainTabFragment();
                    }
                    changeFragment(fromfragment, mMainTabFragment);
                    fromfragment = mMainTabFragment;
                }
                break;
            case R.id.tab_group:
                if (checked) {
                    if (mGroupTabFragment == null) {
                        mGroupTabFragment = new GroupTabFragment();
                    }
                    changeFragment(fromfragment, mGroupTabFragment);
                    fromfragment = mGroupTabFragment;
                }
                break;
            case R.id.tab_setting:
                if (checked) {
                    if (mSettingTabFragment == null) {
                        mSettingTabFragment = new SettingTabFragment();
                    }
                    changeFragment(fromfragment, mSettingTabFragment);
                    fromfragment = mSettingTabFragment;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 事件处理
     * @param messageEvent 事件消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageDeal(MessageEvent messageEvent) {
        switch (messageEvent.getmPosition()) {
            case MANAGE_FRAGMENT:
                Logger.d("接收到请求事件！！！");
                break;
            case MASSAGE_FRAGMENT:

                break;
            case NEWS_FRAGMENT:

                break;
            default:
                break;
        }
    }
}
