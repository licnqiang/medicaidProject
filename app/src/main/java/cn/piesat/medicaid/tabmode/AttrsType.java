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
 * @describe 属性分类表
 */
@Table(database = AppDatabase.class)
public class AttrsType extends BaseModel implements Serializable {
    @PrimaryKey(autoincrement = true)
    public long _id;

    /**
     * PK
     */
    @Column
    public String id;

    /**
     * 父id
     */
    @Column
    public String pid;

    /**
     * 描述
     */
    @Column
    public String comment;


}
