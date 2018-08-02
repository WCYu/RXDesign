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
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.TextGridview;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.BGAddressBean;
import com.rxjy.rxdesign.entity.DesDaiMeasureABean;
import com.rxjy.rxdesign.entity.ShangQuanBean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 物业
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
    @Bind(R.id.tv_weidang)
    TextView tvWeidang;
    @Bind(R.id.et_kongtiaoname)
    EditText etKongtiaoname;
    @Bind(R.id.ly_kongtiaoname)
    LinearLayout lyKongtiaoname;
    @Bind(R.id.et_xiaofangname)
    EditText etXiaofangname;
    @Bind(R.id.ly_xiaofangname)
    LinearLayout lyXiaofangname;
    @Bind(R.id.tgv_baoxiangicd)
    TextGridview tgvBaoxiangicd;
    @Bind(R.id.et_baoxiangicdname)
    EditText etBaoxiangicdname;
    @Bind(R.id.ly_baoxiangicdname)
    LinearLayout lyBaoxiangicdname;
    @Bind(R.id.tv_fanghuo)
    TextView tvFanghuo;
    @Bind(R.id.tv_huanbao)
    TextView tvHuanbao;

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
    private OptionsPickerView weidangPV;
    private OptionsPickerView fanghuoPV;
    private OptionsPickerView huanbaoPV;

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
    }

    /**
     * 要添加的数据的赋值
     */
    private ArrayList<String> projectmateriallist;//保护材料
    private ArrayList<String> elevatoruselist;//电梯使用情况
    private ArrayList<String> constructiontimelist;//施工时间
    private ArrayList<String> yesnolist;//公共区域保护，物业保险保额，指定消防、空调、垃圾外运公司
    private ArrayList<String> blueprintlist;//报审蓝图
    private ArrayList<String> ishaslist;//空调消防维保单位、图纸审核
    private ArrayList<String> qualificationlist;//资质要求
    private ArrayList<String> baoxiangicdlist;//指定保险公司
    private ArrayList<String> weidanglist;//围档
    private ArrayList<String> fanghuolist;//围档
    private ArrayList<String> huanbaolist;//围档

    private void initAddData() {
        projectmateriallist = new ArrayList<>();
//        projectmateriallist.add("15mm细木工板");
//        projectmateriallist.add("18mm细木工板");
//        projectmateriallist.add("12mm石膏板");
//        projectmateriallist.add("9.5mm石膏板");
        projectmateriallist.add("无要求");
        projectmateriallist.add("彩布条");
        projectmateriallist.add("石膏板");
        projectmateriallist.add("木工板");
//        projectmateriallist.add("彩钢板");
//        projectmateriallist.add("围挡外围宣传喷绘");
//        projectmateriallist.add("其他");

        weidanglist = new ArrayList<>();
        weidanglist.add("无要求");
        weidanglist.add("彩钢板");
        weidanglist.add("石膏板");
        weidanglist.add("喷绘");

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

        baoxiangicdlist = new ArrayList<>();
        baoxiangicdlist.add("有");
        baoxiangicdlist.add("无");

        ishaslist = new ArrayList<>();
        ishaslist.add("有");
        ishaslist.add("无");

        qualificationlist = new ArrayList<>();
        qualificationlist.add("一级");
        qualificationlist.add("二级");
        qualificationlist.add("无");

        fanghuolist = new ArrayList<>();
        fanghuolist.add("无");
        fanghuolist.add("A");
        fanghuolist.add("A1");
        fanghuolist.add("A2");
        fanghuolist.add("B1");
        fanghuolist.add("B2");
        fanghuolist.add("B3");

        huanbaolist = new ArrayList<>();
        huanbaolist.add("无");
        huanbaolist.add("A");
        huanbaolist.add("E1");
        huanbaolist.add("E2");
        huanbaolist.add("E3");

        initShow();
    }

    /**
     * 展示添加的数据
     */
    int protectmaterialm, elevatorusem, constructiontimem, publicareaprotectm, propertyinsurancem, firecompanym,
            aircoldcompanym, garbagetranscompanym, blueprintm, aircoldfixunitm, qualificationm, drawingreviewm;

    private void initShow() {
        tgvElevatoruse.setTvType("电梯使用");
        tgvElevatoruse.setGvLines(4);
        tgvElevatoruse.setGvData(getActivity(), elevatoruselist);

        tgvProtectmaterial.setTvType("保护材料");
        tgvProtectmaterial.setGvLines(4);
        tgvProtectmaterial.setGvData(getActivity(), projectmateriallist);

        tgvConstructiontime.setTvType("施工时间");
        tgvConstructiontime.setGvLines(4);
        tgvConstructiontime.setGvData(getActivity(), constructiontimelist);

        tgvAircoldcompany.setTvType("空调维保单位");
        tgvAircoldcompany.setGvLines(4);
        tgvAircoldcompany.setGvData(getActivity(), yesnolist);

        tgvFirecompany.setTvType("消防维保单位");
        tgvFirecompany.setGvLines(4);
        tgvFirecompany.setGvData(getActivity(), yesnolist);

        tgvPublicareaprotect.setTvType("公共区域保护");
        tgvPublicareaprotect.setGvLines(4);
        tgvPublicareaprotect.setGvData(getActivity(), yesnolist);

        tgvPropertyinsurance.setTvType("工程一切险");
        tgvPropertyinsurance.setGvLines(4);
        tgvPropertyinsurance.setGvData(getActivity(), yesnolist);

        tgvBaoxiangicd.setTvType("指定保险公司");
        tgvBaoxiangicd.setGvLines(4);
        tgvBaoxiangicd.setGvData(getActivity(), baoxiangicdlist);

        tgvGarbagetranscompany.setTvType("指定垃圾外运");
        tgvGarbagetranscompany.setGvLines(4);
        tgvGarbagetranscompany.setGvData(getActivity(), yesnolist);

        tgvBlueprint.setTvType("报审图纸类型");
        tgvBlueprint.setGvLines(4);
        tgvBlueprint.setGvData(getActivity(), blueprintlist);

        tgvQualification.setTvType("资质要求");
        tgvQualification.setGvLines(4);
        tgvQualification.setGvData(getActivity(), qualificationlist);

        tgvAircoldfixunit.setTvType("空调消防维保单位");
        tgvAircoldfixunit.setGvLines(4);
        tgvAircoldfixunit.setGvData(getActivity(), ishaslist);

        tgvDrawingreview.setTvType("图纸审核");
        tgvDrawingreview.setGvLines(4);
        tgvDrawingreview.setGvData(getActivity(), ishaslist);

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
        tgvProtectmaterial.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//保护材料
                if (protectmaterialm != 1) {
                    protectmaterialm = 1;
                }
                activity.savedatabean.setCa_ProtectiveMaterial(projectmateriallist.get(position));
            }
        });

        tgvElevatoruse.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//电梯使用情况
                if (elevatorusem != 1) {
                    elevatorusem = 1;
                }
                activity.savedatabean.setCa_Elevator(elevatoruselist.get(position));
            }
        });

        tgvConstructiontime.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//施工时间
                if (constructiontimem != 1) {
                    constructiontimem = 1;
                }
                activity.savedatabean.setCa_ReqConTime(constructiontimelist.get(position));
            }
        });

        tgvPublicareaprotect.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//公共区域保护
                if (publicareaprotectm != 1) {
                    publicareaprotectm = 1;
                }
                activity.savedatabean.setCa_ProductProtection(yesnolist.get(position));
            }
        });

        tgvPropertyinsurance.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//工程一切险要求
                if (propertyinsurancem != 1) {
                    propertyinsurancem = 1;
                }
                activity.savedatabean.setCa_PropertyInsurance(yesnolist.get(position));
            }
        });

        tgvBaoxiangicd.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//指定保险公司
                if (baoxiangicdlist.get(position).equals("有")) {
                    lyBaoxiangicdname.setVisibility(View.VISIBLE);
                } else {
                    lyBaoxiangicdname.setVisibility(View.GONE);
                }
            }
        });

        tgvFirecompany.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//消防维保单位
                if (firecompanym != 1) {
                    firecompanym = 1;
                }
                if (yesnolist.get(position).equals("是")) {
                    lyXiaofangname.setVisibility(View.VISIBLE);
                } else {
                    lyXiaofangname.setVisibility(View.GONE);
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

        tgvAircoldcompany.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//空调维保单位
                if (aircoldcompanym != 1) {
                    aircoldcompanym = 1;
                }
                if (yesnolist.get(position).equals("是")) {
                    lyKongtiaoname.setVisibility(View.VISIBLE);
                } else {
                    lyKongtiaoname.setVisibility(View.GONE);
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

        tgvGarbagetranscompany.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//指定垃圾外运公司
                if (garbagetranscompanym != 1) {
                    garbagetranscompanym = 1;
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

        tgvBlueprint.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//报审蓝图
                if (blueprintm != 1) {
                    blueprintm = 1;
                }
                activity.savedatabean.setCa_Blueprint(blueprintlist.get(position));
            }
        });

        tgvAircoldfixunit.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//空调消防维保单位
                if (aircoldfixunitm != 1) {
                    aircoldfixunitm = 1;
                }
                activity.savedatabean.setCa_Maintenance(ishaslist.get(position));
            }
        });

        tgvQualification.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//资质要求
                if (qualificationm != 1) {
                    qualificationm = 1;
                }
                activity.savedatabean.setCa_Aptitude(qualificationlist.get(position));
            }
        });

        tgvDrawingreview.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//图纸审核
                if (drawingreviewm != 1) {
                    drawingreviewm = 1;
                }
                activity.savedatabean.setCa_DrawAudit(ishaslist.get(position));
            }
        });

    }

    private void ShowView(DesDaiMeasureABean.BodyBean info) {
        if (!StringUtils.isEmpty(info.getCa_SpecialRequirement())) {
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
            etNewPropertyperson.setText(info.getCa_LeadName());
        }
        if (info.getCa_TwoManagerTel() != null && info.getCa_TwoManagerTel().length() > 0) {
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

    @OnClick({R.id.tv_weidang, R.id.tv_fanghuo, R.id.tv_huanbao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_weidang:
                weidangPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvWeidang.setText(weidanglist.get(options1));
                        tvWeidang.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                    }
                }).build();
                weidangPV.setPicker(weidanglist);
                weidangPV.show();
                break;
            case R.id.tv_fanghuo:
                fanghuoPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvFanghuo.setText(fanghuolist.get(options1));
                        tvFanghuo.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                    }
                }).build();
                fanghuoPV.setPicker(fanghuolist);
                fanghuoPV.show();
                break;
            case R.id.tv_huanbao:
                huanbaoPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvHuanbao.setText(huanbaolist.get(options1));
                        tvHuanbao.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                    }
                }).build();
                huanbaoPV.setPicker(huanbaolist);
                huanbaoPV.show();
                break;
        }
    }
}
