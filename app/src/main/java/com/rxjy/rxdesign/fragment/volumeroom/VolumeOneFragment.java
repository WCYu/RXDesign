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
import com.rxjy.rxdesign.adapter.home.DesDaiImgAdapter;
import com.rxjy.rxdesign.custom.TextGridview;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.AllImagesInfo;
import com.rxjy.rxdesign.entity.DesDaiMeasureABean;
import com.rxjy.rxdesign.entity.NewOneInfo;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VolumeOneFragment extends BaseFragment {

    @Bind(R.id.tgv_houseface)
    TextGridview tgvHouseface;
    @Bind(R.id.tgv_wallmaterial)
    TextGridview tgvWallmaterial;
    @Bind(R.id.tgv_topmaterial)
    TextGridview tgvTopmaterial;
    @Bind(R.id.tgv_groundmaterial)
    TextGridview tgvGroundmaterial;
    @Bind(R.id.et_new_kongtiaoshebei)
    EditText etNewKongtiaoshebei;
    @Bind(R.id.et_new_qiangdianxiang)
    EditText etNewQiangdianxiang;
    @Bind(R.id.et_new_ruodianxiang)
    EditText etNewRuodianxiang;
    @Bind(R.id.et_new_shangshuidian)
    EditText etNewShangshuidian;
    @Bind(R.id.et_xiashuidian)
    EditText etXiashuidian;
    @Bind(R.id.et_new_xiashuidianchicun)
    EditText etNewXiashuidianchicun;
    @Bind(R.id.et_new_zhuliangxiagao)
    EditText etNewZhuliangxiagao;
    @Bind(R.id.et_new_ciliangxiagao)
    EditText etNewCiliangxiagao;
    @Bind(R.id.et_new_yuandingmiangao)
    EditText etNewYuandingmiangao;
    @Bind(R.id.et_new_penlinzuididian)
    EditText etNewPenlinzuididian;
    @Bind(R.id.et_new_fengkouzuididian)
    EditText etNewFengkouzuididian;
    @Bind(R.id.et_new_muqiangkuangjiajianju)
    EditText etNewMuqiangkuangjiajianju;
    @Bind(R.id.tgv_windowtype)
    TextGridview tgvWindowtype;
    @Bind(R.id.et_measure_windowsill_height)
    EditText etMeasureWindowsillHeight;
    @Bind(R.id.et_measure_windows_height)
    EditText etMeasureWindowsHeight;
    @Bind(R.id.et_measure_windowsill_width)
    EditText etMeasureWindowsillWidth;
    @Bind(R.id.tgv_occupypublic)
    TextGridview tgvOccupypublic;
    @Bind(R.id.et_measure_ca_cargo_door_hight)
    EditText etMeasureCaCargoDoorHight;
    @Bind(R.id.et_measure_ca_cargo_door_wide)
    EditText etMeasureCaCargoDoorWide;
    @Bind(R.id.tgv_groundsmooth)
    TextGridview tgvGroundsmooth;
    @Bind(R.id.et_measure_ca_grond_elevation)
    EditText etMeasureCaGrondElevation;
    @Bind(R.id.tgv_waterpath)
    TextGridview tgvWaterpath;
    @Bind(R.id.tgv_originalground)
    TextGridview tgvOriginalground;
    @Bind(R.id.et_dmGaoCha)
    EditText etDmGaoCha;
    @Bind(R.id.ly_dmGaoCha)
    LinearLayout lyDmGaoCha;
    @Bind(R.id.tgv_qdianxiang)
    TextGridview tgvQdianxiang;
    @Bind(R.id.et_qdainxiangkuan)
    EditText etQdainxiangkuan;
    @Bind(R.id.et_qdainxianggao)
    EditText etQdainxianggao;
    @Bind(R.id.ly_qdianxiang)
    LinearLayout lyQdianxiang;
    @Bind(R.id.tgv_dianxiang)
    TextGridview tgvDianxiang;
    @Bind(R.id.et_rdainxiangkuan)
    EditText etRdainxiangkuan;
    @Bind(R.id.et_rdainxianggao)
    EditText etRdainxianggao;
    @Bind(R.id.ly_rdianxiang)
    LinearLayout lyRdianxiang;
    @Bind(R.id.et_diaodingzuididian)
    EditText etDiaodingzuididian;
    @Bind(R.id.tv_gwjiegou)
    TextView tvGwjiegou;
    @Bind(R.id.tgv_shangshui)
    TextGridview tgvShangshui;
    @Bind(R.id.tgv_xiashui)
    TextGridview tgvXiashui;
    @Bind(R.id.et_ciliangxiagao)
    EditText etCiliangxiagao;

    private int caCaHousingSituation = -1;                //房屋现状
    private String caHouseOrientation = "";                 //房屋朝向..
    private int caOccupyPublicCorridor = -1;             //占用公共过道
    private double caCargoDoorHight = -1;                   //货梯们高度
    private double caCargoDoorWide = -1;                    //货梯们宽度
    private int caOriginalGround = -1;                    //原地面保存   1是
    private String caIsGroundSmooth = "";                    //原地面平整
    private String caOriginalGroundMaterial = "";           //原地面材质
    private String caOriginalWallMaterial = "";             //原墙面材质
    private String caOriginalTopMaterial = "";              //原顶面材质
    private double caGroundElevation = -1;                  //地面高度差
    private double caSpaceMaxHeight = -1;                   //空间最高点
    private double caSpaceMinHeight = -1;                   //空间最低点
    private double camainBeamHeight = -1;                   //主梁下
    private double caSecondaryHeight = -1;                  //次梁下
    private double caMinimumSprayHeight = -1;               //喷淋最低点
    private int caAirConditionerNum = -1;                   //空调设备数
    private double caTuyereMinimumHeight = -1;             //风口最低点
    private int caStrongBoxNum = -1;                        //强电箱个数
    private int caWeakBoxNum = -1;                          //弱电箱个数
    private String caWaterPath = "";                        //上下水路径
    private int caUpWaterSpot = -1;                      //上水电
    private int caDownWaterSpot = -1;                    //下水点
    private double caDownWaterSpotSize = -1;                 //下水管径
    private double caCurtainWallSpacing = -1;                //幕墙架间距
    private String caWindowType = "";                          //窗户类型
    private double caWindowSillHight = -1;                    //窗台高
    private double caWindowWidth = -1;                          //窗户宽
    private double caWindowHight = -1;                          //窗户高

    //new
    private int kongtiaoshebei;
    private int qiangdianxiang;
    private int ruodianxiang;
    private int shangshuidian;
    private int xiashuidian;
    private double xiashuidianchicun;
    private double zhuliangxiagao;
    private double ciliangxiagao;
    private double yuandingmiangao;
    private double penlinzuididian;
    private double fengkouzuididian;
    private double muqiangkuangjiajianju;

    int itemmoney = 10;
    int moneynum;//当前金额对应的个数

    //private MatDao md;
    private boolean hasrwdID;

    private List<AllImagesInfo.Album> albumList;

    private DesDaiImgAdapter mAdapter;

    private NewOneInfo.BodyBean Bodyinfo;

    /**
     * 要添加的数据的赋值
     */
    private ArrayList<String> housefacelist;//房屋朝向
    private ArrayList<String> wallmaterialist;//原墙面材质
    private ArrayList<String> topmaterialist;//原顶面材质
    private ArrayList<String> groundmaterialist;//原地面材质
    private ArrayList<String> windowtypeialist;//窗户类型
    private ArrayList<String> yesnolist;//占用公共过道、原地面平整、原地面保存
    private ArrayList<String> waterpathlist;//上下水路径
    private ArrayList<String> youwulist;//上下水路径
    private ArrayList<String> jiegoulist;//上下水路径

    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;
    private DesDaiMeasureActivity activity;
    private OptionsPickerView jiegouPV;

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
        return R.layout.fragment_volume_one;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lhousedata != null) {
            ShowView(lhousedata);
        } else {
            DesDaiMeasureActivity desDaiMeasureActivity = (DesDaiMeasureActivity) getActivity();
            if (desDaiMeasureActivity.savedatabean != null) {
                Log.e("tag_获取数据", "正常");
                ShowView(desDaiMeasureActivity.savedatabean);
            }
        }
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        activity = (DesDaiMeasureActivity) getActivity();
        initAddData();
        initPhotoData();
    }

    private void initPhotoData() {
        albumList = new ArrayList<>();
        mAdapter = new DesDaiImgAdapter(getActivity(), albumList);
    }

    private void initAddData() {
        housefacelist = new ArrayList<>();
        housefacelist.add("东");
        housefacelist.add("西");
        housefacelist.add("南");
        housefacelist.add("北");

        wallmaterialist = new ArrayList<>();
        wallmaterialist.add("水泥");
        wallmaterialist.add("刮大白");
        wallmaterialist.add("乳胶漆");
        wallmaterialist.add("墙纸");

        topmaterialist = new ArrayList<>();
        topmaterialist.add("水泥毛坯");
        topmaterialist.add("矿棉板吊顶");
        topmaterialist.add("石膏板吊顶");
        topmaterialist.add("铝方通/铝扣/格栅");
        topmaterialist.add("裸顶喷漆");

        groundmaterialist = new ArrayList<>();
        groundmaterialist.add("水泥毛坯");
        groundmaterialist.add("网格地板");
        groundmaterialist.add("地毯");
        groundmaterialist.add("地砖");
        groundmaterialist.add("木地板");
        groundmaterialist.add("塑胶地板");

        windowtypeialist = new ArrayList<>();
        windowtypeialist.add("玻璃幕墙");
        windowtypeialist.add("飘窗");
        windowtypeialist.add("普通窗");
        windowtypeialist.add("落地窗");

        yesnolist = new ArrayList<>();
        yesnolist.add("是");
        yesnolist.add("否");

        waterpathlist = new ArrayList<>();
        waterpathlist.add("板底");
        waterpathlist.add("板顶");

        youwulist = new ArrayList<>();
        youwulist.add("有");
        youwulist.add("无");

        jiegoulist = new ArrayList<>();
        jiegoulist.add("请选择");
        jiegoulist.add("钢结构");
        jiegoulist.add("砖结构");
        jiegoulist.add("钢筋混泥土框架结构");

        initShow();
    }

    int housefacem, wallmaterialm, topmaterialm, groundmaterialm, windowtypem, occupypublicm, groundsmoothm, waterpathm, originalgroundm;//代表还没有赋值

    /**
     * 展示添加量房信息的源数据
     */
    private void initShow() {


        tgvGroundsmooth.setTvType("原地面平整");
        tgvGroundsmooth.setGvLines(4);
        tgvGroundsmooth.setGvData(getActivity(), yesnolist);

        tgvGroundmaterial.setTvType("原地面材质");
        tgvGroundmaterial.setGvLines(4);
        tgvGroundmaterial.setGvData(getActivity(), groundmaterialist);

        tgvTopmaterial.setTvType("原顶面材质");
        tgvTopmaterial.setGvLines(2);
        tgvTopmaterial.setGvData(getActivity(), topmaterialist);

        tgvHouseface.setTvType("入户门朝向");
        tgvHouseface.setGvLines(4);
        tgvHouseface.setGvData(getActivity(), housefacelist);

        tgvShangshui.setTvType("上水点");
        tgvShangshui.setGvLines(4);
        tgvShangshui.setGvData(getActivity(), youwulist);

        tgvXiashui.setTvType("下水点");
        tgvXiashui.setGvLines(4);
        tgvXiashui.setGvData(getActivity(), youwulist);

        tgvWallmaterial.setTvType("原墙面材质");
        tgvWallmaterial.setGvLines(4);
        tgvWallmaterial.setGvData(getActivity(), wallmaterialist);

        tgvQdianxiang.setTvType("强电箱");
        tgvQdianxiang.setGvLines(4);
        tgvQdianxiang.setGvData(getActivity(), youwulist);

        tgvDianxiang.setTvType("弱电箱");
        tgvDianxiang.setGvLines(4);
        tgvDianxiang.setGvData(getActivity(), youwulist);

        tgvWindowtype.setTvType("窗户类型");
        tgvWindowtype.setGvLines(4);
        tgvWindowtype.setGvData(getActivity(), windowtypeialist);

        tgvOccupypublic.setTvType("占用公共过道");//1是  2否
        tgvOccupypublic.setGvLines(4);
        tgvOccupypublic.setGvData(getActivity(), yesnolist);

        tgvWaterpath.setTvType("上下水路径");
        tgvWaterpath.setGvLines(4);
        tgvWaterpath.setGvData(getActivity(), waterpathlist);

        tgvOriginalground.setTvType("原地面保存");
        tgvOriginalground.setGvLines(4);
        tgvOriginalground.setGvData(getActivity(), yesnolist);

        checkListener();
    }

    private void checkListener() {

        tgvGroundsmooth.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//原地面平整
                if (groundsmoothm != 1) {
                    groundsmoothm = 1;
                }
                if (yesnolist.get(position).equals("是")) {
                    lyDmGaoCha.setVisibility(View.GONE);
                } else {
                    lyDmGaoCha.setVisibility(View.VISIBLE);
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_isGroundSmooth(yesnolist.get(position));
                }
            }
        });

        tgvHouseface.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//房屋朝向
                if (housefacem != 1) {
                    housefacem = 1;
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_HouseOrientation(housefacelist.get(position));
                }
            }
        });

        tgvXiashui.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//下水点

            }
        });

        tgvShangshui.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//上水点

            }
        });

        tgvWallmaterial.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//原墙面材质
                if (wallmaterialm != 1) {
                    wallmaterialm = 1;
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_OriginalWallMaterial(wallmaterialist.get(position));
                }
            }
        });

        tgvTopmaterial.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//原顶面材质
                if (topmaterialm != 1) {
                    topmaterialm = 1;

                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_OriginalTopMaterial(topmaterialist.get(position));
                }
            }
        });

        tgvQdianxiang.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//强电箱
                if (youwulist.get(position).equals("有")) {
                    lyQdianxiang.setVisibility(View.VISIBLE);
                } else {
                    lyQdianxiang.setVisibility(View.GONE);
                }
            }
        });

        tgvDianxiang.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//弱电箱
                if (youwulist.get(position).equals("有")) {
                    lyRdianxiang.setVisibility(View.VISIBLE);
                } else {
                    lyRdianxiang.setVisibility(View.GONE);
                }
            }
        });

        tgvGroundmaterial.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//原地面材质
                if (groundmaterialm != 1) {
                    groundmaterialm = 1;
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_OriginalGroundMaterial(groundmaterialist.get(position));
                }
            }
        });

        tgvWindowtype.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//窗户类型
                if (windowtypem != 1) {
                    windowtypem = 1;
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_WindowType(windowtypeialist.get(position));
                }
            }
        });

        tgvOccupypublic.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//占用公共过道
                if (occupypublicm != 1) {
                    occupypublicm = 1;
                }
                switch (position) {
                    case 0:
                        if (activity.savedatabean != null) {
                            activity.savedatabean.setCa_OccupyPublicCorridor("1");
                        }
                        break;
                    case 1:
                        if (activity.savedatabean != null) {
                            activity.savedatabean.setCa_OccupyPublicCorridor("2");
                        }
                        break;
                }
            }
        });

        tgvWaterpath.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//上下水路径
                if (waterpathm != 1) {
                    waterpathm = 1;
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_WaterPath(waterpathlist.get(position));
                }
            }
        });

        tgvOriginalground.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//原地面保存
                if (originalgroundm != 1) {
                    originalgroundm = 1;
                }
                switch (position) {
                    case 0:
                        if (activity.savedatabean != null) {
                            activity.savedatabean.setCa_OriginalGround("1");
                        }
                        break;
                    case 1:
                        if (activity.savedatabean != null) {
                            activity.savedatabean.setCa_OriginalGround("2");
                        }
                        break;
                }
            }
        });

    }

    private void ShowView(DesDaiMeasureABean.BodyBean bean) {
        moneynum = activity.moneynum;
        //展示
        if (!StringUtils.isEmpty(bean.getCa_HouseOrientation())) {
            housefacem = 1;
            for (int i = 0; i < housefacelist.size(); i++) {
                if (housefacelist.get(i).equals(bean.getCa_HouseOrientation())) {
                    tgvHouseface.setContents(bean.getCa_HouseOrientation(), i);
                    break;
                }
            }
        }
        if (!StringUtils.isEmpty(bean.getCa_OccupyPublicCorridor())) {
            occupypublicm = 1;
            switch (bean.getCa_OccupyPublicCorridor()) {
                case "1":
                    tgvOccupypublic.setContents("是", 0);
                    break;
                case "2":
                    tgvOccupypublic.setContents("否", 1);
                    break;
            }
        }
        if (!StringUtils.isEmpty(bean.getCa_OriginalGround())) {
            originalgroundm = 1;
            switch (bean.getCa_OriginalGround()) {
                case "1":
                    tgvOriginalground.setContents("是", 0);
                    break;
                case "2":
                    tgvOriginalground.setContents("否", 1);
                    break;
            }
        }
        if (!StringUtils.isEmpty(bean.getCa_isGroundSmooth())) {
            groundsmoothm = 1;
            for (int i = 0; i < yesnolist.size(); i++) {
                if (yesnolist.get(i).equals(bean.getCa_isGroundSmooth())) {
                    tgvGroundsmooth.setContents(bean.getCa_isGroundSmooth(), i);
                    break;
                }
            }
        }
        if (!StringUtils.isEmpty(bean.getCa_OriginalGroundMaterial())) {
            groundmaterialm = 1;
            for (int i = 0; i < groundmaterialist.size(); i++) {
                if (groundmaterialist.get(i).equals(bean.getCa_OriginalGroundMaterial())) {
                    tgvGroundmaterial.setContents(bean.getCa_OriginalGroundMaterial(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(bean.getCa_OriginalWallMaterial())) {
            wallmaterialm = 1;
            for (int i = 0; i < wallmaterialist.size(); i++) {
                if (wallmaterialist.get(i).equals(bean.getCa_OriginalWallMaterial())) {
                    tgvWallmaterial.setContents(bean.getCa_OriginalWallMaterial(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(bean.getCa_OriginalTopMaterial())) {
            topmaterialm = 1;
            for (int i = 0; i < topmaterialist.size(); i++) {
                if (topmaterialist.get(i).equals(bean.getCa_OriginalTopMaterial())) {
                    tgvTopmaterial.setContents(bean.getCa_OriginalTopMaterial(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(bean.getCa_WaterPath())) {
            waterpathm = 1;
            for (int i = 0; i < waterpathlist.size(); i++) {
                if (waterpathlist.get(i).equals(bean.getCa_WaterPath())) {
                    tgvWaterpath.setContents(bean.getCa_WaterPath(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(bean.getCa_WindowType())) {
            windowtypem = 1;
            for (int i = 0; i < windowtypeialist.size(); i++) {
                if (windowtypeialist.get(i).equals(bean.getCa_WindowType())) {
                    tgvWindowtype.setContents(bean.getCa_WindowType(), i);
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(bean.getCa_CargoDoorHight())) {
            caCargoDoorHight = Double.parseDouble(bean.getCa_CargoDoorHight());                   //货梯们高度
            etMeasureCaCargoDoorHight.setText(caCargoDoorHight + "");
        }
        if (!StringUtils.isEmpty(bean.getCa_CargoDoorWide())) {
            caCargoDoorWide = Double.parseDouble(bean.getCa_CargoDoorWide());                    //货梯们宽度
            etMeasureCaCargoDoorWide.setText(caCargoDoorWide + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_GroundElevation())) {
            caGroundElevation = Double.parseDouble(bean.getCa_GroundElevation());                  //地面高度差
            etMeasureCaGrondElevation.setText(caGroundElevation + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_SpaceMaxHeight())) {
            caSpaceMaxHeight = Double.parseDouble(bean.getCa_SpaceMaxHeight());                   //空间最高点
            etNewYuandingmiangao.setText(caSpaceMaxHeight + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_mainBeamHeight())) {
            camainBeamHeight = Double.parseDouble(bean.getCa_mainBeamHeight());                   //主梁下
            etNewZhuliangxiagao.setText(camainBeamHeight + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_SecondaryHeight())) {
            caSecondaryHeight = Double.parseDouble(bean.getCa_SecondaryHeight());                  //次梁下
            etNewCiliangxiagao.setText(caSecondaryHeight + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_MinimumSprayHeight())) {
            caMinimumSprayHeight = Double.parseDouble(bean.getCa_MinimumSprayHeight());               //喷淋最低点
            etNewPenlinzuididian.setText(caMinimumSprayHeight + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_AirConditionerNum())) {
            caAirConditionerNum = Integer.parseInt(bean.getCa_AirConditionerNum());                   //空调设备数
            etNewKongtiaoshebei.setText(caAirConditionerNum + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_TuyereMinimumHeight())) {
            caTuyereMinimumHeight = Double.parseDouble(bean.getCa_TuyereMinimumHeight());             //风口最低点
            etNewFengkouzuididian.setText(caTuyereMinimumHeight + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_StrongBoxNum())) {
            caStrongBoxNum = Integer.parseInt(bean.getCa_StrongBoxNum());                        //强电箱个数
            etNewQiangdianxiang.setText(caStrongBoxNum + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_WeakBoxNum())) {
            caWeakBoxNum = Integer.parseInt(bean.getCa_WeakBoxNum());                          //弱电箱个数
            etNewRuodianxiang.setText(caWeakBoxNum + "");
        }

//        caWaterPath = info.getCa_WaterPath();                        //上下水路径
        if (!StringUtils.isEmpty(bean.getCa_UpWaterSpot())) {
            caUpWaterSpot = Integer.parseInt(bean.getCa_UpWaterSpot());                      //上水电
            etNewShangshuidian.setText(caUpWaterSpot + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_DownWaterSpot())) {
            caDownWaterSpot = Integer.parseInt(bean.getCa_DownWaterSpot());                    //下水点
            etXiashuidian.setText(caDownWaterSpot + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_DownWaterSpotSize())) {
            caDownWaterSpotSize = Double.parseDouble(bean.getCa_DownWaterSpotSize());                 //下水管径
            etNewXiashuidianchicun.setText(caDownWaterSpotSize + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_CurtainWallSpacing())) {
            caCurtainWallSpacing = Double.parseDouble(bean.getCa_CurtainWallSpacing());                //幕墙架间距
            etNewMuqiangkuangjiajianju.setText(caCurtainWallSpacing + "");
        }
//        caWindowType = info.getCa_WindowType();                          //窗户类型
        if (!StringUtils.isEmpty(bean.getCa_windowsillHight())) {
            caWindowSillHight = Double.parseDouble(bean.getCa_windowsillHight());                    //窗台高
            etMeasureWindowsillHeight.setText(caWindowSillHight + "");
        }
        if (!StringUtils.isEmpty(bean.getCa_WindowWidth())) {
            caWindowWidth = Double.parseDouble(bean.getCa_WindowWidth());                          //窗户宽
            etMeasureWindowsillWidth.setText(caWindowWidth + "");
        }
        if (!StringUtils.isEmpty(bean.getCa_WindowHight())) {
            caWindowHight = Double.parseDouble(bean.getCa_WindowHight());                          //窗户高
            etMeasureWindowsHeight.setText(caWindowHight + "");
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

    @OnClick(R.id.tv_gwjiegou)
    public void onViewClicked() {
        jiegouPV = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvGwjiegou.setText(jiegoulist.get(options1));
                tvGwjiegou.setTextColor(getActivity().getResources().getColor(R.color.textblack));
            }
        }).build();
        jiegouPV.setPicker(jiegoulist);
        jiegouPV.show();
    }
}
