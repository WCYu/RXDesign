package com.rxjy.rxdesign.fragment.volumeroom;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.home.DesAlbumActivity;
import com.rxjy.rxdesign.activity.home.DesDaiMeasureActivity;
import com.rxjy.rxdesign.adapter.home.DesDaiImgAdapter;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.CustomGridView;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.AllImagesInfo;
import com.rxjy.rxdesign.entity.DesDaiMeasureABean;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.Constants;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.rxjy.rxdesign.utils.Constants.LF_NUM;

public class VolumeTwoFragment extends BaseFragment {

    @Bind(R.id.gv_des_dai_measure_photo)
    CustomGridView gvDesDaiMeasurePhoto;

    //量房详情信息
    private DesDaiMeasureABean.BodyBean lhousedata;
    private DesDaiImgAdapter mAdapter;
    double allmoney;
    BigDecimal bigDecimal;
    double showmoney;
    int nowphotonum;
    int moneynum;
    public int position;
    private AllImagesInfo.Album ainfo;

    private ArrayList<AllImagesInfo.Album> albumList;
    private DesDaiMeasureActivity activity;

    public void setLHouseData(DesDaiMeasureABean.BodyBean bean) {
        lhousedata = bean;

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
        return R.layout.fragment_volume_two;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        activity = (DesDaiMeasureActivity) getActivity();
        albumList = new ArrayList<>();
        mAdapter = new DesDaiImgAdapter(getActivity(), albumList);
        gvDesDaiMeasurePhoto.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (clientInfo != null) {
            getDaiMeasureImageData(clientInfo.getCi_RwdId(), "1");
        }
    }

    private void getDaiMeasureImageData(String ci_rwdId, String type) {
        Map map = new HashMap();
        map.put("ci_rwdid", ci_rwdId);
        map.put("enumType", type);
        //http://app.wenes.cn/api/DWorksDetail/GetAllImages
        OkhttpUtils.doGet(getActivity(), PathUrl.GELFIMGURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取图片信息", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        AllImagesInfo info = JSONUtils.toObject(data, AllImagesInfo.class);
                        List<AllImagesInfo.Album> body = info.getBody();
                        albumList.clear();
                        albumList.addAll(body);
                        mAdapter.notifyDataSetChanged();
                        //设置钱数。。个数
                        nowphotonum = 0;
                        for (int i = 0; i < body.size(); i++) {
                            if (body.get(i).getChildList().size() > 0) {
                                nowphotonum = nowphotonum + 1;
                            }
                        }
                        Log.e("nowphotonum", nowphotonum + "");
                        Log.e("当前个数总：", activity.moneynum + "");
                        Log.e("提交的个数总：", activity.moneynum - activity.lfphotonum + nowphotonum + "");
                        activity.setMoneynum(activity.moneynum - activity.lfphotonum + nowphotonum);
                        moneynum = activity.moneynum - activity.lfphotonum + nowphotonum;
                        allmoney = activity.getmaxmoney;
                        showmoney = allmoney / LF_NUM * moneynum;
                        bigDecimal = new BigDecimal(showmoney);
                        showmoney = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        activity.money = showmoney;
                        Log.e("金额；", activity.money + "");
                        activity.setMoney(activity.money);
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取获取图片信息失败", message);
            }
        });
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initLinstener() {
        gvDesDaiMeasurePhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                AllImagesInfo.Album info = albumList.get(position);
                ainfo = info;
                List<AllImagesInfo.Album.image> imageList = info.getChildList();
                Intent albumIntent = new Intent(getActivity(), DesAlbumActivity.class);
                albumIntent.putExtra(Constants.ACTION_TO_ALBUM_IMG_INFO, (ArrayList<AllImagesInfo.Album.image>) imageList);
                albumIntent.putExtra(Constants.ACTION_TO_ALBUM_IMG_WORKS_ID, clientInfo.getWorksID());
                albumIntent.putExtra(Constants.ACTION_TO_ALBUM_IMG_CATALOG_ID, info.getCatalogID());
                albumIntent.putExtra(Constants.ACTION_TO_ALBUM_IMG_USER_ID, clientInfo.getCustomerID());
                startActivity(albumIntent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
