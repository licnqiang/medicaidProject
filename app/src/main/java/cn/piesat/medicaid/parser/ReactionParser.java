package cn.piesat.medicaid.parser;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import cn.piesat.medicaid.common.BaseParser;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.tabmode.ChemicalReaction;
import cn.piesat.medicaid.tabmode.ChemicalReaction_Table;
import cn.piesat.medicaid.tabmode.Reactant;
import cn.piesat.medicaid.tabmode.Reactant_Table;

/**
 * @author lq
 * @fileName UserInfoParser
 * @data on  2019/2/22 17:17
 * @describe 反应物
 */
public class ReactionParser extends BaseParser {

    private final String reactionType;


    private List<Reactant> mInfo;
    private Controller.GetResultListenerCallback listener;

    public ReactionParser(Controller.GetResultListenerCallback listener,
                          String reactionType) {
        this.listener = listener;
        this.reactionType = reactionType;
    }

    @Override
    protected void parser() {
        try {
            //反应物查询
            mInfo = SQLite.select().from(Reactant.class).where(Reactant_Table.type.eq(reactionType))
                    .queryList();
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
