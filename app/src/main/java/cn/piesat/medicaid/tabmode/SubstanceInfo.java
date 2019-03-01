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
 * @describe 物质信息表
 */
@Table(database = AppDatabase.class)
public class SubstanceInfo extends BaseModel implements Serializable {
    @PrimaryKey(autoincrement = true)
    public long _id;

    /**
     * 物质编号
     */
    @Column
    public String substanceNum;

    /**
     * 物质名称
     */
    @Column
    public String substanceName;

    /**
     * 备注
     */
    @Column
    public String remark;

    /**
     * 中文别名
     */
    @Column
    public String chAlias;

    /**
     * 英文名称
     */
    @Column
    public String enAlias;

    /**
     * 分子式
     */
    @Column
    public String molecularFormula;

    /**
     * 分子量
     */
    @Column
    public String molecularWeight;

    /**
     * 用途
     */
    @Column
    public String purpose;

    /**
     * 外观与性状
     */
    @Column
    public String appearanceAndCharacter;

    /**
     * 溶解性
     */
    @Column
    public String solubility;

    /**
     * 稳定性
     */
    @Column
    public String stability;

    /**
     * 危害特性
     */
    @Column
    public String hazardProperty;

    /**
     * 健康危害
     */
    @Column
    public String healthHazard;

    /**
     * 毒理学信息
     */
    @Column
    public String toxicologyInfo;

    /**
     * 临床表现
     */
    @Column
    public String clinicalManifestation;

    /**
     * 保护措施
     */
    @Column
    public String protectiveAction;

    /**
     * 急救措施
     */
    @Column
    public String emergencyTreatment;
}
