package com.rxjy.rxdesign.activity.more;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.LHouseNumBean;
import com.rxjy.rxdesign.entity.LHouseSubmitBean;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.entity.PersonDataBean;
import com.rxjy.rxdesign.entity.ProjectTypeBean;
import com.rxjy.rxdesign.entity.SaveDataBean;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.MySharedPreferences;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.SelectorUtils;
import com.rxjy.rxdesign.utils.StringUtils;
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

public class ReturnGuestActivity extends BaseActivity {

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
    @Bind(R.id.ed_cname)
    EditText edCname;
    @Bind(R.id.v_cname)
    TextView vCname;
    @Bind(R.id.ed_phone)
    EditText edPhone;
    @Bind(R.id.v_phone)
    TextView vPhone;
    @Bind(R.id.tv_pname)
    TextView tvPname;
    @Bind(R.id.ed_pname)
    EditText edPname;
    @Bind(R.id.v_pname)
    TextView vPname;
    @Bind(R.id.tv_ptype)
    TextView tvPtype;
    @Bind(R.id.ll_ptype)
    LinearLayout llPtype;
    @Bind(R.id.v_ptype)
    View vPtype;
    @Bind(R.id.tv_pshuxing)
    TextView tvPshuxing;
    @Bind(R.id.ll_pshuxing)
    LinearLayout llPshuxing;
    @Bind(R.id.v_shuxing)
    TextView vShuxing;
    @Bind(R.id.tv_fixtime)
    TextView tvFixtime;
    @Bind(R.id.ll_fixtime)
    LinearLayout llFixtime;
    @Bind(R.id.v_fixtime)
    TextView vFixtime;
    @Bind(R.id.mianji_tv)
    TextView mianjiTv;
    @Bind(R.id.ed_area)
    EditText edArea;
    @Bind(R.id.mi_tv)
    TextView miTv;
    @Bind(R.id.v_area)
    TextView vArea;
    @Bind(R.id.tv_yumoney)
    TextView tvYumoney;
    @Bind(R.id.ed_yumoney)
    EditText edYumoney;
    @Bind(R.id.tv_wan)
    TextView tvWan;
    @Bind(R.id.v_yumoney)
    TextView vYumoney;
    @Bind(R.id.ed_haddress)
    EditText edHaddress;
    @Bind(R.id.v_haddress)
    TextView vHaddress;
    @Bind(R.id.tv_hstatue)
    TextView tvHstatue;
    @Bind(R.id.ll_hstatue)
    LinearLayout llHstatue;
    @Bind(R.id.v_hstatue)
    TextView vHstatue;
    @Bind(R.id.tv_housestatue)
    TextView tvHousestatue;
    @Bind(R.id.ll_housestatue)
    LinearLayout llHousestatue;
    @Bind(R.id.v_housestatue)
    TextView vHousestatue;
    @Bind(R.id.tv_chengjiaotype)
    TextView tvChengjiaotype;
    @Bind(R.id.ll_chengjiaotype)
    LinearLayout llChengjiaotype;
    @Bind(R.id.v_chengjiaotype)
    TextView vChengjiaotype;
    @Bind(R.id.tv_lhousetime)
    TextView tvLhousetime;
    @Bind(R.id.ll_lhousetime)
    LinearLayout llLhousetime;
    @Bind(R.id.v_lhousetime)
    TextView vLhousetime;
    @Bind(R.id.ll_cname)
    LinearLayout llCname;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;

    ArrayList<String> housefromlist, houselist, transtypelist, attributelist;
    List<String> protypefulist;
    List<List<String>> protypezilist;
    List<ProjectTypeBean.FatherDataBean> protypelist;
    private OptionsPickerView ptypePV;
    int ptypec = 0, ptypetwoc = 0, pshuxingc = 0, hstatec = 0, hfromsc = 0, chengjiaoc = 0;
    private LHouseSubmitBean.clientInfos subdataone;
    private LHouseSubmitBean.clientAuxiliarys subdatatwo;
    private String clientInfo;
    private String clientAuxiliary;
    private String zhuangxiutime;
    private String liangfangtime;
    private OptionsPickerView pickerView;

