package cn.piesat.medicaid.util;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;

/**
 * @author lq
 * @fileName DataUtil
 * @data on  2019/3/1 14:46
 * @describe 数据处理工具类
 */
public class DataUtil {
    /**
     * 删除ArrayList中重复元素
     */
    public static void removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
    }

    /**
     * 小数点保存2位数
     */
    public static String savetwoData(float data) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(data);
    }
}
