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
 * @describe 化学反应表
 */
@Table(database = AppDatabase.class)
public class ChemicalReaction extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    public long _id;

    /**
     * 主反应物ID
     */
    @Column
    public String mainReactantID;

    /**
     * 主反应物
     */
    @Column
    public String mainReactantName;

    /**
     * 其他反应物ID
     */
    @Column
    public String otherReactantID;

    /**
     * 其他反应物
     */
    @Column
    public String otherReactantName;

    /**
     * 反应条件ID
     */
    @Column
    public String reactionConditionID;

    /**
     * 反应条件
     */
    @Column
    public String reactionCondition;

    /**
     * 生成物
     */
    @Column
    public String reactionProduct;

    /**
     * 反应结果
     */
    @Column
    public String reactionResult;


}
