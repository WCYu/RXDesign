package com.rxjy.rxdesign.activity.home;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.IconInfo;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.entity.PersonDataBean;
import com.rxjy.rxdesign.fragment.FindFragment;
import com.rxjy.rxdesign.fragment.HomeFragment;
import com.rxjy.rxdesign.fragment.MainFragment;
import com.rxjy.rxdesign.fragment.MemberHomeFragment;
import com.rxjy.rxdesign.fragment.MoreFragment;
import com.rxjy.rxdesign.fragment.NewPeopleHomeFragment;
import com.rxjy.rxdesign.utils.CheckPermissionsUitl;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.MySharedPreferences;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import static com.rxjy.rxdesign.receiver.RxdesignMessageReceiver.MSG_NUM;
import static com.rxjy.rxdesign.receiver.RxdesignMessageReceiver.msgnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by asus on 2018/5/21.
 */

public class JoininNjjActivity extends BaseActivity {

    @Bind(R.id.fl_main)
    FrameLayout flMain;
    @Bind(R.id.iv_tab_home)
    ImageView ivTabHome;
    @Bind(R.id.tv_tab_home)
    TextView tvTabHome;
    @Bind(R.id.rl_tab_home)
    RelativeLayout rlTabHome;
    @Bind(R.id.iv_tab_wallet)
    ImageView ivTabWallet;
    @Bind(R.id.tv_tab_wallet)
    TextView tvTabWallet;
    @Bind(R.id.rl_tab_wallet)
    RelativeLayout rlTabWallet;
    @Bind(R.id.iv_tab_find)
    ImageView ivTabFind;
    @Bind(R.id.tv_tab_find)
    TextView tvTabFind;
    @Bind(R.id.rl_tab_find)
    RelativeLayout rlTabFind;
    @Bind(R.id.iv_tab_mine)
    ImageView ivTabMine;
    @Bind(R.id.tv_tab_mine)
    TextView tvTabMine;
    @Bind(R.id.ll_view)
    LinearLayout llView;
    @Bind(R.id.img_reddot)
    public ImageView img_reddot;
    @Bind(R.id.rl_tab_mine)
    RelativeLayout rlTabMine;
    @Bind(R.id.tv_msgnum)
    TextView tvMsgnum;

    private static boolean isExit = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    private Fragment currentFragment;
    //指定Fragment的坐标
    private final int HOME_FRAGMENT = 0;
    private final int WALLET_FRAGMENT = 1;
    private final int FIND_FRAGMENT = 2;
    private final int MINE_FRAGMENT = 3;
    //Tab图标的集合
    private List<IconInfo> iconList;
    //碎片的集合
    private List<Fragment> fragmentList;

    private MemberHomeFragment memberHomeFragment;
    private HomeFragment homeFragment;
    private MoreFragment moreFragment;
    private FindFragment findFragment;
    public MainFragment mineFragment;
    private NewPeopleHomeFragment newPeopleHomeFragment;

    private AlertDialog alertDialog;
    private int[] iconNormal = new int[]{
            R.mipmap.ic_tab_hostpage,
            R.mipmap.ic_tab_more,
            R.mipmap.ic_tab_find,
            R.mipmap.ic_tab_main
    };
    private int[] iconPressed = new int[]{
            R.mipmap.ic_homenblue,
            R.mipmap.ic_morenblue,
            R.mipmap.ic_findnblue,
            R.mipmap.ic_minenblue
    };

    int type = 0;

