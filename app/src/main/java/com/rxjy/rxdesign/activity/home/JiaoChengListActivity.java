package com.rxjy.rxdesign.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JiaoChengListActivity extends BaseActivity {

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
    @Bind(R.id.ll_biaoshu)
    LinearLayout llBiaoshu;
    @Bind(R.id.ll_yusuan)
    LinearLayout llYusuan;

    @Override
    public int getLayout() {
        return R.layout.activity_jiao_cheng_list;
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
        tvTitle.setText("标书");
    }

    @Override
    public void initAdapter() {

    }

    @OnClick({R.id.img_back, R.id.ll_biaoshu, R.id.ll_yusuan})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_biaoshu:
                intent = new Intent(JiaoChengListActivity.this, BiaoShuActivity.class);
                intent.putExtra(Constants.JIAOCHENG, 1);
                startActivity(intent);
                break;
            case R.id.ll_yusuan:
                intent = new Intent(JiaoChengListActivity.this, BiaoShuActivity.class);
                intent.putExtra(Constants.JIAOCHENG, 2);
                startActivity(intent);
                break;
        }
    }
}
