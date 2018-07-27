package com.rxjy.rxdesign.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.adapter.home.PlanImgAdapter;
import com.rxjy.rxdesign.adapter.home.PlanImgCaiAdapter;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.entity.PlanImageBean;
import com.rxjy.rxdesign.entity.PlanStrBean;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.StringUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanActivity extends BaseActivity {

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
    @Bind(R.id.tv_from)
    TextView tvFrom;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.gv_chu)
    GridView gvChu;
    @Bind(R.id.gv_cai)
    GridView gvCai;
    @Bind(R.id.tv_describe)
    TextView tvDescribe;

    ArrayList<String> chuimglist = new ArrayList<>();
    ArrayList<String> caiimglist = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.activity_plan;
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
        tvTitle.setText("方案");
        lyBackground.setBackgroundColor(getResources().getColor(R.color.transparent));
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String address = intent.getStringExtra("address");
        String rwdid = intent.getStringExtra("rwdid");
        tvFrom.setText(title);
        tvAddress.setText(address);
        GetTxt(rwdid);
        GetImage(rwdid);
    }

    private void GetImage(String rwdid) {
        Map map = new HashMap();
        map.put("rwdId", rwdid);
        OkhttpUtils.doGet(this, PathUrl.FAIMGURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取方案图片", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        PlanImageBean info = JSONUtils.toObject(data, PlanImageBean.class);
                        List<PlanImageBean.Data> body = info.getBody();
                        chuimglist.clear();
                        caiimglist.clear();
                        for (int i = 0; i < body.size(); i++) {
                            if (body.get(i).getCatalogID() == 57 && !StringUtils.isEmpty(body.get(i).getImageUrl())) {//初稿
                                chuimglist.add(body.get(i).getImageUrl());
                            } else if (body.get(i).getCatalogID() == 90 && !StringUtils.isEmpty(body.get(i).getImageUrl())) {//彩平
                                caiimglist.add(body.get(i).getImageUrl());
                            }
                        }
                        ShowChuimgs();
                        ShowCaiimgs();
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取方案图片失败", message);
            }
        });
    }

    /**
     * 初稿
     */
    PlanImgAdapter chuadapter;

    private void ShowChuimgs() {
        chuadapter = new PlanImgAdapter(this, chuimglist);
        gvChu.setAdapter(chuadapter);
        gvChu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (chuimglist.size() > 0) {
                    watchLargerImage("", chuimglist, position, "", "");
                }
            }
        });
    }

    /**
     * 彩平
     */
    PlanImgCaiAdapter caiadapter;

    private void ShowCaiimgs() {
        caiadapter = new PlanImgCaiAdapter(this, caiimglist);
        gvCai.setAdapter(caiadapter);
        gvCai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (caiimglist.size() > 0) {
                    watchLargerImage("", caiimglist, position, "", "");
                }
            }
        });
    }

    private void GetTxt(String rwdid) {
        Map map = new HashMap();
        map.put("rwdId", rwdid);
        OkhttpUtils.doGet(this, PathUrl.FADATAURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取方案说明", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        PlanStrBean info = JSONUtils.toObject(data, PlanStrBean.class);
                        PlanStrBean.Data body = info.getBody();
                        if (!StringUtils.isEmpty(body.getProjectBrief())) {
                            tvDescribe.setText(body.getProjectBrief());
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
                Log.e("tag_获取方案说明失败", message);
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
