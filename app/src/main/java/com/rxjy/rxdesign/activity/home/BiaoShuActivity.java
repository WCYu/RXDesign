package com.rxjy.rxdesign.activity.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.BaseActivity;
import com.rxjy.rxdesign.fragment.BiaoShuFragment;
import com.rxjy.rxdesign.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BiaoShuActivity extends BaseActivity {

    @Bind(R.id.biaoshu_vp)
    ViewPager biaoshuVp;
    @Bind(R.id.biaoshu_cancel)
    ImageView biaoshuCancel;
    private int jiaochengbs;
    private MyPagerAdapter2 adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_biao_shu;
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
        adapter = new MyPagerAdapter2(getSupportFragmentManager());
        biaoshuVp.setAdapter(adapter);
    }

    @Override
    public void initAdapter() {

    }

    @OnClick(R.id.biaoshu_cancel)
    public void onViewClicked() {
        finish();
    }

    private int[] pics = new int[]{
            R.mipmap.page01,
            R.mipmap.page02,
            R.mipmap.page03,
            R.mipmap.page04,
            R.mipmap.page05,
            R.mipmap.page06,
            R.mipmap.page07,
            R.mipmap.page08,
            R.mipmap.page09,
            R.mipmap.page10,
            R.mipmap.page11,
            R.mipmap.page12,
            R.mipmap.page13,
            R.mipmap.page14,
            R.mipmap.page15,
            R.mipmap.page16,
            R.mipmap.page17,
            R.mipmap.page18,
            R.mipmap.page19,
            R.mipmap.page20,
            R.mipmap.page21,
            R.mipmap.page22,
            R.mipmap.page23,
            R.mipmap.page24,
            R.mipmap.page26,
            R.mipmap.page27,
            R.mipmap.page28,
            R.mipmap.page29,
            R.mipmap.page30,
            R.mipmap.page31,
            R.mipmap.page32,
            R.mipmap.page33,
            R.mipmap.page34
    };
    private int[] pics2 = new int[]{
            R.mipmap.yusuan1,
            R.mipmap.yusuan2,
            R.mipmap.yusuan3,
            R.mipmap.yusuan4,
            R.mipmap.yusuan5,
            R.mipmap.yusuan6,
            R.mipmap.yusuan7,
            R.mipmap.yusuan8,
            R.mipmap.yusuan9,
            R.mipmap.yusuan10,
            R.mipmap.yusuan11,
            R.mipmap.yusuan12,
            R.mipmap.yusuan13,
            R.mipmap.yusuan14,
            R.mipmap.yusuan15,
    };

    public class MyPagerAdapter2 extends FragmentStatePagerAdapter {

        public MyPagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            jiaochengbs = getIntent().getIntExtra(Constants.JIAOCHENG, -1);
            switch (jiaochengbs) {
                case 1:
                    return pics.length;
                case 2:
                    return pics2.length;
                default:
                    return 0;
            }
        }

        @Override
        public Fragment getItem(int position) {
            jiaochengbs = getIntent().getIntExtra(Constants.JIAOCHENG, -1);
            switch (jiaochengbs) {
                case 1:
                    Log.e("position", position + ",:" + pics.length);
                    if (position < pics.length) {
                        return BiaoShuFragment.newInstance(pics, position);
                    }
                case 2:
                    Log.e("position", position + ",:" + pics2.length);
                    if (position < pics2.length) {
                        return BiaoShuFragment.newInstance(pics2, position);
                    }
                default:
                    return null;
            }
        }
    }
}
