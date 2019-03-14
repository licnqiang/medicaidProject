package cn.piesat.medicaid.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.common.Constant;
import cn.piesat.medicaid.ui.adapter.ResultAdapter;

public class ResultActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.result_title)
    TextView resultTitle;
    @BindView(R.id.result_list)
    RecyclerView resultList;
    int type = 2;
    private List<String> results;
    private ResultAdapter resultAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    protected void initView() {
        tvTitle.setText(getIntent().getStringExtra(Constant.COUNT_TYPE));
        type = getIntentData();
    }

    @Override
    protected void initData() {

        results = new ArrayList<>();
        resultAdapter = new ResultAdapter(this, results);
        resultList.setLayoutManager(new LinearLayoutManager(this));
        resultList.setAdapter(resultAdapter);

        showResultType(type);
    }

    private void showResultType(int type) {
        switch (type) {
            //死亡
            case Constant.countResult.TYPE_DIE:
                setDieShowData();
                break;
            //立即
            case Constant.countResult.TYPE_NOW_MANAGE:
                setNowManageShowData();
                break;
            //延期
            case Constant.countResult.TYEP_LATER:
                selaterShowData();
                break;
            //延期
            case Constant.countResult.frist_MANAGE:
                setFirstManageShowData();
                break;
        }
    }

    /**
     * 延期结果显示
     */
    private void selaterShowData() {
        results.clear();
        results.add("伤员的运送可能会延误。");
        results.add("包括严重和潜在的危及生命的伤害，但状态预计不会再几个小时内显著恶化。");
        resultTitle.setText("延期处理");
        resultTitle.setBackgroundResource(R.mipmap.yanqichuli);
        resultAdapter.notifyDataSetChanged();
    }

    /**
     * 立即处理结果显示
     */
    private void setNowManageShowData() {
        results.clear();
        results.add("受害者可以通过立即干预和运输得到帮助。");
        results.add("为抢救伤员, 需要几分钟的医疗护理(最多60分钟)。");
        resultTitle.setText("立即处理");
        resultTitle.setBackgroundResource(R.mipmap.lijchuli);
        resultAdapter.notifyDataSetChanged();
    }

    /**
     * 死亡结果显示
     */
    private void setDieShowData() {
        results.clear();
        results.add("应提供姑息治疗和疼痛缓解。");
        results.add("受害者不太可能活下来给严重的伤害，现有的护理水平，或者两者兼而有之。");
        resultTitle.setText("濒死或已亡");
        resultTitle.setBackgroundResource(R.mipmap.black);
        resultAdapter.notifyDataSetChanged();
    }

    /**
     * 优先处理
     */
    private void setFirstManageShowData() {
        results.clear();
        results.add("受轻伤的队员。");
        results.add("这种情况不太可能再几天内恶化。");
        results.add("可协助自理：“能自主行走的伤员”。");
        resultTitle.setText("优先处理");
        resultTitle.setBackgroundResource(R.mipmap.youxianchuli);
        resultAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.img_back, R.id.start_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.start_again:
                finish();
                break;
        }
    }

    public int getIntentData() {
        return getIntent().getIntExtra(Constant.RESULT_TYPE, Constant.countResult.TYPE_NOW_MANAGE);
    }
}
