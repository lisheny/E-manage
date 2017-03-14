package com.anbaoxing.e_marketing.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anbaoxing.e_marketing.R;
import com.anbaoxing.e_marketing.beens.Cilist;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LENOVO on 2017/2/9.
 */

public class MainTabRecyclerviewAdapter extends RecyclerView.Adapter<MainTabRecyclerviewAdapter.MyViewHolder> {

    private Cilist cilist;
    private List<Cilist> mDatas;
    private Context mContext;
    private OnItemClickLitener mOnItemClickLitener;

    public MainTabRecyclerviewAdapter(Context context, List<Cilist> datas) {
        this.mDatas = datas;
        this.mContext = context;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.fragment_main_tab_recyclerview_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        cilist = mDatas.get(position);

        holder.mainTabItemTitle.setText(cilist.getMsgtitle());
        holder.mainTabItemBody.setText(cilist.getMsgcontent());
//        holder.mainTabSendTime.setText(cilist.getCreatetime());

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

        @BindView(R.id.main_tab_item_imv)
        ImageView mainTabItemImv;
        @BindView(R.id.main_tab_item_title)
        TextView mainTabItemTitle;
        @BindView(R.id.main_tab_item_body)
        TextView mainTabItemBody;
        @BindView(R.id.main_tab_item_send_time)
        TextView mainTabSendTime;

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


