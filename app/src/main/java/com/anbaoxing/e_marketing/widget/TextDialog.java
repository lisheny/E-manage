package com.anbaoxing.e_marketing.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anbaoxing.e_marketing.R;


/**
 * author：kangjia
 * time:  2015-12-15
 */
public class TextDialog extends Dialog implements View.OnClickListener {

    /** 自定义对话框主题 */
    public static final int DIALOG_THEME = R.style.dialog_with_alpha;

    /** 确定按钮 **/
    private Button btConfirm;
    /** 取消按钮 **/
    private Button btCancel;

    private TextView tvContent;
    private TextView tvTitle;

    private TextView tvLine;

    private TextDialogListener textDialogListener;

    private String title,contentText, leftText, rightText;

    /** 是否隐藏取消按钮 默认不隐藏 */
    private boolean isCancelGone = false;

    /**  两 个按钮 有标题  */
    public TextDialog(Context context, String title, String contentText, TextDialogListener textDialogListener) {
        super(context,DIALOG_THEME);

        this.title = title;
        this.contentText = contentText;

        this.textDialogListener = textDialogListener;
    }

    /**  两 个按钮 有标题  可设置 确认 和 取消 按钮文本 */
    public TextDialog(Context context, String title, String contentText, String leftText, String rightText,
                      TextDialogListener textDialogListener) {
        super(context,DIALOG_THEME);

        this.title = title;
        this.contentText = contentText;

        this.leftText = leftText;
        this.rightText = rightText;

        this.textDialogListener = textDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 指定布局
        this.setContentView(R.layout.text_dialog_item);

        // 确认
        btConfirm = (Button) findViewById(R.id.text_dialog_confirm);
        //取消
        btCancel = (Button) findViewById(R.id.text_dialog_cancel);

        //内容
        tvContent = (TextView) findViewById(R.id.text_dialog_content);
        //标题
        tvTitle = (TextView)findViewById(R.id.text_dialog_title);

        tvLine = (TextView)findViewById(R.id.text_dialog_line);

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(rightText)) {
            btConfirm.setText(rightText);
        }

        if (!TextUtils.isEmpty(leftText)) {
            btCancel.setText(leftText);
        }

        //内容
        tvContent.setText(contentText);

        if (isCancelGone) {
            btCancel.setVisibility(View.GONE);
            tvLine.setVisibility(View.GONE);
        }

        // 为按钮绑定点击事件监听器
        btConfirm.setOnClickListener(this);
        btCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_dialog_confirm:
                if (textDialogListener != null) {
                    // 点击了 确认 按钮
                    textDialogListener.onConfirmClick();
                }
                this.cancel();
                break;
            case R.id.text_dialog_cancel:
                // 点击了 取消 按钮
                this.cancel();
                break;
        }
    }

    public interface TextDialogListener {
        //确认
        void onConfirmClick();
    }
}
