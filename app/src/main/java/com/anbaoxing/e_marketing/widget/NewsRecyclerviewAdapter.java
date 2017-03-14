package com.anbaoxing.e_marketing.widget;

/**
 * Created by LENOVO on 2017/2/13.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.beens.Cilist;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class NewsRecyclerviewAdapter extends RecyclerView.Adapter<NewsRecyclerviewAdapter.MyViewHolder> {

    private Cilist cilist;
    private List<Cilist> mDatas;
    private Context mContext;
    private OnItemClickLitener mOnItemClickLitener;

    public NewsRecyclerviewAdapter(Context context, List<Cilist> datas) {
        this.mDatas = datas;
        this.mContext = context;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.fragment_news_recyclerview_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        cilist = mDatas.get(position);
        holder.newsRecyItemName.setText(cilist.getMsgauthor());
        holder.newsRecyItemTitle.setText(cilist.getMsgtitle());
        holder.newsRecyItemBody.setText(cilist.getMsgcontent());
        try {
            OkHttpUtils
                    .get()
                    .url(cilist.getImages())
                    .build()
                    .execute(new BitmapCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            holder.newsRecyItemBigimv.setImageResource(R.mipmap.user_avatar);
                        }

                        @Override
                        public void onResponse(Bitmap response, int id) {
                            holder.newsRecyItemBigimv.setImageBitmap(response);
                        }
                    });
        } catch (Exception e) {
            holder.newsRecyItemBigimv.setImageResource(R.mipmap.user_avatar);
        }

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_recy_item_smalltimv)
        ImageView newsRecyItemSmalltimv;
        @BindView(R.id.news_recy_item_name)
        TextView newsRecyItemName;
        @BindView(R.id.news_recy_item_time)
        TextView newsRecyItemTime;
        @BindView(R.id.news_recy_item_title)
        TextView newsRecyItemTitle;
        @BindView(R.id.news_recy_item_body)
        TextView newsRecyItemBody;
        @BindView(R.id.news_recy_item_bigimv)
        ImageView newsRecyItemBigimv;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}