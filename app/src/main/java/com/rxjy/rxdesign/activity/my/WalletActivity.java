package com.rxjy.rxdesign.activity.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.MoneyTzView;
import com.rxjy.rxdesign.custom.RiseNumberTextView;
import com.rxjy.rxdesign.entity.MoneyDesBean;
import com.rxjy.rxdesign.entity.MoneyTzBean;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.StringUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.ly_background)
    LinearLayout lyBackground;
    @Bind(R.id.publish)
    ImageView publish;
    @Bind(R.id.tv_money)
    RiseNumberTextView tvMoney;
    @Bind(R.id.mv_one)
    MoneyTzView mvOne;
    @Bind(R.id.mv_two)
    MoneyTzView mvTwo;
    @Bind(R.id.mv_three)
    MoneyTzView mvThree;
    @Bind(R.id.mv_four)
    MoneyTzView mvFour;
    @Bind(R.id.mv_five)
    MoneyTzView mvFive;
    @Bind(R.id.mv_six)
    MoneyTzView mvSix;
    private int year;
    private int month;
    String desmoney, desgcmoney, desjfmoney;
    String tzxzresultmoney, tzxzgcmoney, tzxzjfmoney, tzxzfhmoney;
    Float moneyall;

    @Override
    public int getLayout() {
        return R.layout.activity_wallet;
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
        tvTitle.setText("钱包");
        lyBackground.setBackgroundColor(getResources().getColor(R.color.transparent));
        mvOne.setTitleAndType("总收（绩效收入=过程收入+结果收入）", "保底工资", "绩效收入", "取其高", "合计");
        mvTwo.setTitleAndType("过程", "过程收入", "", "", "合计");
        mvThree.setTitleAndType("结果", "结果收入", "", "", "合计");
        mvFour.setTitleAndType("奖罚", "奖金", "罚款", "签单奖金", "合计");
        mvFive.setTitleAndType("提成", "设计", "施工", "", "合计");
        mvSix.setTitleAndType("社保", "社保", "公积金", "", "合计");
        getMoneryData();
    }

    @Override
    public void initAdapter() {

    }

    @OnClick({R.id.img_back, R.id.tv_right, R.id.mv_one, R.id.mv_two, R.id.mv_three, R.id.mv_four, R.id.mv_five, R.id.mv_six})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_right:
//                ToastUtil.getInstance().toastCentent("暂无此功能！");
                break;
            case R.id.mv_one:
                DataDetails(1);
                break;
            case R.id.mv_two:
                DataDetails(2);
                break;
            case R.id.mv_three:
                DataDetails(3);
                break;
            case R.id.mv_four:
                DataDetails(4);
                break;
            case R.id.mv_five:
                DataDetails(5);
                break;
            case R.id.mv_six:
                DataDetails(6);
                break;
        }
    }

    /**
     * 查看详情
     */
    private void DataDetails(int type) {//种类
        switch (type) {
            case 2:
                startActivity(new Intent(this, MoneyDetailsZaActivity.class).putExtra("type", type + "").putExtra("money", desgcmoney));
                break;
            case 3:
                startActivity(new Intent(this, MoneyDetailsZaActivity.class).putExtra("type", type + "").putExtra("money", desmoney));
                break;
            case 4:
                startActivity(new Intent(this, MoneyDetailsTzTwoActivity.class).putExtra("type", "gcjiangfa").putExtra("money", desjfmoney));//主案奖罚和工程奖罚共用
                break;
        }
    }

    public void getMoneryData() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        Map map = new HashMap();
        map.put("year", year);
        map.put("month", month);
        map.put("cardNo", App.cardNo);
        OkhttpUtils.doGet(this, PathUrl.WTSMONEYURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取行政经理钱包", data);
                MoneyDesBean info = JSONUtils.toObject(data, MoneyDesBean.class);
                MoneyDesBean.BodyBean body = info.getBody();
                int statusCode = info.getStatusCode();
                String statusMsg = info.getStatusMsg();
                if (statusCode == 0) {
                    moneyall = Float.valueOf(body.getComprehensiveIncome());
                    tvMoney.withNumber(moneyall, false).start();
                    mvOne.setContent(body.getDesignerAchievements(), body.getAchievementsWages(), body.getTakeItHigh(), body.getComprehensiveIncome());
                    desgcmoney = body.getProcessIncome();
                    mvTwo.setContent(body.getProcessIncome(), "", "", body.getProcessIncome());
                    desmoney = body.getResultMoney();
                    mvThree.setContent(body.getResultMoney(), "", "", body.getResultMoney());
                    desjfmoney = body.getWagesMoney();
                    mvFour.setContent(body.getCashMoney(), body.getWagesMoney(), body.getBonus(), body.getWagesTotal());
                    mvFive.setContent(body.getDesignCommission(), body.getShiGongCommission(), "", body.getCommissionTotal());
                    mvSix.setContent(body.getSocialSecurity(), body.getHousingFund(), "", body.getHstotal());
                } else {
                    ToastUtil.getInstance().toastCentent(statusMsg);
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取政经理钱包失败", message);
            }
        });

    }
}
