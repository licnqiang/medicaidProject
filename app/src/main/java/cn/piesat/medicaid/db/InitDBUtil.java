package cn.piesat.medicaid.db;

import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

import java.io.File;

import cn.piesat.medicaid.util.FileUtil;


/**
 * Created by lq on 2018/7/3.
 * 初始化数据库
 */

public class InitDBUtil {

    public static void initDB(Context context) {
        //更改dbflow数据库的路径: /storage/emulated/0/Android/data/cn.piesat.medicaid/db/appdatabase.db
        String dbName = FileUtil.getDatabasePath();
        /**
         * 方式一：未加密dbFlow数据库
         * 未加密情况下，找到路径下db，打开，可以看到表结构和表字段。
         */
        FlowManager.init(new DatabaseContext(context, new File(dbName), true));
//        /**
//         * 方式二：加密dbFlow数据库
//         *  自定义SQLiteCiperHelperImpl类，设定db密码
//         *  加密情况下，找到路径下db，打开为空，表结构和表字段完全隐藏。
//         *  pwd: walker789
//         */
//        FlowManager.init(new FlowConfig.Builder(new DatabaseContext(context, new File(dbName), true))
//                .addDatabaseConfig(new DatabaseConfig.Builder(AppDatabase.class)
//                        .openHelper(new DatabaseConfig.OpenHelperCreator() {
//                            @Override
//                            public OpenHelper createHelper(DatabaseDefinition databaseDefinition, DatabaseHelperListener helperListener) {
//                                return new SQLiteCiperHelperImpl(databaseDefinition, helperListener);
//                            }
//                        }).build()).build());
//
    }

}
