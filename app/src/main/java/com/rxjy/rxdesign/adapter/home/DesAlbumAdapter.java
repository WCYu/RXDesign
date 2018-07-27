package com.rxjy.rxdesign.adapter.home;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rxjy.rxdesign.R;
import com.rxjy.rxdesign.base.PathUrl;
import com.rxjy.rxdesign.entity.AllImagesInfo;
import com.rxjy.rxdesign.entity.PersonBean;
import com.rxjy.rxdesign.utils.AutoUtils;
import com.rxjy.rxdesign.utils.Constants;
import com.rxjy.rxdesign.utils.JSONUtils;
import com.rxjy.rxdesign.utils.OkhttpUtils;
import com.rxjy.rxdesign.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/22.
 */
public class DesAlbumAdapter extends BaseAdapter {

    OnDeleteImg onDeleteImg;

    private Context context;
    private List<AllImagesInfo.Album.image> dataList;
    private int picsize = -1;

    public DesAlbumAdapter(Context context, List<AllImagesInfo.Album.image> dataList, int picsize) {
        this.picsize = picsize;
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        final AllImagesInfo.Album.image info = dataList.get(position);

        final ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.gv_item_des_ablum, null);
            AutoUtils.auto(convertView);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (viewGroup.getChildCount() == position) {

            if (position == getCount() - 1 && picsize != 1) {
                holder.ivDesAblum.setScaleType(ImageView.ScaleType.CENTER);
                holder.ivDesAblumDelete.setVisibility(View.GONE);
                holder.ivDesAblum.setImageResource(R.mipmap.add);
            } else {
                holder.ivDesAblum.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.ivDesAblumDelete.setVisibility(View.VISIBLE);
                RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.zhanweitu);
                Glide.with(context).load(Constants.WenesImgBaseURl + info.getImageUrl()).apply(options).into(holder.ivDesAblum);
            }

        }

        holder.ivDesAblumDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImage(position);
            }
        });

        return convertView;
    }

    private void deleteImage(final int position) {
        int detailID = dataList.get(position).getDetailID();
        String imageUrl = dataList.get(position).getImageUrl();
        Log.e("删除图片", detailID + " " + imageUrl);
        Map map = new HashMap();
        map.put("id", detailID);
        OkhttpUtils.doPost(PathUrl.DELETEIMGWSTURL, map, new OkhttpUtils.MyCall() {
            @Override
            public void success(String data) {
                Log.e("tag_删除图片", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String statusMsg = jsonObject.getString("StatusMsg");
                    int statusCode = jsonObject.getInt("StatusCode");
                    if (statusCode == 0) {
                        onDeleteImg.suress("成功",position);
                    } else {
                        ToastUtil.getInstance().toastCentent(statusMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(String message) {
                Log.e("tag_删除图片失败", message);

            }
        });
    }

    static class ViewHolder {
        @Bind(R.id.iv_des_ablum)
        ImageView ivDesAblum;
        @Bind(R.id.iv_des_ablum_delete)
        ImageView ivDesAblumDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnDeleteImg {
        void suress(String suress,int position);
    }

    public void setOnDeleteImg(OnDeleteImg onDeleteImg) {
        this.onDeleteImg = onDeleteImg;
    }
}
