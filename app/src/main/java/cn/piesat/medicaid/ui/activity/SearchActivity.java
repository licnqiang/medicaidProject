package cn.piesat.medicaid.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.common.Constant;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.modeBean.PkBean;
import cn.piesat.medicaid.tabmode.ReactionCondition;
import cn.piesat.medicaid.tabmode.SubstanceInfo;
import cn.piesat.medicaid.ui.adapter.SearchAdapter;
import cn.piesat.medicaid.ui.view.OnItemCheckClickListener;
import cn.piesat.medicaid.ui.view.OnItemClickListener;
import cn.piesat.medicaid.util.ToastUtils;

/**
 * @author lq
 * @fileName SearchActivity
 * @data on  2019/2/27 14:05
 * @describe 药物名搜索
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener, OnItemClickListener, OnItemCheckClickListener {
    @BindView(R.id.tv_right)
    TextView tvRight;
    /**
     * 设置一页搜索的最大条目数
     */
    private int PAGE_SHOW_MAX_NUM = 50;
    /**
     * 当前搜索页数
     */
    private int page = 0;
    /**
     * 搜索关键字
     */
    private String searchKey = "";

    @BindView(R.id.view_search)
    SearchView viewSearch;
    @BindView(R.id.search_recycler)
    PullLoadMoreRecyclerView searchRecycler;
    private List<SubstanceInfo> substanceInfos;
    private SearchAdapter adapter;
    /**
     * 已选择得物质
     */
    public List<SubstanceInfo> selectSubstance = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(R.string.substance_pk);
        viewSearch.setOnQueryTextListener(this);
        initRecyclerView();
        initAdapter();
    }

    private void initAdapter() {
        substanceInfos = new ArrayList<>();
        adapter = new SearchAdapter(this, substanceInfos);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemCheckClickListener(this);
        searchRecycler.setAdapter(adapter);
    }

    private void initRecyclerView() {
        searchRecycler.setLinearLayout();
        searchRecycler.setPullRefreshEnable(true);
        searchRecycler.setPushRefreshEnable(true);
        searchRecycler.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                //刷新重置当前页数
                setQueryMode(searchKey, true);
            }

            @Override
            public void onLoadMore() {
                setQueryMode(searchKey, false);
            }
        });
    }

    @Override
    protected void initData() {
        setQueryMode("", true);
    }

    /**
     * 点击键盘搜索
     */
    @Override
    public boolean onQueryTextSubmit(String s) {
        setQueryMode(s, true);
        return false;
    }

    /**
     * 搜索框搜索文字变化
     */
    @Override
    public boolean onQueryTextChange(String s) {
        setQueryMode(s, true);
        return false;
    }

    /**
     * 点击搜索列表
     */
    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(SearchActivity.this, SubstanceDetailActivity.class).putExtra(Constant.sysConfig.TAKE_DATA_KEY, substanceInfos.get(position)));
    }

    /**
     * 点击checkbox
     */
    @Override
    public void onItemClick(View view, int position, boolean select) {
        if (select) {
            if (!selectSubstance.contains(substanceInfos.get(position))) {
                if (selectSubstance.size() > 1) {
                    selectSubstance.remove(0);
                    adapter.notifyDataSetChanged();
                }
                selectSubstance.add(substanceInfos.get(position));
            }
        } else {
            if (selectSubstance.contains(substanceInfos.get(position))) {
                selectSubstance.remove(substanceInfos.get(position));
                adapter.notifyDataSetChanged();
            }
        }

    }

    /**
     * 设置搜索数据模式
     *
     * @param strKey     搜索关键字
     * @param isInitPage 是否重置页数  true 为刷新数据   false为加载更多
     */
    private void setQueryMode(String strKey, boolean isInitPage) {
        if (isInitPage) {
            page = 0;
            searchKey = strKey;
            adapter.setSearchKey(searchKey);
        }
        Controller.GetSubstanceInfoSearch(searchCallback, searchKey, page, PAGE_SHOW_MAX_NUM);

    }


    /**
     * 查询请求的数据
     * page=0 数据刷新
     * page>0 加载更多
     * 每次请求成功page自增，方便下次分页请求
     */
    Controller.GetResultListenerCallback searchCallback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            searchRecycler.setPullLoadMoreCompleted();
            List<SubstanceInfo> substanceInfo = (List<SubstanceInfo>) o;
            if (null != substanceInfo) {
                if (substanceInfo.size() > 0) {
                    if (page > 0) {
                        adapter.AllData(substanceInfo);
                    } else {
                        adapter.refreshData(substanceInfo);
                    }
                    page++;
                } else {
                    if (page > 0) {
                        ToastUtils.showShort(SearchActivity.this, "没有更多数据哦");
                    } else {
                        ToastUtils.showShort(SearchActivity.this, "没有搜索到相关信息");
                    }
                }
            }
        }

        @Override
        public void onErro(Object o) {
            searchRecycler.setPullLoadMoreCompleted();
            ToastUtils.showShort(SearchActivity.this, "搜索出错,请尝试重新搜索");
        }
    };

    @OnClick({R.id.img_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            /**
             * 对比
             */
            case R.id.tv_right:
                if (selectSubstance.size() == 2) {
                    setData();
                } else {
                    ToastUtils.showShort(this, "请选择两项物质对比");
                }
                break;
        }
    }

    /**
     * 整合数据
     */
    private void setData() {
        List<PkBean> pkBeans = new ArrayList<>();
        PkBean pkBean = new PkBean();
        pkBean.itemName1 = "<b>中文名:</b> " + selectSubstance.get(0).substanceName + "<br/>" +
                "<b>中文别名:</b> " + selectSubstance.get(0).chAlias + "<br/>" +
                "<b>英文名:</b> " + selectSubstance.get(0).enAlias + "<br/>" +
                "<b>分子式:</b> " + selectSubstance.get(0).molecularFormula + "<br/>" +
                "<b>分子量:</b> " + selectSubstance.get(0).molecularWeight + "<br/>" +
                "<b>外观与性状:</b> " + selectSubstance.get(0).appearanceAndCharacter + "<br/>" +
                "<b>溶解性:</b> " + selectSubstance.get(0).solubility + "<br/>" +
                "<b>稳定性:</b> " + selectSubstance.get(0).stability + "<br/>";
        ;
        pkBean.itemName2 = "<b>中文名:</b> " + selectSubstance.get(1).substanceName + "<br/>" +
                "<b>中文别名:</b> " + selectSubstance.get(1).chAlias + "<br/>" +
                "<b>英文名:</b> " + selectSubstance.get(1).enAlias + "<br/>" +
                "<b>分子式:</b> " + selectSubstance.get(1).molecularFormula + "<br/>" +
                "<b>分子量:</b> " + selectSubstance.get(1).molecularWeight + "<br/>" +
                "<b>外观与性状:</b> " + selectSubstance.get(1).appearanceAndCharacter + "<br/>" +
                "<b>溶解性:</b> " + selectSubstance.get(1).solubility + "<br/>" +
                "<b>稳定性:</b> " + selectSubstance.get(1).stability + "<br/>";
        ;

        PkBean pkBean1 = new PkBean();
        pkBean1.itemName1 = "<b>外观与性状:</b>" + selectSubstance.get(0).appearanceAndCharacter;
        pkBean1.itemName2 = "<b>外观与性状:</b>" + selectSubstance.get(1).appearanceAndCharacter;

        PkBean pkBean2 = new PkBean();
        pkBean2.itemName1 = "<b>危害特性:</b>" + selectSubstance.get(0).hazardProperty;
        pkBean2.itemName2 = "<b>危害特性:</b>" + selectSubstance.get(1).hazardProperty;


        PkBean pkBean3 = new PkBean();
        pkBean3.itemName1 = "<b>毒理学信息:</b>" + selectSubstance.get(0).toxicologyInfo;
        pkBean3.itemName2 = "<b>毒理学信息:</b>" + selectSubstance.get(1).toxicologyInfo;

        PkBean pkBean4 = new PkBean();
        pkBean4.itemName1 = "<b>临床表现:</b>" + selectSubstance.get(0).clinicalManifestation;
        pkBean4.itemName2 = "<b>临床表现:</b>" + selectSubstance.get(1).clinicalManifestation;

        PkBean pkBean5 = new PkBean();
        pkBean5.itemName1 = "<b>保护措施:</b>" + selectSubstance.get(0).protectiveAction;
        pkBean5.itemName2 = "<b>保护措施:</b>" + selectSubstance.get(1).protectiveAction;

        PkBean pkBean6 = new PkBean();
        pkBean6.itemName1 = "<b>急救措施:</b>" + selectSubstance.get(0).emergencyTreatment;
        pkBean6.itemName2 = "<b>急救措施:</b>" + selectSubstance.get(1).emergencyTreatment;

        pkBeans.add(pkBean);
        pkBeans.add(pkBean1);
        pkBeans.add(pkBean2);
        pkBeans.add(pkBean3);
        pkBeans.add(pkBean4);
        pkBeans.add(pkBean5);
        pkBeans.add(pkBean6);

        startActivity(new Intent(SearchActivity.this, PKActivity.class).putExtra(Constant.sysConfig.TAKE_DATA_KEY, (Serializable) pkBeans));
    }

}
