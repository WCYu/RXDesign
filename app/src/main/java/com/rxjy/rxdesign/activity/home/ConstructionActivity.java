package com.rxjy.rxdesign.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.IconInfo;
import com.rxjy.rxdesign.fragment.construction.HeTongFragment;
import com.rxjy.rxdesign.fragment.construction.TuZhiFragment;
import com.rxjy.rxdesign.fragment.construction.YuSuanFragment;
import com.rxjy.rxdesign.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 施工已签
* */
public class ConstructionActivity extends BaseActivity {

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
    @Bind(R.id.tv_tab_hetong)
    TextView tvTabHetong;
    @Bind(R.id.tv_tab_hetong_line)
    TextView tvTabHetongLine;
    @Bind(R.id.ll_tab_hetong)
    LinearLayout llTabHetong;
    @Bind(R.id.tv_tab_yusuan)
    TextView tvTabYusuan;
    @Bind(R.id.tv_tab_yusuan_line)
    TextView tvTabYusuanLine;
    @Bind(R.id.ll_tab_yusuan)
    LinearLayout llTabYusuan;
    @Bind(R.id.tv_tab_tuzhi)
    TextView tvTabTuzhi;
    @Bind(R.id.tv_tab_tuzhi_line)
    TextView tvTabTuzhiLine;
    @Bind(R.id.ll_tab_tuzhi)
    LinearLayout llTabTuzhi;
    @Bind(R.id.fl_des_dai_main)
    FrameLayout flDesDaiMain;

    //碎片的集合
    private List<Fragment> fragmentList;
    private List<IconInfo> iconList;
    //指定Fragment的坐标
    private final int ONE_FRAGMENT = 0;
    private final int TWO_FRAGMENT = 1;
    private final int THREE_FRAGMENT = 2;
    private HeTongFragment heTongFragment;
    private YuSuanFragment yuSuanFragment;
    private TuZhiFragment tuZhiFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_construction;
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
        tvTitle.setText("施工已签");
        initIcon();
        initFragment();
        setRwdidToFragment();
    }

    @Override
    public void initAdapter() {

    }

    private void setRwdidToFragment() {
        AllClientNewBean.ClientNewBean info = (AllClientNewBean.ClientNewBean) getIntent().getSerializableExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO);
        heTongFragment.setClientInfo(info);
        yuSuanFragment.setClientInfo(info);
        tuZhiFragment.setClientInfo(info);
    }

    private void initIcon() {
        //初始化iconList数据集合
        iconList = new ArrayList<>();
        //将图标添加到集合中
        iconList.add(new IconInfo(tvTabHetong, tvTabHetongLine));
        iconList.add(new IconInfo(tvTabYusuan, tvTabYusuanLine));
        iconList.add(new IconInfo(tvTabTuzhi, tvTabTuzhiLine));
    }

    private void initFragment() {
        if (heTongFragment == null) {
            heTongFragment = new HeTongFragment();
        }
        if (yuSuanFragment == null) {
            yuSuanFragment = new YuSuanFragment();
        }
        if (tuZhiFragment == null) {
            tuZhiFragment = new TuZhiFragment();
        }

        //初始化fragmentList数据集合
        fragmentList = new ArrayList<>();
        //将碎片添加到集合中
        fragmentList.add(heTongFragment);
        fragmentList.add(yuSuanFragment);
        fragmentList.add(tuZhiFragment);
    }

    private void showFragment(Fragment fragment, int position) {
        JumpFragment(fragment);
        setIcon(position);
    }

    private void JumpFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction action = manager.beginTransaction();
        if (!fragment.isAdded()) {
            action.add(R.id.fl_des_dai_main, fragment);
        }
        hideFragment(action);
        action.show(fragment);
        action.commit();
    }

    private void hideFragment(FragmentTransaction action) {
        if (heTongFragment != null) {
            action.hide(heTongFragment);
        }
        if (yuSuanFragment != null) {
            action.hide(yuSuanFragment);
        }
        if (tuZhiFragment != null) {
            action.hide(tuZhiFragment);
        }
    }

    private void setIcon(int position) {
        for (int i = 0; i < iconList.size(); i++) {
            iconList.get(i).getTextView().setTextColor(this.getResources().getColor(R.color.cor666));
            iconList.get(i).getTextView2().setVisibility(View.INVISIBLE);
        }
        iconList.get(position).getTextView().setTextColor(this.getResources().getColor(R.color.cor0068b7));
        iconList.get(position).getTextView2().setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.img_back, R.id.ll_tab_hetong, R.id.ll_tab_yusuan, R.id.ll_tab_tuzhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_tab_hetong:
                showFragment(fragmentList.get(ONE_FRAGMENT), ONE_FRAGMENT);
                break;
            case R.id.ll_tab_yusuan:
                showFragment(fragmentList.get(TWO_FRAGMENT), TWO_FRAGMENT);
                break;
            case R.id.ll_tab_tuzhi:
                showFragment(fragmentList.get(THREE_FRAGMENT), THREE_FRAGMENT);
                break;
        }
    }
}
