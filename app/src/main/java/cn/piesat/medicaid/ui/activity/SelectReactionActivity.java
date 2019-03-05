package cn.piesat.medicaid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.tabmode.Attrs;
import cn.piesat.medicaid.tabmode.ChemicalReaction;
import cn.piesat.medicaid.tabmode.Reactant;
import cn.piesat.medicaid.tabmode.ReactionCondition;
import cn.piesat.medicaid.tabmode.SubstanceInfo;
import cn.piesat.medicaid.ui.adapter.ReactantAdapter;
import cn.piesat.medicaid.ui.adapter.ReactionConditionAdapter;
import cn.piesat.medicaid.ui.adapter.ReactionPagerAdapter;
import cn.piesat.medicaid.ui.fragment.OneGroupFragment;
import cn.piesat.medicaid.ui.fragment.OneTypeFragment;
import cn.piesat.medicaid.ui.view.IndicatorLineUtil;
import cn.piesat.medicaid.ui.view.OnItemCheckClickListener;
import cn.piesat.medicaid.ui.view.OnSearchKeyChange;
import cn.piesat.medicaid.util.ToastUtils;

/**
 * 模拟实验室检验
 */
public class SelectReactionActivity extends BaseActivity implements OnItemCheckClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.reactant_state)
    RecyclerView reactantState;
    private ReactionPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"添加一种物质", "添加一组物质"};
    private ReactionConditionAdapter reactionConditionAdapter;
    private List<ReactionCondition> reactionConditions;

    /**
     * 主反应物
     */
    private String mainReactantIDs = "A2002";
    /**
     * 其他反应物
     */
    public List<String> otherReactantIDs = new ArrayList<>();
    /**
     * 反应条件
     */
    private List<String> reactionConditionIDs = new ArrayList<>();
    private StringBuffer stringBuffer;
    private StringBuffer stringBufferCondition;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_reaction;
    }

    @Override
    protected void initView() {
        getIntentData();
        init();
    }

    @Override
    protected void initData() {
        setReactantState();
    }

    private void setReactantState() {
        reactionConditions = new ArrayList<>();
        reactantState.setLayoutManager(new GridLayoutManager(this, 4));
        reactionConditionAdapter = new ReactionConditionAdapter(this, reactionConditions);
        reactionConditionAdapter.setOnItemCheckClickListener(this);
        reactantState.setAdapter(reactionConditionAdapter);
    }

    private void init() {
        fragments.add(new OneTypeFragment(onSearchKeyChange));
        fragments.add(new OneGroupFragment(onSearchKeyChange));
        tabLayout.setupWithViewPager(viewPager, false);
        pagerAdapter = new ReactionPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(pagerAdapter);
    }

    /**
     * 回调数据处理选中得关键字
     */
    private OnSearchKeyChange onSearchKeyChange = new OnSearchKeyChange() {
        @Override
        public void OnSearchKeyChange(Object object, boolean isSelect) {
            Log.e("-------","-----object1--"+new Gson().toJson(object));

            Reactant reactant = (Reactant) object;
            if (null == reactant) {
                return;
            }
            if (isSelect) {
                if (!otherReactantIDs.contains(reactant.reactantID)) {
                    otherReactantIDs.add(reactant.reactantID);
                }
            } else {
                if (otherReactantIDs.contains(reactant.reactantID)) {
                    otherReactantIDs.remove(reactant.reactantID);
                }
            }
        }
    };



    @Override
    public void onItemClick(View view, int position, boolean select) {
        if (reactionConditions.size() > 0) {
            ReactionCondition reactionCondition = reactionConditions.get(position);
            if (select) {
                if (!reactionConditionIDs.contains(reactionCondition.reactionConID)) {
                    reactionConditionIDs.add(reactionCondition.reactionConID);
                }
            } else {
                if (reactionConditionIDs.contains(reactionCondition.reactionConID)) {
                    reactionConditionIDs.remove(reactionCondition.reactionConID);
                }
            }
        }
    }

    @OnClick({R.id.img_back, R.id.btn_reactant})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_reactant:
                if (!mainReactantIDs.isEmpty()) {
                    /**
                     * 处理选择得反应物
                     * 拼接字符串
                     */
                    stringBuffer = new StringBuffer();
                    for (int i = 0; i < otherReactantIDs.size(); i++) {
                        if (i == otherReactantIDs.size() - 1) {
                            stringBuffer.append(otherReactantIDs.get(i));
                        } else {
                            stringBuffer.append(otherReactantIDs.get(i) + "|");
                        }
                    }
                    /**
                     * 处理选择得实验条件
                     * 拼接字符串
                     */
                    stringBufferCondition = new StringBuffer();
                    for (int i = 0; i < reactionConditionIDs.size(); i++) {
                        if (i == reactionConditionIDs.size() - 1) {
                            stringBufferCondition.append(reactionConditionIDs.get(i));
                        } else {
                            stringBufferCondition.append(reactionConditionIDs.get(i) + "|");
                        }
                    }
                }

                /**
                 * 搜索反应结果
                 */
                Controller.GetReactionResult(resultCallback, mainReactantIDs, stringBuffer.toString(), stringBufferCondition.toString());
                break;

            default:
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Controller.GetReactionConditionList(callback);
    }

    Controller.GetResultListenerCallback callback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            List<ReactionCondition> reactionConditionList = (List<ReactionCondition>) o;
            reactionConditionAdapter.refreshData(reactionConditionList);
        }

        @Override
        public void onErro(Object o) {

        }
    };

    /**
     * 反应结果
     */
    Controller.GetResultListenerCallback resultCallback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            ChemicalReaction chemicalReaction = (ChemicalReaction) o;
            if (null == chemicalReaction) {
                ToastUtils.showShort(SelectReactionActivity.this, "暂无该反应结果");
            }else {
                startActivity(new Intent(SelectReactionActivity.this,ReactionResultActivity.class).putExtra("detail",chemicalReaction));
            }
        }

        @Override
        public void onErro(Object o) {
            ToastUtils.showShort(SelectReactionActivity.this, "查询出错,请尝试!");
        }
    };

    public void getIntentData() {
        mainReactantIDs = getIntent().getStringExtra("substanceNum");
    }

}
