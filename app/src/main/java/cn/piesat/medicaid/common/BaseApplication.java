package cn.piesat.medicaid.common;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowLog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lq
 * @fileName: BaseApplication
 */
public class BaseApplication extends Application {
    private static BaseApplication mAppInstance;
    /**
     * 管理activity 的容器
     */
    public Map<String, Activity> activityMap = new HashMap<String, Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
    }

    /**
     * 获得Application对象
     */
    public static BaseApplication getApplication() {
        return mAppInstance;
    }


    /**
     * 收集创建的Activity
     */
    public void putActivityInfoToMap(Activity activity) {
        if (activity != null) {
            String activityName = activity.getClass().getSimpleName();
            Log.i("info", "putActivity--->" + activityName);

            activityMap.put(activityName, activity);
        }
    }

    /**
     * 移除activity
     */
    public void removeActivityInfoFromMap(Activity activity) {
        if (activity != null) {
            String activityName = activity.getClass().getSimpleName();
            Log.i("info", "removeActivity--->" + activityName);
            if (activityMap.containsKey(activityName)) {
                activityMap.remove(activityName);
            }
        }
    }

    /**
     * 关闭所有界面
     */
    public void closeAllActivityByMap() {
        if (!activityMap.isEmpty()) {
            Collection<Activity> activities = activityMap.values();
            Iterator<Activity> it = activities.iterator();
            while (it.hasNext()) {
                Activity activity = it.next();
                String activityName = activity.getClass().getSimpleName();

                Log.i("info", "removeActivity--->" + activityName);

                activity.finish();
            }
        }
    }
}
