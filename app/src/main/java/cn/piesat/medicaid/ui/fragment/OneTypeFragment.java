package cn.piesat.medicaid.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseFragment;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.tabmode.Reactant;
import cn.piesat.medicaid.ui.activity.SelectReactionActivity;
import cn.piesat.medicaid.ui.adapter.ReactantAdapter;
import cn.piesat.medicaid.ui.view.OnItemCheckClickListener;
import cn.piesat.medicaid.ui.view.OnSearchKeyChange;

/**
 * 添加一种物质
 */
@SuppressLint("ValidFragment")
public class OneTypeFragment extends BaseFragment implements OnItemCheckClickListener {
    @BindView(R.id.view_search)
    SearchView viewSearch;
    @BindView(R.id.search_recycler)
    PullLoadMoreRecyclerView searchRecycler;
    private ReactantAdapter reactantAdapter;
    private List<Reactant> reactants;

    private OnSearchKeyChange onSearchKeyChange;

    public OneTypeFragment(OnSearchKeyChange onSearchKeyChange) {
        this.onSearchKeyChange = onSearchKeyChange;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one_type;
    }

    @Override
    protected void initView() {
        setAdapter();
        Controller.GetReactionList(callback, "1");

    }

    private void setAdapter() {
        reactants = new ArrayList<>();
        reactantAdapter = new ReactantAdapter(getActivity(), reactants);
        reactantAdapter.setOnItemCheckClickListener(this);
        searchRecycler.setLinearLayout();
        searchRecycler.setPushRefreshEnable(false);
        searchRecycler.setPullRefreshEnable(false);
        searchRecycler.setAdapter(reactantAdapter);
    }

    @Override
    protected void initData() {

    }


    Controller.GetResultListenerCallback callback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            List<Reactant> reactantList = (List<Reactant>) o;
            reactantAdapter.refreshData(reactantList);
        }

        @Override
        public void onErro(Object o) {

        }
    };


    /**
     * 判断反应物容器中是否该反应物得编号
     * 保存/删除 容器中保存得
     */
    @Override
    public void onItemClick(View view, int position, boolean select) {
        if (null != onSearchKeyChange && null != reactants) {
            onSearchKeyChange.OnSearchKeyChange(reactants.get(position),select);

        }
    }
}
