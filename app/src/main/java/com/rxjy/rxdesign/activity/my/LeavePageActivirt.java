package com.rxjy.rxdesign.activity.my;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeavePageActivirt extends BaseActivity {

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
    @Bind(R.id.tv_entry_type)
    TextView tvEntryType;
    @Bind(R.id.et_bank_master)
    TextView etBankMaster;
    @Bind(R.id.tv_bank_master_line)
    TextView tvBankMasterLine;
    @Bind(R.id.tv_kaishi)
    TextView tvKaishi;
    @Bind(R.id.et_kaishi)
    TextView etKaishi;
    @Bind(R.id.tv_kaishi_line)
    TextView tvKaishiLine;
    @Bind(R.id.tv_jieshu)
    TextView tvJieshu;
    @Bind(R.id.et_jieshu)
    TextView etJieshu;
    @Bind(R.id.tv_jieshu_line)
    TextView tvJieshuLine;
    @Bind(R.id.tv_zongshichang)
    TextView tvZongshichang;
    @Bind(R.id.et_zongshichang)
    EditText etZongshichang;
    @Bind(R.id.tv_zongshichang_line)
    TextView tvZongshichangLine;
    @Bind(R.id.leave_Reason)
    EditText leaveReason;
    @Bind(R.id.submit)
    Button submit;

    @Override
    public int getLayout() {
        return R.layout.activity_leave_page_activirt;
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
        tvTitle.setText("请假审批");
    }

    @Override
    public void initAdapter() {

    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