    @Override
    public int getLayout() {
        return R.layout.activity_return_guest;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        edCname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处为得到焦点时
                    vCname.setEnabled(true);
                } else {
                    //此处为失去焦点时
                    vCname.setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
        edPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处为得到焦点时
                    vPhone.setEnabled(true);
                } else {
                    //此处为失去焦点时
                    vPhone.setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
        edPname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处为得到焦点时
                    vPname.setEnabled(true);
                } else {
                    //此处为失去焦点时
                    vPname.setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
        edArea.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处为得到焦点时
                    vArea.setEnabled(true);
                } else {
                    //此处为失去焦点时
                    vArea.setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
        edYumoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处为得到焦点时
                    vYumoney.setEnabled(true);
                } else {
                    //此处为失去焦点时
                    vYumoney.setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
        edHaddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处为得到焦点时
                    vHaddress.setEnabled(true);
                } else {
                    //此处为失去焦点时
                    vHaddress.setEnabled(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void intData() {
        tvTitle.setText("回头客");

        housefromlist = new ArrayList<>();//房源状态
        housefromlist.add("请选择房源状态");
        housefromlist.add("已定");
        housefromlist.add("未定");

        houselist = new ArrayList<>();//房屋状况
        houselist.add("请选择房屋状况");
        houselist.add("毛坯房");
        houselist.add("清水房");
        houselist.add("旧房改造");
        houselist.add("翻新");
        houselist.add("精装房");

        transtypelist = new ArrayList<>();//成交类型
        transtypelist.add("请选择成交类型");
        transtypelist.add("租");
        transtypelist.add("买");
        transtypelist.add("自建房");

        attributelist = new ArrayList<>();//项目属性
        attributelist.add("请选择项目属性");
        attributelist.add("正常单");
        attributelist.add("看图报价单");
        attributelist.add("设计单");
        attributelist.add("家具单");
        attributelist.add("弱电单");
        attributelist.add("局部改造");

        protypefulist = new ArrayList<>();
        protypezilist = new ArrayList<>();
        protypelist = new ArrayList<>();

        getProjecttype();
    }

    @Override
    public void initAdapter() {

    }

    @OnClick({R.id.img_back, R.id.tv_ptype, R.id.tv_pshuxing, R.id.tv_fixtime, R.id.tv_hstatue, R.id.tv_housestatue, R.id.tv_chengjiaotype, R.id.tv_lhousetime, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_ptype://项目类型
                if (protypefulist != null && protypezilist != null) {
                    ptypePV = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tvPtype.setText(protypelist.get(options1).getMingCheng() + "-" + protypelist.get(options1).getZiji().get(options2).getMingCheng());
                            tvPtype.setTextColor(ReturnGuestActivity.this.getResources().getColor(R.color.textblack));
                            ptypec = Integer.parseInt(protypelist.get(options1).getID());
                            ptypetwoc = Integer.parseInt(protypelist.get(options1).getZiji().get(options2).getID());
                        }
                    }).build();
                    ptypePV.setPicker(protypefulist, protypezilist);
                    ptypePV.show();
                }
                break;
            case R.id.tv_pshuxing://项目属性
//                SelectorUtils.dataSelector(tvPshuxing, attributelist);
                pickerView = new OptionsPickerView.Builder(App.context, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvPshuxing.setText(attributelist.get(options1).toString());
                        pshuxingc = options1;
                    }
                }).build();
                pickerView.setPicker(attributelist);
                pickerView.show();
                break;
            case R.id.tv_fixtime://装修时间
                SelectorUtils.timeSelector(tvFixtime, "yyyy/MM/dd");
                break;
            case R.id.tv_hstatue://房屋状况
//                SelectorUtils.dataSelector(tvHstatue, houselist);
                pickerView = new OptionsPickerView.Builder(App.context, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvHstatue.setText(houselist.get(options1).toString());
                        hstatec = options1;
                    }
                }).build();
                pickerView.setPicker(houselist);
                pickerView.show();
                break;
            case R.id.tv_housestatue://房源状态
//                SelectorUtils.dataSelector(tvHousestatue, housefromlist);
                pickerView = new OptionsPickerView.Builder(App.context, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvHousestatue.setText(housefromlist.get(options1).toString());
                        hfromsc = options1;
                    }
                }).build();
                pickerView.setPicker(housefromlist);
                pickerView.show();
                break;
            case R.id.tv_chengjiaotype://成交类型
