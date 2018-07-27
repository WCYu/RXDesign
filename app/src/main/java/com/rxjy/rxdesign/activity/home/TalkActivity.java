package com.rxjy.rxdesign.activity.home;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.more.ReturnGuestActivity;
import com.rxjy.rxdesign.adapter.home.TalkAdapter;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.XListView;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.GetHuiFang;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.utils.AutoUtils;
import com.rxjy.rxdesign.utils.Constants;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 其他方案
* */
public class TalkActivity extends BaseActivity {

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
    @Bind(R.id.zsxm_lv)
    XListView zsxmLv;
    @Bind(R.id.tv_liangfang)
    TextView tvLiangfang;
    @Bind(R.id.tv_plan)
    TextView tvPlan;
    @Bind(R.id.tv_shigongyiqian)
    TextView tvShigongyiqian;
    @Bind(R.id.tv_contract)
    TextView tvContract;
    @Bind(R.id.ll_liangfangandyiqian)
    LinearLayout llLiangfangandyiqian;
    @Bind(R.id.ly_liangfang)
    LinearLayout lyLiangfang;
    @Bind(R.id.ly_fangan)
    LinearLayout lyFangan;
    @Bind(R.id.ly_yusuan)
    LinearLayout lyYusuan;
    @Bind(R.id.ly_qiatan)
    LinearLayout lyQiatan;

    AllClientNewBean.ClientNewBean info;
    private TalkAdapter adapter;
    private List<GetHuiFang.Body> list;

    int idex = 1;
    private PopupWindow popupWindow;
    private AlertDialog alertDialog;

    @Override
    public int getLayout() {
        return R.layout.activity_talk;
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
        tvTitle.setText("在谈项目");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("更多");
        info = (AllClientNewBean.ClientNewBean) getIntent().getSerializableExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO);
        list = new ArrayList<>();
        adapter = new TalkAdapter(this, list);
        zsxmLv.setAdapter(adapter);
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int idex = 1;
        list.clear();
        getHuiFangData(info.getCi_RwdId(), idex);
    }

    private void initListener() {
        zsxmLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ToastUtil.getInstance().toastCentent(i + "");
            }
        });
        zsxmLv.setPullLoadEnable(false);
        zsxmLv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                getHuiFangData(info.getCi_RwdId(), idex);
                onLoad();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    private void onLoad() {
        zsxmLv.stopRefresh();
        zsxmLv.stopLoadMore();
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        //格式化
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        String time = formatter.format(curDate);
        zsxmLv.setRefreshTime(time);
    }

    private void getHuiFangData(String ci_rwdId, int i) {
        showLoading();
        Map map = new HashMap();
        map.put("rwdid", ci_rwdId);
        map.put("pageIndex", i);
        OkhttpUtils.doGet(this, PathUrl.QTDATAURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取用户信息", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        GetHuiFang info = JSONUtils.toObject(data, GetHuiFang.class);
                        List<GetHuiFang.Body> body = info.getBody();
                        if (idex == 1) {
                            list.addAll(0, body);
                            idex++;
                        } else if (idex > 1) {
                            zsxmLv.setTranscriptMode(0);
                            if (body.size() > 0) {
                                list.addAll(0, body);
                                idex++;
                            } else {
                                ToastUtil.getInstance().toastCentent("没有更多数据");
                            }
                        }
                        adapter.notifyDataSetChanged();
                        zsxmLv.setSelection(1 + body.size());
                        dismissLoading();
                    } else {
                        dismissLoading();
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取失败", message);
                dismissLoading();
            }
        });
        setProgressDialog(3000);
    }

    @Override
    public void initAdapter() {

    }

    @OnClick({R.id.img_back, R.id.tv_right, R.id.ly_liangfang, R.id.ly_fangan, R.id.ly_yusuan, R.id.ly_qiatan})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_right:
                View inflate = LayoutInflater.from(TalkActivity.this).inflate(R.layout.popu_more, null);
                popupWindow(inflate, tvRight);
                break;
            case R.id.ly_liangfang:
                intent = new Intent(TalkActivity.this, DesDaiMeasureActivity.class);
                intent.putExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO, getIntent().getSerializableExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO));
                break;
            case R.id.ly_fangan:
                intent = new Intent(TalkActivity.this, PlanActivity.class);
                intent.putExtra("title", info.getCi_ClientName());
                intent.putExtra("address", info.getCi_DecorationAddress());
                intent.putExtra("rwdid", info.getCi_RwdId());
                break;
            case R.id.ly_yusuan:
                break;
            case R.id.ly_qiatan:
                intent = new Intent(TalkActivity.this, BackVisitActivity.class);
                intent.putExtra("cid", info.getCi_RwdId());
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void popupWindow(View vicinityView, View view) {
        AutoUtils.auto(vicinityView);
        popupWindow = new PopupWindow(vicinityView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(view, 0, 0);
        TextView sgyqTv = vicinityView.findViewById(R.id.tv_shigong);
        TextView htTv = vicinityView.findViewById(R.id.tv_hetong);
        sgyqTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TalkActivity.this, ConstructionActivity.class);
                intent.putExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO, info);
                startActivity(intent);
//                ToastUtil.getInstance().toastCentent("敬请期待");
                popupWindow.dismiss();
            }
        });
        htTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TalkActivity.this, CompactActivity.class);
                intent.putExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO, info);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
    }

}
