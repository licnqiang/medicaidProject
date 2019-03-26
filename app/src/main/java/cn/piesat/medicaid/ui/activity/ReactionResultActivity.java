package cn.piesat.medicaid.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.common.Constant;
import cn.piesat.medicaid.tabmode.ChemicalReaction;

/**
 * @author lq
 * @fileName ReactionResultActivity
 * @data on  2019/2/28 14:20
 * @describe 反应结果
 */
public class ReactionResultActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.item_title)
    TextView itemTitle;
    @BindView(R.id.detail_info)
    TextView detailInfo;
    @BindView(R.id.item_detail)
    LinearLayout itemDetail;
    @BindView(R.id.item_title_1)
    TextView itemTitle_1;
    @BindView(R.id.detail_info_1)
    TextView detailInfo_1;
    @BindView(R.id.item_detail_1)
    LinearLayout itemDetail_1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reaction_result;
    }

    @Override
    protected void initView() {
        tvTitle.setText("化学反应结果");
    }

    @Override
    protected void initData() {
        getIntentData();
    }

    private void getIntentData() {
        ChemicalReaction chemicalReaction = (ChemicalReaction) getIntent().getSerializableExtra(Constant.sysConfig.TAKE_DATA_KEY);
        if (null != chemicalReaction) {
            itemTitle.setText("反应结果:");
            if (null == chemicalReaction.reactionResult || chemicalReaction.reactionResult.isEmpty()) {
                detailInfo.setText("无反应结果");
            } else {
                detailInfo.setText(chemicalReaction.reactionResult);
            }
            itemTitle_1.setText("反应生成物:");
            if (null == chemicalReaction.reactionProduct || chemicalReaction.reactionProduct.isEmpty()) {
                detailInfo_1.setText("无生成物");
            } else {
                detailInfo_1.setText(chemicalReaction.reactionProduct);
            }
        }
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
