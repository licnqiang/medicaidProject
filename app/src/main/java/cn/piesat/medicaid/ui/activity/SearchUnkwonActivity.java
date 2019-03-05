package cn.piesat.medicaid.ui.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.modeBean.SubstanceScale;
import cn.piesat.medicaid.tabmode.Attrs;
import cn.piesat.medicaid.ui.adapter.ReactionPagerAdapter;
import cn.piesat.medicaid.ui.adapter.SearchKeyWordAdapter;
import cn.piesat.medicaid.ui.adapter.SearchUnkwonAdapter;
import cn.piesat.medicaid.ui.fragment.AttrFragment;
import cn.piesat.medicaid.ui.fragment.TypeFragment;
import cn.piesat.medicaid.ui.view.OnItemClickListener;
import cn.piesat.medicaid.ui.view.OnSearchKeyChange;

public class SearchUnkwonActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.search_list)
    RecyclerView searchList;
    @BindView(R.id.search_result)
    RecyclerView searchResult;
    private SearchKeyWordAdapter searchKeyWordAdapter;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"属性", "症状"};
    private ReactionPagerAdapter pagerAdapter;

    public List<Attrs> searchKeyWords = new ArrayList<>();
    public AttrFragment attrFragment;
    public TypeFragment typeFragment;
    private SearchUnkwonAdapter searchUnkwonAdapter;
    private List<SubstanceScale> substanceScales;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_unkwon;
    }

    @Override
    protected void initView() {
        init();
        initRecyclerView();
        initAdapter();
        initSearchResultAdapter();
    }

    private void initSearchResultAdapter() {
        substanceScales=new ArrayList<>();
        searchUnkwonAdapter = new SearchUnkwonAdapter(this, substanceScales);
        searchResult.setLayoutManager(new LinearLayoutManager(this));
        searchResult.setAdapter(searchUnkwonAdapter);
    }

    private void initAdapter() {
        searchKeyWordAdapter = new SearchKeyWordAdapter(this, searchKeyWords);
        searchKeyWordAdapter.setOnItemClickListener(this);
        searchList.setAdapter(searchKeyWordAdapter);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        searchList.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
    }

    private void init() {
        attrFragment = new AttrFragment(onSearchKeyChange, this);
        typeFragment = new TypeFragment(onSearchKeyChange, this);
        fragments.add(attrFragment);
        fragments.add(typeFragment);
        tablayout.setupWithViewPager(viewpager, false);
        pagerAdapter = new ReactionPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewpager.setAdapter(pagerAdapter);
    }

    @OnClick({R.id.img_back, R.id.del_all_key_word})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.del_all_key_word:
                searchKeyWords.clear();
                searchKeyWordAdapter.notifyDataSetChanged();
                if (null != attrFragment && null != attrFragment.twoAttriAndTypeAdapter) {
                    attrFragment.twoAttriAndTypeAdapter.notifyDataSetChanged();
                }
                if (null != typeFragment && null != typeFragment.twoAttriAndTypeAdapter) {
                    typeFragment.twoAttriAndTypeAdapter.notifyDataSetChanged();
                }
                /**
                 * 重新请求查询结果
                 */
                Controller.GetSubstanceIdByAttrsId(resultCallback, fulterId());
                break;
        }
    }


    /**
     * 搜索框 删除关键字
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        if (null != searchKeyWords && searchKeyWords.size() > position) {
            searchKeyWords.remove(position);
            searchKeyWordAdapter.notifyDataSetChanged();
        }
        if (null != attrFragment && null != attrFragment.twoAttriAndTypeAdapter) {
            attrFragment.twoAttriAndTypeAdapter.notifyDataSetChanged();
        }
        if (null != typeFragment && null != typeFragment.twoAttriAndTypeAdapter) {
            typeFragment.twoAttriAndTypeAdapter.notifyDataSetChanged();
        }
        /**
         * 重新请求查询结果
         */
        Controller.GetSubstanceIdByAttrsId(resultCallback, fulterId());
    }

    private List<String> fulterId() {
        List<String> ids = new ArrayList<>();
        for (Attrs attrs : searchKeyWords) {
            ids.add(attrs.id);
        }
        return ids;
    }


    /**
     * 回调数据处理选中得关键字
     */
    private OnSearchKeyChange onSearchKeyChange = new OnSearchKeyChange() {
        @Override
        public void OnSearchKeyChange(Object object, boolean isSelect) {
            Attrs attrs = (Attrs) object;
            if (null == attrs) {
                return;
            }

            if (isSelect) {
                if (!searchKeyWords.contains(attrs)) {
                    searchKeyWords.add(attrs);
                }
            } else {
                if (searchKeyWords.contains(attrs)) {
                    searchKeyWords.remove(attrs);
                }
            }
            searchKeyWordAdapter.notifyDataSetChanged();

            /**
             * 重新请求查询结果
             */
            Controller.GetSubstanceIdByAttrsId(resultCallback, fulterId());
        }
    };


    Controller.GetResultListenerCallback resultCallback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            List<SubstanceScale> substanceScales=(List<SubstanceScale>)o;
            Collections.sort(substanceScales);//编译通过；
            if(null!=searchUnkwonAdapter){
                searchUnkwonAdapter.refreshData(substanceScales);
            }

        }

        @Override
        public void onErro(Object o) {

        }
    };
}
