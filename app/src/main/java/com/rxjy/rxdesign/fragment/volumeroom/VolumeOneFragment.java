package com.rxjy.rxdesign.fragment.volumeroom;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.home.DesDaiMeasureActivity;
import com.rxjy.rxdesign.adapter.home.DesDaiImgAdapter;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.TextGridview;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.AllImagesInfo;
import com.rxjy.rxdesign.entity.DesDaiMeasureABean;
import com.rxjy.rxdesign.entity.NewOneInfo;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.StringUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rxjy.rxdesign.utils.Constants.LF_NUM;

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

    int itemmoney = 10;//单项数据的钱（假设为10）、、、、、、、、、、、、、、、、、、、、
    int moneynum;//当前金额对应的个数

    //private MatDao md;
    private boolean hasrwdID;

    private List<AllImagesInfo.Album> albumList;

    private DesDaiImgAdapter mAdapter;

    private NewOneInfo.BodyBean Bodyinfo;

    /**
     * 要添加的数据的赋值
     */
    private List<String> housefacelist;//房屋朝向
    private List<String> wallmaterialist;//原墙面材质
    private List<String> topmaterialist;//原顶面材质
    private List<String> groundmaterialist;//原地面材质
    private List<String> windowtypeialist;//窗户类型
    private List<String> yesnolist;//占用公共过道、原地面平整、原地面保存
    private List<String> waterpathlist;//上下水路径

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
//        etNewKongtiaoshebei.addTextChangedListener(new MyEditListener(etNewKongtiaoshebei));
//        etNewQiangdianxiang.addTextChangedListener(new MyEditListener(etNewQiangdianxiang));
//        etNewRuodianxiang.addTextChangedListener(new MyEditListener(etNewRuodianxiang));
//        etNewShangshuidian.addTextChangedListener(new MyEditListener(etNewShangshuidian));
//        etXiashuidian.addTextChangedListener(new MyEditListener(etXiashuidian));
//        etNewXiashuidianchicun.addTextChangedListener(new MyEditListener(etNewXiashuidianchicun));
//        etNewZhuliangxiagao.addTextChangedListener(new MyEditListener(etNewZhuliangxiagao));
//        etNewCiliangxiagao.addTextChangedListener(new MyEditListener(etNewCiliangxiagao));
//        etNewYuandingmiangao.addTextChangedListener(new MyEditListener(etNewYuandingmiangao));
//        etNewPenlinzuididian.addTextChangedListener(new MyEditListener(etNewPenlinzuididian));
//        etNewFengkouzuididian.addTextChangedListener(new MyEditListener(etNewFengkouzuididian));
//        etNewMuqiangkuangjiajianju.addTextChangedListener(new MyEditListener(etNewMuqiangkuangjiajianju));
//        etMeasureCaCargoDoorHight.addTextChangedListener(new MyEditListener(etMeasureCaCargoDoorHight));
//        etMeasureCaCargoDoorWide.addTextChangedListener(new MyEditListener(etMeasureCaCargoDoorWide));
//        etMeasureCaGrondElevation.addTextChangedListener(new MyEditListener(etMeasureCaGrondElevation));
//        etMeasureWindowsillHeight.addTextChangedListener(new MyEditListener(etMeasureWindowsillHeight));
//        etMeasureWindowsillWidth.addTextChangedListener(new MyEditListener(etMeasureWindowsillWidth));
//        etMeasureWindowsHeight.addTextChangedListener(new MyEditListener(etMeasureWindowsHeight));
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

        initShow();
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
            ca_cargo_door_hightm = 1;