//                SelectorUtils.dataSelector(tvChengjiaotype, transtypelist);
                pickerView = new OptionsPickerView.Builder(App.context, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvChengjiaotype.setText(transtypelist.get(options1).toString());
                        chengjiaoc = options1;
                    }
                }).build();
                pickerView.setPicker(transtypelist);
                pickerView.show();
                break;
            case R.id.tv_lhousetime://量房时间
                SelectorUtils.timeSelector(tvLhousetime, "yyyy/MM/dd");
                break;
            case R.id.tv_submit:
                subdataone = new LHouseSubmitBean.clientInfos();
                subdatatwo = new LHouseSubmitBean.clientAuxiliarys();
                String cname = edCname.getText().toString();
                String phone = edPhone.getText().toString();
                String pname = edPname.getText().toString();
                String area = edArea.getText().toString();
                String yumoney = edYumoney.getText().toString();
                String lhaddress = edHaddress.getText().toString();
                zhuangxiutime = tvFixtime.getText().toString();
                liangfangtime = tvLhousetime.getText().toString();
                if (StringUtils.isEmpty(cname)) {
                    ToastUtil.getInstance().toastCentent("请输入客户姓名！");
                    return;
                }
                if (StringUtils.isEmpty(phone)) {
                    ToastUtil.getInstance().toastCentent("请输入电话！");
                    return;
                }
                if (StringUtils.isEmpty(pname)) {
                    ToastUtil.getInstance().toastCentent("请输入项目名称！");
                    return;
                }
                if (ptypec == 0) {
                    ToastUtil.getInstance().toastCentent("请输入项目类型！");
                    return;
                }
                if (pshuxingc == 0) {
                    ToastUtil.getInstance().toastCentent("请输入项目属性！");
                    return;
                }
                if (StringUtils.isEmpty(zhuangxiutime)) {
                    ToastUtil.getInstance().toastCentent("请输入装修时间！");
                    return;
                }

                if (StringUtils.isEmpty(area)) {
                    ToastUtil.getInstance().toastCentent("请输入面积！");
                    return;
                }
                if (StringUtils.isEmpty(yumoney)) {
                    ToastUtil.getInstance().toastCentent("请输入客户预算！");
                    return;
                }
                if (StringUtils.isEmpty(lhaddress)) {
                    ToastUtil.getInstance().toastCentent("请输入量房地址！");
                    return;
                }
                if (hstatec == 0) {
                    ToastUtil.getInstance().toastCentent("请输入房屋状况！");
                    return;
                }
                if (hfromsc == 0) {
                    ToastUtil.getInstance().toastCentent("请输入房源状态！");
                    return;
                }
                if (chengjiaoc == 0) {
                    ToastUtil.getInstance().toastCentent("请输入成交类型！");
                    return;
                }
                if (StringUtils.isEmpty(liangfangtime)) {
                    ToastUtil.getInstance().toastCentent("请输入量房时间！");
                    return;
                }

                subdataone.setCi_proHead(cname);
                subdataone.setCi_proHeadTel(phone);
                subdataone.setCi_ClientName(pname);
                subdataone.setCi_Type(ptypec + "");
                subdataone.setCi_Area(area);
                subdataone.setCi_DecorationAddress(lhaddress);
                subdataone.setCi_DesignerCard(App.cardNo);
                clientInfo = JSONUtils.toString(subdataone);
                Log.e("clientInfo", clientInfo);

                subdatatwo.setCa_proAttribute(pshuxingc + "");
                subdatatwo.setCa_DecorationDate(zhuangxiutime);
                subdatatwo.setCa_DecBudgetPrice(yumoney);
                subdatatwo.setCa_HousingType(hstatec + "");
                subdatatwo.setCa_AvailabilityStatus(hfromsc + "");
                subdatatwo.setCa_TransactionType(chengjiaoc + "");
                subdatatwo.setCa_MeasureDate(liangfangtime);
                subdatatwo.setCa_SWIndustryTypeID(ptypetwoc + "");
                clientAuxiliary = JSONUtils.toString(subdatatwo);
                Log.e("clientAuxiliary", clientAuxiliary);
                getDanHaoData();
                tvSubmit.setEnabled(false);
                break;
        }
    }

    public void getProjecttype() {
        Map map = new HashMap();
        OkhttpUtils.doGet(this, PathUrl.GETXIANGMUTYPEURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取项目类型", data);
//                try {
//                    JSONObject jsonObject = new JSONObject(data);
//                    String statusMsg = jsonObject.getString("StatusMsg");
//                    int statusCode = jsonObject.getInt("StatusCode");
//                    if (statusCode == 0) {
                ProjectTypeBean info = JSONUtils.toObject(data, ProjectTypeBean.class);
                ArrayList<ProjectTypeBean.FatherDataBean> body = info.getBody();
//                Log.e("项目类型", body.size() + "");
                protypelist.addAll(body);
                for (ProjectTypeBean.FatherDataBean fatherDataBean : body) {
//                            Log.e("项目类型",fatherDataBean.getMingCheng());
                    protypefulist.add(fatherDataBean.getMingCheng());
                    List<String> child = new ArrayList<>();
                    for (ProjectTypeBean.FatherDataBean.SonDataBean childbean : fatherDataBean.getZiji()) {
                        child.add(childbean.getMingCheng());
//                                Log.e("项目名称",childbean.getMingCheng());
                    }
                    protypezilist.add(child);
                }
//                    } else {
//                        ToastUtil.getInstance().toastCentent(statusMsg);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取项目类型失败", message);
            }
        });
    }

    public void getDanHaoData() {
        Map map = new HashMap();
        map.put("areaid", App.region_id);
        map.put("phone", MySharedPreferences.getInstance().getUserPhone());
        OkhttpUtils.doGet(this, PathUrl.GETDANHAOURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_获取单号信息", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        LHouseNumBean info = JSONUtils.toObject(data, LHouseNumBean.class);
                        LHouseNumBean.Body body = info.getBody();
                        String orderNumber = body.getOrderNumber();
                        submitDanHaoData(orderNumber);
                    } else {
                        tvSubmit.setEnabled(true);
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String message) {
                Log.e("tag_获取单号失败", message);
                tvSubmit.setEnabled(true);
            }
        });
    }

    private void submitDanHaoData(String orderNumber) {
        Map map = new HashMap();
        map.put("rwdid", orderNumber);
        map.put("clientInfo", clientInfo);
        map.put("clientAuxiliary", clientAuxiliary);
        OkhttpUtils.doPost(PathUrl.TIJIAODANHAOURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_提交单号", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 100) {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                        finish();
                    } else {
                        tvSubmit.setEnabled(true);
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(String message) {
                Log.e("tag_提交单号失败", message);
                tvSubmit.setEnabled(true);
            }
        });
    }

}
