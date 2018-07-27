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

import static com.rxjy.rxdesign.utils.Constants.LF_NUM;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolumeFourFragment extends BaseFragment {

    @Bind(R.id.tgv_protectmaterial)
    TextGridview tgvProtectmaterial;
    @Bind(R.id.tgv_elevatoruse)
    TextGridview tgvElevatoruse;
    @Bind(R.id.tgv_constructiontime)
    TextGridview tgvConstructiontime;
    @Bind(R.id.tgv_publicareaprotect)
    TextGridview tgvPublicareaprotect;
    @Bind(R.id.tgv_propertyinsurance)
    TextGridview tgvPropertyinsurance;
    @Bind(R.id.tgv_firecompany)
    TextGridview tgvFirecompany;
    @Bind(R.id.tgv_aircoldcompany)
    TextGridview tgvAircoldcompany;
    @Bind(R.id.tgv_garbagetranscompany)
    TextGridview tgvGarbagetranscompany;
    @Bind(R.id.tgv_blueprint)
    TextGridview tgvBlueprint;
    @Bind(R.id.et_new_wuyeshigongyaoqiu)
    EditText etNewWuyeshigongyaoqiu;
    @Bind(R.id.et_new_propertyperson)
    EditText etNewPropertyperson;
    @Bind(R.id.et_new_twofixperson)
    EditText etNewTwofixperson;
    @Bind(R.id.tgv_aircoldfixunit)
    TextGridview tgvAircoldfixunit;
    @Bind(R.id.tgv_qualification)
    TextGridview tgvQualification;
    @Bind(R.id.tgv_drawingreview)
    TextGridview tgvDrawingreview;

    private String shigongshijian;
    private String gonggongquyubaohu;
    private String wuyegongcheng;
    private String zhidingxiaofang;
    private String zhidingkongtiao;
    private String zhidinglajiwaiyun;
    private String baoshenlantu;
    private String diantishiyong;
    private String baohucailiao;
    private String wuyeshigongyaoqiu;
    private String propertyperson;//物业负责人
    private String twofixperson;//二装经理
    private String aircoldfixunit;//空调消防维保单位
    private String qualification;//资质要求
    private String drawingreview;//图纸审核

    int itemmoney = 10;//单项数据的钱（假设为10）、、、、、、、、、、、、、、、、、、、、

    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;
    private DesDaiMeasureActivity activity;

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
        return R.layout.fragment_volume_four;
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
        etNewWuyeshigongyaoqiu.addTextChangedListener(new MyEditListener(etNewWuyeshigongyaoqiu));
        etNewPropertyperson.addTextChangedListener(new MyEditListener(etNewPropertyperson));
        etNewTwofixperson.addTextChangedListener(new MyEditListener(etNewTwofixperson));
    }

    /**
     * 要添加的数据的赋值
     */
    private List<String> projectmateriallist;//保护材料
    private List<String> elevatoruselist;//电梯使用情况
    private List<String> constructiontimelist;//施工时间
    private List<String> yesnolist;//公共区域保护，物业保险保额，指定消防、空调、垃圾外运公司
    private List<String> blueprintlist;//报审蓝图
    private List<String> ishaslist;//空调消防维保单位、图纸审核
    private List<String> qualificationlist;//资质要求

    private void initAddData() {
        projectmateriallist = new ArrayList<>();
        projectmateriallist.add("15mm细木工板");
        projectmateriallist.add("18mm细木工板");
        projectmateriallist.add("12mm石膏板");
        projectmateriallist.add("9.5mm石膏板");
        projectmateriallist.add("彩布条");
        projectmateriallist.add("彩钢板");
        projectmateriallist.add("围挡外围宣传喷绘");
        projectmateriallist.add("其他");

        elevatoruselist = new ArrayList<>();
        elevatoruselist.add("免费");
        elevatoruselist.add("收费");
        elevatoruselist.add("禁用");

        constructiontimelist = new ArrayList<>();
        constructiontimelist.add("白天");
        constructiontimelist.add("晚上");
        constructiontimelist.add("均可");

        yesnolist = new ArrayList<>();
        yesnolist.add("是");
        yesnolist.add("否");

        blueprintlist = new ArrayList<>();
        blueprintlist.add("蓝图");
        blueprintlist.add("白图");

        ishaslist = new ArrayList<>();
        ishaslist.add("有");
        ishaslist.add("无");

        qualificationlist = new ArrayList<>();
        qualificationlist.add("一级");
        qualificationlist.add("二级");
        qualificationlist.add("无");

        initShow();
    }

    /**
     * 展示添加的数据
     */
    int protectmaterialm, elevatorusem, constructiontimem, publicareaprotectm, propertyinsurancem, firecompanym,
            aircoldcompanym, garbagetranscompanym, blueprintm, aircoldfixunitm, qualificationm, drawingreviewm;

    private void initShow() {
        tgvProtectmaterial.setTvType("保护材料");
        tgvProtectmaterial.setGvLines(4);
        tgvProtectmaterial.setGvData(getActivity(), projectmateriallist);
        tgvProtectmaterial.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (protectmaterialm != 1) {
                    protectmaterialm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_ProtectiveMaterial(projectmateriallist.get(position));
            }
        });

        tgvElevatoruse.setTvType("电梯使用情况");
        tgvElevatoruse.setGvLines(4);
        tgvElevatoruse.setGvData(getActivity(), elevatoruselist);
        tgvElevatoruse.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (elevatorusem != 1) {
                    elevatorusem = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_Elevator(elevatoruselist.get(position));
            }
        });

        tgvConstructiontime.setTvType("施工时间");
        tgvConstructiontime.setGvLines(4);
        tgvConstructiontime.setGvData(getActivity(), constructiontimelist);
        tgvConstructiontime.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (constructiontimem != 1) {
                    constructiontimem = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_ReqConTime(constructiontimelist.get(position));
            }
        });

        tgvPublicareaprotect.setTvType("公共区域保护");
        tgvPublicareaprotect.setGvLines(4);
        tgvPublicareaprotect.setGvData(getActivity(), yesnolist);
        tgvPublicareaprotect.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (publicareaprotectm != 1) {
                    publicareaprotectm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_ProductProtection(yesnolist.get(position));
            }
        });

        tgvPropertyinsurance.setTvType("工程一切险要求");
        tgvPropertyinsurance.setGvLines(4);
        tgvPropertyinsurance.setGvData(getActivity(), yesnolist);
        tgvPropertyinsurance.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (propertyinsurancem != 1) {
                    propertyinsurancem = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_PropertyInsurance(yesnolist.get(position));
            }
        });

        tgvFirecompany.setTvType("指定消防公司");
        tgvFirecompany.setGvLines(4);
        tgvFirecompany.setGvData(getActivity(), yesnolist);
        tgvFirecompany.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (firecompanym != 1) {
                    firecompanym = 1;
                    addMoney();
                }
                switch (position) {
                    case 0:
                        activity.savedatabean.setCa_DesignatedFireCompany("1");
                        break;
                    case 1:
                        activity.savedatabean.setCa_DesignatedFireCompany("2");
                        break;
                }
            }
        });

        tgvAircoldcompany.setTvType("指定空调公司");
        tgvAircoldcompany.setGvLines(4);
        tgvAircoldcompany.setGvData(getActivity(), yesnolist);
        tgvAircoldcompany.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (aircoldcompanym != 1) {
                    aircoldcompanym = 1;
                    addMoney();
                }
                switch (position) {
                    case 0:
                        activity.savedatabean.setCa_DesignatedAirCompany("1");
                        break;
                    case 1:
                        activity.savedatabean.setCa_DesignatedAirCompany("2");
                        break;
                }
            }
        });

        tgvGarbagetranscompany.setTvType("指定垃圾外运公司");
        tgvGarbagetranscompany.setGvLines(4);
        tgvGarbagetranscompany.setGvData(getActivity(), yesnolist);
        tgvGarbagetranscompany.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (garbagetranscompanym != 1) {
                    garbagetranscompanym = 1;
                    addMoney();
                }
                switch (position) {
                    case 0:
                        activity.savedatabean.setCa_DesignatedSinotrans("1");
                        break;
                    case 1:
                        activity.savedatabean.setCa_DesignatedSinotrans("2");
                        break;
                }
            }
        });

        tgvBlueprint.setTvType("报审蓝图");
        tgvBlueprint.setGvLines(4);
        tgvBlueprint.setGvData(getActivity(), blueprintlist);
        tgvBlueprint.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (blueprintm != 1) {
                    blueprintm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_Blueprint(blueprintlist.get(position));
            }
        });

        tgvAircoldfixunit.setTvType("空调消防维保单位");
        tgvAircoldfixunit.setGvLines(4);
        tgvAircoldfixunit.setGvData(getActivity(), ishaslist);
        tgvAircoldfixunit.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (aircoldfixunitm != 1) {
                    aircoldfixunitm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_Maintenance(ishaslist.get(position));
            }
        });

        tgvQualification.setTvType("资质要求");
        tgvQualification.setGvLines(4);
        tgvQualification.setGvData(getActivity(), qualificationlist);
        tgvQualification.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (qualificationm != 1) {
                    qualificationm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_Aptitude(qualificationlist.get(position));
            }
        });

        tgvDrawingreview.setTvType("图纸审核");
        tgvDrawingreview.setGvLines(4);
        tgvDrawingreview.setGvData(getActivity(), ishaslist);
        tgvDrawingreview.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (drawingreviewm != 1) {
                    drawingreviewm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_DrawAudit(ishaslist.get(position));
            }
        });

