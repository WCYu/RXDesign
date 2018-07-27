package com.rxjy.rxdesign.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.adapter.my.MoneyDZaAdapter;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.RiseNumberTextView;
import com.rxjy.rxdesign.entity.MoneyDZaProcessBean;
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

public class MoneyDetailsZaActivity extends BaseActivity {

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
    @Bind(R.id.lv_za)
    ListView lvZa;

    int year, month;
    String cardno;
    private MoneyDZaAdapter moneyDZaAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_money_details_za;
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
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String money = intent.getStringExtra("money");
        tvMoney.setText(money);
        cardno = App.cardNo;
        Calendar c = Calendar.getInstance();
//        year = 2017;
        year = c.get(Calendar.YEAR);
//        month = 12;
        month = c.get(Calendar.MONTH) + 1;

        switch (type) {
            case "2":
                tvTitle.setText("过程");
                getzaprocessdata(year + "", month + "", cardno);
                break;
            case "3":
                tvTitle.setText("结果");
                getzaprocessdata(year + "", month + "", cardno);
                break;
        }
    }

    private void getzaprocessdata(String s, String s1, String cardno) {
        Map map = new HashMap();
        map.put("year", s);
        map.put("month", s1);
        map.put("cardNo", cardno);
        OkhttpUtils.doGet(this, PathUrl.ZAURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取钱包详情信息", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        MoneyDZaProcessBean info = JSONUtils.toObject(data, MoneyDZaProcessBean.class);
                        ArrayList<MoneyDZaProcessBean.BodyBean> body = info.getBody();
                        moneyDZaAdapter = new MoneyDZaAdapter(MoneyDetailsZaActivity.this, body);
                        lvZa.setAdapter(moneyDZaAdapter);
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取钱包详情失败", message);
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
