package cn.piesat.medicaid.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.modeBean.PkBean;
import cn.piesat.medicaid.ui.adapter.PkAdapter;

public class PKActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pk_list)
    RecyclerView pkList;
    private PkAdapter pkAdapter;
    private List<PkBean> pkBeans;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pk;
    }

    @Override
    protected void initView() {
        pkList.setLayoutManager(new LinearLayoutManager(this));
        pkBeans = new ArrayList<>();
        pkAdapter = new PkAdapter(this, pkBeans);
        pkList.setAdapter(pkAdapter);
    }

    @Override
    protected void initData() {
        getIntentData();
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    public void getIntentData() {
        pkBeans.addAll((List<PkBean>) getIntent().getSerializableExtra("detail"));
        Log.e("----------","----pkBeans------"+new Gson().toJson(pkBeans));
        pkAdapter.notifyDataSetChanged();
    }
}
