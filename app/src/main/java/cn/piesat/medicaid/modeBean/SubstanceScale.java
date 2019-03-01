package cn.piesat.medicaid.modeBean;

import android.support.annotation.NonNull;

/**
 * @author lq
 * @fileName SubstanceScale
 * @data on  2019/3/1 15:08
 * @describe 计算物质比例返回值
 */
public class SubstanceScale implements Comparable<SubstanceScale> {
    public String substanceName;
    public float scale;

    /**
     * 通过比例值逆序排序
     *
     * @param substanceScale
     * @return
     */
    @Override
    public int compareTo(@NonNull SubstanceScale substanceScale) {
        return new Float(substanceScale.scale).compareTo(this.scale);

    }
}
