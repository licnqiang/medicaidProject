package cn.piesat.medicaid.parser;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import cn.piesat.medicaid.common.BaseParser;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.modeBean.AttriAndsymType;
import cn.piesat.medicaid.tabmode.Attrs;
import cn.piesat.medicaid.tabmode.AttrsType;
import cn.piesat.medicaid.tabmode.AttrsType_Table;
import cn.piesat.medicaid.tabmode.Attrs_Table;
import cn.piesat.medicaid.tabmode.Reactant;
import cn.piesat.medicaid.tabmode.Reactant_Table;

/**
 * @author lq
 * @fileName UserInfoParser
 * @data on  2019/2/22 17:17
 * @describe 属性/症状下的所有一级分类和相对应的二级分类
 */
public class AttriAndSymptomTypeParser extends BaseParser {
    /**
     * 搜索类型
     * 1 属性
     * 2 症状
     */
    private final String pid;

    private List<AttriAndsymType> mInfo;
    private Controller.GetResultListenerCallback listener;

    public AttriAndSymptomTypeParser(Controller.GetResultListenerCallback listener,
                                     String pid) {
        this.listener = listener;
        this.pid = pid;
    }

    @Override
    protected void parser() {
        try {
            //属性/症状一级分类查询
            List<AttrsType> oneAttrsType = SQLite.select().from(AttrsType.class).where(AttrsType_Table.pid.eq(pid))
                    .queryList();
            mInfo = new ArrayList<>();
            for (AttrsType attrsType : oneAttrsType) {
                AttriAndsymType attriAndsymType = new AttriAndsymType();
                //一级分类下的二级分类实体类
                List<Attrs> attrsList = SQLite.select().from(Attrs.class).where(Attrs_Table.type.eq(attrsType.id))
                        .queryList();
                attriAndsymType.attrsList = attrsList;
                //一级分类名
                attriAndsymType.typeName = attrsType.comment;
                mInfo.add(attriAndsymType);
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
