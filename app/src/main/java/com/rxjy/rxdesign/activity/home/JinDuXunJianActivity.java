package com.rxjy.rxdesign.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.adapter.home.XunjianAdapter;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.GetZaishiInfo;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.entity.XunjianListInfo;
import com.rxjy.rxdesign.utils.Constants;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
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

public class JinDuXunJianActivity extends BaseActivity {

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
    @Bind(R.id.tv_xujnian_xiangmuname)
    TextView tvXujnianXiangmuname;
    @Bind(R.id.tv_xunjian_xiangmuaddress)
    TextView tvXunjianXiangmuaddress;
    @Bind(R.id.lv_jinduxunjian)
    ListView lvJinduxunjian;

    String gongsiname;
    String rwdId;
    XunjianAdapter mAdapter;
    List<XunjianListInfo.BodyBean> list;
    List<XunjianListInfo.BodyBean> bodyBean;

    @Override
    public int getLayout() {
        return R.layout.activity_jin_du_xun_jian;
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
        tvTitle.setText("进度巡检");
        lyBackground.setBackgroundColor(getResources().getColor(R.color.transparent));
        GetZaishiInfo.BodyBean info = (GetZaishiInfo.BodyBean) getIntent().getSerializableExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO);
        rwdId = info.getCi_RwdId();

        gongsiname = info.getCi_ClientName();
        list = new ArrayList<>();
        mAdapter = new XunjianAdapter(this, list, gongsiname);
        lvJinduxunjian.setAdapter(mAdapter);
        tvXujnianXiangmuname.setText(info.getCi_ClientName());
        tvXunjianXiangmuaddress.setText(info.getCi_DecorationAddress());
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getScheduleCheckList(rwdId);
    }

    private void getScheduleCheckList(String rwdId) {
        Map map = new HashMap();
        map.put("rwdId", rwdId);
        OkhttpUtils.doGet(this, PathUrl.XJLISTURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取巡检列表", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        XunjianListInfo info = JSONUtils.toObject(data, XunjianListInfo.class);
                        bodyBean = info.getBody();
                        list.clear();
                        list.addAll(bodyBean);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取巡检列表失败", message);
            }
        });
    }

    private void initListener() {
        lvJinduxunjian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(JinduXunjianActivity.this, JinduxunjianXQYActivty.class);
//                intent.putExtra("scid", bodyBean.get(position).getSc_Id());
//                intent.putExtra("stateName", bodyBean.get(position).getSc_StateName());
//                startActivity(intent);
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
