package com.rxjy.rxdesign.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.entity.SubInfo;
import com.rxjy.rxdesign.utils.AndroidBug5497Workaround;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.StringUtils;
import com.rxjy.rxdesign.utils.ToastUtil;
import com.rxjy.rxdesign.utils.UrlTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 回访
* */
public class BackVisitActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.publish)
    ImageView publish;
    @Bind(R.id.ly_background)
    LinearLayout lyBackground;
    @Bind(R.id.rb_face_to_face)
    RadioButton rbFaceToFace;
    @Bind(R.id.rb_communication)
    RadioButton rbCommunication;
    @Bind(R.id.rb_un_talk)
    RadioButton rbUnTalk;
    @Bind(R.id.rb_un_companytalk)
    RadioButton rbUnCompanytalk;
    @Bind(R.id.gv_talk)
    RadioGroup gvTalk;
    @Bind(R.id.ed_progress)
    EditText edProgress;
    @Bind(R.id.ed_problem)
    EditText edProblem;
    @Bind(R.id.ed_nextplan)
    EditText edNextplan;
    @Bind(R.id.ed_help)
    EditText edHelp;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;

    String cid;
    int type;

    @Override
    public int getLayout() {
        return R.layout.activity_back_visit;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this);
    }

    @Override
    public void intData() {
        AndroidBug5497Workaround.assistActivity(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initListener();
        Intent intent = getIntent();
        cid = intent.getStringExtra("cid");
        tvTitle.setText("回访");
    }

    private void initListener() {
        gvTalk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_face_to_face:
                        type = 0;
                        break;
                    case R.id.rb_communication:
                        type = 1;
                        break;
                    case R.id.rb_un_talk:
                        type = 2;
                        break;
                    case R.id.rb_un_companytalk:
                        type = 3;
                        break;
                }
            }
        });
    }

    @Override
    public void initAdapter() {

    }

    @OnClick({R.id.img_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_submit:
                String progress = edProgress.getText().toString();
                String problem = edProblem.getText().toString();
                String nextplan = edNextplan.getText().toString();
                String help = edHelp.getText().toString();
                if (StringUtils.isEmpty(progress)) {
                    ToastUtil.getInstance().toastCentent("请填写项目进展！");
                    break;
                }
                if (StringUtils.isEmpty(problem)) {
                    ToastUtil.getInstance().toastCentent("请填写遇到问题！");
                    break;
                }
                if (StringUtils.isEmpty(nextplan)) {
                    ToastUtil.getInstance().toastCentent("请填写下步计划！");
                    break;
                }
                if (StringUtils.isEmpty(help)) {
                    ToastUtil.getInstance().toastCentent("请填写部门协助！");
                    break;
                }
                String addstr = "<b>项目进展：</b>" + progress + "<br><b>遇到问题：</b>" + problem + "<br><b>下步计划：</b>" + nextplan + "<br><b>部门协助：</b>" + help;
                Log.e("addstr", addstr);
                String addstrs = UrlTools.encode(addstr);
                addHuiFangData(cid, addstrs, type);
                break;
        }
    }

    private void addHuiFangData(String cid, String addstrs, int type) {
        Map map = new HashMap();
        map.put("rwdid", cid);
        map.put("content", addstrs);
        map.put("methods", type);
        OkhttpUtils.doPost(PathUrl.ADDHFURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_提交回访", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                        finish();
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(String message) {
                Log.e("tag_提交回访失败", message);

            }
        });
    }
}
