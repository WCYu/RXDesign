package com.rxjy.rxdesign.fragment.volumeroom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.home.DesDaiMeasureActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.TextGridview;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.DesDaiMeasureABean;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.entity.ProjectTypeBean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.StringUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

import static com.rxjy.rxdesign.utils.Constants.LF_NUM;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolumeFiveFragment extends BaseFragment {

    @Bind(R.id.et_new_xiangmumingcheng)
    EditText etNewXiangmumingcheng;
    @Bind(R.id.et_new_bangongdidian)
    EditText etNewBangongdidian;
    @Bind(R.id.tv_measure_tenancy_term_end_time)
    TextView tvMeasureTenancyTermEndTime;
    @Bind(R.id.tv_measure_tenancy_term_time_line)
    TextView tvMeasureTenancyTermTimeLine;
    @Bind(R.id.tv_measure_tenancy_term_start_time)
    TextView tvMeasureTenancyTermStartTime;
    @Bind(R.id.tv_projectattribute)
    TextView tvProjectattribute;
    @Bind(R.id.rl_projectattribute)
    RelativeLayout rlProjectattribute;
    @Bind(R.id.tgv_industrytype)
    TextGridview tgvIndustrytype;

    private String xiangmumingcheng;
    private String bangongdidian;
    private String zuqi;
    private String xiangmuleixing;
    private String hangyeleixing;

    private String zuqiEnd;
    private String zuqiStart;
    int isshowed;
    private OptionsPickerView pVprojecttype;
    List<String> protypefulist;
    List<List<String>> protypezilist;
    List<ProjectTypeBean.FatherDataBean> protypelist;

    int itemmoney = 10;//单项数据的钱（假设为10）、、、、、、、、、、、、、、、、、、、、

    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;
    private DesDaiMeasureActivity activity;
    private String projectname;
    private String workaddress;

    private String caLeaseTermStart = "";
    private String caLeaseTermEnd = "";
    int end_timem, start_timem;

    public void setLHouseData(DesDaiMeasureABean.BodyBean bean) {
        lhousedata = bean;

    }

    /**
     * 量房列表中的数据
     */
    private AllClientNewBean.ClientNewBean clientInfo;

    public void setClientInfo(AllClientNewBean.ClientNewBean info) {
        clientInfo = info;
    }

    @Override
    public int getInitId() {
        return R.layout.fragment_volume_five;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        getProjecttype();
        activity = (DesDaiMeasureActivity) getActivity();

        initAddData();

        etNewXiangmumingcheng.addTextChangedListener(new MyEditListener(etNewXiangmumingcheng));
        etNewBangongdidian.addTextChangedListener(new MyEditListener(etNewBangongdidian));
    }

    /**
     * 展示添加装修需求的源数据
     */
    private List<String> projectattributelist;
    private List<String> industrytypelist;

    private void initAddData() {
        projectattributelist = new ArrayList<>();
        projectattributelist.add("办公");
        projectattributelist.add("餐饮");
        projectattributelist.add("商业");
        projectattributelist.add("酒店");
        projectattributelist.add("其它");

        industrytypelist = new ArrayList<>();
        industrytypelist.add("直营连锁");
        industrytypelist.add("加盟");
        industrytypelist.add("联盟");

        initShow();
    }

    /**
     * 展示添加的源数据
     */
    int projectattributem, industrytypem;

    private void initShow() {
        tgvIndustrytype.setTvType("经营模式");
        tgvIndustrytype.setGvLines(4);
        tgvIndustrytype.setGvData(getActivity(), industrytypelist);
        tgvIndustrytype.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (industrytypem != 1) {
                    industrytypem = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_HangYeLeIXing(212 + position + "");
            }
        });

