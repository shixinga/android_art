package csx.haha.com.common.widget.recycler;

/**
 * Created by sunray on 2018-4-18.
 */

public interface AdapterCallback<T> {
    void update(T data, RecyclerAdapter.MyViewHolder<T> viewHolder);
}
