package com.anbaoxing.e_marketing.utils;

import android.app.Activity;
import android.app.ProgressDialog;

import com.anbaoxing.e_marketing.R;


/**
 * author：kang
 * time:  2016-12-2
 *
 * 显示进度对话框
 */
public class DialogUtils {

    private static ProgressDialog dialog;

    /** 对话框显示的内容 */
    private static String messageLoad; // 正在加载...
    private static String messageWait; // 请稍后...

    /** 显示对话框 */
    public static void showWaitDialog(Activity activity, String message){
        closeDialog();
        dialog = new ProgressDialog(activity, R.style.progress_dialog_style);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }

    /** 显示对话框,点击对话框外面不会取消 */
    public static void showWaitDialogNoCancel(Activity activity, String message){
        closeDialog();
        dialog = new ProgressDialog(activity, R.style.progress_dialog_style);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    /** 对话框显示的内容 - 正在加载... */
    public synchronized static String getMessageLoad(){
        if (messageLoad == null){
            messageLoad = "正在加载...";
        }
        return messageLoad;
    }

    /** 对话框显示的内容 - 请稍后... */
    public synchronized static String getMessageWait(){
        if (messageWait == null){
            messageWait = "请稍后...";
        }
        return messageWait;
    }

    /** 关闭对话框 */
    public static void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
