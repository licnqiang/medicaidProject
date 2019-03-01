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
 * @describe 用户数据操作类
 */
public class SearchInfoParser extends BaseParser {
    private String searchStr;
    private final int page;
    private final int maxNum;
    private List<SubstanceInfo> mInfo;
    private Controller.GetResultListenerCallback listener;

    public SearchInfoParser(Controller.GetResultListenerCallback listener, String searchStr, int page, int maxNum) {
        this.listener = listener;
        this.searchStr = searchStr;
        this.page = page;
        this.maxNum = maxNum;
    }

    @Override
    protected void parser() {
        try {
            //分页查询
            mInfo = SQLite.select().from(SubstanceInfo.class).where(SubstanceInfo_Table.substanceName.like("%" + searchStr + "%")).limit(maxNum).offset(page * maxNum).queryList();
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
