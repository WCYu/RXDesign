package com.rxjy.rxdesign.fragment.volumeroom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.qqtheme.framework.picker.DatePicker;

import android.widget.EditText;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.home.DesDaiMeasureActivity;
import com.rxjy.rxdesign.custom.TextGridview;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.DesDaiMeasureABean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rxjy.rxdesign.utils.Constants.LF_NUM;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolumeThreeFragment extends BaseFragment {

    @Bind(R.id.tgv_projectattribute)
    TextGridview tgvProjectattribute;
    @Bind(R.id.tgv_demandstyle)
    TextGridview tgvDemandstyle;
    @Bind(R.id.tgv_zhaobiao)
    TextGridview tgvZhaobiao;
    @Bind(R.id.tgv_fengshui)
    TextGridview tgvFengshui;
    @Bind(R.id.tgv_soft)
    TextGridview tgvSoft;
    @Bind(R.id.tgv_intelligentweak)
    TextGridview tgvIntelligentweak;
    @Bind(R.id.tgv_padmoney)
    TextGridview tgvPadmoney;
    @Bind(R.id.et_new_zhuangxiuyusuan)
    EditText etNewZhuangxiuyusuan;
    @Bind(R.id.et_new_gongqi)
    EditText etNewGongqi;
    @Bind(R.id.tv_measure_tenancy_term_end_time)
    TextView tvMeasureTenancyTermEndTime;
    @Bind(R.id.tv_measure_tenancy_term_time_line)
    TextView tvMeasureTenancyTermTimeLine;
    @Bind(R.id.tv_measure_tenancy_term_start_time)
    TextView tvMeasureTenancyTermStartTime;
    @Bind(R.id.et_new_liangfangdizhi)
    EditText etNewLiangfangdizhi;
    @Bind(R.id.et_new_kongjianxuqiu)
    EditText etNewKongjianxuqiu;
    @Bind(R.id.et_new_shangwubeizhu)
    EditText etNewShangwubeizhu;

    //风格意向
    private String caIntentionalStyle = "";
    //家具软装
    private String caSoftFurniture = "";
    //智能弱电
    private String caIntelligentWeakCurrent = "";
    //风水要求
    private String caFengshuiRequirements = "";
    //项目属性
    private String xiangmushuxing;
    private String zhaobiao;
    private String zhuangxiuyusuan;
    private String gongqi;
    private String yujizhuangxiushijian;
    private String liangfangdizhi;
    private String kongjianxuqiu;
    private String shangwubeizhu;
    int itemmoney = 10;//单项数据的钱（假设为10）、、、、、、、、、、、、、、、、、、、、

    /**
     * 要添加的数据的赋值
     */
    private List<String> projectattribute;//项目属性
    private List<String> demandstyle;//意向风格
    private List<String> yesnolist;//招标、风水要求、家具软装、只能弱电

    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;
    private DesDaiMeasureActivity activity;
    private String caLeaseTermEnd;
    private String caLeaseTermStart;
    private double term_start_timem;

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
        return R.layout.fragment_volume_three;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        Log.e("单项数据的钱", itemmoney + "");
        activity = (DesDaiMeasureActivity) getActivity();
        etNewZhuangxiuyusuan.addTextChangedListener(new MyEditListener(etNewZhuangxiuyusuan));
        etNewGongqi.addTextChangedListener(new MyEditListener(etNewGongqi));
        etNewLiangfangdizhi.addTextChangedListener(new MyEditListener(etNewLiangfangdizhi));
        etNewKongjianxuqiu.addTextChangedListener(new MyEditListener(etNewKongjianxuqiu));
        initAddData();
    }

    private void initAddData() {
        projectattribute = new ArrayList<>();
        projectattribute.add("正常单");//1
        projectattribute.add("看图报价单");//2
        projectattribute.add("设计单");//3
        projectattribute.add("家具单");//6
        projectattribute.add("弱电单");//7
        projectattribute.add("局部改造");//8

        demandstyle = new ArrayList<>();
        demandstyle.add("现代简约");
        demandstyle.add("简欧风格");
        demandstyle.add("新中式");
        demandstyle.add("LOFT");
        demandstyle.add("其他");

        yesnolist = new ArrayList<>();
        yesnolist.add("是");
        yesnolist.add("否");

        initShow();
    }

    /**
     * 展示添加装修需求的源数据
     */
    int projectattributem, demandstylem, zhaobiaom, fengshuim, softm, intelligentweakm, advancePayment;

    private void initShow() {
        tgvProjectattribute.setTvType("项目属性");
        tgvProjectattribute.setGvLines(4);
        tgvProjectattribute.setGvData(getActivity(), projectattribute);
        tgvProjectattribute.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (projectattributem != 1) {
                    projectattributem = 1;
                    addMoney();
                }
                switch (position) {
                    case 0:
                        activity.savedatabean.setCa_proAttribute("1");
                        break;
                    case 1:
                        activity.savedatabean.setCa_proAttribute("2");
                        break;
                    case 2:
                        activity.savedatabean.setCa_proAttribute("3");
                        break;
                    case 3:
                        activity.savedatabean.setCa_proAttribute("6");
                        break;
                    case 4:
                        activity.savedatabean.setCa_proAttribute("7");
                        break;
                    case 5:
                        activity.savedatabean.setCa_proAttribute("8");
                        break;
                }
            }
        });

        tgvDemandstyle.setTvType("意向风格");
        tgvDemandstyle.setGvLines(4);
        tgvDemandstyle.setGvData(getActivity(), demandstyle);
        tgvDemandstyle.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (demandstylem != 1) {
                    demandstylem = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_IntentionalStyle(demandstyle.get(position));
            }
        });

        tgvZhaobiao.setTvType("招标");
        tgvZhaobiao.setGvLines(4);
        tgvZhaobiao.setGvData(getActivity(), yesnolist);
        tgvZhaobiao.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (zhaobiaom != 1) {
                    zhaobiaom = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_InviteTenders(yesnolist.get(position));
            }
        });

        tgvFengshui.setTvType("风水要求");
        tgvFengshui.setGvLines(4);
        tgvFengshui.setGvData(getActivity(), yesnolist);
        tgvFengshui.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (fengshuim != 1) {
                    fengshuim = 1;
                    addMoney();
                }
                switch (position) {
                    case 0:
                        activity.savedatabean.setCa_FengShuiRequirements("1");
                        break;
                    case 1:
                        activity.savedatabean.setCa_FengShuiRequirements("2");
                        break;
                }
            }
        });

        tgvSoft.setTvType("家具软装");
        tgvSoft.setGvLines(4);
        tgvSoft.setGvData(getActivity(), yesnolist);
        tgvSoft.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (softm != 1) {
                    softm = 1;
                    addMoney();
                }
                switch (position) {
                    case 0:
                        activity.savedatabean.setCa_SoftFurniture("1");
                        break;
                    case 1:
                        activity.savedatabean.setCa_SoftFurniture("2");
                        break;
                }
            }
        });

        tgvIntelligentweak.setTvType("智能弱电");
        tgvIntelligentweak.setGvLines(4);
        tgvIntelligentweak.setGvData(getActivity(), yesnolist);
        tgvIntelligentweak.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (intelligentweakm != 1) {
                    intelligentweakm = 1;
                    addMoney();
                }
                switch (position) {
                    case 0:
                        activity.savedatabean.setCa_IntelligentWeakCurrent("1");
                        break;
                    case 1:
                        activity.savedatabean.setCa_IntelligentWeakCurrent("2");
                        break;
                }
            }
        });
        tgvPadmoney.setTvType("垫资");
        tgvPadmoney.setGvLines(4);
        tgvPadmoney.setGvData(getActivity(), yesnolist);
        tgvPadmoney.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (advancePayment != 1) {
                    advancePayment = 1;
                    addMoney();
                }
                activity.savedatabean.setCi_advancePayment(yesnolist.get(position));
            }
        });

        ShowView(lhousedata);

    }

    int zhuangxiuyusuanm, gongqim, liangfangdizhim, kongjianxuqium;

    /**
     * 输入框改变后金钱的变化
     */
    private void editchanges(int length, int type) {
        Log.e("length" + length, "type" + type);
        if (length > 0) {
            switch (type) {
                case 0:
                    if (zhuangxiuyusuanm != 1) {
                        zhuangxiuyusuanm = 1;
                        addMoney();
                    }
                    break;
                case 1:
                    if (gongqim != 1) {
                        gongqim = 1;
                        addMoney();
                    }
                    break;
                case 2:
                    if (liangfangdizhim != 1) {
                        liangfangdizhim = 1;
                        addMoney();
                    }
                    break;
                case 3:
                    if (kongjianxuqium != 1) {
                        kongjianxuqium = 1;
                        addMoney();
                    }
                    break;
            }
        } else {
            switch (type) {
                case 0:
                    zhuangxiuyusuanm = 0;
                    Log.e("jianle", "0");
                    noaddMoney();
                    break;
                case 1:
                    gongqim = 0;
                    Log.e("jianle", "1");
                    noaddMoney();
                    break;
                case 2:
                    liangfangdizhim = 0;
                    Log.e("jianle", "2");
                    noaddMoney();
                    break;
                case 3:
                    kongjianxuqium = 0;
                    Log.e("jianle", "3");
                    noaddMoney();
                    break;
            }
        }
    }

    private void ShowView(DesDaiMeasureABean.BodyBean bean) {
        moneynum = activity.moneynum;

        if (!StringUtils.isEmpty(bean.getCi_DecorationAddress())) {
            liangfangdizhim = 1;
            etNewLiangfangdizhi.setText(bean.getCi_DecorationAddress());
        }
        if (!StringUtils.isEmpty(bean.getCa_DecBudgetPrice())) {
            zhuangxiuyusuanm = 1;
            etNewZhuangxiuyusuan.setText(bean.getCa_DecBudgetPrice());
        }
        if (!StringUtils.isEmpty(bean.getCa_ProjectTime())) {
            gongqim = 1;
            etNewGongqi.setText(bean.getCa_ProjectTime());
        }
        if (!StringUtils.isEmpty(bean.getCa_DecorationDate())) {
            term_start_timem = 1;
            tvMeasureTenancyTermStartTime.setText(bean.getCa_DecorationDate());
        }
        if (!StringUtils.isEmpty(bean.getCa_SpaceRequirement())) {
            kongjianxuqium = 1;
            etNewKongjianxuqiu.setText(bean.getCa_SpaceRequirement());
        }

//        etNewShangwubeizhu.setText(info.getCa_swRemarks());

        if (bean.getCa_proAttribute() != null && bean.getCa_proAttribute().length() > 0) {
            projectattributem = 1;
            int position = Integer.parseInt(bean.getCa_proAttribute());
//            tgv_projectattribute.setContents(projectattribute.get(position - 1), position - 1);
            switch (position) {
                case 1:
                    tgvProjectattribute.setContents("正常单", 0);
                    break;
                case 2:
                    tgvProjectattribute.setContents("看图报价单", 1);
                    break;
                case 3:
                    tgvProjectattribute.setContents("设计单", 2);
                    break;
                case 6:
                    tgvProjectattribute.setContents("家具单", 3);
                    break;
                case 7:
                    tgvProjectattribute.setContents("弱电单", 4);
                    break;
                case 8:
                    tgvProjectattribute.setContents("局部改造", 5);
                    break;
            }
        }

        if (!StringUtils.isEmpty(bean.getCa_IntentionalStyle())) {
            demandstylem = 1;
            for (int i = 0; i < demandstyle.size(); i++) {
                if (demandstyle.get(i).equals(bean.getCa_IntentionalStyle())) {
                    tgvDemandstyle.setContents(bean.getCa_IntentionalStyle(), i);
                    break;
                }
            }
        }

        //招标
        if (!StringUtils.isEmpty(bean.getCa_InviteTenders())) {
            zhaobiaom = 1;
            for (int i = 0; i < yesnolist.size(); i++) {
                if (yesnolist.get(i).equals(bean.getCa_InviteTenders())) {
                    tgvZhaobiao.setContents(bean.getCa_InviteTenders(), i);
                    break;
                }
            }
        }

        //家具软装
        if (!StringUtils.isEmpty(bean.getCa_SoftFurniture())) {
            softm = 1;
            switch (bean.getCa_SoftFurniture()) {
                case "1":
                    tgvSoft.setContents("是", 0);
                    break;
                case "2":
                    tgvSoft.setContents("否", 1);
                    break;
            }
        }

        //智能弱电
        if (!StringUtils.isEmpty(bean.getCa_IntelligentWeakCurrent())) {
            intelligentweakm = 1;
            switch (bean.getCa_IntelligentWeakCurrent()) {
                case "1":
                    tgvIntelligentweak.setContents("是", 0);
                    break;
                case "2":
                    tgvIntelligentweak.setContents("否", 1);
                    break;
            }
        }

        //风水要求
        if (!StringUtils.isEmpty(bean.getCa_FengShuiRequirements())) {
            fengshuim = 1;
            switch (bean.getCa_FengShuiRequirements()) {
                case "1":
                    tgvFengshui.setContents("是", 0);
                    break;
                case "2":
                    tgvFengshui.setContents("否", 1);
                    break;
            }
        }
        //垫资
        if (!StringUtils.isEmpty(bean.getCi_advancePayment())) {
            advancePayment = 1;
            for (int i = 0; i < yesnolist.size(); i++) {
                if (yesnolist.get(i).equals(bean.getCi_advancePayment())) {
                    tgvPadmoney.setContents(bean.getCi_advancePayment(), i);
                    break;
                }
            }
        }

    }

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

    @Override
    public void initAdapter() {

    }

    @Override
    public void initLinstener() {

    }

    private String yumoneyss = "", jobtimess = "", lhouseaddressss = "", spaceneedss = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv_measure_tenancy_term_end_time, R.id.tv_measure_tenancy_term_start_time})
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
                        if (term_start_timem != 1) {
                            term_start_timem = 1;
                            addMoney();
                        }
                        activity.savedatabean.setCa_DecorationDate(caLeaseTermStart);
                    }
                });
                startPicker.show();
                break;
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
                case R.id.et_new_zhuangxiuyusuan:
                    editchanges(lengths, 0);
                    if (lengths > 0) {
                        yumoneyss = edittext.getText().toString().trim();
                    } else {
                        yumoneyss = "";
                    }
                    activity.savedatabean.setCa_DecBudgetPrice(yumoneyss);
                    break;
                case R.id.et_new_gongqi:
                    editchanges(lengths, 1);
                    if (lengths > 0) {
                        jobtimess = edittext.getText().toString().trim();
                    } else {
                        jobtimess = "";
                    }
                    activity.savedatabean.setCa_ProjectTime(jobtimess);
                    break;
                case R.id.et_new_liangfangdizhi:
                    editchanges(lengths, 2);
                    if (lengths > 0) {
                        lhouseaddressss = edittext.getText().toString().trim();
                    } else {
                        lhouseaddressss = "";
                    }
                    activity.savedatabean.setCi_DecorationAddress(lhouseaddressss);
                    break;
                case R.id.et_new_kongjianxuqiu:
                    editchanges(lengths, 3);
                    if (lengths > 0) {
                        spaceneedss = edittext.getText().toString().trim();
                    } else {
                        spaceneedss = "";
                    }
                    activity.savedatabean.setCa_SpaceRequirement(spaceneedss);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
