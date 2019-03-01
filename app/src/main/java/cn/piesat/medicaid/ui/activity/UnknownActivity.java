package cn.piesat.medicaid.ui.activity;


import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.modeBean.AttriAndsymType;
import cn.piesat.medicaid.modeBean.SubstanceScale;
import cn.piesat.medicaid.tabmode.Attrs;
import cn.piesat.medicaid.tabmode.AttrsType;
import cn.piesat.medicaid.tabmode.SubstanceAttrs;

public class UnknownActivity extends BaseActivity {

    List<String> ids = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_unknown;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //属性 症状 一级分类
      Controller.GetAttriAndSymptomType(callback,"1");
        //二级分类
//        Controller.GetTypeDetail(typeDetailCallback, "210");

//        ids.add("200078");
//        ids.add("100023");
////        ids.add("100044");
//
//        //通过数据id 查物质id
//        Controller.GetSubstanceIdByAttrsId(ubstanceCallback, ids);


    }


    Controller.GetResultListenerCallback callback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            List<AttriAndsymType> attriAndsymTypes = (List<AttriAndsymType>) o;
            Log.e("----------", "----AttriAndsymType----" + new Gson().toJson(attriAndsymTypes));
        }

        @Override
        public void onErro(Object o) {

        }
    };

    Controller.GetResultListenerCallback typeDetailCallback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            List<Attrs> attrsTypes = (List<Attrs>) o;
            Log.e("----------", "----attrsTypes----" + new Gson().toJson(attrsTypes));
        }

        @Override
        public void onErro(Object o) {

        }
    };

    Controller.GetResultListenerCallback ubstanceCallback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            List<SubstanceScale> substanceAttrs = (List<SubstanceScale>) o;
            Log.e("-----编译-----", "----substanceAttrs----" + new Gson().toJson(substanceAttrs));
            Collections.sort(substanceAttrs);//编译通过；
            Log.e("-----编译通过-----", "----substanceAttrs----" + new Gson().toJson(substanceAttrs));
        }

        @Override
        public void onErro(Object o) {

        }
    };
}
