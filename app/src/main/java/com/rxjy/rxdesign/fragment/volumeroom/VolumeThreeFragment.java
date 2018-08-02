package com.rxjy.rxdesign.fragment.volumeroom;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.home.DesDaiMeasureActivity;
import com.rxjy.rxdesign.custom.TextGridview;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.DesDaiMeasureABean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 装修
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
    @Bind(R.id.tv_jihuatouzi)
    TextView tvJihuatouzi;
    @Bind(R.id.tv_dingwei)
    TextView tvDingwei;
    @Bind(R.id.tv_zhuangxiumudi)
    TextView tvZhuangxiumudi;
    @Bind(R.id.tv_kaigongTime)
    TextView tvKaigongTime;
    @Bind(R.id.tv_kaiyeTime)
    TextView tvKaiyeTime;
    @Bind(R.id.et_duishou)
    EditText etDuishou;
    @Bind(R.id.tv_kongtiao)
    TextView tvKongtiao;
    @Bind(R.id.tv_xiaofang)
    TextView tvXiaofang;
    @Bind(R.id.tgv_shejiMoney)
    TextGridview tgvShejiMoney;
    @Bind(R.id.tgv_xuqiu)
    TextGridview tgvXuqiu;
    @Bind(R.id.et_jhtouziMoney)
    EditText etJhtouziMoney;
    @Bind(R.id.ly_jhtouziMoney)
    LinearLayout lyJhtouziMoney;
    @Bind(R.id.tv_jtkaigongTime)
    TextView tvJtkaigongTime;
    @Bind(R.id.ly_jtkaigongTime)
    LinearLayout lyJtkaigongTime;
    @Bind(R.id.tv_jtkaiyeTime)
    TextView tvJtkaiyeTime;
    @Bind(R.id.ly_jtkaiyeTime)
    LinearLayout lyJtkaiyeTime;
    @Bind(R.id.tv_kongtiaoType)
    TextView tvKongtiaoType;
    @Bind(R.id.et_fanwei)
    EditText etFanwei;
    @Bind(R.id.et_xuqiu)
    EditText etXuqiu;
    @Bind(R.id.ly_kongtiaoType)
    LinearLayout lyKongtiaoType;
    @Bind(R.id.ly_fanwei)
    LinearLayout lyFanwei;
    @Bind(R.id.ly_xuqiu)
    LinearLayout lyXuqiu;

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
    private ArrayList<String> projectattribute;//项目属性
    private ArrayList<String> demandstyle;//意向风格
    private ArrayList<String> yesnolist;//招标、风水要求、
    private ArrayList<String> jiajulist;//家具软装、只能弱电
    private ArrayList<String> touzilist;//投资
    private ArrayList<String> dingweilist;//定位
    private ArrayList<String> mudilist;//装修目的
    private ArrayList<String> kongtiaolist;//空调需求
    private ArrayList<String> xiaofanglist;//消防需求
    private ArrayList<String> shejiMoneylist;//设计费用
    private ArrayList<String> zhuangxiulist;//装修需求
    private ArrayList<String> kaiyelist;//计划开业时间
    private ArrayList<String> kaigonglist;//计划开工时间
    private ArrayList<String> kongtiaoTypelist;//计划开工时间

    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;
    private DesDaiMeasureActivity activity;
    private String caLeaseTermEnd;
    private String caLeaseTermStart;
    private double term_start_timem;
    private String kaiGongTerm, kaiYeTerm;
    private OptionsPickerView touZiPV;
    private OptionsPickerView dingWeiPV;
    private OptionsPickerView muDiPV;
    private OptionsPickerView kongTiaoPV;
    private OptionsPickerView xiaoFangPV;
    private OptionsPickerView kaiGongPV;
    private OptionsPickerView kaiYePV;
    private OptionsPickerView kongtiaoTypePV;

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
        initAddData();
    }

    private void initAddData() {
        projectattribute = new ArrayList<>();
        projectattribute.add("正常单");//1
        projectattribute.add("看图报价单");//2
        projectattribute.add("设计单");//3
//        projectattribute.add("家具单");//6
//        projectattribute.add("弱电单");//7
//        projectattribute.add("局部改造");//8

        demandstyle = new ArrayList<>();
        demandstyle.add("现代简约");
        demandstyle.add("简欧风格");
        demandstyle.add("新中式");
        demandstyle.add("LOFT");
        demandstyle.add("工业风格");
        demandstyle.add("中式风格");
        demandstyle.add("港式风格");
        demandstyle.add("日式风格");
        demandstyle.add("田园风格");
        demandstyle.add("东南亚");
        demandstyle.add("后现代");
        demandstyle.add("地中海");
        demandstyle.add("其他");

        yesnolist = new ArrayList<>();
        yesnolist.add("是");
        yesnolist.add("否");

        jiajulist = new ArrayList<>();
        jiajulist.add("有");
        jiajulist.add("暂无");

        touzilist = new ArrayList<>();
        touzilist.add("请选择");
        touzilist.add("明确");
        touzilist.add("不明确");

        dingweilist = new ArrayList<>();
        dingweilist.add("请选择");
        dingweilist.add("单店");
        dingweilist.add("分公司");
        dingweilist.add("连锁");
        dingweilist.add("加盟");

        mudilist = new ArrayList<>();
        mudilist.add("请选择");
        mudilist.add("扩大经营");
        mudilist.add("变更地址");
        mudilist.add("原址翻新");

        kongtiaolist = new ArrayList<>();
        kongtiaolist.add("请选择");
        kongtiaolist.add("整体");
        kongtiaolist.add("局部");
        kongtiaolist.add("无");

        xiaofanglist = new ArrayList<>();
        xiaofanglist.add("请选择");
        xiaofanglist.add("整体");
        xiaofanglist.add("局部");
        xiaofanglist.add("无");

        shejiMoneylist = new ArrayList<>();
        shejiMoneylist.add("是");
        shejiMoneylist.add("否");

        zhuangxiulist = new ArrayList<>();
        zhuangxiulist.add("明确");
        zhuangxiulist.add("不明确");

        kaiyelist = new ArrayList<>();
        kaiyelist.add("请选择");
        kaiyelist.add("明确");
        kaiyelist.add("暂无");

        kaigonglist = new ArrayList<>();
        kaigonglist.add("请选择");
        kaigonglist.add("明确");
        kaigonglist.add("暂无");

        kongtiaoTypelist = new ArrayList<>();
        kongtiaoTypelist.add("请选择");
        kongtiaoTypelist.add("海尔");
        kongtiaoTypelist.add("格力");
        kongtiaoTypelist.add("大金");
        kongtiaoTypelist.add("日立");
        kongtiaoTypelist.add("York约克");
        kongtiaoTypelist.add("Mcquay麦克维尔");
        kongtiaoTypelist.add("开利");
        kongtiaoTypelist.add("美的");
        kongtiaoTypelist.add("三菱");
        kongtiaoTypelist.add("特灵");
        kongtiaoTypelist.add("顿汉布什");
        kongtiaoTypelist.add("美意");
        kongtiaoTypelist.add("东芝");
        kongtiaoTypelist.add("志高");
        kongtiaoTypelist.add("奥克斯");
        kongtiaoTypelist.add("TCL");
        kongtiaoTypelist.add("科龙");
        kongtiaoTypelist.add("西门子");
        kongtiaoTypelist.add("新科");

        initShow();
    }

    /**
     * 展示添加装修需求的源数据
     */
    int projectattributem, demandstylem, zhaobiaom, fengshuim, softm, intelligentweakm, advancePayment;

    private void initShow() {
        tgvProjectattribute.setTvType("属性");
        tgvProjectattribute.setGvLines(4);
        tgvProjectattribute.setGvData(getActivity(), projectattribute);


        tgvDemandstyle.setTvType("意向风格");
        tgvDemandstyle.setGvLines(4);
        tgvDemandstyle.setGvData(getActivity(), demandstyle);


        tgvZhaobiao.setTvType("招标");
        tgvZhaobiao.setGvLines(4);
        tgvZhaobiao.setGvData(getActivity(), yesnolist);


        tgvFengshui.setTvType("风水要求");
        tgvFengshui.setGvLines(4);
        tgvFengshui.setGvData(getActivity(), yesnolist);


        tgvSoft.setTvType("家具需求");
        tgvSoft.setGvLines(4);
        tgvSoft.setGvData(getActivity(), jiajulist);

        tgvIntelligentweak.setTvType("弱电需求");
        tgvIntelligentweak.setGvLines(4);
        tgvIntelligentweak.setGvData(getActivity(), jiajulist);

        tgvPadmoney.setTvType("垫资");
        tgvPadmoney.setGvLines(4);
        tgvPadmoney.setGvData(getActivity(), yesnolist);

        tgvShejiMoney.setTvType("接受设计费");
        tgvShejiMoney.setGvLines(4);
        tgvShejiMoney.setGvData(getActivity(), shejiMoneylist);

        tgvXuqiu.setTvType("装修需求");
        tgvXuqiu.setGvLines(4);
        tgvXuqiu.setGvData(getActivity(), zhuangxiulist);

        if (lhousedata != null) {
            ShowView(lhousedata);
        } else {
            DesDaiMeasureActivity desDaiMeasureActivity = (DesDaiMeasureActivity) getActivity();
            if (desDaiMeasureActivity.savedatabean != null) {
                Log.e("tag_获取数据", "正常");
                ShowView(desDaiMeasureActivity.savedatabean);
            }
        }

        checkListener();
    }

    private void checkListener() {
        tgvProjectattribute.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//项目属性
                if (projectattributem != 1) {
                    projectattributem = 1;
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

        tgvDemandstyle.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//风格
                if (demandstylem != 1) {
                    demandstylem = 1;
                }
                activity.savedatabean.setCa_IntentionalStyle(demandstyle.get(position));
            }
        });

        tgvZhaobiao.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//招标
                if (zhaobiaom != 1) {
                    zhaobiaom = 1;
                }
                activity.savedatabean.setCa_InviteTenders(yesnolist.get(position));
            }
        });

        tgvFengshui.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//风水
                if (fengshuim != 1) {
                    fengshuim = 1;
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

        tgvSoft.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//家具
                if (softm != 1) {
                    softm = 1;
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

        tgvIntelligentweak.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//智能弱电
                if (intelligentweakm != 1) {
                    intelligentweakm = 1;
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

        tgvPadmoney.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//垫资
                if (advancePayment != 1) {
                    advancePayment = 1;
                }
                activity.savedatabean.setCi_advancePayment(yesnolist.get(position));
            }
        });

        tgvShejiMoney.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {
                shejiMoneylist.get(position);
                if (shejiMoneylist.get(position).equals("是")) {
                    lyFanwei.setVisibility(View.VISIBLE);
                } else {
                    lyFanwei.setVisibility(View.GONE);
                }
            }
        });

        tgvXuqiu.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {
                zhuangxiulist.get(position);
                if (zhuangxiulist.get(position).equals("明确")) {
                    lyXuqiu.setVisibility(View.VISIBLE);
                } else {
                    lyXuqiu.setVisibility(View.GONE);
                }
            }
        });
    }

    int zhuangxiuyusuanm, gongqim, liangfangdizhim, kongjianxuqium;

    private void ShowView(DesDaiMeasureABean.BodyBean bean) {

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

        if (bean.getCa_proAttribute() != null && bean.getCa_proAttribute().length() > 0) {
            projectattributem = 1;
            int position = Integer.parseInt(bean.getCa_proAttribute());
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
                    tgvSoft.setContents("有", 0);
                    break;
                case "2":
                    tgvSoft.setContents("暂无", 1);
                    break;
            }
        }

        //智能弱电
        if (!StringUtils.isEmpty(bean.getCa_IntelligentWeakCurrent())) {
            intelligentweakm = 1;
            switch (bean.getCa_IntelligentWeakCurrent()) {
                case "1":
                    tgvIntelligentweak.setContents("有", 0);
                    break;
                case "2":
                    tgvIntelligentweak.setContents("暂无", 1);
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

    @Override
    public void initAdapter() {

    }

    @Override
    public void initLinstener() {

    }

    @OnClick({R.id.tv_measure_tenancy_term_end_time, R.id.tv_measure_tenancy_term_start_time, R.id.tv_jihuatouzi, R.id.tv_dingwei, R.id.tv_zhuangxiumudi, R.id.tv_kaigongTime, R.id.tv_kaiyeTime, R.id.tv_kongtiao, R.id.tv_xiaofang, R.id.tv_jtkaigongTime, R.id.tv_jtkaiyeTime, R.id.tv_kongtiaoType})
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
                        }
                        activity.savedatabean.setCa_DecorationDate(caLeaseTermStart);
                    }
                });
                startPicker.show();
                break;
            case R.id.tv_jihuatouzi://计划投资
                touZiPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvJihuatouzi.setText(touzilist.get(options1));
                        tvJihuatouzi.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                        if (touzilist.get(options1).equals("明确")) {
                            lyJhtouziMoney.setVisibility(View.VISIBLE);
                        } else {
                            lyJhtouziMoney.setVisibility(View.GONE);
                        }
                    }
                }).build();
                touZiPV.setPicker(touzilist);
                touZiPV.show();
                break;
            case R.id.tv_dingwei://定位
                dingWeiPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvDingwei.setText(dingweilist.get(options1));
                        tvDingwei.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                    }
                }).build();
                dingWeiPV.setPicker(dingweilist);
                dingWeiPV.show();
                break;
            case R.id.tv_zhuangxiumudi://装修目的
                muDiPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvZhuangxiumudi.setText(mudilist.get(options1));
                        tvZhuangxiumudi.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                    }
                }).build();
                muDiPV.setPicker(mudilist);
                muDiPV.show();
                break;
            case R.id.tv_kaigongTime://计划开工时间
                kaiGongPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvKaigongTime.setText(kaigonglist.get(options1));
                        tvKaigongTime.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                        if (kaigonglist.get(options1).equals("明确")) {
                            lyJtkaigongTime.setVisibility(View.VISIBLE);
                        } else {
                            lyJtkaigongTime.setVisibility(View.GONE);
                        }
                    }
                }).build();
                kaiGongPV.setPicker(kaigonglist);
                kaiGongPV.show();
                break;
            case R.id.tv_kaiyeTime://计划开业时间
                kaiYePV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvKaiyeTime.setText(kaiyelist.get(options1));
                        tvKaiyeTime.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                        if (kaiyelist.get(options1).equals("明确")) {
                            lyJtkaiyeTime.setVisibility(View.VISIBLE);
                        } else {
                            lyJtkaiyeTime.setVisibility(View.GONE);
                        }
                    }
                }).build();
                kaiYePV.setPicker(kaiyelist);
                kaiYePV.show();
                break;
            case R.id.tv_kongtiao://空调需求
                kongTiaoPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvKongtiao.setText(kongtiaolist.get(options1));
                        tvKongtiao.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                        if (kongtiaolist.get(options1).equals("无")) {
                            lyKongtiaoType.setVisibility(View.GONE);
                        } else {
                            lyKongtiaoType.setVisibility(View.VISIBLE);
                        }
                    }
                }).build();
                kongTiaoPV.setPicker(kongtiaolist);
                kongTiaoPV.show();
                break;
            case R.id.tv_xiaofang://消防需求
                xiaoFangPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvXiaofang.setText(xiaofanglist.get(options1));
                        tvXiaofang.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                    }
                }).build();
                xiaoFangPV.setPicker(xiaofanglist);
                xiaoFangPV.show();
                break;
            case R.id.tv_jtkaigongTime://具体开工时间
                DatePicker kaiYePicker = new DatePicker(getActivity());
                kaiYePicker.setRange(2017, 2100);//年份范围
                kaiYePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        kaiGongTerm = year + "-" + month + "-" + day;
                        tvJtkaigongTime.setText(kaiGongTerm);
                    }
                });
                kaiYePicker.show();
                break;
            case R.id.tv_jtkaiyeTime://具体开业时间
                DatePicker kaiGongPicker = new DatePicker(getActivity());
                kaiGongPicker.setRange(2017, 2100);//年份范围
                kaiGongPicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        kaiYeTerm = year + "-" + month + "-" + day;
                        tvJtkaiyeTime.setText(kaiYeTerm);
                    }
                });
                kaiGongPicker.show();
                break;
            case R.id.tv_kongtiaoType://空调类型
                kongtiaoTypePV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvKongtiaoType.setText(kongtiaoTypelist.get(options1));
                        tvKongtiaoType.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                    }
                }).build();
                kongtiaoTypePV.setPicker(kongtiaoTypelist);
                kongtiaoTypePV.show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
