package cn.piesat.medicaid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.db.InitDBUtil;

/**
 * @author lq
 * @fileName
 * @data on  2019/2/28 16:48
 * @describe TODO
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        /**
         * 初始化数据库
         */
        InitDBUtil.initDB(this);
    }

    @Override
    protected void initData() {
        imgBack.setVisibility(View.GONE);
        tvTitle.setText("病理手册");

    }

    @OnClick({R.id.tv_search_known, R.id.tv_search_unknown, R.id.tv_wounded_stream})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_known:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            case R.id.tv_search_unknown:
                startActivity(new Intent(MainActivity.this, SearchUnkwonActivity.class));
                break;
            case R.id.tv_wounded_stream:
                startActivity(new Intent(MainActivity.this, WoundMenuActivity.class));
                break;
        }
    }
}
