package cn.piesat.medicaid.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.common.Constant;

public class WoundMenuActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wound_menu;
    }

    @Override
    protected void initView() {
        tvTitle.setText("伤员分流");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.adult_count, R.id.child_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.adult_count:
                startActivity(new Intent(this, SubjectActivity.class).putExtra(Constant.COUNT_TYPE, "成人分类算法"));
                break;
            case R.id.child_count:
                startActivity(new Intent(this, SubjectActivity.class).putExtra(Constant.COUNT_TYPE, "儿童分类算法"));
                break;
        }
    }
}
