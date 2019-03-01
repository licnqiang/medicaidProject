package cn.piesat.medicaid.parser;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import cn.piesat.medicaid.common.BaseParser;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.tabmode.SubstanceInfo;
import cn.piesat.medicaid.tabmode.SubstanceInfo_Table;

/**
 * @author lq
 * @fileName UserInfoParser
 * @data on  2019/2/22 17:17
 * @describe 通过id查询物质详情
 */
public class SubstanceInfoParser extends BaseParser {
    private String substanceNum;
    private List<SubstanceInfo> mInfo;
    private Controller.GetResultListenerCallback listener;

    public SubstanceInfoParser(Controller.GetResultListenerCallback listener, String substanceNum) {
        this.listener = listener;
        this.substanceNum = substanceNum;
    }

    @Override
    protected void parser() {
        try {
            //通过id查询物质详情
            mInfo = SQLite.select().from(SubstanceInfo.class).where(SubstanceInfo_Table.substanceNum.eq(substanceNum)).queryList();
        } catch (Exception e) {
            Log.e("http", "sql错误--e==" + e);
            listener.onErro("");
        }
    }

    @Override
    protected void Success() {
        listener.onFinished(mInfo);
    }

    @Override
    protected void Error() {
        listener.onErro("");
    }
}
