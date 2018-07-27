package com.rxjy.rxdesign.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.adapter.my.MoneyDLisTwoAdapter;
import com.rxjy.rxdesign.adapter.my.MoneyDListAdapter;
import com.rxjy.rxdesign.adapter.my.MoneyDTitleAdapter;
import com.rxjy.rxdesign.adapter.my.MoneyDZaRewardAdapter;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.RiseNumberTextView;
import com.rxjy.rxdesign.entity.MoneyDTzFenhongBean;
import com.rxjy.rxdesign.entity.MoneyDTzRewardBean;
import com.rxjy.rxdesign.entity.MoneyDZaRewardBean;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoneyDetailsTzTwoActivity extends BaseActivity {

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
    @Bind(R.id.tv_money)
    RiseNumberTextView tvMoney;
    @Bind(R.id.gv_one)
    GridView gvOne;
    @Bind(R.id.lv_one)
    ListView lvOne;

    MoneyDTitleAdapter moneyDTitleAdapter;
    MoneyDZaRewardAdapter moneyDZaRewardAdapter;

    int year, month;
    String cardno;

    @Override
    public int getLayout() {
        return R.layout.activity_money_details_tz_two;
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

    /**
     * 显示种类标题
     */
    ArrayList<String> titlelist;

    private void ShowTitle() {
        gvOne.setNumColumns(titlelist.size());
        moneyDTitleAdapter = new MoneyDTitleAdapter(this, titlelist);
        gvOne.setAdapter(moneyDTitleAdapter);
    }

    ArrayList<MoneyDTzFenhongBean.BodyBean> fenhongdata;
    ArrayList<MoneyDTzRewardBean.BodyBean> rewarddata;

    @Override
    public void intData() {
        cardno = App.cardNo;
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String money = intent.getStringExtra("money");
        tvMoney.setText(money);
        titlelist = new ArrayList<>();
        fenhongdata = new ArrayList<>();
        rewarddata = new ArrayList<>();
        switch (type) {
            case "fenhong":
                tvTitle.setText("分红");
                titlelist.add("日期");
                titlelist.add("金额");
                titlelist.add("比例");
                titlelist.add("分红");
                ShowTitle();
                break;
            case "jiangfa":
                tvTitle.setText("奖罚");
                titlelist.add("日期");
                titlelist.add("来源");
                titlelist.add("内容");
                titlelist.add("金额");
                titlelist.add("状态");
                ShowTitle();
                break;
            case "gcjiangfa":
                tvTitle.setText("奖罚");
                titlelist.add("时间");
                titlelist.add("原因");
                titlelist.add("金额");
                titlelist.add("发起人");
                ShowTitle();
                getzarewarddata(year + "", month + "", cardno);
                break;
            case "swgongzi":
                tvTitle.setText("工资");
                titlelist.add("时间");
                titlelist.add("理由");
                titlelist.add("备注");
                titlelist.add("补贴金额");
                ShowTitle();
                break;
            case "swhistory":
                tvTitle.setText("历史");
                titlelist.add("月份");
                titlelist.add("工资");
                titlelist.add("提成");
                titlelist.add("综合");
                titlelist.add("剩余");
                ShowTitle();
                break;
        }
    }

    private void getzarewarddata(String s, String s1, String cardno) {
        Map map = new HashMap();
        map.put("year", s);
        map.put("month", s1);
        map.put("cardNo", cardno);
        OkhttpUtils.doGet(this, PathUrl.ZAJFURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取分红信息", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        MoneyDZaRewardBean info = JSONUtils.toObject(data, MoneyDZaRewardBean.class);
                        ArrayList<MoneyDZaRewardBean.BodyBean> body = info.getBody();
                        moneyDZaRewardAdapter = new MoneyDZaRewardAdapter(MoneyDetailsTzTwoActivity.this, body);
                        lvOne.setAdapter(moneyDZaRewardAdapter);
                        lvOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                startActivity(new Intent(MoneyDetailsTzTwoActivity.this, TextDetailsActivity.class).putExtra("txt", data.getBody().get(position).getRpRemarks()));
                            }
                        });
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取分红失败", message);
            }
        });
    }

    @Override
    public void initAdapter() {

    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
