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
public class VolumeEightFragment extends BaseFragment {

    @Bind(R.id.et_new_kehuxingming)
    EditText etNewKehuxingming;
    @Bind(R.id.tgv_age)
    TextGridview tgvAge;
    @Bind(R.id.tgv_sex)
    TextGridview tgvSex;
    @Bind(R.id.tgv_contract)
    TextGridview tgvContract;
    @Bind(R.id.tgv_identity)
    TextGridview tgvIdentity;
    @Bind(R.id.tgv_attention)
    TextGridview tgvAttention;
    @Bind(R.id.tgv_character)
    TextGridview tgvCharacter;

    private String kehuxingming;
    private String shenfen;
    private String xingbie;
    private String nianling;
    private String zhuzhong;
    private String hetongfuzeren;
    private String xingge;

    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;
    private DesDaiMeasureActivity activity;

    public void setLHouseData(DesDaiMeasureABean.BodyBean bean) {
        lhousedata = bean;

    }

    // 量房列表中的数据
    private AllClientNewBean.ClientNewBean clientInfo;

    public void setClientInfo(AllClientNewBean.ClientNewBean info) {
        clientInfo = info;
    }

    @Override
    public int getInitId() {
        return R.layout.fragment_volume_eight;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        activity = (DesDaiMeasureActivity) getActivity();
        etNewKehuxingming.addTextChangedListener(new MyEditListener(etNewKehuxingming));
        initAddData();
    }

    //展示添加的源数据
    private List<String> agelist;
    private List<String> sexlist;
    private List<String> contractlist;
    private List<String> identitylist;
    private List<String> attentionlist;
    private List<String> characterlist;

    private void initAddData() {
        agelist = new ArrayList<>();
        agelist.add("20-30岁");
        agelist.add("30-40岁");
        agelist.add("40-50岁");
        agelist.add("50岁以上");

        sexlist = new ArrayList<>();
        sexlist.add("男");
        sexlist.add("女");

        contractlist = new ArrayList<>();
        contractlist.add("是");
        contractlist.add("否");

        identitylist = new ArrayList<>();
        identitylist.add("行政");
        identitylist.add("副总");
        identitylist.add("老板");
        identitylist.add("负责人");
        identitylist.add("老板助理");
        identitylist.add("合伙人");
        identitylist.add("财务");

        attentionlist = new ArrayList<>();
        attentionlist.add("团队");
        attentionlist.add("平台");
        attentionlist.add("细节");
        attentionlist.add("价格");
        attentionlist.add("设计");
        attentionlist.add("施工工艺");
        attentionlist.add("为人处事");

        characterlist = new ArrayList<>();
        characterlist.add("温和");
        characterlist.add("暴躁");
        characterlist.add("强势");
        characterlist.add("随性");
        characterlist.add("急性子");
        characterlist.add("慢性子");
        characterlist.add("矫情");
        characterlist.add("纠结");
        initShow();
    }

    //展示添加的源数据
    int agem, sexm, contractm, identitym, attentionm, characterm;

