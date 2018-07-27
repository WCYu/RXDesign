package com.rxjy.rxdesign.activity.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.adapter.my.NewsRedAdapter;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.InfoMessageBean;
import com.rxjy.rxdesign.entity.InfoMessageBodyBean;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedEnvelopesActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.ly_background)
    LinearLayout lyBackground;
    @Bind(R.id.task_list)
    ListView taskList;
    private NewsRedAdapter newsRedAdapter;
    private ArrayList<InfoMessageBodyBean> list;

    @Override
    public int getLayout() {
        return R.layout.activity_red_envelopes;
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
        tvTitle.setText("红包任务");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRedTaskData();
    }

    @Override
    public void initAdapter() {
        list = new ArrayList<>();
        newsRedAdapter = new NewsRedAdapter(this, list);
        taskList.setAdapter(newsRedAdapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(RedEnvelopesActivity.this, MessageDetailsActivity.class).putExtra("id", list.get(i).getId() + ""));
            }
        });
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    public void getRedTaskData() {
        String url = null;
        Map map = new HashMap();
        switch (App.is_group) {
            case "0":
            case "1":
                url = PathUrl.REDTASKURL;
                map.put("cardNo", App.cardNo);
                map.put("Group", getIntent().getStringExtra("group_id"));
                break;
            case "2":
                url = PathUrl.WBREDTASKURL;
                map.put("CardNo", App.cardNo);
                map.put("Group", getIntent().getStringExtra("group_id"));
                break;
            default:
                url = PathUrl.REDTASKURL;
                map.put("cardNo", App.cardNo);
                map.put("Group", getIntent().getStringExtra("group_id"));
                break;
        }
        OkhttpUtils.doGet(this, url, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取红包任务列表", data);
                InfoMessageBean info = JSONUtils.toObject(data, InfoMessageBean.class);
                ArrayList<InfoMessageBodyBean> body = info.getBody();
                int statusCode = info.getStatusCode();
                String statusMsg = info.getStatusMsg();
                if (statusCode == 0) {
                    list.clear();
                    list.addAll(body);
                    newsRedAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.getInstance().toastCentent(statusMsg);
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取红包任务列表失败", message);
            }
        });
    }
}
