package cn.piesat.medicaid.ui.activity;


import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.util.FileUtil;

public class HazardAssessmentActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mapview)
    MapView mapview;
    private AMap aMap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hazard_assessment;
    }

    @Override
    protected void initView() {
        tvTitle.setText("危害评估");
        setMap();
    }

    private void setMap() {
//        MapsInitializer.sdcardDir = FileUtil.getMapPath();
        aMap = mapview.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        aMap.getUiSettings().setCompassEnabled(false);
        aMap.getUiSettings().setLogoBottomMargin(-500);
        aMap.setLoadOfflineData(true);
        aMap.reloadMap();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapview.onCreate(savedInstanceState);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
