package cn.piesat.medicaid.db;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sqlcipher.SQLCipherOpenHelper;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;

/**
 * author: lq
 * created on: 2018/7/3
 * description: 加密dbflow数据库helper类
 */

public class SQLiteCiperHelperImpl extends SQLCipherOpenHelper {

    public SQLiteCiperHelperImpl(DatabaseDefinition databaseDefinition, DatabaseHelperListener listener) {
        super(databaseDefinition, listener);
    }

    @Override
    protected String getCipherSecret() {
        return "abelice";
    }
}
