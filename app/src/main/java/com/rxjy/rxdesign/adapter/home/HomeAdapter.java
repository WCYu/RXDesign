package com.rxjy.rxdesign.adapter.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.adapter.utils.SingleBaseAdapter;
import com.rxjy.rxdesign.adapter.utils.SingleViewHolder;
import com.rxjy.rxdesign.entity.AllClientNewBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AAA on 2017/7/26.
 */

public class HomeAdapter extends SingleBaseAdapter<AllClientNewBean.ClientNewBean, HomeAdapter.ViewHolder> {

    public HomeAdapter(Context context, List<AllClientNewBean.ClientNewBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.list_item_home;
    }

    @Override
    public ViewHolder initViewHolder() {
        return new ViewHolder();
    }

    @Override
    public void onBindView(int position, final AllClientNewBean.ClientNewBean data, ViewHolder holder) {
        holder.tvName.setText(data.getCi_ClientName());
        holder.tvAddress.setText(data.getCi_DecorationAddress());
        holder.tvstate.setText(data.getStateName());
        holder.tvdate.setText(data.getCa_MeasureDate());

        if (data.getStateName().equals("待接单")) {
            holder.tvstate.setTextColor(context.getResources().getColor(R.color.textred));
        } else {
            holder.tvstate.setTextColor(context.getResources().getColor(R.color.colorPrimaryDes));
        }
        holder.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showercode.toshowercode(data.getCi_RwdId());
            }
        });

    }

    class ViewHolder implements SingleViewHolder {
        @Bind(R.id.tv_list_item_home_name)
        TextView tvName;
        @Bind(R.id.tv_list_item_home_address)
        TextView tvAddress;
        @Bind(R.id.tv_list_item_home_date)
        TextView tvdate;
        @Bind(R.id.tv_list_item_home_state)
        TextView tvstate;
        @Bind(R.id.iv_img)
        ImageView ivImg;

        @Override
        public void onInFlate(View v) {
            ButterKnife.bind(this, v);
        }
    }


    ShowERcode showercode;

    public interface ShowERcode{
        public void toshowercode(String orderid);
    }

    public void toshowercode(ShowERcode showercodes){
        showercode=showercodes;
    }

}