package cn.piesat.medicaid.common;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author lq
 * @fileName BaseParser
 * @data on  2019/2/22 16:58
 * @describe 基础解析层
 */
public abstract class BaseParser extends Thread {

    public final static int WHAT_SUCCESS = 0;// 成功

    public final static int WHAT_ERROR = 1;// 失败

    // 解析
    protected abstract void parser() throws Exception;

    // 获取数据成功
    protected abstract void Success();

    // 获取数据失败
    protected abstract void Error();

    protected Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_SUCCESS:
                    Success();
                    break;

                case WHAT_ERROR:
                    Error();
                    break;
            }
        }

    };

    @Override
    public void run() {
        super.run();
        try {
            parser();
            mHandler.sendEmptyMessage(WHAT_SUCCESS);
        } catch (Exception e) {
            Log.e("---sql---", "解析错误--e==" + e);
            mHandler.sendEmptyMessage(WHAT_ERROR);
        }
    }
}
