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
import cn.qqtheme.framework.picker.DatePicker;

import static com.rxjy.rxdesign.utils.Constants.LF_NUM;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolumeSixFragment extends BaseFragment {

    @Bind(R.id.tgv_housestatus)
    TextGridview tgvHousestatus;
    @Bind(R.id.tgv_norenttime)
    TextGridview tgvNorenttime;
    @Bind(R.id.tgv_makedealtype)
    TextGridview tgvMakedealtype;
    @Bind(R.id.tgv_housefromstatus)
    TextGridview tgvHousefromstatus;
    @Bind(R.id.et_new_buildmianji)
    EditText etNewBuildmianji;
    @Bind(R.id.et_new_mianji)
    EditText etNewMianji;
    @Bind(R.id.tv_new_jiaofangshijian)
    TextView tvNewJiaofangshijian;
    @Bind(R.id.tv_new_liangfangshijian)
    TextView tvNewLiangfangshijian;

    private String fangwuzhuangkuang;
    private String mianzuqi;
    private String chengjiaoleixing;
    private String fangyuanzhuangtai;
    private String mianji;
    private String jiaofangdate;
    private String liangfangdate;

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
        return R.layout.fragment_volume_six;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        activity = (DesDaiMeasureActivity) getActivity();
        initAddData();
//        etNewMianji.addTextChangedListener(new MyEditListener(etNewMianji));
//        etNewBuildmianji.addTextChangedListener(new MyEditListener(etNewBuildmianji));
    }

    /**
     * 展示添加装修需求的源数据
     */
    private List<String> housestatuslist;
    private List<String> norentlist;
    private List<String> makedealtypelist;
    private List<String> housefromstatuslist;

    private void initAddData() {
        housestatuslist = new ArrayList<>();
        housestatuslist.add("毛坯房");
        housestatuslist.add("清水房");
        housestatuslist.add("旧房改造");
        housestatuslist.add("翻新");
        housestatuslist.add("精装房");

        norentlist = new ArrayList<>();
        norentlist.add("一个月内");
        norentlist.add("1-2个月");
        norentlist.add("2个月以上");
        norentlist.add("无");

        makedealtypelist = new ArrayList<>();
        makedealtypelist.add("租");
        makedealtypelist.add("买");
        makedealtypelist.add("自建");

        housefromstatuslist = new ArrayList<>();
        housefromstatuslist.add("已定");
        housefromstatuslist.add("未定");

        initShow();
    }

    /**
     * 展示添加的源数据
     */
    int housestatusm, norenttimem, makedealtypem, housefromstatusm;

    private void initShow() {
        tgvHousestatus.setTvType("房屋状况");
        tgvHousestatus.setGvLines(4);
        tgvHousestatus.setGvData(getActivity(), housestatuslist);
        tgvHousestatus.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (housestatusm != 1) {
                    housestatusm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_HousingType(position + 1 + "");
            }
        });

        tgvNorenttime.setTvType("免租期");
        tgvNorenttime.setGvLines(4);
        tgvNorenttime.setGvData(getActivity(), norentlist);
        tgvNorenttime.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (norenttimem != 1) {
                    norenttimem = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_RentFreeDate(position + 1 + "");
            }
        });

        tgvMakedealtype.setTvType("成交类型");
        tgvMakedealtype.setGvLines(4);
        tgvMakedealtype.setGvData(getActivity(), makedealtypelist);
        tgvMakedealtype.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (makedealtypem != 1) {
                    makedealtypem = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_TransactionType(position + 1 + "");
            }
        });

        tgvHousefromstatus.setTvType("房源状态");
        tgvHousefromstatus.setGvLines(4);
        tgvHousefromstatus.setGvData(getActivity(), housefromstatuslist);
        tgvHousefromstatus.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (housefromstatusm != 1) {
                    housefromstatusm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_AvailabilityStatus(position + 1 + "");
            }
        });

