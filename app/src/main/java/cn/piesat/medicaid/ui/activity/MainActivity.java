package cn.piesat.medicaid.ui.activity;

import android.content.Intent;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.db.InitDBUtil;

/**
 *@author lq
 *@fileName
 *@data on  2019/2/28 16:48
 *@describe TODO
 */
public class MainActivity extends BaseActivity {

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

    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
//        startActivity(new Intent(MainActivity.this, SearchActivity.class));
//        startActivity(new Intent(MainActivity.this, ReactionResultActivity.class));
        startActivity(new Intent(MainActivity.this, UnknownActivity.class));
    }
}
