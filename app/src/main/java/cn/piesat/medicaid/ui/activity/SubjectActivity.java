package cn.piesat.medicaid.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.common.BaseActivity;
import cn.piesat.medicaid.common.BaseApplication;
import cn.piesat.medicaid.common.Constant;
import cn.piesat.medicaid.modeBean.SubjectInfo;
import cn.piesat.medicaid.ui.adapter.RecordAdapter;
import cn.piesat.medicaid.util.FileUtil;
import cn.piesat.medicaid.util.ToastUtils;

public class SubjectActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.subject_name)
    TextView subjectName;
    @BindView(R.id.one_select)
    TextView oneSelect;
    @BindView(R.id.two_select)
    TextView twoSelect;
    String title;
    @BindView(R.id.record_list)
    RecyclerView recordList;
    private List<String> record_list = new ArrayList<>();
    private RecordAdapter recordAdapter;
    private List<SubjectInfo> subjects;

    private List<SubjectInfo> saveSubject = new ArrayList<>();
    /**
     * 当先正在答题的实体类
     */
    private SubjectInfo subject;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_subject;
    }

    @Override
    protected void initView() {
        title = getIntentData();
        tvTitle.setText(title);
    }

    @Override
    protected void initData() {
        getSubjectData();
        setData(null);
        recordAdapter = new RecordAdapter(this, record_list);
        recordList.setLayoutManager(new LinearLayoutManager(this));
        recordList.setAdapter(recordAdapter);
    }

    @OnClick({R.id.img_back, R.id.one_select, R.id.two_select, R.id.new_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.one_select:
                NextSubject(subject.one_type, oneSelect.getText().toString());
                break;
            case R.id.two_select:
                NextSubject(subject.two_type, twoSelect.getText().toString());
                break;
            //重新开始
            case R.id.new_start:
                clearData();
                break;
        }
    }

    public String getIntentData() {
        return getIntent().getStringExtra(Constant.COUNT_TYPE);
    }

    public void getSubjectData() {
        try {
            InputStream in = BaseApplication.getApplication().getAssets().open(
                    "Subject.txt");
            String result = FileUtil.ToString(in);
            subjects = Arrays.asList(new Gson().fromJson(result, SubjectInfo[].class));
            Log.e("http", "Http本地--" + result);
        } catch (IOException e) {
            ToastUtils.showShort(this, "获取数据出错");
            Log.e("http", "--e--local==" + e);
        }
    }

    public void setData(SubjectInfo subjectInfo) {
        if (null == subjectInfo) {
            subject = subjects.get(0);
            subjectName.setText(subject.name);
            oneSelect.setText(subject.one);
            twoSelect.setText(subject.two);
        } else {
            subject = subjectInfo;
            subjectName.setText(subjectInfo.name);
            oneSelect.setText(subjectInfo.one);
            twoSelect.setText(subjectInfo.two);
        }
        saveSubject.add(subjectInfo);
    }

    public void NextSubject(int type, String selcet) {
        /**
         * 区分是成人算法还是儿童算法
         */
        if (type == 0) {
            if (title.equals("成人分类算法")) {
                //成人呼吸速率
                NextSubject(3, selcet);
            } else {
                //儿童呼吸速率
                NextSubject(7, selcet);
            }
//            record_list.add(subject.name + "----" + selcet);
//            recordAdapter.notifyDataSetChanged();
//            setData(subject);
        } else if (type < 100) {
            for (SubjectInfo subjectInfo : subjects) {
                if (type == subjectInfo.id) {
                    record_list.add(subject.name + "----" + selcet);
                    recordAdapter.notifyDataSetChanged();
                    setData(subjectInfo);
                }
            }
        } else {
            clearData();
            startActivity(new Intent(this, ResultActivity.class).putExtra(Constant.RESULT_TYPE, type)
                    .putExtra(Constant.COUNT_TYPE, title));
        }
    }

    /**
     * 重新开始清除数据
     */
    private void clearData() {
        record_list.clear();
        recordAdapter.notifyDataSetChanged();
        saveSubject.clear();
        setData(null);
    }
}

