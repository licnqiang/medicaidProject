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
 * @describe 反应条件表
 */
@Table(database = AppDatabase.class)
public class ReactionCondition extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    public long _id;

    /**
     * 反应条件ID
     */
    @Column
    public String reactionConID;

    /**
     * 反应条件名称
     */
    @Column
    public String reactionConName;

    /**
     * 0-未启用；1-启用
     */
    @Column
    public String state;


}
