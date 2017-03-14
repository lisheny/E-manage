package com.anbaoxing.e_marketing.moudle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.http.OkHttpClientManager;
import com.anbaoxing.e_marketing.http.OkHttpFileResponseHandler;
import com.anbaoxing.e_marketing.http.OkHttpResponseHandler;
import com.anbaoxing.e_marketing.http.RequestPackage;
import com.anbaoxing.e_marketing.utils.Constant;
import com.anbaoxing.e_marketing.utils.DialogUtils;
import com.anbaoxing.e_marketing.utils.MainUtils;
import com.anbaoxing.e_marketing.utils.RequestUtils;
import com.anbaoxing.e_marketing.utils.VersionUtils;
import com.anbaoxing.e_marketing.widget.TextDialog;
import com.blankj.utilcode.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author：kang
 * time:  2016-12-2
 * 项目升级，需要 OkHttp 框架支持
 */
public class AppUpdateBiz extends RequestPackageImpl{

    /** TAG */
    private static final String TAG = "AppUpdateBiz  -->  ";

    /** 下载的 Apk 文件名称 */
    private static final String APK_NAME = "Application.apk";

    public static final String versioncode = "versioncode";
    public static final String downloadurl = "appurl";

    /** 进度对话框 (ProgressDialog) */
    private ProgressDialog dialog;
    private Activity context;

    /** 是否取消下载 */
    private boolean isCancelDownload = false;
    private OnAppUpdateListener appUpdateListener;

    /** 提示升级的 Dialog */
    private AlertDialog updateAlertDialog;
    /** 提示升级的 TextDialog */
    private TextDialog textDialog;

    /** 项目升级，需要 OkHttp 框架支持 */
    private static AppUpdateBiz appUpdateBiz;

    private AppUpdateBiz(@NonNull Activity context) {
        this(context, RequestPackage.APP_UPDATE_COMMAND);
    }

    private AppUpdateBiz(@NonNull Activity context, String command) {
        super(command);
        this.context = context;
    }

    /** 获取 AppUpdateBiz 对象，用单例模式可以解决一个问题 */
    public static AppUpdateBiz getAppUpdateBiz(@NonNull Activity activity){
        if (appUpdateBiz == null){
            synchronized (AppUpdateBiz.class){
                if ((appUpdateBiz == null) && (activity != null)){
                    appUpdateBiz = new AppUpdateBiz(activity);
                }
            }
        }
        return appUpdateBiz;
    }

