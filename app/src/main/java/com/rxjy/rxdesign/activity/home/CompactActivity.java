package com.rxjy.rxdesign.activity.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.AllClientNewBean;
import com.rxjy.rxdesign.entity.DesignInfo;
import com.rxjy.rxdesign.entity.OrderGetBean;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.utils.Constants;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 合同
* */
public class CompactActivity extends BaseActivity {

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
    @Bind(R.id.payment_Ratio)
    TextView paymentRatio;
    @Bind(R.id.contract_Signing)
    TextView contractSigning;
    @Bind(R.id.time_limit)
    EditText timeLimit;
    @Bind(R.id.ocnstructionTime)
    TextView ocnstructionTime;
    @Bind(R.id.regional_Protection)
    TextView regionalProtection;
    @Bind(R.id.Blueprint)
    TextView Blueprint;
    @Bind(R.id.audit_Cycle)
    EditText auditCycle;
    @Bind(R.id.management_Expense)
    EditText managementExpense;
    @Bind(R.id.deposit)
    EditText deposit;
    @Bind(R.id.waterElectricity)
    EditText waterElectricity;
    @Bind(R.id.discharge_Fee)
    EditText dischargeFee;
    @Bind(R.id.air_Conditioner)
    TextView airConditioner;
    @Bind(R.id.fire_Control)
    TextView fireControl;
    @Bind(R.id.all_Engineering)
    EditText allEngineering;
    @Bind(R.id.submission)
    Button submission;

    AllClientNewBean.ClientNewBean info;

    @Override
    public int getLayout() {
        return R.layout.activity_compact;
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
        tvTitle.setText("合同");
        info = (AllClientNewBean.ClientNewBean) getIntent().getSerializableExtra(Constants.ACTION_TO_DAI_MEASURE_CLIENT_INFO);
        Log.e("lrj", info.getCi_RwdId());
        getDegisInfoData(info.getCi_RwdId());
    }

    private void getDegisInfoData(String ci_rwdId) {
        Map map = new HashMap();
        map.put("rwdId", ci_rwdId);
        OkhttpUtils.doGet(this, PathUrl.GETJIEDANURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取接单信息", data);
                DesignInfo info = JSONUtils.toObject(data, DesignInfo.class);
                DesignInfo.BodyBean body = info.getBody();
                int statusCode = info.getStatusCode();
                String statusMsg = info.getStatusMsg();
                if (statusCode == 0) {
                    DesignInfo.BodyBean.BaseInformationBean baseInformation = body.getBaseInformation();
                    DesignInfo.BodyBean.BuildingInformationBean buildingInformation = body.getBuildingInformation();
                    String ca_htPayProportion = body.getContractInfomationPojo().getCa_HtPayProportion();
                    if (!ca_htPayProportion.equals("")) {
                        paymentRatio.setText(ca_htPayProportion);
                    }
                    String ca_htSignDate = body.getContractInfomationPojo().getCa_HtSignDate();
                    if (!ca_htSignDate.equals("")) {
                        contractSigning.setText(ca_htSignDate);
                    }
                    String ca_htWorkCycle = body.getContractInfomationPojo().getCa_HtWorkCycle();
                    if (!ca_htSignDate.equals("")) {
                        timeLimit.setText(ca_htWorkCycle);
                    }
                    DesignInfo.BodyBean.PropertyInformationBean propertyInformation = body.getPropertyInformation();
                    String ca_reqConTime = propertyInformation.getCa_ReqConTime();
                    if (!ca_reqConTime.equals("")) {
                        ocnstructionTime.setText(ca_reqConTime);
                    }
                    String ca_productProtection = propertyInformation.getCa_ProductProtection();
                    if (!ca_productProtection.equals("")) {
                        regionalProtection.setText(ca_productProtection);
                    }
                    String ca_blueprint = propertyInformation.getCa_Blueprint();
                    if (!ca_blueprint.equals("")) {
                        Blueprint.setText(ca_blueprint);
                    }
                } else {
                    ToastUtil.getInstance().toastCentent(statusMsg);
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取接单失败", message);
            }
        });
    }

    @Override
    public void initAdapter() {

    }

    @OnClick({R.id.img_back, R.id.submission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.submission:
                break;
        }
    }
}
