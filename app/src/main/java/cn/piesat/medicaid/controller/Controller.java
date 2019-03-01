package cn.piesat.medicaid.controller;


import java.util.HashMap;
import java.util.List;

import cn.piesat.medicaid.parser.AttriAndSymptomTypeParser;
import cn.piesat.medicaid.parser.ReactionConditionParser;
import cn.piesat.medicaid.parser.ReactionParser;
import cn.piesat.medicaid.parser.ReactionResultParser;
import cn.piesat.medicaid.parser.SearchInfoParser;
import cn.piesat.medicaid.parser.SubstanceIdByAttrsIdParser;
import cn.piesat.medicaid.parser.SubstanceInfoParser;
import cn.piesat.medicaid.parser.TypeDetailParser;

/**
 * @author lq
 * @fileName Controller
 * @data on  2019/2/22 17:03
 * @describe 控制层
 */
public class Controller {

    public interface GetResultListenerCallback {
        /**
         * 请求成功
         *
         * @param o 成功回调响应数据
         */
        void onFinished(Object o);

        /**
         * 请求失败
         *
         * @param o 成功回调响应数据
         */
        void onErro(Object o);
    }

    /**
     * 物质信息搜索
     *
     * @param listener  回调监听
     * @param searchStr 搜索关键字
     * @param page      搜索页数
     * @param maxNum    一页几条
     */
    public static void GetSubstanceInfoSearch(final GetResultListenerCallback listener,
                                              final String searchStr, final int page, final int maxNum) {

        SearchInfoParser mParser = new SearchInfoParser(listener, searchStr, page, maxNum);
        mParser.start();
    }


    /**
     * 查询反应结果
     *
     * @param listener
     * @param mainReactantID      主反应物id
     * @param otherReactantID     其他反应物id
     * @param reactionConditionID 反应条件id
     */
    public static void GetReactionResult(final GetResultListenerCallback listener,
                                         final String mainReactantID, final String otherReactantID, final String reactionConditionID) {

        ReactionResultParser mParser = new ReactionResultParser(listener, mainReactantID, otherReactantID, reactionConditionID);
        mParser.start();
    }

    /**
     * 查询反应物列表
     *
     * @param listener
     * @param reactionType 反应物类型 1-单个物质；2-一组物质
     */
    public static void GetReactionList(final GetResultListenerCallback listener,
                                       final String reactionType) {

        ReactionParser mParser = new ReactionParser(listener, reactionType);
        mParser.start();
    }

    /**
     * 查询反应条件列表
     *
     * @param listener
     */
    public static void GetReactionConditionList(final GetResultListenerCallback listener) {
        ReactionConditionParser mParser = new ReactionConditionParser(listener);
        mParser.start();
    }

    /**
     * 查询物质详情
     *
     * @param listener
     * @param substanceNum 物质编号
     */
    public static void GetSubstanceInfo(final GetResultListenerCallback listener, final String substanceNum) {
        SubstanceInfoParser mParser = new SubstanceInfoParser(listener, substanceNum);
        mParser.start();
    }

    /**
     * 属性/症状分类 下的搜索一级分类和相对应得二级分类
     *
     * @param listener
     * @param pid      属性/症状id
     */
    public static void GetAttriAndSymptomType(final GetResultListenerCallback listener, final String pid) {
        AttriAndSymptomTypeParser mParser = new AttriAndSymptomTypeParser(listener, pid);
        mParser.start();
    }

    /**
     * 查询属性/症状分类 2级分类
     *
     * @param listener
     * @param id       属性/症状 一级分类id
     */
    public static void GetTypeDetail(final GetResultListenerCallback listener, final String id) {
        TypeDetailParser mParser = new TypeDetailParser(listener, id);
        mParser.start();
    }

    /**
     * 查询属性/症状分类 2级分类
     *
     * @param listener
     * @param ids      属性id集合
     */
    public static void GetSubstanceIdByAttrsId(final GetResultListenerCallback listener, final List<String> ids) {
        SubstanceIdByAttrsIdParser mParser = new SubstanceIdByAttrsIdParser(listener, ids);
        mParser.start();
    }

}
