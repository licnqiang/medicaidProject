package cn.piesat.medicaid.common;

/**
 * @author lq
 * @fileName Constant
 * @data on  2019/2/27 16:51
 * @describe TODO
 */
public class Constant {

    public static String RESULT_TYPE = "result_type";
    public static String COUNT_TYPE = "count_type";

    public interface sysConfig {
        String TAKE_DATA_KEY = "detail";
        //属性
        String TYPE_ATTRS = "1";
        //症状
        String TYPE_SYMPTOM = "2";
        //属性
        String TYPE_ONE_TYPE = "1";
        //症状
        String TYPE_ONE_GROUP = "2";
    }

    public interface countResult {
        //死亡
        int TYPE_DIE = 100;
        //立即处理
        int TYPE_NOW_MANAGE = 101;
        //延期处理
        int TYEP_LATER = 102;
        //优先处理
        int frist_MANAGE = 103;
    }
}
