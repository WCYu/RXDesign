package com.rxjy.rxdesign.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.activity.more.AdministrationRedActivity;
import com.rxjy.rxdesign.activity.more.ReturnGuestActivity;
import com.rxjy.rxdesign.activity.utils.WebViewActivity;
import com.rxjy.rxdesign.base.App;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更多
 */
public class MoreFragment extends BaseFragment {

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
    @Bind(R.id.iv_morebanner)
    ImageView ivMorebanner;
    @Bind(R.id.img_fuwu1)
    ImageView imgFuwu1;
    @Bind(R.id.tv_fuwu1)
    TextView tvFuwu1;
    @Bind(R.id.ly_hongbao)
    LinearLayout lyHongbao;
    @Bind(R.id.img_fuwu2)
    ImageView imgFuwu2;
    @Bind(R.id.tv_fuwu2)
    TextView tvFuwu2;
    @Bind(R.id.ly_huitouke)
    LinearLayout lyHuitouke;
    @Bind(R.id.img_fuwu3)
    ImageView imgFuwu3;
    @Bind(R.id.tv_fuwu3)
    TextView tvFuwu3;
    @Bind(R.id.ly_tuijian)
    LinearLayout lyTuijian;
    @Bind(R.id.ly_jiameng)
    LinearLayout lyJiameng;
    @Bind(R.id.ly_dianpu)
    LinearLayout lyDianpu;
    @Bind(R.id.ly_anli)
    LinearLayout lyAnli;
    @Bind(R.id.ly_zaishi)
    LinearLayout lyZaishi;
    @Bind(R.id.ly_caiwu)
    LinearLayout lyCaiwu;
    @Bind(R.id.ly_paidan)
    LinearLayout lyPaidan;
    @Bind(R.id.ly_bowen)
    LinearLayout lyBowen;
    @Bind(R.id.ly_fuwu)
    LinearLayout lyFuwu;
    @Bind(R.id.ly_xiaxian)
    LinearLayout lyXiaxian;
    @Bind(R.id.ly_touzi)
    LinearLayout lyTouzi;

    @Override
    public int getInitId() {
        return R.layout.fragment_more;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData() {
        imgBack.setVisibility(View.GONE);
        tvTitle.setText("");
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initLinstener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.img_back, R.id.iv_morebanner, R.id.ly_hongbao, R.id.ly_huitouke, R.id.ly_tuijian, R.id.ly_jiameng, R.id.ly_dianpu, R.id.ly_anli, R.id.ly_zaishi, R.id.ly_caiwu, R.id.ly_paidan, R.id.ly_bowen, R.id.ly_fuwu, R.id.ly_xiaxian, R.id.ly_touzi})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_morebanner:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://m.rxjy.com/");
                intent.putExtra("title", "瑞祥装饰");
                break;
            case R.id.ly_hongbao:
                intent = new Intent(getActivity(), AdministrationRedActivity.class);
                break;
            case R.id.ly_huitouke:
                intent = new Intent(getActivity(), ReturnGuestActivity.class);
                break;
            case R.id.ly_tuijian:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "https://api.niujingji.cn:8183/static/develop/recommendApp.html?CardNo=" + App.cardNo);
                intent.putExtra("title", "加盟推荐");
                break;
            case R.id.ly_jiameng:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://c.wenes.cn/#/Personnel/App/jiaMeng?card=1");
                intent.putExtra("title", "加盟");
                break;
            case R.id.ly_dianpu:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://c.wenes.cn/#/Personnel/App/dianPu?card=1");
                intent.putExtra("title", "店铺");
                break;
            case R.id.ly_anli:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://c.wenes.cn/#/Personnel/App/anLi?card=1");
                intent.putExtra("title", "案例");
                break;
            case R.id.ly_zaishi:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://c.wenes.cn/#/Personnel/App/zaiShi?card=1");
                intent.putExtra("title", "施工");
                break;
            case R.id.ly_caiwu:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://c.wenes.cn/#/Personnel/App/caiWu?card=1");
                intent.putExtra("title", "财务");
                break;
            case R.id.ly_paidan:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://c.wenes.cn/#/Personnel/App/paiDan?card=1");
                intent.putExtra("title", "派单");
                break;
            case R.id.ly_bowen:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://c.wenes.cn/#/Personnel/App/boWen?card=1");
                intent.putExtra("title", "博文");
                break;
            case R.id.ly_fuwu:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://c.wenes.cn/#/Personnel/App/fuWu?card=1");
                intent.putExtra("title", "服务");
                break;
            case R.id.ly_xiaxian:
                break;
            case R.id.ly_touzi:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
