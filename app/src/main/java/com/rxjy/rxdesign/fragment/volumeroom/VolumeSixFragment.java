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
 * 房源
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
    @Bind(R.id.tv_liangfangdidian)
    TextView tvLiangfangdidian;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.et_zuqi)
    EditText etZuqi;
    @Bind(R.id.et_yuezujin)
    EditText etYuezujin;
    @Bind(R.id.et_jine)
    EditText etJine;
    @Bind(R.id.ly_zu)
    LinearLayout lyZu;
    @Bind(R.id.ly_mai)
    LinearLayout lyMai;
    @Bind(R.id.tv_liangfangquyu)
    TextView tvLiangfangquyu;
    @Bind(R.id.ly_liangfangquyu)
    LinearLayout lyLiangfangquyu;
    @Bind(R.id.tv_liangfangshangquan)
    TextView tvLiangfangshangquan;
    @Bind(R.id.ly_liangfangshangquan)
    LinearLayout lyLiangfangshangquan;

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
    private OptionsPickerView bGAddressPV;
    private OptionsPickerView quYuPV;
    private OptionsPickerView shangQuanPV;

    public void setLHouseData(DesDaiMeasureABean.BodyBean bean) {
        lhousedata = bean;
        if (lhousedata != null) {
            ShowView(lhousedata);
        } else {
            Log.e("tag_SixFragment", "lhousedata为空");
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
        getBanGongAddress();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lhousedata != null) {
            ShowView(lhousedata);
        } else {
            Log.e("tag_SixFragment", "lhousedata为空");
        }
    }

    /**
     * 展示添加装修需求的源数据
     */
    private List<String> housestatuslist;
    private List<String> norentlist;
    private List<String> makedealtypelist;
    private List<String> housefromstatuslist;

    private ArrayList<String> bGAddresslist;
    private ArrayList<Integer> bGAddressIDlist;
    private ArrayList<String> quyulist;
    private ArrayList<Integer> quyuIDlist;
    private ArrayList<String> shangquanlist;

    private void initAddData() {
        housestatuslist = new ArrayList<>();
        housestatuslist.add("请选择");
        housestatuslist.add("毛坯房");
        housestatuslist.add("全拆全改");
        housestatuslist.add("局部改造");

        norentlist = new ArrayList<>();
        norentlist.add("一个月内");
        norentlist.add("1-2个月");
        norentlist.add("2个月以上");
        norentlist.add("无");

        makedealtypelist = new ArrayList<>();
        makedealtypelist.add("请选择");
        makedealtypelist.add("租");
        makedealtypelist.add("买");
        makedealtypelist.add("自建");

        housefromstatuslist = new ArrayList<>();
        housefromstatuslist.add("请选择");
        housefromstatuslist.add("已定");
        housefromstatuslist.add("未定");
        housefromstatuslist.add("订金");

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
    int housestatusm, norenttimem, makedealtypem, housefromstatusm;

    private void initShow() {
        tgvHousestatus.setTvType("现状");
        tgvHousestatus.setGvLines(4);
        tgvHousestatus.setGvData(getActivity(), housestatuslist);


        tgvNorenttime.setTvType("免租期");
        tgvNorenttime.setGvLines(4);
        tgvNorenttime.setGvData(getActivity(), norentlist);


        tgvMakedealtype.setTvType("成交类型");
        tgvMakedealtype.setGvLines(4);
        tgvMakedealtype.setGvData(getActivity(), makedealtypelist);


        tgvHousefromstatus.setTvType("状态");
        tgvHousefromstatus.setGvLines(4);
        tgvHousefromstatus.setGvData(getActivity(), housefromstatuslist);

        checkListener();
    }

    private void checkListener() {
        tgvHousestatus.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//现状
                if (housestatusm != 1) {
                    housestatusm = 1;
                }
                activity.savedatabean.setCa_HousingType(position + 1 + "");
            }
        });

        tgvNorenttime.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//免租期
                if (norenttimem != 1) {
                    norenttimem = 1;
                }
                activity.savedatabean.setCa_RentFreeDate(position + 1 + "");
            }
        });

        tgvMakedealtype.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//成交类型
                if (makedealtypem != 1) {
                    makedealtypem = 1;
                }
                String type = makedealtypelist.get(position);
                if (type.equals("租")) {
                    lyMai.setVisibility(View.GONE);
                    lyZu.setVisibility(View.VISIBLE);
                } else if (type.equals("买")) {
                    lyMai.setVisibility(View.VISIBLE);
                    lyZu.setVisibility(View.GONE);
                } else {
                    lyMai.setVisibility(View.GONE);
                    lyZu.setVisibility(View.GONE);
                }
                activity.savedatabean.setCa_TransactionType(position + 1 + "");
            }
        });

        tgvHousefromstatus.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//状态
                if (housefromstatusm != 1) {
                    housefromstatusm = 1;
                }
                activity.savedatabean.setCa_AvailabilityStatus(position + 1 + "");
            }
        });

    }

    private void ShowView(DesDaiMeasureABean.BodyBean info) {
        if (!StringUtils.isEmpty(info.getCi_Area())) {
            etNewMianji.setText(info.getCi_Area());
        }

        if (!StringUtils.isEmpty(info.getCi_BuildingArea())) {
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
            if (makedealtypelist.get(position - 1).equals("租")) {
                lyMai.setVisibility(View.GONE);
                lyZu.setVisibility(View.VISIBLE);
            } else if (makedealtypelist.get(position - 1).equals("买")) {
                lyMai.setVisibility(View.VISIBLE);
                lyZu.setVisibility(View.GONE);
            } else {
                lyMai.setVisibility(View.GONE);
                lyZu.setVisibility(View.GONE);
            }
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

    @OnClick({R.id.tv_new_jiaofangshijian, R.id.tv_new_liangfangshijian, R.id.tv_liangfangdidian, R.id.tv_liangfangquyu, R.id.tv_liangfangshangquan})
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
                        }
                    }
                });
                Picker.show();
                break;
            case R.id.tv_liangfangdidian://量房地址
                if (bGAddresslist.size() > 0) {
                    bGAddressPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tvLiangfangdidian.setText(bGAddresslist.get(options1));
                            tvLiangfangdidian.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                            lyLiangfangquyu.setVisibility(View.VISIBLE);
                            getBanGongAddressTwo(bGAddressIDlist.get(options1));
                        }
                    }).build();
                    bGAddressPV.setPicker(bGAddresslist);
                    bGAddressPV.show();
                } else {
                    ToastUtil.getInstance().toastCentent("未获取到量房地址");
                }
                break;
            case R.id.tv_liangfangquyu:
                if (quyulist.size() > 0) {
                    quYuPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tvLiangfangquyu.setText(quyulist.get(options1));
                            tvLiangfangquyu.setTextColor(getActivity().getResources().getColor(R.color.textblack));
                            getBanGongAddressShangQuan(quyuIDlist.get(options1));

                            lyLiangfangshangquan.setVisibility(View.VISIBLE);
                        }
                    }).build();
                    quYuPV.setPicker(quyulist);
                    quYuPV.show();
                } else {
                    ToastUtil.getInstance().toastCentent("未获取到区域地址");
                }
                break;
            case R.id.tv_liangfangshangquan:
                if (shangquanlist.size() > 0) {
                    shangQuanPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tvLiangfangshangquan.setText(shangquanlist.get(options1));
                            tvLiangfangshangquan.setTextColor(getActivity().getResources().getColor(R.color.textblack));
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