    public static JoininNjjActivity activity;
    //检测权限列表
    private String[] requestPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE
    };
    private TextView text;
    private TextView msgtwo;
    private MySharedPreferences instance;

    @Override
    public void initView() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_njj;
    }

    @Override
    public void intData() {
        activity = this;
        instance = MySharedPreferences.getInstance();
        isShow();
        initIcon();
        initFragment();
        //加载默认显示碎片
        showFragment(fragmentList.get(HOME_FRAGMENT), HOME_FRAGMENT);
        SharedPreferences preferences = getSharedPreferences(MSG_NUM, MODE_PRIVATE);
        int num = preferences.getInt(msgnum, 0);
        if (num > 0) {
            ShowMsgNum(num);
        }
    }

    @Override
    public void initAdapter() {

    }

    public void ShowMsgNum(int num) {
        if (num > 0) {
            tvMsgnum.setText(num + "");
            tvMsgnum.setVisibility(View.VISIBLE);
        } else {
            tvMsgnum.setVisibility(View.GONE);
        }
    }

    private void initIcon() {
        //初始化iconList数据集合
        iconList = new ArrayList<>();
        //将图标添加到集合中
        iconList.add(new IconInfo(ivTabHome, tvTabHome));
        iconList.add(new IconInfo(ivTabWallet, tvTabWallet));
        iconList.add(new IconInfo(ivTabFind, tvTabFind));
        iconList.add(new IconInfo(ivTabMine, tvTabMine));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //权限检测
        CheckPermissionsUitl.checkPermissions(this, requestPermissions);
        Log.e("App.is", App.is_exist + "");
    }

    private void initFragment() {
        //初始化碎片
        if (memberHomeFragment == null) {
            memberHomeFragment = new MemberHomeFragment();
        }
        if (newPeopleHomeFragment == null) {
            newPeopleHomeFragment = new NewPeopleHomeFragment();
        }
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (moreFragment == null) {
            moreFragment = new MoreFragment();
        }
        if (findFragment == null) {
            findFragment = new FindFragment();
        }
        if (mineFragment == null) {
            mineFragment = new MainFragment();
        }

        fragmentList = new ArrayList<>();
        if (getIntent().getIntExtra("isShow", 0) == 1) {
            fragmentList.add(homeFragment);
        } else {
            fragmentList.add(newPeopleHomeFragment);
        }
//        fragmentList.add(memberHomeFragment);
        fragmentList.add(moreFragment);
        fragmentList.add(findFragment);
        fragmentList.add(mineFragment);
    }

    //显示指定Fragment界面的方法
    private void showFragment(Fragment fragment, int position) {
        JumpFragment(fragment);
        setIcon(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        type = 0;
        Log.e("下线", "应用杀死");
//        String cardNo = MySharedPreferences.getInstance().getCardNo();
//        appDownLine(cardNo);
    }

    private void appDownLine(String cardNo) {
        if (!TextUtils.isEmpty(cardNo)) {
            Map map = new HashMap();
            map.put("cardNo", App.cardNo);
            OkhttpUtils.doPost("https://api.dcwzg.com:9191/actionapi/AppHome/OfflineApp", map, new OkhttpUtils.MyCall() {
                @Override
                public void success(String data) {
                    Log.e("tag_下线", data);
                }

                @Override
                public void error(String message) {
                    Log.e("tag_下线失败", message);
                }
            });
        }
    }

    //加载指定Fragment的方法
    private void JumpFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction action = manager.beginTransaction();
        hideFragment(action);
        if (!fragment.isAdded()) {
            action.add(R.id.fl_main, fragment);
        }
        action.show(fragment);
        action.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (memberHomeFragment != null) {
            ft.hide(memberHomeFragment);
        }
        if (newPeopleHomeFragment != null) {
            ft.hide(newPeopleHomeFragment);
        }
        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (moreFragment != null) {
            ft.hide(moreFragment);
        }

        if (findFragment != null) {
            ft.hide(findFragment);
        }

        if (mineFragment != null) {
            ft.hide(mineFragment);
        }
        Log.e("隐藏所有Fragment", "隐藏");
    }

    //设置图标点击效果
    private void setIcon(int position) {

        for (int i = 0; i < iconList.size(); i++) {
            iconList.get(i).getImageView().setImageResource(iconNormal[i]);
            iconList.get(i).getTextView().setTextColor(this.getResources().getColor(R.color.colorGrayDark));
        }

        iconList.get(position).getImageView().setImageResource(iconPressed[position]);
        iconList.get(position).getTextView().setTextColor(this.getResources().getColor(R.color.colorPrimaryDes));
    }

    @OnClick({R.id.rl_tab_home, R.id.rl_tab_wallet, R.id.rl_tab_find, R.id.rl_tab_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tab_home:
                showFragment(fragmentList.get(HOME_FRAGMENT), HOME_FRAGMENT);
                break;
            case R.id.rl_tab_wallet:
                showFragment(fragmentList.get(WALLET_FRAGMENT), WALLET_FRAGMENT);
                break;
            case R.id.rl_tab_find:
                showFragment(fragmentList.get(FIND_FRAGMENT), FIND_FRAGMENT);
                break;
            case R.id.rl_tab_mine:
                SharedPreferences preferences = getSharedPreferences(MSG_NUM, MODE_PRIVATE);
                preferences.edit().putInt(msgnum, 0).commit();
                ShowMsgNum(0);
                showFragment(fragmentList.get(MINE_FRAGMENT), MINE_FRAGMENT);
                break;
        }
    }

    public void isShow() {

        Map map = new HashMap();
        map.put("cardno", App.cardNo);
        map.put("appId", App.app_id);

        OkhttpUtils.doGet(this, PathUrl.ISSHOWREDDOTURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取是否有新文章", data);
                PersonBean info = JSONUtils.toObject(data, PersonBean.class);
                PersonDataBean body = info.getBody();
                int statusCode = info.getStatusCode();
                String statusMsg = info.getStatusMsg();
                if (statusCode == 0) {
                    img_reddot.setVisibility(View.VISIBLE);
                } else {
                    img_reddot.setVisibility(View.GONE);
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取是否有新文章失败", message);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.getInstance().toastCentent("再按一次退出程序", JoininNjjActivity.this);
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
//            System.exit(0);
            finish();
        }
    }
}
