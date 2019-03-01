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
 * @describe 实验数据表
 */
@Table(database = AppDatabase.class)
public class ExperimentalData extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    public long _id;

    /**
     *实验编号
     */
    @Column
    public String substanceNum;

    /**
     *编号
     */
    @Column
    public long number;

    /**
     *毒性类型
     */
    @Column
    public String  virulenceType;

    /**
     *服用方式
     */
    @Column
    public String testMethod;

    /**
     *测试对象
     */
    @Column
    public String testObject;

    /**
     *测试计量
     */
    @Column
    public String dose;

    /**
     *测试报告
     */
    @Column
    public String toxicEffect;


}
