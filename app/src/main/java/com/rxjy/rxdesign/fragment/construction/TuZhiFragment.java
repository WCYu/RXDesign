package com.rxjy.rxdesign.fragment.construction;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.entity.AllClientNewBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class TuZhiFragment extends Fragment {

    //客户信息
    private AllClientNewBean.ClientNewBean clientInfo;

    public void setClientInfo(AllClientNewBean.ClientNewBean info) {
        clientInfo = info;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tu_zhi, container, false);
    }

}
