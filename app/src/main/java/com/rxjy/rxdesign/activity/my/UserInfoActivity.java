package com.rxjy.rxdesign.activity.my;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.entity.PersonDataBean;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.MySharedPreferences;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.PhotoUtils;
import com.rxjy.rxdesign.utils.ToastUtil;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/*
* 个人资料
* */

public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.ly_background)
    LinearLayout rlBackground;
    @Bind(R.id.ic_icon)
    ImageView icIcon;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_trydate)
    TextView tvTrydate;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.rl_sex)
    RelativeLayout rlSex;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.rl_phone)
    RelativeLayout rlPhone;
    @Bind(R.id.et_email)
    TextView etEmail;
    @Bind(R.id.rl_email)
    RelativeLayout rlEmail;
    @Bind(R.id.tv_ismarry)
    TextView tvIsmarry;
    @Bind(R.id.rl_ismarry)
    RelativeLayout rlIsmarry;
    @Bind(R.id.tv_ztwo)
    TextView tvZtwo;
    @Bind(R.id.rl_identity)
    RelativeLayout rlIdentity;
    @Bind(R.id.tv_zthree)
    TextView tvZthree;
    @Bind(R.id.rl_shenqing)
    RelativeLayout rlShenqing;
    @Bind(R.id.tv_join)
    TextView tvJoin;
    @Bind(R.id.tv_gcid)
    TextView tvGcid;
    @Bind(R.id.tv_team)
    TextView tvTeam;
    @Bind(R.id.tv_userId)
    TextView tvUserId;
    @Bind(R.id.ly_userItem)
    LinearLayout ly_userItem;

    private SimpleAdapter simpleAdapter;

    private ArrayList<String> imgList;

    @Override
    public int getLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void intData() {
        tvTitle.setText("个人资料");
        rlBackground.setBackgroundColor(getResources().getColor(R.color.touming));
        getUserData();
        if (!TextUtils.isEmpty(MySharedPreferences.getInstance().getImage())) {
            Glide.with(UserInfoActivity.this).load(MySharedPreferences.getInstance().getImage()).apply(RequestOptions.circleCropTransform()).into(icIcon);
        }
        switch (App.is_group) {
            case "0":

            case "1":
                break;
            case "2":
                ly_userItem.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvUsername.setText(MySharedPreferences.getInstance().getName());
        tvName.setText(MySharedPreferences.getInstance().getName());
    }

    @Override
    public void initAdapter() {

    }

    @OnClick({R.id.img_back, R.id.ic_icon, R.id.rl_identity, R.id.rl_shenqing})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.img_back://返回
                finish();
                break;
            case R.id.ic_icon:
                PhotoUtils.checkIco(10001);
                break;
            case R.id.rl_identity:
                intent = new Intent(UserInfoActivity.this, UserInfoModifyActivity.class);
                break;
            case R.id.rl_shenqing:
                intent = new Intent(UserInfoActivity.this, EntryInformationActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10001:
                imgList = new ArrayList<>();
                if (resultCode == RESULT_OK) {
                    List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < localMedias.size(); i++) {
                        imgList.add(localMedias.get(i).getCutPath());
                    }
                    //上传。。。。
                    uploadImg();
                    Glide.with(UserInfoActivity.this).load(imgList.get(0)).apply(RequestOptions.circleCropTransform()).into(icIcon);
                    Log.e("图片：", imgList.get(0));
                }
                break;
        }
    }

    private void uploadImg() {
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File(imgList.get(0));
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("facefile", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        builder.addFormDataPart("cardNo", App.cardNo);
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(PathUrl.ICONIMGURL).addHeader("Referer", "iPanda.Android")
                .addHeader("User-Agent", "CNTV_APP_CLIENT_CBOX_MOBILE")
                .post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("tag_头像上传_失败", e.getMessage().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("tag_头像上传", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        JSONObject object = jsonObject.getJSONObject("Body");
                        String url = object.getString("url");
//                            Log.e("tag_头像上传成功", url);
                        MySharedPreferences.getInstance().setImage(url);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getUserData() {
        showLoading();
        Map map = new HashMap();
        map.put("Phone", MySharedPreferences.getInstance().getAccount());
        map.put("Type", "1");
        OkhttpUtils.doGet(PathUrl.USERDATAURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取用户信息", data);
                PersonBean info = JSONUtils.toObject(data, PersonBean.class);
                PersonDataBean body = info.getBody();
                int statusCode = info.getStatusCode();
                String statusMsg = info.getStatusMsg();
                if (statusCode == 0) {
                    tvUsername.setText(body.getName());
                    tvName.setText(body.getName());
                    tvPhone.setText(body.getPhone());
                    tvSex.setText(body.getSex());
                    tvUserId.setText(body.getCardNo());
                    tvTeam.setText(body.getPostName());
                    tvGcid.setText(body.getDepartName());
                    tvTrydate.setText(body.getTryHillockTime());
                    if (body.getZ2IsFinsh() == 1) {
                        tvZtwo.setVisibility(View.GONE);
                    } else {
                        tvZtwo.setVisibility(View.VISIBLE);
                    }
                    if (body.getZ3IsFinsh() == 1) {
                        tvZthree.setVisibility(View.GONE);
                    } else {
                        tvZthree.setVisibility(View.VISIBLE);
                    }
                } else {
                    ToastUtil.getInstance().toastCentent(statusMsg);
                }
                dismissLoading();
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取用户信息失败", message);
                dismissLoading();
                ToastUtil.getInstance().toastCentent("获取用户信息失败");
            }
        });
        setProgressDialog(3000);
    }

}
