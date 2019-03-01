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
 * @describe 分子结构
 */
@Table(database = AppDatabase.class)
public class CAS extends BaseModel implements Serializable {

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
     * 英文简称
     */
    @Column
    public String enAlias;

    /**
     * 分子结构
     */
    @Column
    public String molecule;


}
