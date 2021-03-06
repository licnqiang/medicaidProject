package cn.piesat.medicaid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.common.Constant;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.modeBean.SubstanceDetail;
import cn.piesat.medicaid.tabmode.ChemicalReaction;
import cn.piesat.medicaid.tabmode.Reactant;
import cn.piesat.medicaid.tabmode.ReactionCondition;
import cn.piesat.medicaid.ui.adapter.ReactionConditionAdapter;
import cn.piesat.medicaid.ui.adapter.ReactionPagerAdapter;
import cn.piesat.medicaid.ui.fragment.OneGroupFragment;
import cn.piesat.medicaid.ui.fragment.OneTypeFragment;
import cn.piesat.medicaid.ui.view.OnItemCheckClickListener;
import cn.piesat.medicaid.ui.view.OnSearchKeyChange;
import cn.piesat.medicaid.util.ToastUtils;

/**
 * 模拟实验室检验
 */
public class SelectReactionActivity extends BaseActivity implements OnItemCheckClickListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.reactant_state)
    RecyclerView reactantState;
    @BindView(R.id.one_type)
    Button oneType;
    @BindView(R.id.one_group)
    Button oneGroup;
    private ReactionPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
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
        oneType.setSelected(true);
        oneGroup.setSelected(false);
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
        pagerAdapter = new ReactionPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOnPageChangeListener(this);
        viewPager.setAdapter(pagerAdapter);
        Controller.GetReactionConditionList(callback);
    }

    /**
     * 回调数据处理选中得关键字
     */
    private OnSearchKeyChange onSearchKeyChange = new OnSearchKeyChange() {
        @Override
        public void OnSearchKeyChange(Object object, boolean isSelect) {
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

    @OnClick({R.id.img_back, R.id.btn_reactant, R.id.one_type, R.id.one_group})
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

            case R.id.one_type:
                oneType.setSelected(true);
                oneGroup.setSelected(false);
                viewPager.setCurrentItem(0);
                break;
            case R.id.one_group:
                oneType.setSelected(false);
                oneGroup.setSelected(true);
                viewPager.setCurrentItem(1);
                break;

            default:
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    Controller.GetResultListenerCallback callback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            if (null == o) {
                return;
            }
            List<ReactionCondition> reactionConditionList = (List<ReactionCondition>) o;
            reactionConditionAdapter.refreshData(reactionConditionList);
        }

        @Override
        public void onErro(Object o) {
            ToastUtils.showShort(SelectReactionActivity.this, "查询出错");
        }
    };

    /**
     * 反应结果
     */
    Controller.GetResultListenerCallback resultCallback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            if (null == o) {
                ToastUtils.showShort(SelectReactionActivity.this, "暂无该反应结果");
                return;
            }
            ChemicalReaction chemicalReaction = (ChemicalReaction) o;
            if (null != chemicalReaction) {
                startActivity(new Intent(SelectReactionActivity.this, ReactionResultActivity.class).putExtra(Constant.sysConfig.TAKE_DATA_KEY, chemicalReaction));
            }
        }

        @Override
        public void onErro(Object o) {
            ToastUtils.showShort(SelectReactionActivity.this, "查询出错,请尝试!");
        }
    };

    public void getIntentData() {
        SubstanceDetail substanceDetail = (SubstanceDetail) getIntent().getSerializableExtra(Constant.SUBSTANCENUM);
        mainReactantIDs = substanceDetail.substanceNum;
        tvTitle.setText(substanceDetail.substanceName + "添加化学物质");
    }


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
