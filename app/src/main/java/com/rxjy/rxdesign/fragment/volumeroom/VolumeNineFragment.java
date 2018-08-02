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
import com.rxjy.rxdesign.entity.ProjectTypeBean;
import com.rxjy.rxdesign.entity.ShangQuanBean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.StringUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 企业
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
    @Bind(R.id.et_quancheng)
    EditText etQuancheng;
    @Bind(R.id.et_mianji)
    EditText etMianji;
    @Bind(R.id.et_zujin)
    EditText etZujin;
    @Bind(R.id.tv_bangongdidian)
    TextView tvBangongdidian;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.ly_isXinKai)
    LinearLayout lyIsXinKai;
    @Bind(R.id.tv_qytype)
    TextView tvQytype;

    int Chenglishijianm;
    @Bind(R.id.tv_bangongdidianquyu)
    TextView tvBangongdidianquyu;
    @Bind(R.id.ly_bangongdidianquyu)
    LinearLayout lyBangongdidianquyu;
    @Bind(R.id.tv_bangongdidianshangquan)
    TextView tvBangongdidianshangquan;
    @Bind(R.id.ly_bangongdidianshangquan)
    LinearLayout lyBangongdidianshangquan;
    private OptionsPickerView ptypePV;
    private OptionsPickerView bGAddressPV;

    int ptypec = 0, ptypetwoc = 0, pshuxingc = 0, hstatec = 0, hfromsc = 0, chengjiaoc = 0;

    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;
    private DesDaiMeasureActivity activity;
    private String chenglishijian;
    private OptionsPickerView quYuPV;
    private OptionsPickerView shangQuanPV;

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
        getQiYeType();
        getBanGongAddress();
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
    private ArrayList<String> enterpriselist;
    private ArrayList<String> enterprisesizelist;
    private ArrayList<String> firstinlist;
    private ArrayList<String> bGAddresslist;
    private ArrayList<Integer> bGAddressIDlist;
    private ArrayList<String> quyulist;
    private ArrayList<Integer> quyuIDlist;
    private ArrayList<String> shangquanlist;
    ArrayList<String> typefulist;
    ArrayList<String> protypefulist;
    ArrayList<List<String>> protypezilist;
    ArrayList<ProjectTypeBean.FatherDataBean> protypelist;

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

        protypefulist = new ArrayList<>();
        protypezilist = new ArrayList<>();
        protypelist = new ArrayList<>();

        typefulist = new ArrayList<>();
        typefulist.add("办公");
        typefulist.add("餐饮");
        typefulist.add("商业");
        typefulist.add("酒店");
        typefulist.add("教育");
        typefulist.add("会所");

        bGAddresslist = new ArrayList<>();
        bGAddressIDlist = new ArrayList<>();
        quyulist = new ArrayList<>();
        quyuIDlist = new ArrayList<>();
        shangquanlist = new ArrayList<>();
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
                }
                activity.savedatabean.setCa_EnterprisesScale(enterprisesizelist.get(position));
            }
        });

        tgvFirstin.setTvType("新开");
        tgvFirstin.setGvLines(4);
        tgvFirstin.setGvData(getActivity(), firstinlist);
        tgvFirstin.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//改变钱数
                if (firstinm != 1) {
                    firstinm = 1;
                }
                activity.savedatabean.setCa_ForeignEnterprises(firstinlist.get(position));
                if (firstinlist.get(position).equals("否")) {
                    lyIsXinKai.setVisibility(View.VISIBLE);
                } else {
                    lyIsXinKai.setVisibility(View.GONE);
                }
            }
        });
    }

    private void ShowView(DesDaiMeasureABean.BodyBean info) {

        if (!StringUtils.isEmpty(info.getCa_EstablishmentTime())) {
            Chenglishijianm = 1;
            if (!info.getCa_EstablishmentTime().trim().isEmpty()) {
                tvNewChenglishijian.setText(info.getCa_EstablishmentTime());
            }
        }
        if (!StringUtils.isEmpty(info.getCa_BusinessScope())) {
            etNewJingyingfanwei.setText(info.getCa_BusinessScope());
        }
        if (!StringUtils.isEmpty(info.getCa_BusinessScope())) {
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
                    if (info.getCa_ForeignEnterprises().equals("否")) {
                        lyIsXinKai.setVisibility(View.VISIBLE);
                    } else {
                        lyIsXinKai.setVisibility(View.GONE);
                    }
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

    @OnClick({R.id.tv_qytype, R.id.tv_new_chenglishijian, R.id.tv_bangongdidian, R.id.tv_bangongdidianquyu, R.id.tv_bangongdidianshangquan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_qytype://企业类型
                if (protypefulist != null && protypezilist != null) {
                    ptypePV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tvQytype.setText(protypelist.get(options1).getMingCheng() + "-" + protypelist.get(options1).getZiji().get(options2).getMingCheng());
                            tvQytype.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                            ptypec = Integer.parseInt(protypelist.get(options1).getID());
                            ptypetwoc = Integer.parseInt(protypelist.get(options1).getZiji().get(options2).getID());
                        }
                    }).build();
                    ptypePV.setPicker(protypefulist, protypezilist);
                    ptypePV.show();
                }
                break;
            case R.id.tv_new_chenglishijian://成立时间
                DatePicker Picker = new DatePicker(getActivity());
                Picker.setRange(2017, 2100);//年份范围
                Picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        chenglishijian = year + "-" + month + "-" + day;
                        tvNewChenglishijian.setText(chenglishijian);
                        if (Chenglishijianm != 1) {
                            Chenglishijianm = 1;
                        }
                        activity.savedatabean.setCa_EstablishmentTime(chenglishijian);
                    }
                });
                Picker.show();
                break;
            case R.id.tv_bangongdidian://办公地点
                if (bGAddresslist.size() > 0) {
                    bGAddressPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tvBangongdidian.setText(bGAddresslist.get(options1));
                            tvBangongdidian.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                            lyBangongdidianquyu.setVisibility(View.VISIBLE);
                            getBanGongAddressTwo(bGAddressIDlist.get(options1));
                        }
                    }).build();
                    bGAddressPV.setPicker(bGAddresslist);
                    bGAddressPV.show();
                } else {
                    ToastUtil.getInstance().toastCentent("未获取到办公地址");
                }
                break;
            case R.id.tv_bangongdidianquyu:
                if (quyulist.size() > 0) {
                    quYuPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tvBangongdidianquyu.setText(quyulist.get(options1));
                            tvBangongdidianquyu.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                            getBanGongAddressShangQuan(quyuIDlist.get(options1));

                            lyBangongdidianshangquan.setVisibility(View.VISIBLE);
                        }
                    }).build();
                    quYuPV.setPicker(quyulist);
                    quYuPV.show();
                } else {
                    ToastUtil.getInstance().toastCentent("未获取到区域地址");
                }
                break;
            case R.id.tv_bangongdidianshangquan:
                if (shangquanlist.size() > 0) {
                    shangQuanPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tvBangongdidianshangquan.setText(shangquanlist.get(options1));
                            tvBangongdidianshangquan.setTextColor(getActivity().getResources().getColor(R.color.textblack));
//                            getBanGongAddressTwo(bGAddressIDlist.get(options1));
                        }
                    }).build();
                    shangQuanPV.setPicker(shangquanlist);
                    shangQuanPV.show();
                } else {
                    ToastUtil.getInstance().toastCentent("未获取到商圈地址");
                }
                break;
        }
    }

    public void getQiYeType() {//获取企业类型
        Map map = new HashMap();
        OkhttpUtils.doGet(getActivity(), PathUrl.GETXIANGMUTYPEURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取项目类型", data);
                ProjectTypeBean info = JSONUtils.toObject(data, ProjectTypeBean.class);
                ArrayList<ProjectTypeBean.FatherDataBean> body = info.getBody();
                protypelist.addAll(body);
                for (ProjectTypeBean.FatherDataBean fatherDataBean : body) {
                    protypefulist.add(fatherDataBean.getMingCheng());
                    List<String> child = new ArrayList<>();
                    for (ProjectTypeBean.FatherDataBean.SonDataBean childbean : fatherDataBean.getZiji()) {
                        child.add(childbean.getMingCheng());
                    }
                    protypezilist.add(child);
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取项目类型失败", message);
            }
        });
    }

    public void getBanGongAddress() {//获取办公地址
        Map map = new HashMap();
        OkhttpUtils.doGet(getActivity(), PathUrl.QYADDRESSURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取办公地址", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 1) {
                        BGAddressBean info = JSONUtils.toObject(data, BGAddressBean.class);
                        List<BGAddressBean.BodyBean> body = info.getBody();
                        for (int i = 0; i < body.size(); i++) {
                            bGAddresslist.add(body.get(i).getName());
                            bGAddressIDlist.add(body.get(i).getId());
                        }
                    }
                    Log.e("tag_获取数据", bGAddresslist.size() + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取办公地址失败", message);
            }
        });
    }

    public void getBanGongAddressTwo(int countryId) {//获取办公地址
        Map map = new HashMap();
        map.put("Id", countryId);
        OkhttpUtils.doGet(getActivity(), PathUrl.QYADDRESSURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取区域地址", data);
                try {
                    quyulist.clear();
                    quyuIDlist.clear();
                    JSONObject jsonObject = new JSONObject(data);
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 1) {
                        BGAddressBean info = JSONUtils.toObject(data, BGAddressBean.class);
                        List<BGAddressBean.BodyBean> body = info.getBody();
                        for (int i = 0; i < body.size(); i++) {
                            quyulist.add(body.get(i).getName());
                            quyuIDlist.add(body.get(i).getId());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取区域地址后失败", message);
            }
        });
    }

    public void getBanGongAddressShangQuan(int countryId) {//获取办公地址
        Map map = new HashMap();
        map.put("countryId", countryId);
        OkhttpUtils.doGet(getActivity(), PathUrl.QYADDRESSTWOURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取商圈地址", data);
                try {
                    shangquanlist.clear();
                    JSONObject jsonObject = new JSONObject(data);
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 1) {
                        ShangQuanBean info = JSONUtils.toObject(data, ShangQuanBean.class);
                        List<ShangQuanBean.BodyBean> body = info.getBody();
                        for (int i = 0; i < body.size(); i++) {
                            shangquanlist.add(body.get(i).getName());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取商圈地址后失败", message);
            }
        });
    }

}
