package com.anbaoxing.e_marketing.activitys;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.anbaoxing.e_marketing.R;
import com.blankj.utilcode.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shaohui.shareutil.ShareUtil;
import me.shaohui.shareutil.share.ShareListener;
import me.shaohui.shareutil.share.SharePlatform;

public class PopWindowActivity extends BaseActivity {

    @BindView(R.id.aty_share_qq)
    TextView atyShareQq;
    @BindView(R.id.aty_share_qqzone)
    TextView atyShareQqzone;
    @BindView(R.id.aty_share_wx)
    TextView atyShareWx;
    @BindView(R.id.aty_share_wx_timeline)
    TextView atyShareWxTimeline;

    private ShareListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_window);
        ButterKnife.bind(this);
        listener = new ShareListener() {
            @Override
            public void shareSuccess() {
                ToastUtils.showShortToast("分享成功！");

            }

            @Override
            public void shareFailure(Exception e) {
                ToastUtils.showShortToast("分享失败！");

            }

            @Override
            public void shareCancel() {
                ToastUtils.showShortToast("分享取消！");

            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @OnClick({R.id.aty_share_qq, R.id.aty_share_qqzone, R.id.aty_share_wx, R.id.aty_share_wx_timeline})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aty_share_qq:
                if (ShareUtil.isInstalled(SharePlatform.QQ,this)){
                    ShareUtil.shareMedia(this, SharePlatform.QQ,
                            "测试标题",
                            "概要说明",
                            "http://www.jikedaohang.com/",
                            "http://www.jikedaohang.com/images/gaoxiao.jpg",
                            listener);
                }else {
                    ToastUtils.showShortToast("未安装QQ");
                }
                finish();
                break;
            case R.id.aty_share_qqzone:
                if (ShareUtil.isInstalled(SharePlatform.QQ,this)){
                    ShareUtil.shareMedia(this, SharePlatform.QZONE,
                            "测试标题",
                            "概要说明某某某000000000",
                            "http://www.jikedaohang.com/",
                            "http://www.jikedaohang.com/images/gaoxiao.jpg",
                            listener);
                }else {
                    ToastUtils.showShortToast("未安装QQ");
                }
                finish();
                break;
            case R.id.aty_share_wx:
                finish();
                break;
            case R.id.aty_share_wx_timeline:
                finish();
                break;
        }
    }
}
