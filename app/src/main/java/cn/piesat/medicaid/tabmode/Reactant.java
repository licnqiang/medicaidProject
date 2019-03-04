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
 * @describe  反应物表
 */
@Table(database = AppDatabase.class)
public class Reactant extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    public long _id;

    /**
     * 反应物ID
     */
    @Column
    public String reactantID;

    /**
     * 反应物名称
     */
    @Column
    public String reactantName;

    /**
     * 1-单个物质；2-一组物质
     */
    @Column
    public String type;


}
