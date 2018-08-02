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

/**
 * 图片
 */
public class DesDaiSurroundingsFragment extends BaseFragment {


    @Bind(R.id.gv_des_dai_surroundings_photo)
    CustomGridView gvDesDaiSurroundingsPhoto;

    private List<AllImagesInfo.Album> albumList;

    private DesDaiImgAdapter mAdapter;
    //客户信息
    private AllClientNewBean.ClientNewBean clientInfo;
    private DesDaiMeasureABean.BodyBean lhousedata;
    private DesDaiMeasureActivity activity;
    int position;
    private AllImagesInfo.Album ainfo;

    public void setClientInfo(AllClientNewBean.ClientNewBean info) {
        clientInfo = info;
    }

    @Override
    public int getInitId() {
        return R.layout.fragment_des_dai_surroundings;
    }

    public void setLHouseData(DesDaiMeasureABean.BodyBean bean) {
        lhousedata = bean;

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
        gvDesDaiSurroundingsPhoto.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDaiSurroundingsData(clientInfo.getCi_RwdId(), "2");
    }

    private void getDaiSurroundingsData(String ci_rwdId, String s) {
        Map map = new HashMap();
        map.put("ci_rwdid", ci_rwdId);
        map.put("enumType", s);
        OkhttpUtils.doGet(getActivity(), PathUrl.GELFIMGURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取用户信息", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        AllImagesInfo info = JSONUtils.toObject(data, AllImagesInfo.class);
                        List<AllImagesInfo.Album> body = info.getBody();
                        setLFHJData(body);
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取失败", message);
            }
        });
    }

    double allmoney;
    BigDecimal bigDecimal;
    double showmoney;
    int nowphotonum;
    int moneynum;

    private void setLFHJData(List<AllImagesInfo.Album> dataList) {
        Log.e("dataList==", dataList.toString());
        albumList.clear();
        albumList.addAll(dataList);
        mAdapter.notifyDataSetChanged();
        //设置钱数。。个数
        nowphotonum = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getChildList().size() > 0) {
                nowphotonum = nowphotonum + 1;
            }
        }
        Log.e("nowphotonum", nowphotonum + "");
        Log.e("当前个数总：", activity.moneynum + "");
        Log.e("提交的个数总：", activity.moneynum - activity.qyphotonum + nowphotonum + "");
        activity.setMoneynum(activity.moneynum - activity.qyphotonum + nowphotonum);
        moneynum = activity.moneynum - activity.qyphotonum + nowphotonum;
        allmoney = activity.getmaxmoney;
        showmoney = allmoney / LF_NUM * moneynum;
        bigDecimal = new BigDecimal(showmoney);
        showmoney = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        activity.money = showmoney;
        Log.e("金额；", activity.money + "");
        activity.setMoney(activity.money);
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initLinstener() {
        gvDesDaiSurroundingsPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
