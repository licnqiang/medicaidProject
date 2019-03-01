package cn.piesat.medicaid.tabmode;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import cn.piesat.medicaid.db.AppDatabase;


/**
 * @author lq
 * @fileName SubstanceInfo
 * @data on  2019/2/27 10:06
 * @describe 物质属性表
 */
@Table(database = AppDatabase.class)
public class SubstanceAttrs extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    public long id;

    /**
     * 物质编号
     */
    @Column
    public String substanceNum;

    /**
     * 属性名
     */
    @Column
    public String attrName;

    /**
     * 属性值id
     */
    @Column
    public String attrValue;


}
