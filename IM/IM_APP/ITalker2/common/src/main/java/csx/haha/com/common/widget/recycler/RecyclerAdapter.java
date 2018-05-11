package csx.haha.com.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import csx.haha.com.common.R;

/**
 * Created by sunray on 2018-4-18.
 */

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder<T>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<T> {
    private final List<T> mDataList;
    private AdapterListener<T> mListener;

    /**
     * 三个构造函数
     */
    public RecyclerAdapter() {
        this(null);
    }
    public RecyclerAdapter(AdapterListener<T> listener) {
        this(new ArrayList<T>(), listener);
    }
    public RecyclerAdapter(List<T> dataList, AdapterListener<T> listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }

    /**
     * 复写默认的布局类型返回
     * @param position 坐标
     * @return 类型，其实复写后返回的都是XML文件的ID
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     *得到布局的类型
     * @param position 坐标
     * @param data 当前的数据
     * @return XML文件的ID，用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, T data);

    @Override
    public MyViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        //子类来实现创建Viewholder
        MyViewHolder<T> holder = myOnCreateViewHolder(root, viewType);

        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        //设置View的Tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        //进行界面注解的绑定
        holder.mUnbinder = ButterKnife.bind(holder, root);
        //绑定callback
        holder.mCallback = this;
        return holder;
    }

    /**
     * 得到一个新的ViewHolder
     *
     * @param root 根布局
     * @param viewType 布局类型，其实就是一个XML的ID
     * @return
     */
    protected abstract MyViewHolder<T> myOnCreateViewHolder(View root, int viewType);

    @Override
    public void onBindViewHolder(MyViewHolder<T> holder, int position) {
        //得到需要绑定的数据
        T data = mDataList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 返回整个集合
     * @return
     */
    public List<T> getItems() {
        return mDataList;
    }

    /**
     * 插入一条数据并通知插入
     * @param data
     */
    public void add(T data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     * @param datas
     */
    public void add(T... datas) {
        if (datas != null && datas.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, datas);
            notifyItemRangeInserted(startPos, datas.length);
        }
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     * @param datas
     */
    public void add(Collection<T> datas) {
        if (datas != null && datas.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(datas);
            notifyItemRangeInserted(startPos, datas.size());
        }
    }

    /**
     * 清空所有的数据
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换为一个新的集合，其中包括了清空
     * @param datas 一个新的集合
     */
    public void replace(Collection<T> datas) {
        mDataList.clear();
        if (datas == null || datas.size() == 0) {
            return;
        }
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }


    @Override
    public void update(T data, MyViewHolder<T> viewHolder) {
        int pos = viewHolder.getAdapterPosition();
        if (pos >= 0) {
            mDataList.remove(pos);
            mDataList.add(pos, data);
            notifyItemChanged(pos);
        }
    }

    @Override
    public void onClick(View v) {
        MyViewHolder viewHolder = (MyViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            //得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回调方法
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        MyViewHolder viewHolder = (MyViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            //得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回调方法
            this.mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }


    /**
     * 设置适配器的监听
     * @param listener
     */
    public void setListener(AdapterListener<T> listener) {
        this.mListener = listener;
    }

    /**
     * 自定义listener
     * @param <T>
     */
    public interface AdapterListener<T> {
        //当Cell点击时触发

        void onItemClick(MyViewHolder holder, T data);

        //当Cell长按时触发
        void onItemLongClick(MyViewHolder holder, T data);
    }

    public static class AdapterListenerImpl<T> implements AdapterListener<T> {

        @Override
        public void onItemClick(MyViewHolder holder, T data) {

        }

        @Override
        public void onItemLongClick(MyViewHolder holder, T data) {

        }
    }

    public static abstract class MyViewHolder<T> extends RecyclerView.ViewHolder {
        protected T mData;
        private Unbinder mUnbinder;
        private AdapterCallback<T> mCallback;
        public MyViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         * @param data 绑定的数据
         */
        void bind(T data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当出发绑定数据的时候，，必须override该方法
         * @param data
         */
        protected abstract void onBind(T data);

        /**
         * Holder自己对自己对应的Data进行更新
         * @param data
         */
        public void updateData(T data) {
            if (mCallback != null) {
                mCallback.update(data, this);
            }
        }
    }
}
