package com.rxjy.rxdesign.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.fragment.utils.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BiaoShuFragment extends BaseFragment {

    @Bind(R.id.iv_biaoshu)
    ImageView ivBiaoshu;

    private static final String ARG_POSITION = "position";
    private static int[] picsz;
    private int position;

    @Override
    public int getInitId() {
        return R.layout.fragment_biao_shu;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    public static BiaoShuFragment newInstance(int[] pics, int position) {
        picsz = pics;
        BiaoShuFragment f = new BiaoShuFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void initData() {
        position = getArguments().getInt(ARG_POSITION);
        ivBiaoshu.setImageResource(picsz[position]);
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
}
