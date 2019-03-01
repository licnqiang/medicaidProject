package cn.piesat.medicaid.parser;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import cn.piesat.medicaid.common.BaseParser;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.tabmode.Reactant;
import cn.piesat.medicaid.tabmode.Reactant_Table;
import cn.piesat.medicaid.tabmode.ReactionCondition;

/**
 * @author lq
 * @fileName UserInfoParser
 * @data on  2019/2/22 17:17
 * @describe 反应条件
 */
public class ReactionConditionParser extends BaseParser {

    private List<ReactionCondition> mInfo;
    private Controller.GetResultListenerCallback listener;

    public ReactionConditionParser(Controller.GetResultListenerCallback listener) {
        this.listener = listener;
    }

    @Override
    protected void parser() {
        try {
            //反应条件查询
            mInfo = SQLite.select().from(ReactionCondition.class).queryList();
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
