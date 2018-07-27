package com.rxjy.rxdesign.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.OrderGetBean;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.entity.PersonDataBean;
import com.rxjy.rxdesign.entity.ResultBean;
import com.rxjy.rxdesign.utils.Constants;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 接单
* */
public class OrderGetActivity extends BaseActivity {

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
    @Bind(R.id.tv_projectnum)
    TextView tvProjectnum;
    @Bind(R.id.v_projectnum)
    TextView vProjectnum;
    @Bind(R.id.tv_projectname)
    TextView tvProjectname;
    @Bind(R.id.v_projectname)
    TextView vProjectname;
    @Bind(R.id.tv_shuxing)
    TextView tvShuxing;
    @Bind(R.id.v_shuxing)
    TextView vShuxing;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.v_type)
    TextView vType;
    @Bind(R.id.tv_decorate)
    TextView tvDecorate;
    @Bind(R.id.v_decorate)
    TextView vDecorate;
    @Bind(R.id.tv_lhouse)
    TextView tvLhouse;
    @Bind(R.id.v_lhouse)
    TextView vLhouse;
    @Bind(R.id.tv_use)
    TextView tvUse;
    @Bind(R.id.v_use)
    TextView vUse;
    @Bind(R.id.tv_yumoney)
    TextView tvYumoney;
    @Bind(R.id.v_yumoney)
    TextView vYumoney;
    @Bind(R.id.tv_no)
    TextView tvNo;
    @Bind(R.id.tv_accept)
    TextView tvAccept;

    String orderid;
    private AllClientNewBean.ClientNewBean infos;

    @Override
    public int getLayout() {
        return R.layout.activity_order_get;
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
        tvTitle.setText("接单");
        Intent intent = getIntent();
        orderid = intent.getStringExtra("orderid");
        infos = (AllClientNewBean.ClientNewBean) intent.getSerializableExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO);
        getJieDanData();
    }

    @Override
    public void initAdapter() {

    }

    @OnClick({R.id.img_back, R.id.tv_no, R.id.tv_accept})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_no://拒单
                orderDeal(2);
                break;
            case R.id.tv_accept://接单
                orderDeal(1);
                break;
        }
    }

    private void orderDeal(int i) {
        Map map = new HashMap();
        map.put("rwdid", orderid);
        map.put("formpars", "{ci_ReciveStatus:" + i + "}");
        map.put("type", "基本");
        OkhttpUtils.doGet(this, PathUrl.JIEDANTURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_接单", data);
                ResultBean info = JSONUtils.toObject(data, ResultBean.class);
                int statusCode = info.getStatusCode();
                String statusMsg = info.getStatusMsg();
                if (statusCode == 0) {
                    ToastUtil.getInstance().toastCentent("接单成功！");//接单或者拒单成功。。。。做下步操作
                    Intent intent = new Intent(OrderGetActivity.this, DesDaiMeasureActivity.class);
                    intent.putExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO, infos);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.getInstance().toastCentent(statusMsg);
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_接单失败", message);
            }
        });
    }

    public void getJieDanData() {
        Map map = new HashMap();
        map.put("rwdId", orderid);
        OkhttpUtils.doGet(this, PathUrl.GETJIEDANURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取接单信息", data);
                OrderGetBean info = JSONUtils.toObject(data, OrderGetBean.class);
                OrderGetBean.BodyBean body = info.getBody();
                int statusCode = info.getStatusCode();
                String statusMsg = info.getStatusMsg();
                if (statusCode == 0) {
                    tvProjectnum.setText(orderid);
                    tvProjectname.setText(info.getBody().getBaseInformation().getCi_ClientName());
                    tvShuxing.setText(info.getBody().getDecorateInformation().getCa_proAttribute());
                    tvType.setText(info.getBody().getBaseInformation().getCi_Type());
                    tvDecorate.setText(info.getBody().getDecorateInformation().getCa_DecorationDate());
                    tvLhouse.setText(info.getBody().getHousingResourcesInformation().getCa_MeasureDate());
                    tvUse.setText(info.getBody().getHousingResourcesInformation().getCi_Area() + " ㎡");
                    tvYumoney.setText(info.getBody().getDecorateInformation().getCa_DecBudgetPrice() + " 万");
                } else {
                    ToastUtil.getInstance().toastCentent(statusMsg);
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取接单失败", message);
            }
        });
    }
}
