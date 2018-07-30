package com.rxjy.rxdesign.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.guide.LoginActivity;
import com.rxjy.rxdesign.activity.home.JoininNjjActivity;
import com.rxjy.rxdesign.activity.my.SettingActivity;
import com.rxjy.rxdesign.activity.utils.WpsImageActivity;
import com.rxjy.rxdesign.utils.AutoUtils;
import com.rxjy.rxdesign.utils.Constants;
import com.rxjy.rxdesign.utils.MySharedPreferences;
import com.rxjy.rxdesign.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        App.context = this;
        App.baseActivity = this;
        //绑定插件
        ButterKnife.bind(this);

        //        //取消标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDes));
        }

        //初始化布局，并且适配
        View view = View.inflate(this, getLayout(), null);
        AutoUtils.setSize(this, false, 750, 1334);
        AutoUtils.auto(view);
        setContentView(view);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //禁止键盘挤压布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //绑定插件
        ButterKnife.bind(this);
        mHandler = new myhandle(this);

        initView();
        intData();
        initAdapter();
    }

    public abstract int getLayout();

    public abstract void initView();

    public abstract void intData();

    public abstract void initAdapter();

    public void showLoading() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setProgressDialog(final long l) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                            ToastUtil.getInstance().toastCentent("当前网络较差", BaseActivity.this);
                        }
                    }
                });
            }
        }).start();
    }

    public void setProgressDialog(final long l, final int size) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                            if (size == 0) {
                                ToastUtil.getInstance().toastCentent("未获取到更多数据", BaseActivity.this);
                            } else {
                                ToastUtil.getInstance().toastCentent("当前网络较差", BaseActivity.this);
                            }
                        }
                    }
                });
            }
        }).start();
    }

    //查看大图
    public void watchLargerImage(String baseurl, List<String> imgList, int position, String title, String type) {
        //imgList.clear();
        Intent intent = new Intent(this, WpsImageActivity.class);
        intent.putExtra("BaseUrl", baseurl);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putStringArrayListExtra(Constants.IMAGE_URL_LIST, (ArrayList<String>) imgList);
        intent.putExtra("camera_position", position);
        startActivity(intent);
    }

    public void showTiShi(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(alertDialog);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextColor(getResources().getColor(R.color.cor666));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    // 内存紧张时回收图片资源
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    // 内存紧张时回收图片资源 API4.0
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }

    //子类可以同时重写这个方法实现Handler传输
    public void handlerMeaasg(Message msg) {

    }

    public static class myhandle extends Handler {
        //使用弱引用防止内存泄漏
        WeakReference<BaseActivity> activityWeakReference;

        public myhandle(BaseActivity activityWeakReference) {
            this.activityWeakReference = new WeakReference<BaseActivity>(activityWeakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activityWeakReference.get() != null) {
                activityWeakReference.get().handlerMeaasg(msg);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
