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
import cn.qqtheme.framework.picker.DatePicker;

import static com.rxjy.rxdesign.utils.Constants.LF_NUM;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolumeNineFragment extends BaseFragment {

    @Bind(R.id.tgv_enterprise)
    TextGridview tgvEnterprise;
    @Bind(R.id.tgv_enterprisesize)
    TextGridview tgvEnterprisesize;
    @Bind(R.id.tgv_firstin)
    TextGridview tgvFirstin;
    @Bind(R.id.tv_new_chenglishijian)
    TextView tvNewChenglishijian;
    @Bind(R.id.et_new_jingyingfanwei)
    EditText etNewJingyingfanwei;
    @Bind(R.id.et_new_qiyewenhua)
    EditText etNewQiyewenhua;

    private String qiyexingzhi;
    private String qiyeguimo;
    private String chenglidate;
    private String qiyeshouciruzhu;
    private String jingyingfanwei;
    private String qiyewenhua;
    int Chenglishijianm;

    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;
    private DesDaiMeasureActivity activity;
    private String chenglishijian;

    public void setLHouseData(DesDaiMeasureABean.BodyBean bean) {
        lhousedata = bean;
        if (lhousedata != null) {
            ShowView(lhousedata);
        } else {
            Log.e("tag_NineFragmenr", "lhousedata为空");
        }
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
        return R.layout.fragment_volume_nine;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        activity = (DesDaiMeasureActivity) getActivity();
        initAddData();
//        etNewJingyingfanwei.addTextChangedListener(new MyEditListener(etNewJingyingfanwei));
//        etNewQiyewenhua.addTextChangedListener(new MyEditListener(etNewQiyewenhua));
//        rgNewQiyeguimo.setOnCheckedChangeListener(this);
//        rgNewQiyexingzhi.setOnCheckedChangeListener(this);
//        rgNewShouciruzhu.setOnCheckedChangeListener(this);

        tvNewChenglishijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker Picker = new DatePicker(getActivity());
                Picker.setRange(2017, 2100);//年份范围
                Picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        chenglishijian = year + "-" + month + "-" + day;
                        tvNewChenglishijian.setText(chenglishijian);
                        if (Chenglishijianm != 1) {
                            Chenglishijianm = 1;
                            addMoney();
                        }
                        activity.savedatabean.setCa_EstablishmentTime(chenglishijian);
                    }
                });
                Picker.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lhousedata != null) {
            ShowView(lhousedata);
        } else {
            Log.e("tag_NineFragmenr", "lhousedata为空");
        }
    }

    /**
     * 展示添加的源数据
     */
    private List<String> enterpriselist;
    private List<String> enterprisesizelist;
    private List<String> firstinlist;

    private void initAddData() {
        enterpriselist = new ArrayList<>();
        enterpriselist.add("国企");
        enterpriselist.add("私企");
        enterpriselist.add("外企");
//        enterpriselist.add("央企");
//        enterpriselist.add("合资");
//        enterpriselist.add("其他");

        enterprisesizelist = new ArrayList<>();
        enterprisesizelist.add("30人以下");
        enterprisesizelist.add("30-50人");
        enterprisesizelist.add("50-100人");
        enterprisesizelist.add("100人以上");

        firstinlist = new ArrayList<>();
        firstinlist.add("是");
        firstinlist.add("否");

        initShow();
    }

    /**
     * 展示添加的源数据
     */
    int enterprisem, enterprisesizem, firstinm;

    private void initShow() {
        tgvEnterprise.setTvType("企业性质");
        tgvEnterprise.setGvLines(4);
        tgvEnterprise.setGvData(getActivity(), enterpriselist);
        tgvEnterprise.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (enterprisem != 1) {
                    enterprisem = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_EnterpriseNature(enterpriselist.get(position));
            }
        });

        tgvEnterprisesize.setTvType("企业规模");
        tgvEnterprisesize.setGvLines(4);
        tgvEnterprisesize.setGvData(getActivity(), enterprisesizelist);
        tgvEnterprisesize.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (enterprisesizem != 1) {
                    enterprisesizem = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_EnterprisesScale(enterprisesizelist.get(position));
            }
        });

        tgvFirstin.setTvType("是否首次创业");
        tgvFirstin.setGvLines(4);
        tgvFirstin.setGvData(getActivity(), firstinlist);
        tgvFirstin.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (firstinm != 1) {
                    firstinm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_ForeignEnterprises(firstinlist.get(position));
            }
        });
    }

    private void ShowView(DesDaiMeasureABean.BodyBean info) {
        moneynum = activity.moneynum;

        if (!StringUtils.isEmpty(info.getCa_EstablishmentTime())) {
            Chenglishijianm = 1;
            if (!info.getCa_EstablishmentTime().trim().isEmpty()) {
                tvNewChenglishijian.setText(info.getCa_EstablishmentTime());
            }
        }
        if (!StringUtils.isEmpty(info.getCa_BusinessScope())) {
            jingyingfanweim = 1;
            etNewJingyingfanwei.setText(info.getCa_BusinessScope());
        }
        if (!StringUtils.isEmpty(info.getCa_BusinessScope())) {
            qiyewenhuam = 1;
            etNewQiyewenhua.setText(info.getCa_CorporateCulture());
        }


        if (!StringUtils.isEmpty(info.getCa_EnterpriseNature())) {
            enterprisem = 1;
            for (int i = 0; i < enterpriselist.size(); i++) {
                if (enterpriselist.get(i).equals(info.getCa_EnterpriseNature())) {
                    tgvEnterprise.setContents(info.getCa_EnterpriseNature(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(info.getCa_EnterprisesScale())) {
            enterprisesizem = 1;
            for (int i = 0; i < enterprisesizelist.size(); i++) {
                if (enterprisesizelist.get(i).equals(info.getCa_EnterprisesScale())) {
                    tgvEnterprisesize.setContents(info.getCa_EnterprisesScale(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(info.getCa_ForeignEnterprises())) {
            firstinm = 1;
            for (int i = 0; i < firstinlist.size(); i++) {
                if (firstinlist.get(i).equals(info.getCa_ForeignEnterprises())) {
                    tgvFirstin.setContents(info.getCa_ForeignEnterprises(), i);
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

    private String fanwei = "", companywenhua = "";

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
                case R.id.et_new_jingyingfanwei:
                    editchanges(lengths, 0);
                    if (lengths > 0) {
                        fanwei = edittext.getText().toString().trim();
                    } else {
                        fanwei = "";
                    }
                    activity.savedatabean.setCa_BusinessScope(fanwei);
                    break;
                case R.id.et_new_qiyewenhua:
                    editchanges(lengths, 1);
                    if (lengths > 0) {
                        companywenhua = edittext.getText().toString().trim();
                    } else {
                        companywenhua = "";
                    }
                    activity.savedatabean.setCa_CorporateCulture(companywenhua);
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

    int jingyingfanweim, qiyewenhuam;

    /**
     * 输入框改变后金钱的变化
     */
    private void editchanges(int length, int type) {
        if (length > 0) {
            switch (type) {
                case 0:
                    if (jingyingfanweim != 1) {
                        jingyingfanweim = 1;
                        addMoney();
                    }
                    break;
                case 1:
                    if (qiyewenhuam != 1) {
                        qiyewenhuam = 1;
                        addMoney();
                    }
                    break;
            }
        } else {
            switch (type) {
                case 0:
                    jingyingfanweim = 0;
                    noaddMoney();
                    break;
                case 1:
                    qiyewenhuam = 0;
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
