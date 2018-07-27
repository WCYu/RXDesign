package com.rxjy.rxdesign.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.adapter.home.DesDaiImgAdapter;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.CustomGridView;
import com.rxjy.rxdesign.entity.AllImagesInfo;
import com.rxjy.rxdesign.entity.GetZaishiInfo;
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

/*
* 竣工验收
* */
public class JunGongActivity extends BaseActivity {

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
    @Bind(R.id.tv_kaigong_xiangmuname)
    TextView tvKaigongXiangmuname;
    @Bind(R.id.tv_kaigong_xiangmuaddress)
    TextView tvKaigongXiangmuaddress;
    @Bind(R.id.tv_kaigongor_jungong)
    TextView tvKaigongorJungong;
    @Bind(R.id.jiedan_jiantou)
    ImageView jiedanJiantou;
    @Bind(R.id.ll_jiedan)
    LinearLayout llJiedan;
    @Bind(R.id.gv_des_dai_measure_photo)
    CustomGridView gvDesDaiMeasurePhoto;

    private List<AllImagesInfo.Album> albumList;
    private DesDaiImgAdapter mAdapter;
    GetZaishiInfo.BodyBean bodyBean;

    private int position;
    private AllImagesInfo.Album ainfo;

    @Override
    public int getLayout() {
        return R.layout.activity_jun_gong;
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
        tvTitle.setText("竣工验收");
        lyBackground.setBackgroundColor(getResources().getColor(R.color.transparent));
        tvKaigongorJungong.setText("竣工验收");
        albumList = new ArrayList<>();
        mAdapter = new DesDaiImgAdapter(this, albumList);
        gvDesDaiMeasurePhoto.setAdapter(mAdapter);
        bodyBean = (GetZaishiInfo.BodyBean) getIntent().getSerializableExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO);
        tvKaigongXiangmuname.setText(bodyBean.getCi_ClientName());
        tvKaigongXiangmuaddress.setText(bodyBean.getCi_DecorationAddress());
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUCAllImages(bodyBean.getCi_RwdId(), 3);
    }

    private void getUCAllImages(String ci_rwdId, int i) {
        Map map = new HashMap();
        map.put("rwdId", ci_rwdId);
        map.put("enumType", i);
        OkhttpUtils.doGet(this, PathUrl.KGJDIMGURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取竣工验收图片", data);
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
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取竣工验收图片失败", message);
            }
        });
    }

    private void initListener() {
        gvDesDaiMeasurePhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                AllImagesInfo.Album info = albumList.get(position);
                ainfo = info;
//                List<AllImagesInfo.Album.image> imageList = info.getChildList();
//                Intent albumIntent = new Intent(this, DesAlbumActivityhd.class);
//                albumIntent.putExtra(Constants.ACTION_TO_ALBUM_IMG_INFO, (ArrayList<AllImagesInfo.Album.image>) imageList);
//                albumIntent.putExtra(Constants.ACTION_TO_ALBUM_IMG_WORKS_ID, info.getWorksID());
//                albumIntent.putExtra(Constants.ACTION_TO_ALBUM_IMG_CATALOG_ID, info.getCatalogID());
//                albumIntent.putExtra(Constants.ACTION_TO_ALBUM_IMG_USER_ID, "01012694");
//                startActivity(albumIntent);
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
