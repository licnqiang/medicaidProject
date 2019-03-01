package cn.piesat.medicaid.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.piesat.medicaid.R;


/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class LoadingUI {

    /**
     * 加载数据对话框
     */
    private static Dialog mLoadingDialog;
    private static TextView loadingText;
    private static String Message;
    private static Handler handler = null;

    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public static void showDialogForLoading(Context context, String msg, boolean cancelable) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null);
        loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        handler = new Handler();
        loadingText.setText(msg);

        mLoadingDialog = new Dialog(context, R.style.loading_dialog_style);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
    }

    /**
     * 关闭加载对话框
     */
    public static void hideDialogForLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
    }
}
