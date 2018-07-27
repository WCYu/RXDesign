package com.rxjy.rxdesign.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.my.JiFenActivity;
import com.rxjy.rxdesign.activity.my.MessageActivity;
import com.rxjy.rxdesign.activity.my.SettingActivity;
import com.rxjy.rxdesign.activity.my.UserInfoActivity;
import com.rxjy.rxdesign.activity.my.WalletActivity;
import com.rxjy.rxdesign.activity.my.WorkActivity;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.MsgNumBean;
import com.rxjy.rxdesign.entity.UserInfoBean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.MySharedPreferences;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 */
public class MainFragment extends BaseFragment {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.img_personicon)
    ImageView imgPersonicon;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_pjob)
    TextView tvPjob;
    @Bind(R.id.tv_paccount)
    TextView tvPaccount;
    @Bind(R.id.imageView5)
    ImageView imageView5;
    @Bind(R.id.img_erweima)
    ImageView img_erweima;
    @Bind(R.id.rl_persondetails)
    RelativeLayout rlPersondetails;
    @Bind(R.id.rl_office)
    RelativeLayout rlOffice;
    @Bind(R.id.rl_wallet)
    RelativeLayout rlWallet;
    @Bind(R.id.rl_jifen)
    RelativeLayout rlJifen;
    @Bind(R.id.rl_chengjiu)
    RelativeLayout rlChengjiu;
    @Bind(R.id.rl_informmessage)
    RelativeLayout rlInformmessage;
    @Bind(R.id.rl_setting)
    RelativeLayout rlSetting;
    @Bind(R.id.tv_messagenum)
    TextView tvMessagenum;

    private UserInfoBean.BodyBean bodyBean;
    private MySharedPreferences instance;
    private String image;
    private String name;

    @Override
    public int getInitId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        imgBack.setVisibility(View.GONE);
        tvTitle.setText("个人中心");
    }

    @Override
    public void initData() {
        instance = MySharedPreferences.getInstance();
        if (!TextUtils.isEmpty(instance.getName())) {
            tvName.setText(instance.getName());
        }
        if (!TextUtils.isEmpty(instance.getCardNo())) {
            tvPaccount.setText("账号：" + instance.getCardNo());
        }
        rlOffice.setVisibility(View.VISIBLE);
        switch (App.is_group) {
            case "0":

            case "1":
                break;
            case "2":
                tvName.setText(instance.getName() + "   " + instance.getPostName());
                rlOffice.setVisibility(View.GONE);
                rlJifen.setEnabled(false);
                rlWallet.setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        image = instance.getImage();
        name = instance.getName();
        getMessageNum();
        if (!TextUtils.isEmpty(image)) {
            Glide.with(this).load(image).apply(RequestOptions.circleCropTransform()).into(imgPersonicon);
        } else {
            Glide.with(getActivity()).load(R.mipmap.morenicon).into(imgPersonicon);
        }
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initLinstener() {

    }

    public void getUserInfo() {
        Map map = new HashMap();
        map.put("cardNo", App.cardNo);
        map.put("token", App.token);

        OkhttpUtils.doPost(PathUrl.ZHAOSHANGUSERURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取用户信息", data);
                Gson gson = new Gson();
                UserInfoBean info = gson.fromJson(data, UserInfoBean.class);
                bodyBean = info.getBody().get(0);
                int statusCode = info.getStatusCode();
                String statusMsg = info.getStatusMsg();
                if (statusCode == 0) {
                    tvName.setText(bodyBean.getU_name());
                    tvPaccount.setText(bodyBean.getCard_no());
                    if (!TextUtils.isEmpty(bodyBean.getImage())) {
                        Glide.with(getActivity()).load(bodyBean.getImage()).apply(RequestOptions.circleCropTransform()).into(imgPersonicon);
                    } else {
                        Glide.with(getActivity()).load(R.mipmap.morenicon).into(imgPersonicon);
                    }
                } else {
                    ToastUtil.getInstance().toastCentent(statusMsg);
                }

            }

            @Override
            public void error(String message) {
                Log.e("tag_登陆失败", message);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rl_persondetails, R.id.rl_office, R.id.rl_wallet, R.id.rl_jifen, R.id.rl_chengjiu, R.id.rl_informmessage, R.id.rl_setting})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_persondetails://用户详情
                intent = new Intent(getActivity(), UserInfoActivity.class);
                break;
            case R.id.rl_office://办公
                intent = new Intent(getActivity(), WorkActivity.class);
                break;
            case R.id.rl_wallet://钱包
                intent = new Intent(getActivity(), WalletActivity.class);
                break;
            case R.id.rl_jifen://积分
                intent = new Intent(getActivity(), JiFenActivity.class);
                intent.putExtra("icon", image);
                intent.putExtra("name", name);
//                ToastUtil.getInstance().toastCentent("敬请期待");
                break;
            case R.id.rl_chengjiu://成就
                ToastUtil.getInstance().toastCentent("敬请期待");
                break;
            case R.id.rl_informmessage://消息
                intent = new Intent(getActivity(), MessageActivity.class);
//                ToastUtil.getInstance().toastCentent("敬请期待");
                break;
            case R.id.rl_setting://设置
                intent = new Intent(getActivity(), SettingActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    //获取消息数量
    public void getMessageNum() {
        String url = null;
        Map map = new HashMap();
        switch (App.is_group) {
            case "0":
            case "1":
                url = PathUrl.MESSAGENUMURL;
                break;
            case "2":
                url = PathUrl.WBMESSAGENUMURL;
                break;
            default:
                url = PathUrl.MESSAGENUMURL;
                break;
        }
        map.put("CardNo", App.cardNo);
        OkhttpUtils.doGet(getActivity(), url, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取消息数量", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        MsgNumBean info = JSONUtils.toObject(data, MsgNumBean.class);
                        MsgNumBean.BodyBean body = info.getBody();
                        if (body.getCount() > 0) {
                            tvMessagenum.setVisibility(View.VISIBLE);
                            tvMessagenum.setText(body.getCount() + "");
                        } else {
                            tvMessagenum.setVisibility(View.GONE);
                        }

                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取消息数量失败", message);
            }
        });
    }
}
