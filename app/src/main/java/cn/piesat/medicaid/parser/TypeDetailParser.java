package cn.piesat.medicaid.parser;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.w3c.dom.Attr;

import java.util.List;

import cn.piesat.medicaid.common.BaseParser;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.tabmode.Attrs;
import cn.piesat.medicaid.tabmode.AttrsType;
import cn.piesat.medicaid.tabmode.AttrsType_Table;
import cn.piesat.medicaid.tabmode.Attrs_Table;

/**
 * @author lq
 * @fileName UserInfoParser
 * @data on  2019/2/22 17:17
 * @describe 属性分类列表
 */
public class TypeDetailParser extends BaseParser {

    private final String id;


    private List<Attrs> mInfo;
    private Controller.GetResultListenerCallback listener;

    public TypeDetailParser(Controller.GetResultListenerCallback listener,
                            String id) {
        this.listener = listener;
        this.id = id;
    }

    @Override
    protected void parser() {
        try {
            //属性分类列表
            mInfo = SQLite.select().from(Attrs.class).where(Attrs_Table.type.eq(id))
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