//            Log.e("getCa_CargoDoorHight：,,,,",bean.getCa_CargoDoorHight());
            caCargoDoorHight = Double.parseDouble(bean.getCa_CargoDoorHight());                   //货梯们高度
            etMeasureCaCargoDoorHight.setText(caCargoDoorHight + "");
        }
        if (!StringUtils.isEmpty(bean.getCa_CargoDoorWide())) {
            ca_cargo_door_widem = 1;
            caCargoDoorWide = Double.parseDouble(bean.getCa_CargoDoorWide());                    //货梯们宽度
            etMeasureCaCargoDoorWide.setText(caCargoDoorWide + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_GroundElevation())) {
            ca_grond_elevationm = 1;
            caGroundElevation = Double.parseDouble(bean.getCa_GroundElevation());                  //地面高度差
            etMeasureCaGrondElevation.setText(caGroundElevation + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_SpaceMaxHeight())) {
            yuandingmiangaom = 1;
            caSpaceMaxHeight = Double.parseDouble(bean.getCa_SpaceMaxHeight());                   //空间最高点
            etNewYuandingmiangao.setText(caSpaceMaxHeight + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_mainBeamHeight())) {
            zhuliangxiagaom = 1;
            camainBeamHeight = Double.parseDouble(bean.getCa_mainBeamHeight());                   //主梁下
            etNewZhuliangxiagao.setText(camainBeamHeight + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_SecondaryHeight())) {
            ciliangxiagaom = 1;
            caSecondaryHeight = Double.parseDouble(bean.getCa_SecondaryHeight());                  //次梁下
            etNewCiliangxiagao.setText(caSecondaryHeight + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_MinimumSprayHeight())) {
            penlinzuididianm = 1;
            caMinimumSprayHeight = Double.parseDouble(bean.getCa_MinimumSprayHeight());               //喷淋最低点
            etNewPenlinzuididian.setText(caMinimumSprayHeight + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_AirConditionerNum())) {
            kongtiaoshebeim = 1;
            caAirConditionerNum = Integer.parseInt(bean.getCa_AirConditionerNum());                   //空调设备数
            etNewKongtiaoshebei.setText(caAirConditionerNum + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_TuyereMinimumHeight())) {
            fengkouzuididianm = 1;
            caTuyereMinimumHeight = Double.parseDouble(bean.getCa_TuyereMinimumHeight());             //风口最低点
            etNewFengkouzuididian.setText(caTuyereMinimumHeight + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_StrongBoxNum())) {
            qiangdianxiangm = 1;
            caStrongBoxNum = Integer.parseInt(bean.getCa_StrongBoxNum());                        //强电箱个数
            etNewQiangdianxiang.setText(caStrongBoxNum + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_WeakBoxNum())) {
            ruodianxiangm = 1;
            caWeakBoxNum = Integer.parseInt(bean.getCa_WeakBoxNum());                          //弱电箱个数
            etNewRuodianxiang.setText(caWeakBoxNum + "");
        }

//        caWaterPath = info.getCa_WaterPath();                        //上下水路径
        if (!StringUtils.isEmpty(bean.getCa_UpWaterSpot())) {
            shangshuidianm = 1;
            caUpWaterSpot = Integer.parseInt(bean.getCa_UpWaterSpot());                      //上水电
            etNewShangshuidian.setText(caUpWaterSpot + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_DownWaterSpot())) {
            xiashuidianm = 1;
            caDownWaterSpot = Integer.parseInt(bean.getCa_DownWaterSpot());                    //下水点
            etXiashuidian.setText(caDownWaterSpot + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_DownWaterSpotSize())) {
            xiashuidianchicunm = 1;
            caDownWaterSpotSize = Double.parseDouble(bean.getCa_DownWaterSpotSize());                 //下水管径
            etNewXiashuidianchicun.setText(caDownWaterSpotSize + "");
        }

        if (!StringUtils.isEmpty(bean.getCa_CurtainWallSpacing())) {
            muqiangkuangjiajianjum = 1;
            caCurtainWallSpacing = Double.parseDouble(bean.getCa_CurtainWallSpacing());                //幕墙架间距
            etNewMuqiangkuangjiajianju.setText(caCurtainWallSpacing + "");
        }