//        mPresenter.GetNewSix(clientInfo.getCi_rwdid());
        if (lhousedata != null) {
            ShowView(lhousedata);
        }
    }

    private void ShowView(DesDaiMeasureABean.BodyBean info) {
        moneynum = activity.moneynum;
        if (!StringUtils.isEmpty(info.getCi_Area())) {
            mianjim = 1;
            etNewMianji.setText(info.getCi_Area());
        }

        if (!StringUtils.isEmpty(info.getCi_BuildingArea())) {
            buildmianji = 1;
            etNewBuildmianji.setText(info.getCi_BuildingArea());
        }

        if (!StringUtils.isEmpty(info.getCa_LaunchTime())) {
            jiaofangshijianm = 1;
            if (!info.getCa_LaunchTime().trim().isEmpty()) {
                tvNewJiaofangshijian.setText(info.getCa_LaunchTime());
            }
        }

        if (!StringUtils.isEmpty(info.getCa_MeasureDate())) {
            liangfangshijianm = 1;
            if (!info.getCa_MeasureDate().trim().isEmpty()) {
                tvNewLiangfangshijian.setText(info.getCa_MeasureDate());
            }
        }

        if (info.getCa_HousingType() != null && info.getCa_HousingType().length() > 0) {
            housestatusm = 1;
            int position = Integer.parseInt(info.getCa_HousingType());
            tgvHousestatus.setContents(housestatuslist.get(position - 1), position - 1);
        }

        if (info.getCa_RentFreeDate() != null && info.getCa_RentFreeDate().length() > 0) {
            norenttimem = 1;
            int position = Integer.parseInt(info.getCa_RentFreeDate());
            tgvNorenttime.setContents(norentlist.get(position - 1), position - 1);
        }

        if (info.getCa_TransactionType() != null && info.getCa_TransactionType().length() > 0) {
            makedealtypem = 1;
            int position = Integer.parseInt(info.getCa_TransactionType());
            tgvMakedealtype.setContents(makedealtypelist.get(position - 1), position - 1);

        }

        if (info.getCa_AvailabilityStatus() != null && info.getCa_AvailabilityStatus().length() > 0) {
            housefromstatusm = 1;
            int position = Integer.parseInt(info.getCa_AvailabilityStatus());
            tgvHousefromstatus.setContents(housefromstatuslist.get(position - 1), position - 1);
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

    private String area = "";
    private String buildarea = "";
    private String jiaofangshijian;
    private String liangfangshijian;
    private int jiaofangshijianm, liangfangshijianm;

    @OnClick({R.id.tv_new_jiaofangshijian, R.id.tv_new_liangfangshijian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_new_jiaofangshijian:
                DatePicker endPicker = new DatePicker(getActivity());
                endPicker.setRange(2017, 2100);//年份范围
                endPicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {

                        jiaofangshijian = year + "-" + month + "-" + day;
                        tvNewJiaofangshijian.setText(jiaofangshijian);
                        activity.savedatabean.setCa_LaunchTime(jiaofangshijian);
                        if (jiaofangshijianm != 1) {
                            jiaofangshijianm = 1;
                            addMoney();
                        }

                    }
                });
                endPicker.show();
                break;
            case R.id.tv_new_liangfangshijian:
                DatePicker Picker = new DatePicker(getActivity());
                Picker.setRange(2017, 2100);//年份范围
                Picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        liangfangshijianm = 1;
                        liangfangshijian = year + "-" + month + "-" + day;
                        tvNewLiangfangshijian.setText(liangfangshijian);
                        activity.savedatabean.setCa_MeasureDate(liangfangshijian);
                        if (liangfangshijianm != 1) {
                            liangfangshijianm = 1;
                            addMoney();
                        }
                    }
                });
                Picker.show();
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
                case R.id.et_new_mianji:
                    editchanges(lengths, 0);
                    if (lengths > 0) {
                        area = edittext.getText().toString().trim();
                    } else {
                        area = "";
                    }
                    activity.savedatabean.setCi_Area(area);
                    break;
                case R.id.et_new_buildmianji:
                    editchanges(lengths, 1);
                    if (lengths > 0) {
                        buildarea = edittext.getText().toString().trim();
                    } else {
                        buildarea = "";
                    }
                    activity.savedatabean.setCi_BuildingArea(buildarea);
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

    int mianjim, buildmianji;

    /**
     * 输入框改变后金钱的变化
     */
    private void editchanges(int length, int type) {
        Log.e("length" + length, "type" + type);
        if (length > 0) {
            switch (type) {
                case 0:
                    if (mianjim != 1) {
                        mianjim = 1;
                        addMoney();
                    }
                    break;
                case 1:
                    if (buildmianji != 1) {
                        buildmianji = 1;
                        addMoney();
                    }
                    break;
            }
        } else {
            switch (type) {
                case 0:
                    mianjim = 0;
                    noaddMoney();
                    break;
                case 1:
                    buildmianji = 0;
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