//        mPresenter.GetNewFive(clientInfo.getCi_rwdid());
        ShowView(lhousedata);
    }

    private void ShowView(DesDaiMeasureABean.BodyBean info) {
        moneynum = activity.moneynum;
        if (!StringUtils.isEmpty(info.getCi_ClientName())) {
            xiangmumingchengm = 1;
            etNewXiangmumingcheng.setText(info.getCi_ClientName());
        }
        if (!StringUtils.isEmpty(info.getCi_OfficeAddress())) {
            bangongdidianm = 1;
            etNewBangongdidian.setText(info.getCi_OfficeAddress());
        }

        if (info.getCa_LeaseTermStart() != null && info.getCa_LeaseTermStart().length() > 0) {
            start_timem = 1;
            tvMeasureTenancyTermStartTime.setText(info.getCa_LeaseTermStart());
        }
        if (info.getCa_LeaseTermEnd() != null && info.getCa_LeaseTermEnd().length() > 0) {
            end_timem = 1;
            tvMeasureTenancyTermEndTime.setText(info.getCa_LeaseTermEnd());
        }

        if (info.getCa_HangYeLeIXing() != null && info.getCa_HangYeLeIXing().length() > 0) {
            industrytypem = 1;
            int position = Integer.parseInt(info.getCa_HangYeLeIXing());
            if (position >= 212) {
                tgvIndustrytype.setContents(industrytypelist.get(position - 212), position - 212);
            }
        }
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initLinstener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_measure_tenancy_term_end_time, R.id.tv_measure_tenancy_term_start_time, R.id.rl_projectattribute})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_measure_tenancy_term_end_time:
                DatePicker endPicker = new DatePicker(getActivity());
                endPicker.setRange(2017, 2100);//年份范围
                endPicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        caLeaseTermEnd = year + "-" + month + "-" + day;
                        tvMeasureTenancyTermEndTime.setText(caLeaseTermEnd);
                        if (end_timem != 1) {
                            end_timem = 1;
                            addMoney();
                        }
                        activity.savedatabean.setCa_LeaseTermEnd(caLeaseTermEnd);
                    }
                });
                endPicker.show();
                break;
            case R.id.tv_measure_tenancy_term_start_time:
                DatePicker startPicker = new DatePicker(getActivity());
                startPicker.setRange(2017, 2100);//年份范围
                startPicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        caLeaseTermStart = year + "-" + month + "-" + day;
                        tvMeasureTenancyTermStartTime.setText(caLeaseTermStart);
                        if (start_timem != 1) {
                            start_timem = 1;
                            addMoney();
                        }
                        activity.savedatabean.setCa_LeaseTermStart(caLeaseTermStart);
                    }
                });
                startPicker.show();
                break;
            case R.id.rl_projectattribute:
                if (isshowed == 1) {
//                    ToastUtil.getInstance().toastCentent("类型");
                    Showpupwindow();
                }
                break;
        }
    }

    private void Showpupwindow() {

        pVprojecttype = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvProjectattribute.setText(protypelist.get(options1).getMingCheng() + "-" + protypelist.get(options1).getZiji().get(options2).getMingCheng());
                activity.savedatabean.setCi_Type(protypelist.get(options1).getID());
                activity.savedatabean.setCa_SWIndustryTypeID(protypelist.get(options1).getZiji().get(options2).getID());
                if (projectattributem != 1) {
                    projectattributem = 1;
                    addMoney();
                    addMoney();
                }
            }
        }).build();
        pVprojecttype.setPicker(protypefulist, protypezilist);
        pVprojecttype.show();
    }

    public void getProjecttype() {

        OkhttpUtils.doGet(getActivity(), PathUrl.GETXMDATAURL, null, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取项目类型", data);
                ProjectTypeBean info = JSONUtils.toObject(data, ProjectTypeBean.class);
                ArrayList<ProjectTypeBean.FatherDataBean> body = info.getBody();
                setXMData(body);
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取项目类型失败", message);
            }
        });
    }

    public void setXMData(ArrayList<ProjectTypeBean.FatherDataBean> XMData) {
        isshowed = 1;
        //得到了数据展示
        protypefulist = new ArrayList<>();
        protypezilist = new ArrayList<>();
        protypelist = new ArrayList<>();
        protypelist.addAll(XMData);
        for (ProjectTypeBean.FatherDataBean fatherDataBean : XMData) {
            protypefulist.add(fatherDataBean.getMingCheng());
            List<String> child = new ArrayList<>();
            for (ProjectTypeBean.FatherDataBean.SonDataBean childbean : fatherDataBean.getZiji()) {
                child.add(childbean.getMingCheng());
            }
            protypezilist.add(child);
        }

        //判断有没有值并展示
        if (lhousedata.getCi_Type() != null && lhousedata.getCi_Type().length() > 0) {
            projectattributem = 1;
            for (int i = 0; i < protypelist.size(); i++) {
                if (lhousedata.getCi_Type().equals(protypelist.get(i).getID())) {
                    tvProjectattribute.setText(protypelist.get(i).getMingCheng());
                    Log.e("333", "show");
                    for (int j = 0; j < protypelist.get(i).getZiji().size(); j++) {
                        if (lhousedata.getCa_SWIndustryTypeID().equals(protypelist.get(i).getZiji().get(j).getID())) {
                            Log.e("444", "show");
                            tvProjectattribute.setText(protypelist.get(i).getMingCheng() + "-" + protypelist.get(i).getZiji().get(j).getMingCheng());
                        }
                    }
                }
            }
        }
    }

    private class MyEditListener implements TextWatcher {

        private EditText edittext;

        public MyEditListener(EditText edittext) {
            super();
            this.edittext = edittext;
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub
            int lengths = arg0.length();
            switch (edittext.getId()) {
                case R.id.et_new_xiangmumingcheng:
                    editchanges(lengths, 0);
                    if (lengths > 0) {
                        projectname = edittext.getText().toString().trim();
                    } else {
                        projectname = "";
                    }
                    activity.savedatabean.setCi_ClientName(projectname);
                    break;
                case R.id.et_new_bangongdidian:
                    editchanges(lengths, 1);
                    if (lengths > 0) {
                        workaddress = edittext.getText().toString().trim();
                    } else {
                        workaddress = "";
                    }
                    activity.savedatabean.setCi_OfficeAddress(workaddress);
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
            // TODO Auto-generated method stub
        }
    }

    int xiangmumingchengm, bangongdidianm;

    /**
     * 输入框改变后金钱的变化
     */
    private void editchanges(int length, int type) {
        Log.e("length" + length, "type" + type);
        if (length > 0) {
            switch (type) {
                case 0:
                    if (xiangmumingchengm != 1) {
                        xiangmumingchengm = 1;
                        addMoney();
                    }
                    break;
                case 1:
                    if (bangongdidianm != 1) {
                        bangongdidianm = 1;
                        addMoney();
                    }
                    break;
            }
        } else {
            switch (type) {
                case 0:
                    xiangmumingchengm = 0;
                    noaddMoney();
                    break;
                case 1:
                    bangongdidianm = 0;
                    noaddMoney();
                    break;
            }
        }
    }

    //显示金额（金额=总金额/96*个数 ）
    double allmoney;
    BigDecimal bigDecimal;
    double showmoney;
    int moneynum;//当前金额对应的个数

    private void addMoney() {
        moneynum = moneynum + 1;
        Log.e("个数；", moneynum + "");
        Log.e("金额是+；", lhousedata.getJDMoney() + "");
        allmoney = Double.parseDouble(lhousedata.getJDMoney());
        showmoney = allmoney / LF_NUM * moneynum;
        bigDecimal = new BigDecimal(showmoney);
        showmoney = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        activity.money = showmoney;
        Log.e("金额；", activity.money + "");
        activity.setMoney(activity.money);
        activity.setMoneynum(moneynum);
    }

    private void noaddMoney() {
        moneynum = moneynum - 1;
        Log.e("个数；", moneynum + "");
        allmoney = Double.parseDouble(lhousedata.getJDMoney());
        showmoney = allmoney / LF_NUM * moneynum;
        bigDecimal = new BigDecimal(showmoney);
        showmoney = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        activity.money = showmoney;
        Log.e("金额；", activity.money + "");
        activity.setMoney(activity.money);
        activity.setMoneynum(moneynum);
    }
}