    /**
     * 判断是否有 新的版本,如果有新的版本，就提示是否下载,
     * 版本升级通过 versionCode 判断
     * @param username 用户名
     * @param isShowWaitingDialog    是否显示 等待对话框
     * @param newVersionIsToast      有新的版本是否提示 Toast
     * @param appUpdateListener 接口回调
     */
    public void haveNewVersion(String username, boolean isShowWaitingDialog, final boolean newVersionIsToast,
                               final OnAppUpdateListener appUpdateListener){
        try {
            if (isShowWaitingDialog){
                DialogUtils.showWaitDialogNoCancel(context,DialogUtils.getMessageWait());
            }
            if (TextUtils.isEmpty(username)){
                requestPackage.put(RequestPackageImpl.username,defaultValue);
            }else {
                requestPackage.put(RequestPackageImpl.username, username);
            }
            requestPackage.put(versioncode,Constant.VERSIONCODE);

            this.appUpdateListener = appUpdateListener;
            if (appUpdateListener != null){
                // 发送请求之前
                appUpdateListener.onUpdateBefore(username,requestPackage);
            }
            // 发送请求，判断是否有新的版本，有就提示下载
            OkHttpClientManager.okHttpPost(requestPackage,new OkHttpResponseHandler() {
                @Override
                public void onSucceed(String response, int id) {
                    try {
                        DialogUtils.closeDialog();
                        if (appUpdateListener != null){
                            // 请求发送成功
                            appUpdateListener.onRequestSucceed(response,id);
                        }
                        //将 字符串转换成 JSONObject
                        JSONObject obj = RequestUtils.stringToJsonObject(response);
                        String errMsg = RequestUtils.getString(obj, RequestPackage.errmsg);
                        String errCode = RequestUtils.getString(obj,RequestPackage.errcode);
                        // 判断请求是否成功
                        if (Constant.SUCCESS_CODE.equals(errCode)){
                            String versionCode = RequestUtils.getString(obj,versioncode); // versionCode
                            // 服务器上的版本号
                            int serverVersionCode = Integer.parseInt(versionCode);
                            // 判断服务器上的版本 是否大于 当前版本
                            if (serverVersionCode > VersionUtils.getVersionCode()) {
                                // 下载 Url
                                String downloadUrl = RequestUtils.getString(obj,downloadurl);
                                if (!MainUtils.textIsEmpty(downloadUrl)) {
                                    if (appUpdateListener != null){
                                        // 服务器上有新的版本，是否需要弹出对话框提示用户升级
                                        boolean isHintUserUpdate = appUpdateListener.onIsHintUserUpdate(
                                                serverVersionCode,downloadUrl);
                                        if (!isHintUserUpdate){
                                            // 不提示用户升级
                                            Logger.d(TAG,"onSucceed() --> 不提示用户升级...");
                                            return;
                                        }
                                    }
                                    // 提示用户是否升级的对话框
                                    showUpdateAlertDialog(downloadUrl);
                                    //showUpdateTextDialog(downloadUrl);
                                } else {
                                    ToastUtils.showShortToast("网络繁忙1");
                                }
                            } else {
                                if (newVersionIsToast) {
                                    ToastUtils.showShortToast("当前已经是最新版本" +
                                            VersionUtils.getVersionName());
                                }
                            }
                        }else {
                            ToastUtils.showShortToast(errMsg);
                        }
                    } catch (Exception e) {
                        ToastUtils.showShortToast("网络繁忙2");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call call, Exception e, int id) {
                    if (appUpdateListener != null){
                        // 请求发送失败以后调用
                        appUpdateListener.onRequestFailure(call,e,id);
                    }
                    DialogUtils.closeDialog();
                    ToastUtils.showShortToast("网络繁忙3");
                }
            });
        } catch (Exception e) {
            DialogUtils.closeDialog();
            ToastUtils.showShortToast("网络繁忙4");
            e.printStackTrace();
        }
    }

    /**
     * 判断是否有 新的版本,如果有新的版本，就提示是否下载,
     * 版本升级通过 versionCode 判断
     * @param isShowWaitingDialog    是否显示 等待对话框
     * @param newVersionIsToast      有新的版本是否提示 Toast
     * * @param appUpdateListener 接口回调
     */
    public void haveNewVersion(boolean isShowWaitingDialog,final boolean newVersionIsToast,
                               final OnAppUpdateListener appUpdateListener){
        this.haveNewVersion(null,isShowWaitingDialog,newVersionIsToast,appUpdateListener);
    }

    /**
     * 判断是否有 新的版本,如果有新的版本，就提示是否下载,
     * 版本升级通过 versionCode 判断
     * @param isShowWaitingDialog    是否显示 等待对话框
     * @param newVersionIsToast      有新的版本是否提示 Toast
     */
    public void haveNewVersion(boolean isShowWaitingDialog,final boolean newVersionIsToast){
        this.haveNewVersion(null,isShowWaitingDialog,newVersionIsToast,null);
    }

    /**
     * 判断是否有 新的版本,如果有新的版本，就提示是否下载,
     * 版本升级通过 versionCode 判断
     * @param newVersionIsToast  有新的版本是否提示 Toast
     */
    public void haveNewVersion(boolean newVersionIsToast){
        this.haveNewVersion(null,false,newVersionIsToast,null);
    }

    /** 显示是否升级的对话框 */
    private void showUpdateAlertDialog(final String downloadUrl){
        //关闭 AlertDialog
        this.cancelUpdateAlertDialog();
        // 提示是否升级
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.alert_dialog_style);
        builder.setTitle("升级") //升级
                .setMessage("已检测到最新版本，是否立即升级？");// 已检测到最新版本，是否立即升级？
        // 取消升级
        builder.setNegativeButton("取消升级", new MyAlertDialogListener(downloadUrl));
        // 立即升级
        builder.setPositiveButton("立即升级", new MyAlertDialogListener(downloadUrl));
        builder.setCancelable(false);
        updateAlertDialog = builder.create();
        updateAlertDialog.show();
    }

