package cn.piesat.medicaid.parser;

import android.util.Log;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import cn.piesat.medicaid.common.BaseParser;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.tabmode.ChemicalReaction;
import cn.piesat.medicaid.tabmode.ChemicalReaction_Table;
import cn.piesat.medicaid.tabmode.SubstanceInfo;
import cn.piesat.medicaid.tabmode.SubstanceInfo_Table;

/**
 * @author lq
 * @fileName UserInfoParser
 * @data on  2019/2/22 17:17
 * @describe 反应结果
 */
public class ReactionResultParser extends BaseParser {

    private final String mainReactantID;
    private final String otherReactantID;
    private final String reactionConditionID;

    private ChemicalReaction mInfo;
    private Controller.GetResultListenerCallback listener;

    public ReactionResultParser(Controller.GetResultListenerCallback listener,
                                String mainReactantID, String otherReactantID, String reactionConditionID) {
        this.listener = listener;
        this.mainReactantID = mainReactantID;
        this.otherReactantID = otherReactantID;
        this.reactionConditionID = reactionConditionID;
    }

    @Override
    protected void parser() {
        try {
            //反应结果查询
            mInfo = SQLite.select().from(ChemicalReaction.class).where(ChemicalReaction_Table.mainReactantID.eq(mainReactantID))
                    .and(ChemicalReaction_Table.otherReactantID.eq(otherReactantID))
                    .and(ChemicalReaction_Table.reactionConditionID.eq(reactionConditionID))
                    .querySingle();
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
