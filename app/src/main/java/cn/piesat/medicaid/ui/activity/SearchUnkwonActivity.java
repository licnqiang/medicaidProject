package cn.piesat.medicaid.ui.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
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
import cn.piesat.medicaid.ui.fragment.SymptomFragment;
import cn.piesat.medicaid.ui.view.OnItemClickListener;
import cn.piesat.medicaid.ui.view.OnSearchKeyChange;
import cn.piesat.medicaid.util.ToastUtils;

public class SearchUnkwonActivity extends BaseActivity implements OnItemClickListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.search_list)
    RecyclerView searchList;
    @BindView(R.id.search_result)
    RecyclerView searchResult;
    @BindView(R.id.one_type)
    Button oneType;
    @BindView(R.id.one_group)
    Button oneGroup;
    private SearchKeyWordAdapter searchKeyWordAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ReactionPagerAdapter pagerAdapter;
    public List<Attrs> searchKeyWords = new ArrayList<>();
    public AttrFragment attrFragment;
    public SymptomFragment typeFragment;
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
        substanceScales = new ArrayList<>();
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
        tvTitle.setText("搜索未知物质");
        oneType.setSelected(true);
        oneGroup.setSelected(false);
    }

    private void init() {
        attrFragment = new AttrFragment(onSearchKeyChange, this);
        typeFragment = new SymptomFragment(onSearchKeyChange, this);
        fragments.add(attrFragment);
        fragments.add(typeFragment);
        pagerAdapter = new ReactionPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setOnPageChangeListener(this);
        viewpager.setAdapter(pagerAdapter);
    }

    @OnClick({R.id.img_back, R.id.del_all_key_word, R.id.one_type, R.id.one_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.one_type:
                oneType.setSelected(true);
                oneGroup.setSelected(false);
                viewpager.setCurrentItem(0);
                break;
            case R.id.one_group:
                oneType.setSelected(false);
                oneGroup.setSelected(true);
                viewpager.setCurrentItem(1);
                break;
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

            if (null == o) {
                return;
            }
            List<SubstanceScale> substanceScales = (List<SubstanceScale>) o;
            Collections.sort(substanceScales);//编译通过；
            if (null != searchUnkwonAdapter) {
                searchUnkwonAdapter.refreshData(substanceScales);
            }

        }

        @Override
        public void onErro(Object o) {
            ToastUtils.showShort(SearchUnkwonActivity.this, "搜索出错,请尝试重新搜索");
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (0 == position) {
            oneType.setSelected(true);
            oneGroup.setSelected(false);
        } else {
            oneType.setSelected(false);
            oneGroup.setSelected(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
