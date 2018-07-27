package com.rxjy.rxdesign.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.adapter.my.JifenZAAdapter;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.CircleProgressView;
import com.rxjy.rxdesign.entity.JiFenBean;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.StringUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JiFenActivity extends BaseActivity {

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
    @Bind(R.id.iv_icon)
    ImageView ivIcon;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.iv_starone)
    ImageView ivStarone;
    @Bind(R.id.iv_startwo)
    ImageView ivStartwo;
    @Bind(R.id.iv_starthree)
    ImageView ivStarthree;
    @Bind(R.id.iv_starfour)
    ImageView ivStarfour;
    @Bind(R.id.iv_starfive)
    ImageView ivStarfive;
    @Bind(R.id.tv_details)
    TextView tvDetails;
    @Bind(R.id.imageView18)
    ImageView imageView18;
    @Bind(R.id.textView26)
    TextView textView26;
    @Bind(R.id.gv_level)
    GridView gvLevel;
    @Bind(R.id.hsv_sv)
    HorizontalScrollView hsvSv;
    @Bind(R.id.imageView19)
    ImageView imageView19;
    @Bind(R.id.textView27)
    TextView textView27;
    @Bind(R.id.cpv_one)
    CircleProgressView cpvOne;
    @Bind(R.id.tv_cirtipone)
    TextView tvCirtipone;
    @Bind(R.id.tv_cirnumone)
    TextView tvCirnumone;
    @Bind(R.id.cpv_two)
    CircleProgressView cpvTwo;
    @Bind(R.id.tv_cirtiptwo)
    TextView tvCirtiptwo;
    @Bind(R.id.tv_cirnumtwo)
    TextView tvCirnumtwo;
    @Bind(R.id.imageView20)
    ImageView imageView20;
    @Bind(R.id.textView28)
    TextView textView28;
    @Bind(R.id.tv_moneyone)
    TextView tvMoneyone;
    @Bind(R.id.tv_moneytwo)
    TextView tvMoneytwo;
    @Bind(R.id.tv_moneythree)
    TextView tvMoneythree;
    @Bind(R.id.tv_moneyfour)
    TextView tvMoneyfour;

    ArrayList<String> list;
    JifenZAAdapter jifenZAAdapter;
    ArrayList<String> jidianlist;

    @Override
    public int getLayout() {
        return R.layout.activity_ji_fen;
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
        lyBackground.setBackgroundColor(getResources().getColor(R.color.transparent));
        tvTitle.setText("积分");
        Intent intent = getIntent();
        String icon = intent.getStringExtra("icon");
        String name = intent.getStringExtra("name");
        Glide.with(this).load(icon).apply(RequestOptions.circleCropTransform()).into(ivIcon);
        tvName.setText(name);
        getMessage(App.cardNo);
        cpvOne.setProgress(0, getResources().getColor(R.color.cir_greentwo), getResources().getColor(R.color.cir_greenone), "1", 60);
        cpvTwo.setProgress(0, getResources().getColor(R.color.cir_bluetwo), getResources().getColor(R.color.cir_blueone), "1", 60);
    }

    private void getMessage(String cardNo) {
        Map map = new HashMap();
        map.put("cardNo", cardNo);
        OkhttpUtils.doGet(this, PathUrl.ZAJIFENURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(final String data) {
                Log.e("tag_获取主案积分", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        JiFenBean info = JSONUtils.toObject(data, JiFenBean.class);
                        final ArrayList<JiFenBean.BodyBean> body = info.getBody();

                        if (!StringUtils.isEmpty(body)) {
                            list = new ArrayList<>();
                            jidianlist = new ArrayList<>();
                            list.add("1");
                            list.add("2");
                            list.add("3");
                            list.add("4");
                            list.add("5");
                            list.add("6");
                            list.add("7");
                            list.add("8");
                            list.add("9");
                            list.add("10");
                            jidianlist.add(StringUtils.getPrettyNumber(body.get(0).getOneRemind() + ""));
                            jidianlist.add(StringUtils.getPrettyNumber(body.get(0).getTwoRemind() + ""));
                            jidianlist.add(StringUtils.getPrettyNumber(body.get(0).getThreeRemind() + ""));
                            jidianlist.add(StringUtils.getPrettyNumber(body.get(0).getFourRemind() + ""));
                            jidianlist.add(StringUtils.getPrettyNumber(body.get(0).getFiveRemind() + ""));
                            jidianlist.add(StringUtils.getPrettyNumber(body.get(0).getSixRemind() + ""));
                            jidianlist.add(StringUtils.getPrettyNumber(body.get(0).getSevenRemind() + ""));
                            jidianlist.add(StringUtils.getPrettyNumber(body.get(0).getEightRemind() + ""));
                            jidianlist.add(StringUtils.getPrettyNumber(body.get(0).getNineRemind() + ""));
                            jidianlist.add(StringUtils.getPrettyNumber(body.get(0).getTenRemind() + ""));
                            tvDetails.setText(body.get(0).getDdeclaration());
                            setStar(body.get(0).getAchievementGrade());
                            tvMoneyone.setText("￥" + StringUtils.getPrettyNumber(body.get(0).getLessThenTwo() + ""));
                            tvMoneytwo.setText("￥" + StringUtils.getPrettyNumber(body.get(0).getTwoToFive() + ""));
                            tvMoneythree.setText("￥" + StringUtils.getPrettyNumber(body.get(0).getFiveToTwelve() + ""));
                            tvMoneyfour.setText("￥" + StringUtils.getPrettyNumber(body.get(0).getGreaterThenTwelve() + ""));
                            changeGridView();
                            jifenZAAdapter = new JifenZAAdapter(JiFenActivity.this, list);
                            gvLevel.setAdapter(jifenZAAdapter);
                            jifenZAAdapter.setNum(body.get(0).getGrade());
                            jifenZAAdapter.setList(jidianlist);
                            if (body.get(0).getGrade() > 3) {
                                new Handler().postDelayed((new Runnable() {
                                    @Override
                                    public void run() {
                                        hsvSv.scrollTo(300 * body.get(0).getGrade() - 600, 0);
                                    }
                                }), 3);
                            }

                            BigDecimal shengjicha = body.get(0).getUpStandard().subtract(body.get(0).getFinalMoney());
                            int shengjichaint = shengjicha.intValue();
                            if (shengjichaint > 0) {
                                tvCirnumone.setText(StringUtils.toWanYuan(shengjicha) + "万");
                            } else {
                                tvCirtipone.setText("完成升级任务");
                                tvCirnumtwo.setText(StringUtils.toWanYuan(body.get(0).getUpStandard()) + "万");
                            }

                            BigDecimal jiangjicha = body.get(0).getDownStandard().subtract(body.get(0).getFinalMoney());
                            int jiangjichaint = jiangjicha.intValue();
                            if (jiangjichaint > 0) {
                                tvCirnumtwo.setText(StringUtils.toWanYuan(jiangjicha) + "万");
                            } else {
                                tvCirtiptwo.setText("完成级别任务");
                                tvCirnumtwo.setText(StringUtils.toWanYuan(body.get(0).getDownStandard()) + "万");
                            }
                            BigDecimal oneh = new BigDecimal("100");
                            int shengjibi = body.get(0).getFinalMoney().divide(body.get(0).getUpStandard()).multiply(oneh).intValue();
                            int jiangjibi = body.get(0).getFinalMoney().divide(body.get(0).getDownStandard()).multiply(oneh).intValue();
                            Log.e("shengjibi" + shengjibi, "jiangjibi" + jiangjibi);
                            cpvOne.setProgress(shengjibi, getResources().getColor(R.color.cir_greenone), getResources().getColor(R.color.cir_greentwo), "1", 60);
                            cpvTwo.setProgress(jiangjibi, getResources().getColor(R.color.cir_blueone), getResources().getColor(R.color.cir_bluetwo), "1", 60);
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
                Log.e("tag_获取主案积分失败", message);
            }
        });
    }

    private void setStar(int i) {
        switch (i) {
            case 0:
                ivStarone.setVisibility(View.GONE);
                ivStartwo.setVisibility(View.GONE);
                ivStarthree.setVisibility(View.GONE);
                ivStarfour.setVisibility(View.GONE);
                ivStarfive.setVisibility(View.GONE);
                break;
            case 1:
                ivStarone.setVisibility(View.VISIBLE);
                ivStartwo.setVisibility(View.GONE);
                ivStarthree.setVisibility(View.GONE);
                ivStarfour.setVisibility(View.GONE);
                ivStarfive.setVisibility(View.GONE);
                break;
            case 2:
                ivStarone.setVisibility(View.VISIBLE);
                ivStartwo.setVisibility(View.VISIBLE);
                ivStarthree.setVisibility(View.GONE);
                ivStarfour.setVisibility(View.GONE);
                ivStarfive.setVisibility(View.GONE);
                break;
            case 3:
                ivStarone.setVisibility(View.VISIBLE);
                ivStartwo.setVisibility(View.VISIBLE);
                ivStarthree.setVisibility(View.VISIBLE);
                ivStarfour.setVisibility(View.GONE);
                ivStarfive.setVisibility(View.GONE);
                break;
            case 4:
                ivStarone.setVisibility(View.VISIBLE);
                ivStartwo.setVisibility(View.VISIBLE);
                ivStarthree.setVisibility(View.VISIBLE);
                ivStarfour.setVisibility(View.VISIBLE);
                ivStarfive.setVisibility(View.GONE);
                break;
            case 5:
                ivStarone.setVisibility(View.VISIBLE);
                ivStartwo.setVisibility(View.VISIBLE);
                ivStarthree.setVisibility(View.VISIBLE);
                ivStarfour.setVisibility(View.VISIBLE);
                ivStarfive.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void changeGridView() {
        // item宽度
        int itemWidth = 300;
        // item之间的间隔
        int itemPaddingH = 10;
        int size = list.size();
        // 计算GridView宽度
        int gridviewWidth = size * (itemWidth + itemPaddingH);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gvLevel.setLayoutParams(params);
        gvLevel.setColumnWidth(itemWidth);
        gvLevel.setHorizontalSpacing(itemPaddingH);
        gvLevel.setStretchMode(GridView.NO_STRETCH);
        gvLevel.setNumColumns(size);
    }

    @Override
    public void initAdapter() {

    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
