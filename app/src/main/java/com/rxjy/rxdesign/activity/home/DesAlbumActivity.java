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

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.adapter.home.DesAlbumAdapter;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.AlbumImgInfo;
import com.rxjy.rxdesign.entity.AllImagesInfo;
import com.rxjy.rxdesign.utils.Constants;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.MySharedPreferences;
import com.rxjy.rxdesign.utils.PhotoUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DesAlbumActivity extends BaseActivity {

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
    @Bind(R.id.gv_des_album)
    GridView gvDesAlbum;

    private List<AllImagesInfo.Album.image> daiImgList;
    private List<AllImagesInfo.Album.image> imgList;

    private DesAlbumAdapter mAdapter;
    private int worksID;
    private int catalogID;
    private int userID;

    @Override
    public int getLayout() {
        return R.layout.activity_des_album;
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
    protected void onResume() {
        super.onResume();
        if (imgList != null) {
            if (imgList.size() > 0) {
                if (!(imgList.get(imgList.size() - 1).getImageUrl() == null)) {
                    imgList.add(new AllImagesInfo.Album.image());
                }
            } else {
                imgList.add(new AllImagesInfo.Album.image());
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void intData() {
        tvTitle.setText("网络相册");
        daiImgList = (List<AllImagesInfo.Album.image>) getIntent().getSerializableExtra(Constants.ACTION_TO_ALBUM_IMG_INFO);
        worksID = getIntent().getIntExtra(Constants.ACTION_TO_ALBUM_IMG_WORKS_ID, -1);
        catalogID = getIntent().getIntExtra(Constants.ACTION_TO_ALBUM_IMG_CATALOG_ID, -1);
        userID = getIntent().getIntExtra(Constants.ACTION_TO_ALBUM_IMG_USER_ID, -1);
        initPhotoData();
    }

    @Override
    public void initAdapter() {

    }

    private void initPhotoData() {
        imgList = new ArrayList<>();
        for (AllImagesInfo.Album.image info : daiImgList) {
            imgList.add(new AllImagesInfo.Album.image(info.getDetailID(), info.getImageUrl()));
        }
        imgList.add(new AllImagesInfo.Album.image());
        mAdapter = new DesAlbumAdapter(this, imgList, -1);
        mAdapter.setOnDeleteImg(new DesAlbumAdapter.OnDeleteImg() {
            @Override
            public void suress(String suress, int position) {
                if (suress.equals("成功")) {
                    ToastUtil.getInstance().toastCentent("删除成功");
                    imgList.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        gvDesAlbum.setAdapter(mAdapter);
        gvDesAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AllImagesInfo.Album.image info = imgList.get(i);
                if (i == mAdapter.getCount() - 1) {
                    PhotoUtils.checkManyIco(01010);
                } else {
                    int size = imgList.size();
                    List<String> urllist = new ArrayList<>();
                    for (int j = 0; j < size; j++) {
                        urllist.add(imgList.get(j).getImageUrl());
                    }
                    watchLargerImage(Constants.WenesImgBaseURl, urllist, i, "查看图片", "量房");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 01010) {
                if (data != null) {
                    //得到返回的照片
                    ArrayList imgUrls = new ArrayList();
                    List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < localMedias.size(); i++) {
                        Log.e("获得到的图片", localMedias.get(i).toString());
                        imgUrls.add(localMedias.get(i).getCompressPath());
                        Log.e("获得到的图片", localMedias.get(i).getCompressPath());
                    }
                    subImage(worksID, catalogID, userID + "", imgUrls);
                }
            }
        }
    }

    private void subImage(int worksID, int catalogID, String s, List<String> imgUrls) {
        showLoading();
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        for (int i = 0; i < imgUrls.size(); i++) {
            File file = new File(imgUrls.get(i));
            builder.addFormDataPart("facefile", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));

        }
        builder.addFormDataPart("WorksID", worksID + "");
        builder.addFormDataPart("CatalogID", catalogID + "");
        builder.addFormDataPart("UserId", s);

        RequestBody body = builder.build();
        Request request = new Request.Builder().url("http://api.wenes.cn/jd/UploadJDimages").addHeader("Referer", "iPanda.Android")
                .addHeader("User-Agent", "CNTV_APP_CLIENT_CBOX_MOBILE")
                .post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("tag_头像上传_失败", e.getMessage().toString());
                dismissLoading();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.e("tag_头像上传", string);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int statusCode = jsonObject.getInt("StatusCode");
                            if (statusCode == 0) {
                                dismissLoading();
                                AlbumImgInfo info = JSONUtils.toObject(string, AlbumImgInfo.class);
                                List<AlbumImgInfo.ImgInfo> dataList = info.getBody();
                                imgList.remove(imgList.size() - 1);
                                for (AlbumImgInfo.ImgInfo imgInfo : dataList) {
                                    imgList.add(new AllImagesInfo.Album.image(imgInfo.getDetailID(), imgInfo.getImageUrl()));
                                }
                                //多添加一个作为上传照片的入口
                                imgList.add(new AllImagesInfo.Album.image());
                                if (mAdapter != null) {
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        setProgressDialog(3000);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
