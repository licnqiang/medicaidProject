package cn.piesat.medicaid.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.piesat.medicaid.R;
import cn.piesat.medicaid.controller.Controller;
import cn.piesat.medicaid.tabmode.ChemicalReaction;
import cn.piesat.medicaid.tabmode.ReactionCondition;
import cn.piesat.medicaid.util.ToastUtils;

/**
 * @author lq
 * @fileName ReactionResultActivity
 * @data on  2019/2/28 14:20
 * @describe 反应结果
 */
public class ReactionResultActivity extends AppCompatActivity {
    /**
     * 主反应物
     */
    private String mainReactantIDs="A2002";
    /**
     * 其他反应物
     */
    private String otherReactantIDs="100003";
    /**
     * 反应条件
     */
    private String reactionConditionIDs="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_result);

        Controller.GetReactionResult(callback, mainReactantIDs, otherReactantIDs, reactionConditionIDs);
    }


    Controller.GetResultListenerCallback callback = new Controller.GetResultListenerCallback() {
        @Override
        public void onFinished(Object o) {
            ChemicalReaction chemicalReaction = (ChemicalReaction) o;
            Log.e("-----------", "---reactionConditions-----" + new Gson().toJson(chemicalReaction));
        }

        @Override
        public void onErro(Object o) {
            ToastUtils.showShort(ReactionResultActivity.this, "查询出错,请尝试!");
        }
    };
}
