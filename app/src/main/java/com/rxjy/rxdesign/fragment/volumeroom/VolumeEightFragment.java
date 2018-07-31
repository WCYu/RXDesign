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

import com.bigkoo.pickerview.OptionsPickerView;
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
 * 客户
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
    @Bind(R.id.tv_zhiwu)
    TextView tvZhiwu;
    @Bind(R.id.tv_beijing)
    TextView tvBeijing;
    @Bind(R.id.tv_juese)
    TextView tvJuese;
    @Bind(R.id.tgv_juece)
    TextGridview tgvJuece;
    @Bind(R.id.tgv_chnegyi)
    TextGridview tgvChnegyi;

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
    private OptionsPickerView zhiWuPV;
    private OptionsPickerView beiJingPV;
    private OptionsPickerView jueSePV;

    public void setLHouseData(DesDaiMeasureABean.BodyBean bean) {
        lhousedata = bean;
        if (lhousedata != null) {
            ShowView(lhousedata);
        } else {
            Log.e("tag_EightFragment", "lhousedata为空");
        }
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
//        etNewKehuxingming.addTextChangedListener(new MyEditListener(etNewKehuxingming));
        initAddData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lhousedata != null) {
            ShowView(lhousedata);
        } else {
            Log.e("tag_EightFragment", "lhousedata为空");
        }
    }

    //展示添加的源数据
    private ArrayList<String> agelist;
    private ArrayList<String> sexlist;
    private ArrayList<String> contractlist;
    private ArrayList<String> identitylist;
    private ArrayList<String> attentionlist;
    private ArrayList<String> characterlist;
    private ArrayList<String> juecelist;
    private ArrayList<String> chengyilist;
    private ArrayList<String> zhiwulist;
    private ArrayList<String> beijinglist;
    private ArrayList<String> jueselist;

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

        zhiwulist = new ArrayList<>();
        zhiwulist.add("请选择");
        zhiwulist.add("新政");
        zhiwulist.add("副总");
        zhiwulist.add("老板");
        zhiwulist.add("老板助理");
        zhiwulist.add("合伙人");
        zhiwulist.add("财务");
        zhiwulist.add("无");

        beijinglist = new ArrayList<>();
        beijinglist.add("请选择");
        beijinglist.add("个体户");
        beijinglist.add("白手起家");
        beijinglist.add("职场精英");
        beijinglist.add("霸道总裁");
        beijinglist.add("暴发户");
        beijinglist.add("富二代");
        beijinglist.add("海归派");
        beijinglist.add("公务员");
        beijinglist.add("国企高层");
        beijinglist.add("外企高层");
        beijinglist.add("文艺范");

        jueselist = new ArrayList<>();
        jueselist.add("请选择");
        jueselist.add("传话人");
        jueselist.add("经办人");
        jueselist.add("决策人");

        juecelist = new ArrayList<>();
        juecelist.add("有");
        juecelist.add("无");

        chengyilist = new ArrayList<>();
        chengyilist.add("高");
        chengyilist.add("低");

        initShow();
    }

    //展示添加的源数据
    int agem, sexm, contractm, identitym, attentionm, characterm;

    private void initShow() {
        tgvAge.setTvType("年龄");
        tgvAge.setGvLines(4);
        tgvAge.setGvData(getActivity(), agelist);

        tgvSex.setTvType("性别");
        tgvSex.setGvLines(4);
        tgvSex.setGvData(getActivity(), sexlist);


        tgvContract.setTvType("合同负责人");
        tgvContract.setGvLines(4);
        tgvContract.setGvData(getActivity(), contractlist);

        tgvIdentity.setTvType("身份");
        tgvIdentity.setGvLines(4);

        tgvAttention.setTvType("注重");
        tgvAttention.setGvLines(4);
        tgvAttention.setGvData(getActivity(), attentionlist);

        tgvCharacter.setTvType("性格");
        tgvCharacter.setGvLines(4);
        tgvCharacter.setGvData(getActivity(), characterlist);

        tgvJuece.setTvType("决策权");
        tgvJuece.setGvLines(4);
        tgvJuece.setGvData(getActivity(), juecelist);

        tgvChnegyi.setTvType("诚意度");
        tgvChnegyi.setGvLines(4);
        tgvChnegyi.setGvData(getActivity(), chengyilist);

        checkListener();
    }

    private void checkListener() {
        tgvAge.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//年龄
                if (agem != 1) {
                    agem = 1;
                }
                activity.savedatabean.setCi_proAge(position + 1 + "");
            }
        });

        tgvSex.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//性别
                if (sexm != 1) {
                    sexm = 1;
                }
                activity.savedatabean.setCi_proSex(position + 1 + "");
            }
        });

        tgvContract.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//合同负责人
                if (contractm != 1) {
                    contractm = 1;
                }
                activity.savedatabean.setCa_ContractPerson(position + 1 + "");
            }
        });

        tgvIdentity.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//身份
                if (identitym != 1) {
                    identitym = 1;
                }
                activity.savedatabean.setCa_proHeadIdentity(position + 1 + "");
            }
        });

        tgvAttention.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//注重
                if (attentionm != 1) {
                    attentionm = 1;
                }
                activity.savedatabean.setCa_NoteFocus(attentionlist.get(position));
            }
        });

        tgvCharacter.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//性格
                if (characterm != 1) {
                    characterm = 1;
                }
                activity.savedatabean.setCa_proHeadCharacter(characterlist.get(position));
            }
        });

        tgvJuece.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//决策
            }
        });

        tgvChnegyi.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//诚意
            }
        });

    }

    private void ShowView(DesDaiMeasureABean.BodyBean info) {
        if (!StringUtils.isEmpty(info.getCi_proHead())) {
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

    @OnClick({R.id.tv_zhiwu, R.id.tv_beijing, R.id.tv_juese})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_zhiwu://职务
                zhiWuPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvZhiwu.setText(zhiwulist.get(options1));
                        tvZhiwu.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                    }
                }).build();
                zhiWuPV.setPicker(zhiwulist);
                zhiWuPV.show();
                break;
            case R.id.tv_beijing://背景
                beiJingPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvBeijing.setText(beijinglist.get(options1));
                        tvBeijing.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                    }
                }).build();
                beiJingPV.setPicker(beijinglist);
                beiJingPV.show();
                break;
            case R.id.tv_juese://角色
                jueSePV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvJuese.setText(jueselist.get(options1));
                        tvJuese.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                    }
                }).build();
                jueSePV.setPicker(jueselist);
                jueSePV.show();
                break;
        }
    }
}
