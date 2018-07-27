package com.rxjy.rxdesign.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.my.UserInfoModifyActivity;
import com.rxjy.rxdesign.activity.utils.WebViewActivity;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.BannerBean;
import com.rxjy.rxdesign.entity.BannerDataBean;
import com.rxjy.rxdesign.entity.NewUserDataBean;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.GlideRoundTransform;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 新入职员工首页
 */
public class NewPeopleHomeFragment extends BaseFragment {

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
    @Bind(R.id.vp_banner)
    ViewPager vpBanner;
    @Bind(R.id.tv_ziliao)
    TextView tvZiliao;
    @Bind(R.id.ly_ziliao)
    LinearLayout lyZiliao;
    @Bind(R.id.tv_cheliang)
    TextView tvCheliang;
    @Bind(R.id.ly_cheliang)
    LinearLayout lyCheliang;
    @Bind(R.id.tv_zhusu)
    TextView tvZhusu;
    @Bind(R.id.ly_zhusu)
    LinearLayout lyZhusu;

    int index = 0;
    int size = 0;
    String phonenum, cardno;
    private PagerAdapter pagerAdapter;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 4) {
                if (index >= size) index = 0;
                if (vpBanner != null) {
                    vpBanner.setCurrentItem(index++);
                    handler.sendEmptyMessageDelayed(4, 3000);
                } else {
                    handler.removeMessages(4);
                }
            }
        }

    };

    @Override
    public int getInitId() {
        return R.layout.fragment_new_people_home;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        tvTitle.setText("首页");
        imgBack.setVisibility(View.GONE);
        getUserData();
        cardno = "00001236";
        getBannerList(App.cardNo);
        switch (App.is_group) {
            case "0":
                lyCheliang.setVisibility(View.GONE);
                lyZhusu.setVisibility(View.GONE);
                break;
            case "1":

                break;
        }
    }

    private void getBannerList(String s) {
        Map map = new HashMap();
        map.put("a_ccount", s);
        OkhttpUtils.doGet(getActivity(), PathUrl.BANNERLISTGURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取首页banner", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        BannerBean info = JSONUtils.toObject(data, BannerBean.class);
                        ShowBanner(info.getBody());
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取首页banner失败", message);
            }
        });
    }

    public void getUserData() {
        Map map = new HashMap();
        map.put("card", App.cardNo);
        OkhttpUtils.doGet(PathUrl.NEWYUANGONGURL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("tag_新人信息失败", e.getMessage().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.e("tag_新人信息", string);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        NewUserDataBean dataBean = gson.fromJson(string, NewUserDataBean.class);
                        int statusCode = dataBean.getStatusCode();
                        String statusMsg = dataBean.getStatusMsg();
                        NewUserDataBean.BodyBean body = dataBean.getBody();
                        if (statusCode == 0) {
                            String carstate = body.getCarstate();
                            String staystate = body.getStaystate();
                            String z2state = body.getZ2state();
                            jugdeText(carstate, tvCheliang);
                            jugdeText(staystate, tvZhusu);
                            jugdeText(z2state, tvZiliao);
                        } else {
                            ToastUtil.getInstance().toastCentent(statusMsg, getActivity());
                        }
                    }
                });
            }
        });
    }

    private void ShowBanner(final ArrayList<BannerDataBean> datalist) {

        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return datalist.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = View.inflate(getActivity(), R.layout.vp_bannerview, null);
                ImageView iv_bannerimg = (ImageView) view.findViewById(R.id.iv_bannerimg);
                TextView tv_bannerdescribe = (TextView) view.findViewById(R.id.tv_bannerdescribe);
                TextView tv_bannertodetails = (TextView) view.findViewById(R.id.tv_bannertodetails);
                RequestOptions options = new RequestOptions();
                options.centerCrop().transform(new GlideRoundTransform(getActivity(), 10));
                Glide.with(getActivity()).load(datalist.get(position).getBanner_img()).apply(options).into(iv_bannerimg);
                tv_bannerdescribe.setText(datalist.get(position).getBanner_title());
                tv_bannertodetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", datalist.get(position).getBanner_content());
                        intent.putExtra("title", "详情");
                        startActivity(intent);
                    }
                });
                iv_bannerimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", datalist.get(position).getBanner_content());
                        intent.putExtra("title", "详情");
                        startActivity(intent);
                    }
                });
                container.addView(view);
                return view;
            }
        };
        vpBanner.setAdapter(pagerAdapter);
        vpBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handler.sendEmptyMessageDelayed(4, 3000);
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

    @OnClick({R.id.ly_ziliao, R.id.ly_cheliang, R.id.ly_zhusu})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ly_ziliao:
                intent = new Intent(getActivity(), UserInfoModifyActivity.class);
                break;
            case R.id.ly_cheliang:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://i.rxjy.com/AppGroup/APPIndex/CarMessage?card=" + App.cardNo);
                intent.putExtra("name", "车辆信息");
                intent.putExtra("type", "新人");
                break;
            case R.id.ly_zhusu:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://i.rxjy.com/AppGroup/APPIndex/StayMessage?card=" + App.cardNo);
                intent.putExtra("name", "住宿信息");
                intent.putExtra("type", "新人");
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void jugdeText(String data, TextView view) {
        if (data != null && view != null) {
            switch (data) {
                case "未":
                    view.setText("未");
                    view.setTextColor(Color.parseColor("#e60012"));
                    view.setBackgroundResource(R.drawable.tv_back_red);
                    break;
                case "待":
                    view.setText("待");
                    view.setTextColor(Color.parseColor("#E0FF6100"));
                    view.setBackgroundResource(R.drawable.tv_back_cheng);
                    break;
                case "全":
                    view.setText("全");
                    view.setTextColor(Color.parseColor("#39d27f"));
                    view.setBackgroundResource(R.drawable.tv_back_lv);
                    break;
            }
        }
    }
}
