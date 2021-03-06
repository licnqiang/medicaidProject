package cn.piesat.medicaid.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseFragment;
import cn.piesat.medicaid.common.Constant;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.modeBean.AttriAndsymType;
import cn.piesat.medicaid.tabmode.Attrs;
import cn.piesat.medicaid.ui.activity.SearchUnkwonActivity;
import cn.piesat.medicaid.ui.adapter.OneAttriAndTypeAdapter;
import cn.piesat.medicaid.ui.adapter.TwoAttriAndTypeAdapter;
import cn.piesat.medicaid.ui.view.OnItemCheckClickListener;
import cn.piesat.medicaid.ui.view.OnItemClickListener;
import cn.piesat.medicaid.ui.view.OnSearchKeyChange;
import cn.piesat.medicaid.util.ToastUtils;

/**
 * 症状
 */
@SuppressLint("ValidFragment")
public class SymptomFragment extends BaseFragment implements OnItemClickListener, OnItemCheckClickListener {

    @BindView(R.id.one_key_word_recyclerview)
    RecyclerView oneKeyWordRecyclerview;
    @BindView(R.id.two_key_word_recyclerview)
    RecyclerView twoKeyWordRecyclerview;
    @BindView(R.id.tv_attr_name)
    TextView tvAttrName;

    private OneAttriAndTypeAdapter oneAttriAndTypeAdapter;
    private List<AttriAndsymType> attriAndsymTypes;

    public TwoAttriAndTypeAdapter twoAttriAndTypeAdapter;
    private List<Attrs> attrsList;

    private OnSearchKeyChange onSearchKeyChange;
    private SearchUnkwonActivity mActivity;

    public SymptomFragment(OnSearchKeyChange onSearchKeyChange, SearchUnkwonActivity mActivity) {
        this.onSearchKeyChange = onSearchKeyChange;
        this.mActivity = mActivity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attr;
    }

    @Override
    protected void initView() {
        initRecyclerView();
        initAdapter();
    }

    private void initAdapter() {
        attriAndsymTypes = new ArrayList<>();
        oneAttriAndTypeAdapter = new OneAttriAndTypeAdapter(getActivity(), attriAndsymTypes);
        oneAttriAndTypeAdapter.setOnItemClickListener(this);
        oneKeyWordRecyclerview.setAdapter(oneAttriAndTypeAdapter);

        attrsList = new ArrayList<>();
        twoAttriAndTypeAdapter = new TwoAttriAndTypeAdapter(mActivity, attrsList);
        twoAttriAndTypeAdapter.setOnItemCheckClickListener(this);
        twoKeyWordRecyclerview.setAdapter(twoAttriAndTypeAdapter);
    }

    private void initRecyclerView() {
        oneKeyWordRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 6));
        twoKeyWordRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onItemClick(View view, int position) {
        for (AttriAndsymType attriAndsymType : attriAndsymTypes) {
            attriAndsymType.isSelect = false;
        }
        tvAttrName.setText(attriAndsymTypes.get(position).typeName + " :");
        attriAndsymTypes.get(position).isSelect = true;
        oneAttriAndTypeAdapter.notifyDataSetChanged();
        twoAttriAndTypeAdapter.refreshData(attriAndsymTypes.get(position).attrsList);


    }

    @Override
    public void onItemClick(View view, int position, boolean select) {
        if (null != onSearchKeyChange && null != attrsList.get(position)) {
            onSearchKeyChange.OnSearchKeyChange(attrsList.get(position), select);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Controller.GetAttriAndSymptomType(callback, Constant.sysConfig.TYPE_SYMPTOM);
    }

    /**
     * 请求属性响应得数据
     */
    Controller.GetResultListenerCallback callback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            if (null == o) {
                return;
            }
            List<AttriAndsymType> attriAndsymTypes = (List<AttriAndsymType>) o;
            if (attriAndsymTypes.size() > 0) {
                attriAndsymTypes.get(0).isSelect = true;
                tvAttrName.setText(attriAndsymTypes.get(0).typeName + " :");
                twoAttriAndTypeAdapter.refreshData(attriAndsymTypes.get(0).attrsList);
                oneAttriAndTypeAdapter.refreshData(attriAndsymTypes);
            }
        }

        @Override
        public void onErro(Object o) {
            ToastUtils.showShort(getActivity(), "查询出错,请尝试重新刷新");
        }
    };
}
