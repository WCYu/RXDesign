package com.rxjy.rxdesign.fragment.volumeroom;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.DesDaiMeasureABean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.JSONUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    private String loupan;
    private String qizuo;
    private String louceng;
    private String menpaihao;
    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;

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

    }

    @Override
    public void initLinstener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
