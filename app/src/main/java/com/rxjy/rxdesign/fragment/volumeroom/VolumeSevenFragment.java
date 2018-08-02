package com.rxjy.rxdesign.fragment.volumeroom;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.DesDaiMeasureABean;
import com.rxjy.rxdesign.entity.LouPanBean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.OkhttpUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 楼盘
 */
public class VolumeSevenFragment extends BaseFragment {

    @Bind(R.id.et_new_loupan)
    EditText etNewLoupan;
    @Bind(R.id.et_new_qizuo)
    EditText etNewQizuo;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.et_new_louceng)
    EditText etNewLouceng;
    @Bind(R.id.et_new_menpaihao)
    EditText etNewMenpaihao;
    @Bind(R.id.btn_des_dai_measure_sub)
    Button btnDesDaiMeasureSub;
    @Bind(R.id.loupan_list)
    ListView loupanList;
    @Bind(R.id.et_shangquan)
    EditText etShangquan;

    int state = 0;

    ArrayList<String> louPanList;
    @Bind(R.id.tv_loupanconfirm)
    TextView tvLoupanconfirm;
    private String loupan;
    private String qizuo;
    private String louceng;
    private String menpaihao;
    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;
    private ArrayAdapter arrayAdapter;

    public void setLHouseData(DesDaiMeasureABean.BodyBean bean) {
        lhousedata = bean;
        if (lhousedata != null) {
            ShowView(lhousedata);
        } else {
            Log.e("tag_SevenFragment", "lhousedata为空");
        }
    }

    /**
     * 量房列表中的数据
     */
    private AllClientNewBean.ClientNewBean clientInfo;

    public void setClientInfo(AllClientNewBean.ClientNewBean info) {
        clientInfo = info;
    }

    @Override
    public int getInitId() {
        return R.layout.fragment_volume_seven;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        etNewLoupan.addTextChangedListener(new MyEditListener(etNewLoupan));
        louPanList = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lhousedata != null) {
            ShowView(lhousedata);
        } else {
            Log.e("tag_SevenFragment", "lhousedata为空");
        }
    }

    private void ShowView(DesDaiMeasureABean.BodyBean info) {
        etNewLoupan.setText(info.getCa_RealEstate());
        etNewQizuo.setText(info.getCa_RealEstatePeriod());
        etNewLouceng.setText(info.getCa_DevelopmentFloor());
        etNewMenpaihao.setText(info.getCa_HouseNumber());
    }

    @Override
    public void initAdapter() {
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, louPanList);
        loupanList.setAdapter(arrayAdapter);
    }

    @Override
    public void initLinstener() {
        loupanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                state = 1;
                etNewLoupan.setText(louPanList.get(position));
                louPanList.clear();
                arrayAdapter.notifyDataSetChanged();
                loupanList.setVisibility(View.GONE);
                tvLoupanconfirm.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_loupanconfirm)
    public void onViewClicked() {
        louPanList.clear();
        arrayAdapter.notifyDataSetChanged();
        loupanList.setVisibility(View.GONE);
        tvLoupanconfirm.setVisibility(View.GONE);
    }

    //自定义EditText监听
    private class MyEditListener implements TextWatcher {

        private EditText edittext;

        public MyEditListener(EditText edittext) {
            super();
            this.edittext = edittext;
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            int lengths = arg0.length();
            switch (edittext.getId()) {
                case R.id.et_new_loupan:
                    Log.e("tag_获取楼盘信息", state + "");
                    if (!TextUtils.isEmpty(etNewLoupan.getText().toString()) && state == 0) {
                        Map map = new HashMap();
                        map.put("Name", etNewLoupan.getText().toString());
                        OkhttpUtils.doGet(getActivity(), PathUrl.LPLISTURL, map, new OkhttpUtils.MyCall() {
                            @Override
                            public void success(String data) {
                                Log.e("tag_获取楼盘信息", data);
                                Type type = new TypeToken<ArrayList<LouPanBean>>() {
                                }.getType();
                                Gson gson = new Gson();
                                ArrayList<LouPanBean> louPanBean = gson.fromJson(data, type);
                                louPanList.clear();
                                if (louPanBean.size() > 0) {
                                    loupanList.setVisibility(View.VISIBLE);
                                    tvLoupanconfirm.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < louPanBean.size(); i++) {
                                        louPanList.add(louPanBean.get(i).getBuildName());
                                    }
                                    arrayAdapter.notifyDataSetChanged();
                                } else {
                                    tvLoupanconfirm.setVisibility(View.GONE);
                                    loupanList.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void error(String message) {
                                Log.e("tag_获取楼盘信息失败", message);
                            }
                        });
                    } else {
                        state = 0;
                        louPanList.clear();
                        tvLoupanconfirm.setVisibility(View.GONE);
                        loupanList.setVisibility(View.GONE);
                        arrayAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }
    }

}
