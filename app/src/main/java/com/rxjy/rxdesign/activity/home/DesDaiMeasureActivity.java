package com.rxjy.rxdesign.activity.home;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.guide.LoginActivity;
import com.rxjy.rxdesign.activity.my.SettingActivity;
import com.rxjy.rxdesign.adapter.home.LFPagerAdapter;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.DesDaiMeasureABean;
import com.rxjy.rxdesign.entity.IconInfo;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.fragment.volumeroom.DesDaiSurroundingsFragment;
import com.rxjy.rxdesign.fragment.volumeroom.VolumeEightFragment;
import com.rxjy.rxdesign.fragment.volumeroom.VolumeFiveFragment;
import com.rxjy.rxdesign.fragment.volumeroom.VolumeFourFragment;
import com.rxjy.rxdesign.fragment.volumeroom.VolumeNineFragment;
import com.rxjy.rxdesign.fragment.volumeroom.VolumeOneFragment;
import com.rxjy.rxdesign.fragment.volumeroom.VolumeSevenFragment;
import com.rxjy.rxdesign.fragment.volumeroom.VolumeSixFragment;
import com.rxjy.rxdesign.fragment.volumeroom.VolumeThreeFragment;
import com.rxjy.rxdesign.fragment.volumeroom.VolumeTwoFragment;
import com.rxjy.rxdesign.utils.Constants;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.MySharedPreferences;
import com.rxjy.rxdesign.utils.OkhttpUtils;
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

