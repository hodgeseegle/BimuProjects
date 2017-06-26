package com.can.bimuprojects.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Entity.MessageBean;
import com.can.bimuprojects.Module.Hook.DefaultErrorHook;
import com.can.bimuprojects.Module.Request.FocusRequest;
import com.can.bimuprojects.Module.Response.GetMessageResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.ArticleDetailActivity;
import com.can.bimuprojects.activity.PersonalPageActivity;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.DateUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 消息列表
 * Created by can on 2017/4/12.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String type;
    private ArrayList dataList;
    private LayoutInflater inflater;

    public ListAdapter(Context context, String type, List dataList) {
        this.context = context;
        this.type = type;
        this.dataList = (ArrayList) dataList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (type) {
            case AppConstant.LIST_TYPE_MESSAGE:
                holder = new MessageHolder(inflater.inflate(R.layout.item_list_message, parent,
                        false));
                break;
        }
        return holder;
    }

    //---------------加载数据----------------------------
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
         if (holder instanceof MessageHolder) {
            String postStr = "";
            if (((MessageBean) dataList.get(position)).getType().equals("1")) {
                if (((MessageBean) dataList.get(position)).getIs_comment().equals("1")) {
                    postStr = "评论了你的文章";
                } else {
                    postStr = "回复了你的评论";
                }
            } else if (((MessageBean) dataList.get(position)).getType().equals("2")) {
                postStr = "";
            } else if (((MessageBean) dataList.get(position)).getType().equals("3")) {
                postStr = "关注了你";
            }
            if (((MessageBean) dataList.get(position)).getType().equals("2")) {
                ((MessageHolder) holder).tvContent.setText(((MessageBean) dataList.get(position))
                        .getText());
            } else {
                ((MessageHolder) holder).tvContent.setText(postStr);
            }
            ((MessageHolder) holder).tvTime.setText(DateUtils.timestampToString(((MessageBean)
                    dataList.get(position)).getTimestamp()));
            ((MessageHolder) holder).tvTitle.setText(((MessageBean) dataList.get(position))
                    .getMtitle());
            if (((MessageBean) dataList.get(position)).getIs_read() == 0) {
                ((MessageHolder) holder).tvNotRead.setVisibility(View.VISIBLE);
            } else {
                ((MessageHolder) holder).tvNotRead.setVisibility(View.INVISIBLE);
            }
            if(Util.isOnMainThread())
            Glide.with(context).load(((MessageBean) dataList.get(position)).getAuthor_img_url()).
                    placeholder(R.drawable.default_logo).
                    crossFade().
                    centerCrop().
                    transform(new GlideCircleTransform(context)).into(((MessageHolder) holder).message_icon);
            ((MessageHolder) holder).llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MessageHolder) holder).tvNotRead.isShown()) {
                        //发送请求
                        String noticeId = ((MessageBean) dataList.get(position)).getNotice_id();
                        FocusRequest request = new FocusRequest(8, LoginUtils.getLoginUid(),
                                noticeId);
                        HttpUtils.post(MethodConstant.GET_MESSAGE, request, new ResponseHook() {
                            @Override
                            public void deal(Context context, JsonReceive receive) {
                                GetMessageResponse response = (GetMessageResponse) receive.getResponse();
                                if (response != null) {
                                }
                            }

                        }, new DefaultErrorHook(), GetMessageResponse.class);
                    }
                    if (((MessageBean) dataList.get(position)).getType().equals("1")) {//进入文章详情页
                        context.startActivity(new Intent(context, ArticleDetailActivity.class)
                                .putExtra("id", ((MessageBean) dataList.get(position)).getId()).putExtra("comment_pos", ((MessageBean) dataList.get(position)).getComment_id()));
                        ((Activity) context).overridePendingTransition(
                                android.R.anim.fade_in, android.R.anim.fade_out);
                    } else if (((MessageBean) dataList.get(position)).getType().equals("2")) {
                    } else if (((MessageBean) dataList.get(position)).getType().equals("3")) {//进入个人主页
                        context.startActivity(new Intent(context, PersonalPageActivity.class)
                                .putExtra(AppConstant.UID, ((MessageBean) dataList.get(position)).getId()));
                    }
                    ((MessageHolder) holder).tvNotRead.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    class MessageHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.message_tv_content)
        TextView tvContent;
        @Bind(R.id.view_not_read)
        View tvNotRead;
        @Bind(R.id.message_tv_time)
        TextView tvTime;
        @Bind(R.id.message_tv_title)
        TextView tvTitle;
        @Bind(R.id.message_ll_main)
        LinearLayout llMain;
        @Bind(R.id.message_icon)
        ImageView message_icon;

        public MessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
