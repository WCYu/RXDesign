package com.rxjy.rxdesign.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.acker.simplezxing.activity.CaptureActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.camera.CameraConfigurationUtils;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.home.DesDaiMeasureActivity;
import com.rxjy.rxdesign.activity.home.JiaoChengListActivity;
import com.rxjy.rxdesign.activity.home.JinDuXunJianActivity;
import com.rxjy.rxdesign.activity.home.JunGongActivity;
import com.rxjy.rxdesign.activity.home.KaiGongJiaoDiActivity;
import com.rxjy.rxdesign.activity.home.OrderGetActivity;
import com.rxjy.rxdesign.activity.home.QrLoginActivity;
import com.rxjy.rxdesign.activity.home.TalkActivity;
import com.rxjy.rxdesign.activity.more.ReturnGuestActivity;
import com.rxjy.rxdesign.adapter.home.HomeAdapter;
import com.rxjy.rxdesign.adapter.home.ZaishiAdapter;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.custom.AutoTextView;
import com.rxjy.rxdesign.custom.MyListview;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.DesERLoginBean;
import com.rxjy.rxdesign.entity.ErCodeBean;
import com.rxjy.rxdesign.entity.ErCodeTBean;
import com.rxjy.rxdesign.entity.FloatedBean;
import com.rxjy.rxdesign.entity.GetZaishiInfo;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.entity.PersonDataBean;
import com.rxjy.rxdesign.entity.QRResultBean;
import com.rxjy.rxdesign.entity.QRresultWebBean;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;
import com.rxjy.rxdesign.utils.AutoUtils;
import com.rxjy.rxdesign.utils.Constants;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.MySharedPreferences;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.StringUtils;
import com.rxjy.rxdesign.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

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
    @Bind(R.id.autoTextView)
    AutoTextView autoTextView;
    @Bind(R.id.iv_biaoshu)
    ImageView ivBiaoshu;
    @Bind(R.id.zaitan_jiantou)
    ImageView zaitanJiantou;
    @Bind(R.id.ll_zaitan)
    LinearLayout llZaitan;
    @Bind(R.id.xlv_home_zaitan)
    MyListview xlvHomeZaitan;
    @Bind(R.id.zaishi_jiantou)
    ImageView zaishiJiantou;
    @Bind(R.id.ll_zaishi)
    LinearLayout llZaishi;
    @Bind(R.id.xlv_home_zaishi)
    MyListview xlvHomeZaishi;
    @Bind(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @Bind(R.id.iv_home_noinfo)
    ImageView ivHomeNoinfo;
    @Bind(R.id.tv_home_noinfo)
    TextView tvHomeNoinfo;
    @Bind(R.id.tv_moreperson)
    TextView tvMoreperson;
    @Bind(R.id.tv_huitokei)
    TextView tvHuitokei;
    @Bind(R.id.tv_ercode)
    TextView tvErcode;
    @Bind(R.id.ll_topmore)
    LinearLayout llTopmore;
    @Bind(R.id.home_view)
    WebView homeView;

    HomeAdapter adapter;
    Dialog dialog;
    View v;
    private ImageView iv_close, iv_ercode;
    private ZaishiAdapter zaishiAdapter;
    int zaitanbs = 1;
    int zaishibs = 0;
    private int count = 0;
    private List<FloatedBean> list = new ArrayList<>();
    private Handler handler = new Handler();

    String url = "http://edu.rxjy.com/a/rs/curaInfo/" + App.cardNo + "/tryPostApp?appId=" + App.app_id;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, 3000);
                autoTextView.next();
                FloatedBean floatedBean = list.get(count % list.size());
                String diquName = floatedBean.getCi_DesignerName();
                double signAmount = floatedBean.getSignAmount();
                if (signAmount < 200000) {
                    String s1 = "" + "<font color='#595757'>" + floatedBean.getCi_ClientName() + "</font>" + "" + "<font color='#0080FF'>" + diquName + "</font>" + "" + "<font color='#595757'>签署完成工单</font>";
                    autoTextView.setText(Html.fromHtml(s1));
                } else {
                    double v = signAmount / 10000;
                    double floor = Math.floor(v);
                    String s = String.valueOf(floor);
                    String substring = s.substring(0, s.length() - 2);
                    Log.e("substring", substring);
                    String s1 = "" + "<font color='#595757'>" + floatedBean.getCi_ClientName() + "</font>" + "" + "<font color='#0080FF'>" + diquName + "</font>" + "" + "<font color='#595757'>签署完成</font>" + "" + "<font color='#0080FF'>" + substring + "万" + "</font>";
                    autoTextView.setText(Html.fromHtml(s1));
                }
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private PopupWindow popupWindow;
    private AlertDialog alertDialog;

    @Override
    public int getInitId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        publish.setBackgroundResource(R.mipmap.scan_pc);
        imgBack.setVisibility(View.GONE);
        isShowDT();
        xlvHomeZaitan.setVisibility(View.VISIBLE);
        tvTitle.setText("项目跟进");
        tvRight.setText("+");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setTextSize(20);
        if (App.depart == 3) {
            getFloatedSheet("0");
            autoTextView.setVisibility(View.VISIBLE);
        } else {
            autoTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getZaiTanList();
        getZaiShilist();
    }

    private void getFloatedSheet(String s) {
        Map map = new HashMap();
        map.put("diquId", s);
        OkhttpUtils.doGet(getActivity(), PathUrl.GETTEXTAUTODATAURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取飘单数据", data);
                List<FloatedBean> mlist = new Gson().fromJson(data, new TypeToken<List<FloatedBean>>() {
                }.getType());
                list.clear();
                list = mlist;
                handler.postDelayed(runnable, 0);

            }

            @Override
            public void error(String message) {
                Log.e("tag_获取飘单数据失败", message);
            }
        });
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initLinstener() {

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.popu_check, null);
                popupWindow(inflate, tvRight);
            }
        });
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                getZaiTanList();
                getZaiShilist();
            }
        });
        smartRefresh.setEnableLoadMore(false);

        xlvHomeZaitan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!StringUtils.isEmpty(adapter.getItem(i).getStateName())) {
                    Intent intent = null;
                    switch (adapter.getItem(i).getStateName()) {
                        case "待接单":
                            intent = new Intent(getActivity(), OrderGetActivity.class);
                            intent.putExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO, adapter.getItem(i));
                            intent.putExtra("orderid", adapter.getItem(i).getCi_RwdId());
                            break;
                        case "量房中":
                            intent = new Intent(getActivity(), DesDaiMeasureActivity.class);
                            intent.putExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO, adapter.getItem(i));
                            break;
                        default:
                            intent = new Intent(getActivity(), TalkActivity.class);
                            intent.putExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO, adapter.getItem(i));
                            break;
                    }
                    if (intent != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        xlvHomeZaishi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                if (!StringUtils.isEmpty(zaishiAdapter.getItem(i).getSg_StateName())) {
                    switch (zaishiAdapter.getItem(i).getSg_StateName()) {
                        case "开工":
                            intent = new Intent(getActivity(), KaiGongJiaoDiActivity.class);
                            intent.putExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO, zaishiAdapter.getItem(i));
                            break;
                        case "竣工":
                            intent = new Intent(getActivity(), JunGongActivity.class);
                            intent.putExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO, zaishiAdapter.getItem(i));
                            break;
                        case "施工":
                            intent = new Intent(getActivity(), JinDuXunJianActivity.class);
                            intent.putExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO, zaishiAdapter.getItem(i));
                            break;
                        default:
                            ToastUtil.getInstance().toastCentent("未获取到当前阶段信息");
                            break;
                    }
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_zaitan, R.id.ll_zaishi, R.id.iv_biaoshu, R.id.publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_biaoshu:
                startActivity(new Intent(getActivity(), JiaoChengListActivity.class));
                break;
            case R.id.ll_zaitan:
                if (zaitanbs == 0) {
//                    getZaiTanList();
                    xlvHomeZaitan.setVisibility(View.VISIBLE);
                    zaitanJiantou.setImageResource(R.mipmap.jiantouxia);
                    zaitanbs = 1;
                } else if (zaitanbs == 1) {
                    xlvHomeZaitan.setVisibility(View.GONE);
                    zaitanbs = 0;
                }
                break;
            case R.id.ll_zaishi:
                if (zaishibs == 0) {
//                    getZaiShilist();
                    xlvHomeZaishi.setVisibility(View.VISIBLE);
                    zaishiJiantou.setImageResource(R.mipmap.jiantouxia);
                    zaishibs = 1;
                } else if (zaishibs == 1) {
                    xlvHomeZaishi.setVisibility(View.GONE);
                    zaishibs = 0;
                }
                break;
        }
    }

    public void getZaiTanList() {
        Map map = new HashMap();
        map.put("mobile", App.account);
        OkhttpUtils.doGet(getActivity(), PathUrl.ZAITANLISTURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取再谈列表", data);
                AllClientNewBean info = JSONUtils.toObject(data, AllClientNewBean.class);
                ArrayList<AllClientNewBean.ClientNewBean> body = info.getBody();
                int statusCode = info.getStatusCode();
                String statusMsg = info.getStatusMsg();
                if (statusCode == 0) {
                    if (body.size() == 0) {
                        llZaitan.setVisibility(View.GONE);
                        xlvHomeZaitan.setVisibility(View.GONE);
                    } else {
                        llZaitan.setVisibility(View.VISIBLE);
                        adapter = new HomeAdapter(getActivity(), body);
                        xlvHomeZaitan.setAdapter(adapter);
                        adapter.toshowercode(new HomeAdapter.ShowERcode() {
                            @Override
                            public void toshowercode(String orderid) {//显示二维码
                                showDialog(orderid);
                            }
                        });
                    }
                } else {
                    ToastUtil.getInstance().toastCentent(statusMsg);
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取再谈列表失败", message);
            }
        });
    }

    public void getZaiShilist() {
        Map map = new HashMap();
        map.put("cardNo", App.cardNo);
        OkhttpUtils.doGet(getActivity(), PathUrl.ZAISHILISTURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取在施列表", data);
                GetZaishiInfo info = JSONUtils.toObject(data, GetZaishiInfo.class);
                List<GetZaishiInfo.BodyBean> body = info.getBody();
                int statusCode = info.getStatusCode();
                String statusMsg = info.getStatusMsg();
                if (statusCode == 0) {
                    if (body.size() == 0) {
                        llZaishi.setVisibility(View.GONE);
                        xlvHomeZaishi.setVisibility(View.GONE);
                    } else {
                        llZaishi.setVisibility(View.VISIBLE);
                        zaishiAdapter = new ZaishiAdapter(getActivity(), body);
                        xlvHomeZaishi.setAdapter(zaishiAdapter);
                    }
                } else {
                    ToastUtil.getInstance().toastCentent(statusMsg);
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取在施列表失败", message);
            }
        });
    }

    private void showDialog(String orderid) {
        dialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_ercode, null);
        dialog.setContentView(v);
        Window dialogwindow = dialog.getWindow();
        dialogwindow.setGravity(Gravity.CENTER);
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogwindow.getAttributes();
        lp.height = (int) (display.getHeight());
        lp.width = (int) (display.getWidth());
        dialogwindow.setAttributes(lp);
        dialog.show();
        iv_close = (ImageView) v.findViewById(R.id.iv_close);
        iv_ercode = (ImageView) v.findViewById(R.id.iv_ercode);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Gson gson = new Gson();
        ErCodeTBean erCodeTBean = new ErCodeTBean();
        erCodeTBean.setOrderNo(orderid);
        ErCodeBean erCodeBean = new ErCodeBean();
        erCodeBean.setEvent("CustomerRegister");
        erCodeBean.setParameter(erCodeTBean);
        String ercodestr = gson.toJson(erCodeBean);
        Log.e("jsondata", ercodestr);
        /**
         * 生成二维码
         * "{"event":"CustomerRegister","parameter":{"orderNo":"xx-xxxxxx"}}"
         */
        Bitmap bitmap = generateBitmap(ercodestr, (int) (display.getWidth() * 0.8), (int) (display.getWidth() * 0.8));
        iv_ercode.setImageBitmap(bitmap);
    }

    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void popupWindow(View vicinityView, View view) {
        AutoUtils.auto(vicinityView);
        popupWindow = new PopupWindow(vicinityView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(view, 0, 0);
        TextView htkTv = vicinityView.findViewById(R.id.tv_moreperson);
        TextView khTv = vicinityView.findViewById(R.id.tv_huitokei);
        TextView sysTv = vicinityView.findViewById(R.id.tv_ercode);
        htkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReturnGuestActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        khTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                View inflate = getActivity().getLayoutInflater().inflate(R.layout.orcode_activity, null);
                ImageView erimg = (ImageView) inflate.findViewById(R.id.activity_image);
                Glide.with(getActivity()).load("http://i.rxjy.com/Content/image/appEwm/rx_khpt.png").into(erimg);
                AutoUtils.setSize(getActivity(), false, 720, 1280);
                AutoUtils.auto(inflate);
                dialog.setView(inflate);
                alertDialog = dialog.create();
                alertDialog.show();
                popupWindow.dismiss();
            }
        });
        sysTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRCodeScan();
                popupWindow.dismiss();
            }
        });
    }

    private static final int REQ_CODE_PERMISSION = 0x1111;

    private void QRCodeScan() {//6.0以上的手机需要处理权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
        } else {
            startActivityForResult(new Intent(getActivity(), CaptureActivity.class), CaptureActivity.REQ_CODE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        Log.e("扫码获取的信息RESULT_OK", data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        if (data != null) {
                            try {
                                String result = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                                if (!StringUtils.isEmpty(result)) {
                                    if (result.contains("event")) {
                                        Log.e("event", data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                                        QRresultWebBean info = JSONUtils.toObject(result, QRresultWebBean.class);
                                        String biaoshi = info.getParameter().getLogin_id();
                                        if (biaoshi != null || info.getParameter().getApp_id() == 3 || info.getParameter().getApp_id() == 8) {
                                            Log.e("跳转登陆", data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                                            startActivity(new Intent(getActivity(), QrLoginActivity.class).putExtra("appid", biaoshi));
                                        } else {
                                            Log.e("登陆失败", data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                                        }
                                    } else if (result.contains("uuid")) {
                                        DesERLoginBean info = JSONUtils.toObject(result, DesERLoginBean.class);
                                        String userPsw = MySharedPreferences.getInstance().getUserPsw();
                                        wTSLogin(App.cardNo, userPsw, info.getUuid());
                                    } else {
                                        ToastUtil.getInstance().toastCentent("本平台暂不支持其他二维码扫描！");
                                    }

                                } else {
                                    ToastUtil.getInstance().toastCentent("本平台暂不支持其他二维码扫描！");
                                }
                            } catch (Exception e) {
                                ToastUtil.getInstance().toastCentent("本平台暂不支持其他二维码扫描！");
                                e.printStackTrace();
                            }

                        }
                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            Log.e("扫码获取的信息RESULT_CANCELED", data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        }
                        break;
                }
                break;
        }
    }

    private void wTSLogin(String cardNo, String userPsw, String uuid) {
        Map map = new HashMap();
        map.put("cardno", cardNo);
        map.put("password", userPsw);
        map.put("uuid", uuid);
        OkhttpUtils.doGet(getActivity(), PathUrl.WTSSMLOGINURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_温特斯登陆", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        ToastUtil.getInstance().toastCentent("登陆成功");
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_温特斯登陆失败", message);
            }
        });
    }

    public void isShowDT() {
        if (App.is_group.equals("1") || App.is_group.equals("0")) {
            if (App.u_start != 2 && App.u_start != 3 && App.u_start != 4) {
                homeView.setVisibility(View.VISIBLE);
                homeView.loadUrl(url);
                Log.e("webView————————", url);

                WebSettings settings = homeView.getSettings();
                settings.setJavaScriptEnabled(true);
                homeView.addJavascriptInterface(new WebViewJump(), "android");
                //设置自适应屏幕，两者合用
                settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
                settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
                settings.setDomStorageEnabled(true);
                homeView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                });
            } else {
                homeView.setVisibility(View.GONE);
            }
        } else {
            homeView.setVisibility(View.GONE);
        }
    }

    class WebViewJump {
        @JavascriptInterface
        public void jump() {
            Log.e("tag——", "进入");
            Intent intent = new Intent(getActivity(), getActivity().getClass());
            startActivity(intent);
            getActivity().finish();
        }
    }

}