    private void initShow() {
        tgvAge.setTvType("年龄");
        tgvAge.setGvLines(4);
        tgvAge.setGvData(getActivity(), agelist);
        tgvAge.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (agem != 1) {
                    agem = 1;
                    addMoney();
                }
                activity.savedatabean.setCi_proAge(position + 1 + "");
            }
        });

        tgvSex.setTvType("性别");
        tgvSex.setGvLines(4);
        tgvSex.setGvData(getActivity(), sexlist);
        tgvSex.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (sexm != 1) {
                    sexm = 1;
                    addMoney();
                }
                activity.savedatabean.setCi_proSex(position + 1 + "");
            }
        });

        tgvContract.setTvType("合同负责人");
        tgvContract.setGvLines(4);
        tgvContract.setGvData(getActivity(), contractlist);
        tgvContract.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (contractm != 1) {
                    contractm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_ContractPerson(position + 1 + "");
            }
        });

        tgvIdentity.setTvType("身份");
        tgvIdentity.setGvLines(4);
        tgvIdentity.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (identitym != 1) {
                    identitym = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_proHeadIdentity(position + 1 + "");
            }
        });

        tgvAttention.setTvType("注重");
        tgvAttention.setGvLines(4);
        tgvAttention.setGvData(getActivity(), attentionlist);
        tgvAttention.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (attentionm != 1) {
                    attentionm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_NoteFocus(attentionlist.get(position));
            }
        });

        tgvCharacter.setTvType("性格");
        tgvCharacter.setGvLines(4);
        tgvCharacter.setGvData(getActivity(), characterlist);
        tgvCharacter.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (characterm != 1) {
                    characterm = 1;
                    addMoney();
                }
                activity.savedatabean.setCa_proHeadCharacter(characterlist.get(position));
            }
        });
        ShowView(lhousedata);
    }

    private void ShowView(DesDaiMeasureABean.BodyBean info) {
        moneynum = activity.moneynum;
        if (!StringUtils.isEmpty(info.getCi_proHead())) {
            kehuxingmingm = 1;
            etNewKehuxingming.setText(info.getCi_proHead());
        }

        if (info.getCi_proAge() != null && info.getCi_proAge().length() > 0) {
            agem = 1;
            int position = Integer.parseInt(info.getCi_proAge());
            tgvAge.setContents(agelist.get(position - 1), position - 1);
        }

        if (info.getCi_proSex() != null && info.getCi_proSex().length() > 0) {
            sexm = 1;
            int position = Integer.parseInt(info.getCi_proSex());
            tgvSex.setContents(sexlist.get(position - 1), position - 1);
        }

        if (info.getCa_ContractPerson() != null && info.getCa_ContractPerson().length() > 0) {
            contractm = 1;
            int position = Integer.parseInt(info.getCa_ContractPerson());
            tgvContract.setContents(contractlist.get(position - 1), position - 1);
        }

        if (info.getCa_proHeadIdentity() != null && info.getCa_proHeadIdentity().length() > 0) {
            identitym = 1;
            int position = Integer.parseInt(info.getCa_proHeadIdentity());
            tgvIdentity.setContents(identitylist.get(position - 1), position - 1);
        }

        if (!StringUtils.isEmpty(info.getCa_NoteFocus())) {
            attentionm = 1;
            for (int i = 0; i < attentionlist.size(); i++) {
                if (attentionlist.get(i).equals(info.getCa_NoteFocus())) {
                    tgvAttention.setContents(info.getCa_NoteFocus(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(info.getCa_proHeadCharacter())) {
            characterm = 1;
            for (int i = 0; i < characterlist.size(); i++) {
                if (characterlist.get(i).equals(info.getCa_proHeadCharacter())) {
                    tgvCharacter.setContents(info.getCa_proHeadCharacter(), i);
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

    private String customername = "";

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
                case R.id.et_new_kehuxingming:
                    editchanges(lengths, 0);
                    if (lengths > 0) {
                        customername = edittext.getText().toString().trim();
                    } else {
                        customername = "";
                    }
                    activity.savedatabean.setCi_proHead(customername);
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

    int kehuxingmingm;

    /**
     * 输入框改变后金钱的变化
     */
    private void editchanges(int length, int type) {
        Log.e("length" + length, "type" + type);
        if (length > 0) {
            switch (type) {
                case 0:
                    if (kehuxingmingm != 1) {
                        kehuxingmingm = 1;
                        addMoney();
                    }
                    break;
            }
        } else {
            switch (type) {
                case 0:
                    kehuxingmingm = 0;
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
        allmoney = Double.parseDouble(lhousedata.getJDMoney());
        showmoney = allmoney / LF_NUM * moneynum;
        bigDecimal = new BigDecimal(showmoney);
        showmoney = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        activity.money = showmoney;
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
