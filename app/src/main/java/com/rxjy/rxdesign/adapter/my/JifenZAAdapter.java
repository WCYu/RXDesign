package com.rxjy.rxdesign.adapter.my;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.adapter.utils.SingleBaseAdapter;
import com.rxjy.rxdesign.adapter.utils.SingleViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hjh on 2018/4/3.
 */

public class JifenZAAdapter extends SingleBaseAdapter<String, JifenZAAdapter.ViewHolder> {

    int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    ArrayList<String> list = new ArrayList<>();

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public JifenZAAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_level;
    }

    @Override
    public ViewHolder initViewHolder() {
        return new ViewHolder();
    }

    @Override
    public void onBindView(int position, String data, ViewHolder holder) {
        int choosenum = Integer.parseInt(data);
        holder.tv_level.setText("V" + data);
        holder.tv_levelnum.setText(list.get(position) + "%");
        if (choosenum == num) {
            holder.tv_level.setTextColor(context.getResources().getColor(R.color.txtjf_yellows));
            holder.tv_levelnum.setTextColor(context.getResources().getColor(R.color.text_bluetwo));
            holder.iv_levelicon.setImageResource(R.drawable.ic_levelnow);
            holder.iv_line.setImageResource(R.drawable.ic_guodulinegrey);
            holder.iv_line.setVisibility(View.VISIBLE);
        } else if (num > choosenum) {
            holder.tv_level.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.tv_levelnum.setTextColor(context.getResources().getColor(R.color.text_blueone));
            holder.iv_levelicon.setImageResource(R.drawable.ic_leveled);
            holder.iv_line.setImageResource(R.drawable.ic_guodulineblue);
            holder.iv_line.setVisibility(View.VISIBLE);
        } else if (num < choosenum) {
            holder.tv_level.setTextColor(context.getResources().getColor(R.color.text_greytwo));
            holder.tv_levelnum.setTextColor(context.getResources().getColor(R.color.text_greyone));
            holder.iv_levelicon.setImageResource(R.drawable.ic_levelno);
            holder.iv_line.setImageResource(R.drawable.ic_guodulinegrey);
            if (choosenum == 10) {
                holder.iv_line.setVisibility(View.GONE);
            } else {
                holder.iv_line.setVisibility(View.VISIBLE);
            }
        }
    }

    class ViewHolder implements SingleViewHolder {

        @Bind(R.id.tv_levelnum)
        TextView tv_levelnum;
        @Bind(R.id.tv_level)
        TextView tv_level;
        @Bind(R.id.iv_levelicon)
        ImageView iv_levelicon;
        @Bind(R.id.iv_line)
        ImageView iv_line;

        @Override
        public void onInFlate(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