//        caWindowType = info.getCa_WindowType();                          //窗户类型
        if (!StringUtils.isEmpty(bean.getCa_windowsillHight())) {
            windowsill_heightm = 1;
            caWindowSillHight = Double.parseDouble(bean.getCa_windowsillHight());                    //窗台高
            etMeasureWindowsillHeight.setText(caWindowSillHight + "");
        }
        if (!StringUtils.isEmpty(bean.getCa_WindowWidth())) {
            windowsill_widthm = 1;
            caWindowWidth = Double.parseDouble(bean.getCa_WindowWidth());                          //窗户宽
            etMeasureWindowsillWidth.setText(caWindowWidth + "");
        }
        if (!StringUtils.isEmpty(bean.getCa_WindowHight())) {
            windows_heightm = 1;
            caWindowHight = Double.parseDouble(bean.getCa_WindowHight());                          //窗户高
            etMeasureWindowsHeight.setText(caWindowHight + "");
        }
    }

    int housefacem, wallmaterialm, topmaterialm, groundmaterialm, windowtypem, occupypublicm, groundsmoothm, waterpathm, originalgroundm;//代表还没有赋值

    /**
     * 展示添加量房信息的源数据
     */
    private void initShow() {
        tgvHouseface.setTvType("房屋朝向");
        tgvHouseface.setGvLines(4);
        tgvHouseface.setGvData(getActivity(), housefacelist);
        tgvHouseface.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {
                if (housefacem != 1) {
                    housefacem = 1;
                    addMoney();
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_HouseOrientation(housefacelist.get(position));
                }
            }
        });

        tgvWallmaterial.setTvType("原墙面材质");
        tgvWallmaterial.setGvLines(4);
        tgvWallmaterial.setGvData(getActivity(), wallmaterialist);
        tgvWallmaterial.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//
                if (wallmaterialm != 1) {
                    wallmaterialm = 1;
                    addMoney();
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_OriginalWallMaterial(wallmaterialist.get(position));
                }
            }
        });

        tgvTopmaterial.setTvType("原顶面材质");
        tgvTopmaterial.setGvLines(2);
        tgvTopmaterial.setGvData(getActivity(), topmaterialist);
        tgvTopmaterial.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//
                if (topmaterialm != 1) {
                    topmaterialm = 1;
                    addMoney();
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_OriginalTopMaterial(topmaterialist.get(position));
                }
            }
        });

        tgvGroundmaterial.setTvType("原地面材质");
        tgvGroundmaterial.setGvLines(4);
        tgvGroundmaterial.setGvData(getActivity(), groundmaterialist);
        tgvGroundmaterial.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//
                if (groundmaterialm != 1) {
                    groundmaterialm = 1;
                    addMoney();
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_OriginalGroundMaterial(groundmaterialist.get(position));
                }
            }
        });

        tgvWindowtype.setTvType("窗户类型");
        tgvWindowtype.setGvLines(4);
        tgvWindowtype.setGvData(getActivity(), windowtypeialist);
        tgvWindowtype.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//
                if (windowtypem != 1) {
                    windowtypem = 1;
                    addMoney();
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_WindowType(windowtypeialist.get(position));
                }
            }
        });

        tgvOccupypublic.setTvType("占用公共过道");//1是  2否
        tgvOccupypublic.setGvLines(4);
        tgvOccupypublic.setGvData(getActivity(), yesnolist);
        tgvOccupypublic.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//
                if (occupypublicm != 1) {
                    occupypublicm = 1;
                    addMoney();
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

        tgvGroundsmooth.setTvType("原地面平整");
        tgvGroundsmooth.setGvLines(4);
        tgvGroundsmooth.setGvData(getActivity(), yesnolist);
        tgvGroundsmooth.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//
                if (groundsmoothm != 1) {
                    groundsmoothm = 1;
                    addMoney();
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_isGroundSmooth(yesnolist.get(position));
                }
            }
        });

        tgvWaterpath.setTvType("上下水路径");
        tgvWaterpath.setGvLines(4);
        tgvWaterpath.setGvData(getActivity(), waterpathlist);
        tgvWaterpath.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//
                if (waterpathm != 1) {
                    waterpathm = 1;
                    addMoney();
                }
                if (activity.savedatabean != null) {
                    activity.savedatabean.setCa_WaterPath(waterpathlist.get(position));
                }
            }
        });

        tgvOriginalground.setTvType("原地面保存");
        tgvOriginalground.setGvLines(4);
        tgvOriginalground.setGvData(getActivity(), yesnolist);
        tgvOriginalground.tochoose(new TextGridview.Choose() {
            @Override
            public void tochoose(int position) {//
                if (originalgroundm != 1) {
                    originalgroundm = 1;
                    addMoney();
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

    @Override
    public void initAdapter() {

    }

    @Override
    public void initLinstener() {

    }

//    @OnClick({R.id.btn_des_dai_measure_sub})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_des_dai_measure_sub:
//                caCargoDoorWide = Double.parseDouble(etMeasureCaCargoDoorWide.getText().toString().trim().equals("") ? -1 + "" : etMeasureCaCargoDoorWide.getText().toString().trim());
//                Bodyinfo.setCa_CargoDoorWide(caCargoDoorWide + "");
//                kongtiaoshebei = Integer.parseInt(etNewKongtiaoshebei.getText().toString().trim().equals("") ? -1 + "" : etNewKongtiaoshebei.getText().toString().trim());
//                Bodyinfo.setCa_AirConditionerNum(kongtiaoshebei + "");
//                qiangdianxiang = Integer.parseInt(etNewQiangdianxiang.getText().toString().trim().equals("") ? -1 + "" : etNewQiangdianxiang.getText().toString().trim());
//                Bodyinfo.setCa_StrongBoxNum(qiangdianxiang + "");
//                ruodianxiang = Integer.parseInt(etNewRuodianxiang.getText().toString().trim().equals("") ? -1 + "" : etNewRuodianxiang.getText().toString().trim());
//                Bodyinfo.setCa_WeakBoxNum(ruodianxiang + "");
//                shangshuidian = Integer.parseInt(etNewShangshuidian.getText().toString().trim().equals("") ? -1 + "" : etNewShangshuidian.getText().toString().trim());
//                Bodyinfo.setCa_UpWaterSpot(shangshuidian + "");
//                xiashuidian = Integer.parseInt(etXiashuidian.getText().toString().trim().equals("") ? -1 + "" : etXiashuidian.getText().toString().trim());
//                Bodyinfo.setCa_DownWaterSpot(xiashuidian + "");
//                xiashuidianchicun = Double.parseDouble(etNewXiashuidianchicun.getText().toString().trim().equals("") ? -1 + "" : etNewXiashuidianchicun.getText().toString().trim());
//                Bodyinfo.setCa_DownWaterSpotSize(xiashuidianchicun + "");
//                zhuliangxiagao = Double.parseDouble(etNewZhuliangxiagao.getText().toString().trim().equals("") ? -1 + "" : etNewZhuliangxiagao.getText().toString().trim());
//                Bodyinfo.setCa_mainBeamHeight(zhuliangxiagao + "");
//                ciliangxiagao = Double.parseDouble(etNewCiliangxiagao.getText().toString().trim().equals("") ? -1 + "" : etNewCiliangxiagao.getText().toString().trim());
//                Bodyinfo.setCa_SecondaryHeight(ciliangxiagao + "");
//                yuandingmiangao = Double.parseDouble(etNewYuandingmiangao.getText().toString().trim().equals("") ? -1 + "" : etNewYuandingmiangao.getText().toString().trim());
//                Bodyinfo.setCa_SpaceMaxHeight(yuandingmiangao + "");
//                penlinzuididian = Double.parseDouble(etNewPenlinzuididian.getText().toString().trim().equals("") ? -1 + "" : etNewPenlinzuididian.getText().toString().trim());
//                Bodyinfo.setCa_MinimumSprayHeight(penlinzuididian + "");
//                fengkouzuididian = Double.parseDouble(etNewFengkouzuididian.getText().toString().trim().equals("") ? -1 + "" : etNewFengkouzuididian.getText().toString().trim());
//                Bodyinfo.setCa_TuyereMinimumHeight(fengkouzuididian + "");
//                muqiangkuangjiajianju = Double.parseDouble(etNewMuqiangkuangjiajianju.getText().toString().trim().equals("") ? -1 + "" : etNewMuqiangkuangjiajianju.getText().toString().trim());
//                Bodyinfo.setCa_CurtainWallSpacing(muqiangkuangjiajianju + "");
//                caCargoDoorHight = Double.parseDouble(etMeasureCaCargoDoorHight.getText().toString().trim().equals("") ? -1 + "" : etMeasureCaCargoDoorHight.getText().toString().trim());
//                Bodyinfo.setCa_CargoDoorHight(caCargoDoorHight + "");
//                caGroundElevation = Double.parseDouble(etMeasureCaGrondElevation.getText().toString().trim().equals("") ? -1 + "" : etMeasureCaGrondElevation.getText().toString().trim());
//                Bodyinfo.setCa_GroundElevation(caGroundElevation + "");
//                caWindowSillHight = Double.parseDouble(etMeasureWindowsillHeight.getText().toString().trim().equals("") ? -1 + "" : etMeasureWindowsillHeight.getText().toString().trim());
//                Bodyinfo.setCa_windowsillHight(caWindowSillHight + "");
//                caWindowWidth = Double.parseDouble(etMeasureWindowsillWidth.getText().toString().trim().equals("") ? -1 + "" : etMeasureWindowsillWidth.getText().toString().trim());
//                Bodyinfo.setCa_WindowWidth(caWindowWidth + "");
//                caWindowHight = Double.parseDouble(etMeasureWindowsHeight.getText().toString().trim().equals("") ? -1 + "" : etMeasureWindowsHeight.getText().toString().trim());
//                Bodyinfo.setCa_WindowHight(caWindowHight + "");
//
//                caHouseOrientation = tgvHouseface.getSelectcontent();
//                Bodyinfo.setCa_HouseOrientation(caHouseOrientation);//房屋朝向
//                String isoccupypublic = tgvOccupypublic.getSelectcontent();
//                switch (isoccupypublic) {
//                    case "是":
//                        caOccupyPublicCorridor = 1;
//                        break;
//                    case "否":
//                        caOccupyPublicCorridor = 2;
//                        break;
//                }
//                Bodyinfo.setCa_OccupyPublicCorridor(caOccupyPublicCorridor + "");//占用公共过道 1是2否
//                caOriginalGroundMaterial = tgvGroundmaterial.getSelectcontent();
//                Bodyinfo.setCa_OriginalGroundMaterial(caOriginalGroundMaterial);//原地面材质
//                caIsGroundSmooth = tgvGroundsmooth.getSelectcontent();
//                Bodyinfo.setCa_isGroundSmooth(caIsGroundSmooth + "");//原地面平整
//                caOriginalTopMaterial = tgvTopmaterial.getSelectcontent();
//                Bodyinfo.setCa_OriginalTopMaterial(caOriginalTopMaterial + "");//原顶面材质
//                caOriginalWallMaterial = tgvWallmaterial.getSelectcontent();
//                Bodyinfo.setCa_OriginalWallMaterial(caOriginalWallMaterial + "");//原墙面材质
//                caWindowType = tgvWindowtype.getSelectcontent();
//                Bodyinfo.setCa_WindowType(caWindowType + "");//窗户类型
//                caWaterPath = tgvWaterpath.getSelectcontent();
//                Bodyinfo.setCa_WaterPath(caWaterPath + "");//上下水路径
//                String groundsave = tgvOriginalground.getSelectcontent();
//                switch (groundsave) {
//                    case "是":
//                        caOriginalGround = 1;
//                        break;
//                    case "否":
//                        caOriginalGround = 2;
//                        break;
//                }
//                Bodyinfo.setCa_OriginalGround(caOriginalGround + "");//原地面保存  1是2否
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("提示");
//                builder.setMessage("确认提交量房信息");
//                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, final int i) {
//                        String info = JSONUtils.toString(Bodyinfo);
////                        mPresenter.UpXinxi(clientInfo.getCi_rwdid(), info, "量房");
//                        UpXinxi(clientInfo.getCi_RwdId(), info, "量房");
//                    }
//                });
//                builder.setNegativeButton("取消", null);
//                builder.show();
//                break;
//        }
//    }

    private void UpXinxi(String ci_rwdId, String info, String type) {
        Map map = new HashMap();
        map.put("rwdid", ci_rwdId);
        map.put("formpars", info);
        map.put("type", type);
        OkhttpUtils.doGet(getActivity(), PathUrl.SAVELFURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_提交量房信息", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_提交量房信息失败", message);
            }
        });
    }


    int kongtiaoshebeim, qiangdianxiangm, ruodianxiangm, shangshuidianm, xiashuidianm, xiashuidianchicunm, zhuliangxiagaom, ciliangxiagaom,
            yuandingmiangaom, penlinzuididianm, fengkouzuididianm, muqiangkuangjiajianjum, ca_cargo_door_hightm, ca_cargo_door_widem,
            ca_grond_elevationm, windowsill_heightm, windowsill_widthm, windows_heightm;

    String aircoldshebei = "", strongbox = "", weakbox = "", topwater = "", downwater = "", downwatersize = "", zhuliang = "", cilaing = "", oldtophight = "",
            pinlin = "", windydown = "", screenwall = "", windowsillhight = "", windowhight = "", windowwidth = "", huotihight = "", huotiwidth = "", groundcha = "";

    private class MyEditListener implements TextWatcher {

        private EditText edittext;

        public MyEditListener(EditText edittext) {
            super();
            this.edittext = edittext;
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            int lengths = arg0.length();
            switch (edittext.getId()) {
                case R.id.et_new_kongtiaoshebei:
                    editchanges(lengths, 0);
                    if (lengths > 0) {
                        aircoldshebei = edittext.getText().toString().trim();
                    } else {
                        aircoldshebei = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_AirConditionerNum(aircoldshebei);
                    }
                    break;
                case R.id.et_new_qiangdianxiang:
                    editchanges(lengths, 1);
                    if (lengths > 0) {
                        strongbox = edittext.getText().toString().trim();
                    } else {
                        strongbox = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_StrongBoxNum(strongbox);
                    }
                    break;
                case R.id.et_new_ruodianxiang:
                    editchanges(lengths, 2);
                    if (lengths > 0) {
                        weakbox = edittext.getText().toString().trim();
                    } else {
                        weakbox = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_WeakBoxNum(weakbox);
                    }
                    break;
                case R.id.et_new_shangshuidian:
                    editchanges(lengths, 3);
                    if (lengths > 0) {
                        topwater = edittext.getText().toString().trim();
                    } else {
                        topwater = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_UpWaterSpot(topwater);
                    }
                    break;
                case R.id.et_xiashuidian:
                    editchanges(lengths, 4);
                    if (lengths > 0) {
                        downwater = edittext.getText().toString().trim();
                    } else {
                        downwater = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_DownWaterSpot(downwater);
                    }
                    break;
                case R.id.et_new_xiashuidianchicun:
                    editchanges(lengths, 5);
                    if (lengths > 0) {
                        downwatersize = edittext.getText().toString().trim();
                    } else {
                        downwatersize = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_DownWaterSpotSize(downwatersize);
                    }
                    break;
                case R.id.et_new_zhuliangxiagao:
                    editchanges(lengths, 6);
                    if (lengths > 0) {
                        zhuliang = edittext.getText().toString().trim();
                    } else {
                        zhuliang = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_mainBeamHeight(zhuliang);
                    }
                    break;
                case R.id.et_new_ciliangxiagao:
                    editchanges(lengths, 7);
                    if (lengths > 0) {
                        cilaing = edittext.getText().toString().trim();
                    } else {
                        cilaing = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_SecondaryHeight(cilaing);
                    }
                    break;
                case R.id.et_new_yuandingmiangao:
                    editchanges(lengths, 8);
                    if (lengths > 0) {
                        oldtophight = edittext.getText().toString().trim();
                    } else {
                        oldtophight = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_SpaceMaxHeight(oldtophight);
                    }
                    break;
                case R.id.et_new_penlinzuididian:
                    editchanges(lengths, 9);
                    if (lengths > 0) {
                        pinlin = edittext.getText().toString().trim();
                    } else {
                        pinlin = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_MinimumSprayHeight(pinlin);
                    }
                    break;
                case R.id.et_new_fengkouzuididian:
                    editchanges(lengths, 10);
                    if (lengths > 0) {
                        windydown = edittext.getText().toString().trim();
                    } else {
                        windydown = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_TuyereMinimumHeight(windydown);
                    }
                    break;
                case R.id.et_new_muqiangkuangjiajianju:
                    editchanges(lengths, 11);
                    if (lengths > 0) {
                        screenwall = edittext.getText().toString().trim();
                    } else {
                        screenwall = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_CurtainWallSpacing(screenwall);
                    }
                    break;
                case R.id.et_measure_ca_cargo_door_hight:
                    editchanges(lengths, 12);
                    if (lengths > 0) {
                        huotihight = edittext.getText().toString().trim();
                    } else {
                        huotihight = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_CargoDoorHight(huotihight);
                    }
                    break;
                case R.id.et_measure_ca_cargo_door_wide:
                    editchanges(lengths, 13);
                    if (lengths > 0) {
                        huotiwidth = edittext.getText().toString().trim();
                    } else {
                        huotiwidth = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_CargoDoorWide(huotiwidth);
                    }
                    break;
                case R.id.et_measure_ca_grond_elevation:
                    editchanges(lengths, 14);
                    if (lengths > 0) {
                        groundcha = edittext.getText().toString().trim();
                    } else {
                        groundcha = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_GroundElevation(groundcha);
                    }
                    break;
                case R.id.et_measure_windowsill_height:
                    editchanges(lengths, 15);
                    if (lengths > 0) {
                        windowsillhight = edittext.getText().toString().trim();
                    } else {
                        windowsillhight = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_windowsillHight(windowsillhight);
                    }
                    break;
                case R.id.et_measure_windowsill_width:
                    editchanges(lengths, 16);
                    if (lengths > 0) {
                        windowwidth = edittext.getText().toString().trim();
                    } else {
                        windowwidth = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_WindowWidth(windowwidth);
                    }
                    break;
                case R.id.et_measure_windows_height:
                    editchanges(lengths, 17);
                    if (lengths > 0) {
                        windowhight = edittext.getText().toString().trim();
                    } else {
                        windowhight = "";
                    }
                    if (activity.savedatabean != null) {
                        activity.savedatabean.setCa_WindowHight(windowhight);
                    }
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

    /**
     * 输入框改变后金钱的变化
     */
    private void editchanges(int length, int type) {
        Log.e("length" + length, "type" + type);
        if (length > 0) {
            switch (type) {
                case 0:
                    if (kongtiaoshebeim != 1) {
                        kongtiaoshebeim = 1;
                        addMoney();
                    }
                    break;
                case 1:
                    if (qiangdianxiangm != 1) {
                        qiangdianxiangm = 1;
                        addMoney();
                    }
                    break;
                case 2:
                    if (ruodianxiangm != 1) {
                        ruodianxiangm = 1;
                        addMoney();
                    }
                    break;
                case 3:
                    if (shangshuidianm != 1) {
                        shangshuidianm = 1;
                        addMoney();
                    }
                    break;
                case 4:
                    if (xiashuidianm != 1) {
                        xiashuidianm = 1;
                        addMoney();
                    }
                    break;
                case 5:
                    if (xiashuidianchicunm != 1) {
                        xiashuidianchicunm = 1;
                        addMoney();
                    }
                    break;
                case 6:
                    if (zhuliangxiagaom != 1) {
                        zhuliangxiagaom = 1;
                        addMoney();
                    }
                    break;
                case 7:
                    if (ciliangxiagaom != 1) {
                        ciliangxiagaom = 1;
                        addMoney();
                    }
                    break;
                case 8:
                    if (yuandingmiangaom != 1) {
                        yuandingmiangaom = 1;
                        addMoney();
                    }
                    break;
                case 9:
                    if (penlinzuididianm != 1) {
                        penlinzuididianm = 1;
                        addMoney();
                    }
                    break;
                case 10:
                    if (fengkouzuididianm != 1) {
                        fengkouzuididianm = 1;
                        addMoney();
                    }
                    break;
                case 11:
                    if (muqiangkuangjiajianjum != 1) {
                        muqiangkuangjiajianjum = 1;
                        addMoney();
                    }
                    break;
                case 12:
                    if (ca_cargo_door_hightm != 1) {
                        ca_cargo_door_hightm = 1;
                        addMoney();
                    }
                    break;
                case 13:
                    if (ca_cargo_door_widem != 1) {
                        ca_cargo_door_widem = 1;
                        addMoney();
                    }
                    break;
                case 14:
                    if (ca_grond_elevationm != 1) {
                        ca_grond_elevationm = 1;
                        addMoney();
                    }
                    break;
                case 15:
                    if (windowsill_heightm != 1) {
                        windowsill_heightm = 1;
                        addMoney();
                    }
                    break;
                case 16:
                    if (windowsill_widthm != 1) {
                        windowsill_widthm = 1;
                        addMoney();
                    }
                    break;
                case 17:
                    if (windows_heightm != 1) {
                        windows_heightm = 1;
                        addMoney();
                    }
                    break;

            }
        } else {
            switch (type) {
                case 0:
                    kongtiaoshebeim = 0;
                    noaddMoney();
                    break;
                case 1:
                    qiangdianxiangm = 0;
                    noaddMoney();
                    break;
                case 2:
                    ruodianxiangm = 0;
                    noaddMoney();
                    break;
                case 3:
                    shangshuidianm = 0;
                    noaddMoney();
                    break;
                case 4:
                    xiashuidianm = 0;
                    noaddMoney();
                    break;
                case 5:
                    xiashuidianchicunm = 0;
                    noaddMoney();
                    break;
                case 6:
                    zhuliangxiagaom = 0;
                    noaddMoney();
                    break;
                case 7:
                    ciliangxiagaom = 0;
                    noaddMoney();
                    break;
                case 8:
                    yuandingmiangaom = 0;
                    noaddMoney();
                    break;
                case 9:
                    penlinzuididianm = 0;
                    noaddMoney();
                    break;
                case 10:
                    fengkouzuididianm = 0;
                    noaddMoney();
                    break;
                case 11:
                    muqiangkuangjiajianjum = 0;
                    noaddMoney();
                    break;
                case 12:
                    ca_cargo_door_hightm = 0;
                    noaddMoney();
                    break;
                case 13:
                    ca_cargo_door_widem = 0;
                    noaddMoney();
                    break;
                case 14:
                    ca_grond_elevationm = 0;
                    noaddMoney();
                    break;
                case 15:
                    windowsill_heightm = 0;
                    noaddMoney();
                    break;
                case 16:
                    windowsill_widthm = 0;
                    noaddMoney();
                    break;
                case 17:
                    windows_heightm = 0;
                    noaddMoney();
                    break;
            }
        }
    }

    //显示金额（金额=总金额/96*个数 ）
    double allmoney;
    BigDecimal bigDecimal;
    double showmoney;

    private void addMoney() {
        moneynum = moneynum + 1;
        Log.e("个数；", moneynum + "");
        if (lhousedata != null) {
            Log.e("金额是+；", lhousedata.getJDMoney() + "");
            allmoney = Double.parseDouble(lhousedata.getJDMoney());
        }
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
        if (lhousedata != null) {
            Log.e("个数；", moneynum + "");
            allmoney = Double.parseDouble(lhousedata.getJDMoney());
        }
        showmoney = allmoney / LF_NUM * moneynum;
        bigDecimal = new BigDecimal(showmoney);
        showmoney = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        activity.money = showmoney;
        Log.e("金额；", activity.money + "");
        activity.setMoney(activity.money);
        activity.setMoneynum(moneynum);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