//        mPresenter.GetNewFour(clientInfo.getCi_rwdid());

        ShowView(lhousedata);
    }

    private void ShowView(DesDaiMeasureABean.BodyBean info) {
        moneynum = activity.moneynum;
        if (!StringUtils.isEmpty(info.getCa_SpecialRequirement())) {
            wuyeshigongyaoqium = 1;
            etNewWuyeshigongyaoqiu.setText(info.getCa_SpecialRequirement());
        }

        if (!StringUtils.isEmpty(info.getCa_ProtectiveMaterial())) {
            protectmaterialm = 1;
            for (int i = 0; i < projectmateriallist.size(); i++) {
                if (projectmateriallist.get(i).equals(info.getCa_ProtectiveMaterial())) {
                    tgvProtectmaterial.setContents(info.getCa_ProtectiveMaterial(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(info.getCa_Elevator())) {
            elevatorusem = 1;
            for (int i = 0; i < elevatoruselist.size(); i++) {
                if (elevatoruselist.get(i).equals(info.getCa_Elevator())) {
                    tgvElevatoruse.setContents(info.getCa_Elevator(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(info.getCa_ReqConTime())) {
            constructiontimem = 1;
            for (int i = 0; i < constructiontimelist.size(); i++) {
                if (constructiontimelist.get(i).equals(info.getCa_ReqConTime())) {
                    tgvConstructiontime.setContents(info.getCa_ReqConTime(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(info.getCa_ProductProtection())) {
            publicareaprotectm = 1;
            for (int i = 0; i < yesnolist.size(); i++) {
                if (yesnolist.get(i).equals(info.getCa_ProductProtection())) {
                    tgvPublicareaprotect.setContents(info.getCa_ProductProtection(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(info.getCa_PropertyInsurance())) {
            propertyinsurancem = 1;
            for (int i = 0; i < yesnolist.size(); i++) {
                if (yesnolist.get(i).equals(info.getCa_PropertyInsurance())) {
                    tgvPropertyinsurance.setContents(info.getCa_PropertyInsurance(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(info.getCa_DesignatedFireCompany())) {
            firecompanym = 1;
            switch (info.getCa_DesignatedFireCompany()) {
                case "1":
                    tgvFirecompany.setContents("是", 0);
                    break;
                case "2":
                    tgvFirecompany.setContents("否", 1);
                    break;
            }
        }

        if (!StringUtils.isEmpty(info.getCa_DesignatedAirCompany())) {
            aircoldcompanym = 1;
            switch (info.getCa_DesignatedAirCompany()) {
                case "1":
                    tgvAircoldcompany.setContents("是", 0);
                    break;
                case "2":
                    tgvAircoldcompany.setContents("否", 1);
                    break;
            }
        }

        if (!StringUtils.isEmpty(info.getCa_DesignatedSinotrans())) {
            garbagetranscompanym = 1;
            switch (info.getCa_DesignatedSinotrans()) {
                case "1":
                    tgvGarbagetranscompany.setContents("是", 0);
                    break;
                case "2":
                    tgvGarbagetranscompany.setContents("否", 1);
                    break;
            }
        }

        if (!StringUtils.isEmpty(info.getCa_Blueprint())) {
            blueprintm = 1;
            for (int i = 0; i < blueprintlist.size(); i++) {
                if (blueprintlist.get(i).equals(info.getCa_Blueprint())) {
                    tgvBlueprint.setContents(info.getCa_Blueprint(), i);
                    break;
                }
            }
        }

        if (info.getCa_LeadName() != null && info.getCa_LeadName().length() > 0) {
            propertypersonm = 1;
            etNewPropertyperson.setText(info.getCa_LeadName());
        }
        if (info.getCa_TwoManagerTel() != null && info.getCa_TwoManagerTel().length() > 0) {
            twofixpersonm = 1;
            etNewTwofixperson.setText(info.getCa_TwoManagerTel());
        }

        if (!StringUtils.isEmpty(info.getCa_Maintenance())) {
            aircoldfixunitm = 1;
            for (int i = 0; i < ishaslist.size(); i++) {
                if (ishaslist.get(i).equals(info.getCa_Maintenance())) {
                    tgvAircoldfixunit.setContents(info.getCa_Maintenance(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(info.getCa_Aptitude())) {
            qualificationm = 1;
            for (int i = 0; i < qualificationlist.size(); i++) {
                if (qualificationlist.get(i).equals(info.getCa_Aptitude())) {
                    tgvQualification.setContents(info.getCa_Aptitude(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(info.getCa_DrawAudit())) {
            drawingreviewm = 1;
            for (int i = 0; i < ishaslist.size(); i++) {
                if (ishaslist.get(i).equals(info.getCa_DrawAudit())) {
                    tgvDrawingreview.setContents(info.getCa_DrawAudit(), i);
                    break;
                }
            }
        }
    }

    private String wuyeneed = "", wuyeperson = "", personphone = "";

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
                case R.id.et_new_wuyeshigongyaoqiu:
                    editchanges(lengths, 0);
                    if (lengths > 0) {
                        wuyeneed = edittext.getText().toString().trim();
                    } else {
                        wuyeneed = "";
                    }
                    activity.savedatabean.setCa_SpecialRequirement(wuyeneed);
                    break;
                case R.id.et_new_propertyperson:
                    editchanges(lengths, 1);
                    if (lengths > 0) {
                        wuyeperson = edittext.getText().toString().trim();
                    } else {
                        wuyeperson = "";
                    }
                    activity.savedatabean.setCa_LeadName(wuyeperson);
                    break;
                case R.id.et_new_twofixperson:
                    editchanges(lengths, 3);
                    if (lengths > 0) {
                        personphone = edittext.getText().toString().trim();
                    } else {
                        personphone = "";
                    }
                    activity.savedatabean.setCa_TwoManagerTel(personphone);
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

    int wuyeshigongyaoqium, propertypersonm, twofixpersonm;

    /**
     * 输入框改变后金钱的变化
     */
    private void editchanges(int length, int type) {
        Log.e("length" + length, "type" + type);
        if (length > 0) {
            switch (type) {
                case 0:
                    if (wuyeshigongyaoqium != 1) {
                        wuyeshigongyaoqium = 1;
                        addMoney();
                    }
                    break;
                case 1:
                    if (propertypersonm != 1) {
                        propertypersonm = 1;
                        addMoney();
                    }
                    break;
                case 2:
                    if (twofixpersonm != 1) {
                        twofixpersonm = 1;
                        addMoney();
                    }
                    break;
            }
        } else {
            switch (type) {
                case 0:
                    wuyeshigongyaoqium = 0;
                    noaddMoney();
                    break;
                case 1:
                    propertypersonm = 0;
                    noaddMoney();
                    break;
                case 2:
                    twofixpersonm = 0;
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
}