/*
* 量房
* */
public class DesDaiMeasureActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.publish)
    ImageView publish;
    @Bind(R.id.ly_background)
    LinearLayout lyBackground;
    @Bind(R.id.tv_tab_liangfangxinxi)
    TextView tvTabLiangfangxinxi;
    @Bind(R.id.tv_tab_liangfangxinxi_line)
    TextView tvTabLiangfangxinxiLine;
    @Bind(R.id.ll_tab_liangfangxinxi)
    LinearLayout llTabLiangfangxinxi;
    @Bind(R.id.tv_tab_liangfangzhaopian)
    TextView tvTabLiangfangzhaopian;
    @Bind(R.id.tv_tab_liangfangzhaopian_line)
    TextView tvTabLiangfangzhaopianLine;
    @Bind(R.id.ll_tab_liangfangzhaopian)
    LinearLayout llTabLiangfangzhaopian;
    @Bind(R.id.tv_tab_zhuangxiuxuqiu)
    TextView tvTabZhuangxiuxuqiu;
    @Bind(R.id.tv_tab_zhuangxiuxuqiu_line)
    TextView tvTabZhuangxiuxuqiuLine;
    @Bind(R.id.ll_tab_zhuangxiuxuqiu)
    LinearLayout llTabZhuangxiuxuqiu;
    @Bind(R.id.tv_tab_wuyexinxi)
    TextView tvTabWuyexinxi;
    @Bind(R.id.tv_tab_wuyexinxi_line)
    TextView tvTabWuyexinxiLine;
    @Bind(R.id.ll_tab_wuyexinxi)
    LinearLayout llTabWuyexinxi;
    @Bind(R.id.tv_tab_jibenxinxi)
    TextView tvTabJibenxinxi;
    @Bind(R.id.tv_tab_jibenxinxi_line)
    TextView tvTabJibenxinxiLine;
    @Bind(R.id.ll_tab_jibenxinxi)
    LinearLayout llTabJibenxinxi;
    @Bind(R.id.tv_tab_fangyuanxinxi)
    TextView tvTabFangyuanxinxi;
    @Bind(R.id.tv_tab_fangyuanxinxi_line)
    TextView tvTabFangyuanxinxiLine;
    @Bind(R.id.ll_tab_fangyuanxinxi)
    LinearLayout llTabFangyuanxinxi;
    @Bind(R.id.tv_tab_loupanxinxi)
    TextView tvTabLoupanxinxi;
    @Bind(R.id.tv_tab_loupanxinxi_line)
    TextView tvTabLoupanxinxiLine;
    @Bind(R.id.ll_tab_loupanxinxi)
    LinearLayout llTabLoupanxinxi;
    @Bind(R.id.tv_tab_kehuxinxi)
    TextView tvTabKehuxinxi;
    @Bind(R.id.tv_tab_kehuxinxi_line)
    TextView tvTabKehuxinxiLine;
    @Bind(R.id.ll_tab_kehuxinxi)
    LinearLayout llTabKehuxinxi;
    @Bind(R.id.tv_tab_qiyexinxi)
    TextView tvTabQiyexinxi;
    @Bind(R.id.tv_tab_qiyexinxi_line)
    TextView tvTabQiyexinxiLine;
    @Bind(R.id.ll_tab_qiyexinxi)
    LinearLayout llTabQiyexinxi;
    @Bind(R.id.tv_tab_qiyehuanjing)
    TextView tvTabQiyehuanjing;
    @Bind(R.id.tv_tab_qiyehuanjing_line)
    TextView tvTabQiyehuanjingLine;
    @Bind(R.id.ll_tab_qiyehuanjing)
    LinearLayout llTabQiyehuanjing;
    @Bind(R.id.fl_des_dai_main)
    FrameLayout flDesDaiMain;
    @Bind(R.id.liangfang_zhaoxiang)
    ImageView liangfangZhaoxiang;
    @Bind(R.id.tv_moneyget)
    TextView tvMoneyget;
    @Bind(R.id.btn_des_dai_measure_sub)
    TextView btnDesDaiMeasureSub;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.lf_tab)
    TabLayout lfTab;
    @Bind(R.id.lf_pager)
    ViewPager lfPager;

    //指定Fragment的坐标
    private final int ONE_FRAGMENT = 0;
    private final int TWO_FRAGMENT = 1;
    private final int THREE_FRAGMENT = 2;
    private final int FOUR_FRAGMENT = 3;
    private final int FIVE_FRAGMENT = 4;
    private final int SIX_FRAGMENT = 5;
    private final int SEVEN_FRAGMENT = 6;
    private final int EIGHT_FRAGMENT = 7;
    private final int NINE_FRAGMENT = 8;
    private final int TEN_FRAGMENT = 9;

    private List<IconInfo> iconList;    //Tab图标的集合
    private ArrayList<Fragment> fragmentList;    //碎片的集合
    private AllClientNewBean.ClientNewBean info;

    private VolumeOneFragment volumeOneFragment;
    private VolumeTwoFragment volumeTwoFragment;
    private VolumeThreeFragment volumeThreeFragment;
    private VolumeFourFragment volumeFourFragment;
    private VolumeFiveFragment volumeFiveFragment;
    private VolumeSixFragment volumeSixFragment;
    private VolumeSevenFragment volumeSevenFragment;
    private VolumeEightFragment volumeEightFragment;
    private VolumeNineFragment volumeNineFragment;
    private DesDaiSurroundingsFragment desDaiSurroundingsFragment;
    private Fragment currentFragment;
    public double money;
    public int moneynum;
    public int lfphotonum, qyphotonum;
    public double getmaxmoney;

    public DesDaiMeasureABean.BodyBean savedatabean;
    private ArrayList<String> titleList;
    private LFPagerAdapter lfPagerAdapter;

    public void setSavedatabean(DesDaiMeasureABean.BodyBean savedatabean) {
        this.savedatabean = savedatabean;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_des_dai_measure;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void intData() {
        Constants.HOUSE_MONEY = 0;
        initIntentData();
        tvTitle.setText("量房");
        initIcon();
        initFragment();
        //加载默认显示碎片
//        showFragment(fragmentList.get(ONE_FRAGMENT), ONE_FRAGMENT);
        setClientInfoToFragment();
        initListener();
    }

    private void initListener() {
        lfTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
//                showFragment(fragmentList.get(position), position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (savedatabean == null) {
            Log.e("DesDaiMeasureABean", "数据为空");
            savedatabean = new DesDaiMeasureABean.BodyBean();
        }
        getLHouseData();
    }

    private void initIntentData() {
        info = (AllClientNewBean.ClientNewBean) getIntent().getSerializableExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO);
    }

    private void setClientInfoToFragment() {
        if (info != null) {
            volumeOneFragment.setClientInfo(info);
            volumeTwoFragment.setClientInfo(info);
            volumeThreeFragment.setClientInfo(info);
            volumeFourFragment.setClientInfo(info);
            volumeFiveFragment.setClientInfo(info);
            volumeSixFragment.setClientInfo(info);
            volumeSevenFragment.setClientInfo(info);
            volumeEightFragment.setClientInfo(info);
            volumeNineFragment.setClientInfo(info);
            desDaiSurroundingsFragment.setClientInfo(info);
        }
    }

    private void initFragment() {
        if (volumeOneFragment == null) {
            volumeOneFragment = new VolumeOneFragment();
        }
        if (volumeTwoFragment == null) {
            volumeTwoFragment = new VolumeTwoFragment();
        }
        if (volumeThreeFragment == null) {
            volumeThreeFragment = new VolumeThreeFragment();
        }
        if (volumeFourFragment == null) {
            volumeFourFragment = new VolumeFourFragment();
        }
        if (volumeFiveFragment == null) {
            volumeFiveFragment = new VolumeFiveFragment();
        }
        if (volumeSixFragment == null) {
            volumeSixFragment = new VolumeSixFragment();
        }
        if (volumeSevenFragment == null) {
            volumeSevenFragment = new VolumeSevenFragment();
        }
        if (volumeEightFragment == null) {
            volumeEightFragment = new VolumeEightFragment();
        }
        if (volumeNineFragment == null) {
            volumeNineFragment = new VolumeNineFragment();
        }
        if (desDaiSurroundingsFragment == null) {
            desDaiSurroundingsFragment = new DesDaiSurroundingsFragment();
        }
        fragmentList = new ArrayList<>();
        //将碎片添加到集合中
        fragmentList.add(volumeNineFragment);
        fragmentList.add(volumeEightFragment);
        fragmentList.add(volumeSixFragment);
        fragmentList.add(volumeSevenFragment);
        fragmentList.add(volumeThreeFragment);
        fragmentList.add(volumeFourFragment);
        fragmentList.add(volumeOneFragment);
        fragmentList.add(desDaiSurroundingsFragment);
//        fragmentList.add(volumeTwoFragment);
//        fragmentList.add(volumeFiveFragment);

        titleList = new ArrayList();
        titleList.add("企业");
        titleList.add("客户");
        titleList.add("房源");
        titleList.add("楼盘");
        titleList.add("装修");
        titleList.add("物业");
        titleList.add("量房");
        titleList.add("照片");

        for (int i = 0; i < titleList.size(); i++) {
            lfTab.addTab(lfTab.newTab().setText(titleList.get(i)));
        }
    }

    @Override
    public void initAdapter() {
        lfPagerAdapter = new LFPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        lfPager.setAdapter(lfPagerAdapter);
        lfTab.setupWithViewPager(lfPager);
    }

    /**
     * 显示指定Fragment界面的方法
     */
    private void showFragment(Fragment fragment, int position) {
        jumpFragment(fragment);
        setIcon(position);
    }

    private void jumpFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction action = manager.beginTransaction();
        if (!fragment.isAdded()) {
            action.add(R.id.fl_des_dai_main, fragment);
        }
        if (currentFragment != null) {
            action.hide(currentFragment);
        }
        action.show(fragment);
        action.commit();
        currentFragment = fragment;
    }

    /**
     * 设置图标点击效果
     */
    private void setIcon(int position) {
        for (int i = 0; i < iconList.size(); i++) {
            iconList.get(i).getTextView2().setVisibility(View.INVISIBLE);
        }
        iconList.get(position).getTextView2().setVisibility(View.VISIBLE);
    }

    private void initIcon() {
        //初始化iconList数据集合
        iconList = new ArrayList<>();
        //将图标添加到集合中
        iconList.add(new IconInfo(tvTabLiangfangxinxi, tvTabLiangfangxinxiLine));
        iconList.add(new IconInfo(tvTabLiangfangzhaopian, tvTabLiangfangzhaopianLine));
        iconList.add(new IconInfo(tvTabZhuangxiuxuqiu, tvTabZhuangxiuxuqiuLine));
        iconList.add(new IconInfo(tvTabWuyexinxi, tvTabWuyexinxiLine));
        iconList.add(new IconInfo(tvTabJibenxinxi, tvTabJibenxinxiLine));
        iconList.add(new IconInfo(tvTabFangyuanxinxi, tvTabFangyuanxinxiLine));
        iconList.add(new IconInfo(tvTabLoupanxinxi, tvTabLoupanxinxiLine));
        iconList.add(new IconInfo(tvTabKehuxinxi, tvTabKehuxinxiLine));
        iconList.add(new IconInfo(tvTabQiyexinxi, tvTabQiyexinxiLine));
        iconList.add(new IconInfo(tvTabQiyehuanjing, tvTabQiyehuanjingLine));
    }

    public void setMoney(double moneys) {
        tvMoneyget.setText("￥" + moneys);
        money = moneys;
    }

    public void setMoneynum(int mun) {
        moneynum = mun;
    }

    @OnClick({R.id.img_back, R.id.ll_tab_liangfangxinxi, R.id.ll_tab_liangfangzhaopian, R.id.ll_tab_zhuangxiuxuqiu, R.id.ll_tab_wuyexinxi, R.id.ll_tab_jibenxinxi, R.id.ll_tab_fangyuanxinxi, R.id.ll_tab_loupanxinxi, R.id.ll_tab_kehuxinxi, R.id.ll_tab_qiyexinxi, R.id.ll_tab_qiyehuanjing, R.id.btn_des_dai_measure_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                showDialog();
                break;
            case R.id.ll_tab_liangfangxinxi:
                showFragment(fragmentList.get(ONE_FRAGMENT), ONE_FRAGMENT);
                break;
            case R.id.ll_tab_liangfangzhaopian:
                showFragment(fragmentList.get(TWO_FRAGMENT), TWO_FRAGMENT);
                break;
            case R.id.ll_tab_zhuangxiuxuqiu:
                showFragment(fragmentList.get(THREE_FRAGMENT), THREE_FRAGMENT);
                break;
            case R.id.ll_tab_wuyexinxi:
                showFragment(fragmentList.get(FOUR_FRAGMENT), FOUR_FRAGMENT);
                break;
            case R.id.ll_tab_jibenxinxi:
                showFragment(fragmentList.get(FIVE_FRAGMENT), FIVE_FRAGMENT);
                break;
            case R.id.ll_tab_fangyuanxinxi:
                showFragment(fragmentList.get(SIX_FRAGMENT), SIX_FRAGMENT);
                break;
            case R.id.ll_tab_loupanxinxi:
                showFragment(fragmentList.get(SEVEN_FRAGMENT), SEVEN_FRAGMENT);
                break;
            case R.id.ll_tab_kehuxinxi:
                showFragment(fragmentList.get(EIGHT_FRAGMENT), EIGHT_FRAGMENT);
                break;
            case R.id.ll_tab_qiyexinxi:
                showFragment(fragmentList.get(NINE_FRAGMENT), NINE_FRAGMENT);
                break;
            case R.id.ll_tab_qiyehuanjing:
                showFragment(fragmentList.get(TEN_FRAGMENT), TEN_FRAGMENT);
                break;
            case R.id.btn_des_dai_measure_sub:
                savedatabean.setImagesArray(null);
                String saveinfo = JSONUtils.toString(savedatabean);
                saveLHouseData(info.getCi_RwdId(), saveinfo, money + "", moneynum + "");
                break;
        }
    }

    /**
     * 提示保存的弹出框
     */
    Dialog dialog;
    View v;
    private TextView tv_cancle, tv_sure;

    private void showDialog() {
        new AlertDialog.Builder(this).setTitle("系统提示").setMessage("您的量房资料还未保存，确认返回？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    public void saveLHouseData(String id, String content, String mone, String monenum) {

        Map map = new HashMap();
        map.put("rwdid", id);
        map.put("formpars", content);
        map.put("money", mone);
        map.put("valCount", monenum);
        OkhttpUtils.doPost(PathUrl.BCLFURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_保存量房信息", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                        finish();
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(String message) {
                Log.e("tag_保存量房信息失败", message);

            }
        });
    }

    public void getLHouseData() {
        Map map = new HashMap();
        map.put("rwdid", info.getCi_RwdId());
        OkhttpUtils.doGet(this, PathUrl.GETLFINFOURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取量房信息", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        DesDaiMeasureABean info = JSONUtils.toObject(data, DesDaiMeasureABean.class);
                        DesDaiMeasureABean.BodyBean body = info.getBody();
                        savedatabean = body;
                        moneynum = body.getValCount();//获取到个数用于计算
                        Log.e("个数。。。", moneynum + "");
                        getmaxmoney = Double.parseDouble(body.getJDMoney());
                        money = Double.parseDouble(body.getLFMondey());
                        tvMoneyget.setText("￥" + body.getLFMondey());

                        if (volumeNineFragment == null) {
                            volumeNineFragment = new VolumeNineFragment();
                            volumeNineFragment.setLHouseData(body);
                        } else {
                            volumeNineFragment.setLHouseData(body);
                        }
                        if (volumeEightFragment == null) {
                            volumeEightFragment = new VolumeEightFragment();
                            volumeEightFragment.setLHouseData(body);
                        } else {
                            volumeEightFragment.setLHouseData(body);
                        }

                        if (volumeSixFragment == null) {
                            volumeSixFragment = new VolumeSixFragment();
                            volumeSixFragment.setLHouseData(body);
                        } else {
                            volumeSixFragment.setLHouseData(body);
                        }

                        if (volumeSevenFragment == null) {
                            volumeSevenFragment = new VolumeSevenFragment();
                            volumeSevenFragment.setLHouseData(body);
                        } else {
                            volumeSevenFragment.setLHouseData(body);
                        }

                        if (volumeThreeFragment == null) {
                            volumeThreeFragment = new VolumeThreeFragment();
                            volumeThreeFragment.setLHouseData(body);
                        } else {
                            volumeThreeFragment.setLHouseData(body);
                        }

                        if (volumeFourFragment == null) {
                            volumeFourFragment = new VolumeFourFragment();
                            volumeFourFragment.setLHouseData(body);
                        } else {
                            volumeFourFragment.setLHouseData(body);
                        }

                        if (volumeOneFragment == null) {
                            volumeOneFragment = new VolumeOneFragment();
                            volumeOneFragment.setLHouseData(body);
                        } else {
                            volumeOneFragment.setLHouseData(body);
                        }

                        if (desDaiSurroundingsFragment == null) {
                            desDaiSurroundingsFragment = new DesDaiSurroundingsFragment();
                            desDaiSurroundingsFragment.setLHouseData(body);
                        } else {
                            desDaiSurroundingsFragment.setLHouseData(body);
                        }

//                        volumeTwoFragment.setLHouseData(body);
//                        volumeFiveFragment.setLHouseData(body);
                        if (body.getImagesArray().getLFImages() != null && body.getImagesArray().getLFImages().size() > 0) {
                            for (int i = 0; i < body.getImagesArray().getLFImages().size(); i++) {
                                if (body.getImagesArray().getLFImages().get(i).getChildList().size() > 0) {
                                    lfphotonum = lfphotonum + 1;
                                }
                            }
                        }

                        if (body.getImagesArray().getQYImages() != null && body.getImagesArray().getQYImages().size() > 0) {
                            for (int i = 0; i < body.getImagesArray().getQYImages().size(); i++) {
                                if (body.getImagesArray().getQYImages().get(i).getChildList().size() > 0) {
                                    qyphotonum = qyphotonum + 1;
                                }
                            }
                        }
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("tag_fragment", "赋值失败" + e.toString());
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取量房失败", message);
            }
        });
    }
}
