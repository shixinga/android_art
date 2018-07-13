package com.shixing.itemtouch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by shixing on 2018/5/15.
 */

public class QQAdapter extends RecyclerView.Adapter<QQAdapter.MyViewHolder>
        implements ItemTouchListener {

    private List<QQMessage> mList;
    private StartDtagListener mListener; //这里是MainActivity
    public QQAdapter(List<QQMessage> list) {
        this.mList = list;
    }

    public QQAdapter(List<QQMessage> list, StartDtagListener listener) {
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.iv_logo.setImageResource(mList.get(position).getLogo());
        holder.tv_name.setText(mList.get(position).getName());
        holder.tv_msg.setText(mList.get(position).getLastMsg());
        holder.tv_time.setText(mList.get(position).getTime());
        holder.iv_logo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mListener.onStartDrag(holder);
                }
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //1.数据交换， 2.刷新
        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public boolean onItemRemove(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_logo)
        ImageView iv_logo;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_lastMsg)
        TextView tv_msg;
        @BindView(R.id.tv_time)
        TextView tv_time;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