    /** 关闭 AlertDialog */
    private void cancelUpdateAlertDialog(){
        if (updateAlertDialog != null){
            updateAlertDialog.cancel();

            Logger.d(TAG,  "cancelUpdateAlertDialog()...true");
        }
        if (textDialog != null){
            textDialog.cancel();
        }
    }

    /** 显示是否升级的对话框 */
    private void showUpdateTextDialog(final String downloadUrl){
        //关闭 AlertDialog
        this.cancelUpdateAlertDialog();
        // 不使用这个对话框时，可以将此方法注释
        textDialog = new TextDialog(context, "升级",
               "已检测到最新版本，是否立即升级？", // 已检测到最新版本，是否立即升级？
               "取消升级", // 取消升级
                "立即升级", // 立即升级
                new UpdateTextDialogListener(downloadUrl));
        textDialog.setCancelable(false);
        textDialog.show();
    }

    class UpdateTextDialogListener implements TextDialog.TextDialogListener{
        /** Apk 文件在服务器上的路径 */
        private String downloadUrl;
        public UpdateTextDialogListener(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }
        @Override
        public void onConfirmClick() {
            /** 检查 外部存储目录 */
            checkExternalStorage(downloadUrl);
        }
    }

    class MyAlertDialogListener implements DialogInterface.OnClickListener{
        // Apk 文件在服务器上的路径
        private String downloadUrl;
        public MyAlertDialogListener(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE: // 立即升级
                    if (dialog != null) dialog.cancel();
                    // 检查 外部存储目录
                    checkExternalStorage(downloadUrl);
                    break;
                case DialogInterface.BUTTON_NEGATIVE: // 取消升级
                    if (dialog != null) dialog.cancel();
                    break;
            }
        }
    }

    /**
     * 检查 外部存储目录
     * @param downloadUrl  Apk 文件在服务器上的路径
     */
    private void checkExternalStorage(final String downloadUrl){
        try {
            // 判断 sdcard 的状态
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
                //  获取 SD 卡 外部存储目录 /storage/emulated/0/Download
                File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!sdcard.exists()){
                    Logger.d(TAG,  "SD 卡外部存储目录创建：" + sdcard.mkdirs());
                }
                //  /storage/emulated/0/Download/Application.apk
                String apkPath = sdcard + "/" + AppUpdateBiz.APK_NAME;
                File file = new File(apkPath);
                //判断 file 有没有 -- 如果有就删除
                if (file.exists()){
                    Logger.d(TAG,  "Apk 文件是否删除成功：" + file.delete());
                }
                /**
                 * 根据 Apk 文件 Url 下载 Apk 文件
                 * 手机没有安装 SD 卡，就不会下载
                 * @param downloadUrl  Apk 文件在服务器上的路径
                 * @param destFileDir  目标文件存储的文件夹路径
                 * @param apkPath  Apk 文件在 SD 卡上的路径
                 */
                this.downloadApkFile(downloadUrl,sdcard.toString(),apkPath);
            }else {
                ToastUtils.showShortToast("网络繁忙");
            }
        } catch (Exception e) {
            ToastUtils.showShortToast("网络繁忙");
            e.printStackTrace();
        }
    }

    /**
     * 根据 Apk 文件 Url 下载 Apk 文件
     * 手机没有安装 SD 卡，就不会下载
     * @param downloadUrl  Apk 文件在服务器上的路径
     * @param destFileDir  目标文件存储的文件夹路径
     * @param apkPath  Apk 文件在 SD 卡上的路径
     */
    private void downloadApkFile(final String downloadUrl, String destFileDir, final String apkPath){

        // 下载文件
        OkHttpClientManager.okHttpGetDownloadFile(downloadUrl, TAG, destFileDir, AppUpdateBiz.APK_NAME,
                new OkHttpFileResponseHandler() {
                    @Override
                    public void onBefore(Request request, int id) {
                        Logger.d(TAG,  "onBefore() --> " + request.url().toString());
                        showProgressDialog();
                    }

                    @Override
                    public void onFileSucceed(File response, int id) {
                        closeProgressDialog();
                        Logger.d(TAG,  "onFileSucceed() --> response：" + response + "  id：" + id);
                        if (appUpdateListener != null){
                            //下载文件成功
                            boolean isInstall = appUpdateListener.onDownloadSucceed(response, apkPath,id);
                            if (!isInstall){
                                // 不安装 Apk 文件
                                Logger.d(TAG,  "onFileSucceed() --> 不安装 Apk 文件...");
                                return;
                            }
                        }
                        if (response.exists()){
                            // 安装 Apk 文件
                            installApk(response.toString());
                        }else {
                            ToastUtils.showShortToast("网络繁忙");
                        }
                    }

                    @Override
                    public void onUpdateProgress(float progress, long total, int id) {
                        if (appUpdateListener != null){
                            // 更新 ProgressDialog 进度，true - 已经更新过进度，false - 没有更新过进度
                            boolean isUpdate = appUpdateListener.onUpdateProgress(dialog, progress, total, id);
                            if (isUpdate){
                                // 已经更新过进度
                                Logger.d(TAG,  "onUpdateProgress() --> 已经更新过进度...");
                                return;
                            }
                        }
                        if (dialog != null) {
                            dialog.setMax((int) total);
                            dialog.setProgress((int) (progress * total));
                        }
                        Logger.d(TAG,  "onUpdateProgress() --> progress：" + progress + "  total：" + total + "  id：" + id);
                    }

                    @Override
                    public void onFailure(Call call, Exception e, int id) {
                        closeProgressDialog();
                        if (appUpdateListener != null){
                            // 下载文件失败以后调用
                            appUpdateListener.onDownloadFailure(call,e,id);
                        }
                        Logger.d(TAG,  "onUpdateProgress() --> call：" + call.request().url() +
                                "  e：" + e.getMessage() + "  id：" + id);
                        if (!isCancelDownload) {
                            ToastUtils.showShortToast("网络繁忙");
                        }
                    }
                });
    }

    /** 显示 ProgressDialog */
    private void showProgressDialog(){
        dialog = new ProgressDialog(context,R.style.progress_dialog_style);
        dialog.setTitle("正在升级");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(false);
        dialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "取消升级",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isCancelDownload = true;
                        OkHttpClientManager.cancelRequest(TAG);
                        // 关闭 ProgressDialog
                        closeProgressDialog();
                        Logger.d(TAG,  "onClick() -->  取消升级...");
                    }
                });
        dialog.show();
    }

    /** 关闭 ProgressDialog */
    private void closeProgressDialog(){
        if (dialog != null){
            dialog.cancel();
        }
    }

    /** 安装 Apk 文件 */
    private void installApk(String apkPath) {
        // /storage/emulated/0/Download/yanhong.apk
        Logger.d(TAG,  "Apk 文件路径：" + apkPath);
        File apkFile = new File(apkPath);
        // 如果文件不存在 取消安装
        if (!apkFile.exists()) {
            ToastUtils.showShortToast("安装包解析错误，请重试");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(apkFile);
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /** 升级回调接口 */
    public interface OnAppUpdateListener extends OnFailureListener{

        /**
         * 发送请求之前调用
         * @param username 用户名
         * @param requestPackage 请求包
         */
        void onUpdateBefore(String username, JSONObject requestPackage);

        /**
         * 请求发送成功以后调用
         * @param response 响应数据包
         * @param id 请求 id
         */
        void onRequestSucceed(String response, int id);

        /**
         * 请求发送成功以后，服务器上有新的版本，是否需要弹出对话框提示用户升级
         * @param serverVersionCode 服务器上的版本号
         * @param downloadUrl Apk 文件 Url
         * @return 是否需要提示用户升级，true - 提示用户，false - 不提示用户
         */
        boolean onIsHintUserUpdate(int serverVersionCode, String downloadUrl);

        /**
         * 更新 ProgressDialog 进度,调用多次
         * @param progress 下载进度(字节)
         * @param total 文件大小(字节)
         * @param id 请求 id
         * @return 是否已经更新过进度，true - 已经更新过进度，false - 没有更新过进度
         */
        boolean onUpdateProgress(ProgressDialog dialog, float progress, long total, int id);

        /**
         * 下载文件成功以后调用
         * @param response 下载的文件路径 (/storage/emulated/0/Download/Application.apk)
         * @param apkFile Apk 文件 (/storage/emulated/0/Download/Application.apk)
         * @param id 请求 id
         * @return  是否要安装 Apk 文件，true - 安装，false - 不安装
         */
        boolean onDownloadSucceed(File response, String apkFile, int id);
    }
}
