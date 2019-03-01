package cn.piesat.medicaid.parser;

import android.util.Log;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import cn.piesat.medicaid.common.BaseParser;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.modeBean.SubstanceScale;
import cn.piesat.medicaid.tabmode.ChemicalReaction;
import cn.piesat.medicaid.tabmode.ChemicalReaction_Table;
import cn.piesat.medicaid.tabmode.ReactionCondition;
import cn.piesat.medicaid.tabmode.SubstanceAttrs;
import cn.piesat.medicaid.tabmode.SubstanceAttrs_Table;
import cn.piesat.medicaid.tabmode.SubstanceInfo;
import cn.piesat.medicaid.tabmode.SubstanceInfo_Table;
import cn.piesat.medicaid.util.DataUtil;

/**
 * @author lq
 * @fileName UserInfoParser
 * @data on  2019/2/22 17:17
 * @describe 通过属性id查物质ic
 */
public class SubstanceIdByAttrsIdParser extends BaseParser {
    /*属性id集合*/
    private List<String> ids;
    private List<SubstanceScale> mInfo;
    private Controller.GetResultListenerCallback listener;

    public SubstanceIdByAttrsIdParser(Controller.GetResultListenerCallback listener, List<String> ids) {
        this.listener = listener;
        this.ids = ids;
    }

    @Override
    protected void parser() {
        try {
            //通过属性id查物质id
            List<SubstanceAttrs> substanceAttrs = SQLite.select(SubstanceAttrs_Table.substanceNum).from(SubstanceAttrs.class).where(SubstanceAttrs_Table.attrValue.in(ids)).queryList();
            List<String> oldStr = new ArrayList<>();
            List<String> newStr = new ArrayList<>();
            for (SubstanceAttrs str : substanceAttrs) {
                oldStr.add(str.substanceNum);
            }
            newStr.addAll(oldStr);
            //集合去重
            DataUtil.removeDuplicate(newStr);
            // 查询物质信息表
            List<SubstanceInfo> substanceInfos = SQLite.select().from(SubstanceInfo.class).where(SubstanceInfo_Table.substanceNum.in(newStr)).queryList();
            mInfo = new ArrayList<>();
            for (SubstanceInfo substanceInfo : substanceInfos) {
                SubstanceScale substanceScale = new SubstanceScale();
                int frequency = Collections.frequency(oldStr, substanceInfo.substanceNum);
                substanceScale.substanceName = substanceInfo.substanceName;
                substanceScale.scale = (float) frequency / (float) oldStr.size() * 100;
                mInfo.add(substanceScale);
            }

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
