package cn.piesat.medicaid.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.common.Constant;
import cn.piesat.medicaid.modeBean.SubstanceDetail;
import cn.piesat.medicaid.tabmode.SubstanceInfo;
import cn.piesat.medicaid.ui.adapter.SubstanceDetailAdapter;

/**
 * @author lq
 * @fileName
 * @data on  2019/3/1 16:55
 * @describe 物质各项详情界面
 */
public class SubstanceDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.substance_detail)
    RecyclerView substanceDetail;
    private SubstanceDetailAdapter substanceDetailAdapter;
    private List<SubstanceDetail> substanceDetails;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_substance_detail;
    }

    @Override
    protected void initView() {
        setRecyclerView();
        setData();
        getIntentData();
    }

    private void setData() {
        String[] title = {"基本信息", "危险特性", "健康危害", "毒理学信息", "临床表现", "防护措施", "急救措施", "危害评估", "化学反应"};
        int[] drawable = {R.mipmap.base, R.mipmap.weixianyuan, R.mipmap.heathy, R.mipmap.xiaodushui, R.mipmap.linchangzhengduan, R.mipmap.fanghu, R.mipmap.jijiu, R.mipmap.pinggu, R.mipmap.huaxue};
        for (int i = 0; i < 9; i++) {
            SubstanceDetail substanceDetail = new SubstanceDetail();
            substanceDetail.titleName = title[i];
            substanceDetail.drawableRe=drawable[i];
            substanceDetails.add(substanceDetail);
        }
        substanceDetailAdapter.notifyDataSetChanged();
    }

    private void setRecyclerView() {
        substanceDetails = new ArrayList<>();
        substanceDetailAdapter = new SubstanceDetailAdapter(this, substanceDetails);
        substanceDetail.setLayoutManager(new LinearLayoutManager(this));
        substanceDetail.setAdapter(substanceDetailAdapter);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    public void getIntentData() {
        SubstanceInfo substanceInfo = (SubstanceInfo) getIntent().getSerializableExtra(Constant.sysConfig.TAKE_DATA_KEY);
        tvTitle.setText(substanceInfo.substanceName);

        String baseInfo = "<b>中文名:</b> " + substanceInfo.substanceName + "<br/>" +
                "<b>中文别名:</b> " + substanceInfo.chAlias + "<br/>" +
                "<b>英文名:</b> " + substanceInfo.enAlias + "<br/>" +
                "<b>分子式:</b> " + substanceInfo.molecularFormula + "<br/>" +
                "<b>分子量:</b> " + substanceInfo.molecularWeight + "<br/>" +
                "<b>外观与性状:</b> " + substanceInfo.appearanceAndCharacter + "<br/>" +
                "<b>溶解性:</b> " + substanceInfo.solubility + "<br/>" +
                "<b>稳定性:</b> " + substanceInfo.stability + "<br/>";

        String[] Detail = {baseInfo, substanceInfo.hazardProperty, substanceInfo.healthHazard, substanceInfo.toxicologyInfo
                , substanceInfo.clinicalManifestation, substanceInfo.protectiveAction, substanceInfo.emergencyTreatment,"",""};

        for (int i = 0; i < substanceDetails.size(); i++) {
            substanceDetails.get(i).substanceName=substanceInfo.substanceName;
            substanceDetails.get(i).Content = Detail[i];
            substanceDetails.get(i).substanceNum =substanceInfo.substanceNum;
        }
        substanceDetailAdapter.notifyDataSetChanged();
    }
}
