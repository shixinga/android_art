package com.shixing.itemtouch;

/**
 * Created by shixing on 2018/5/15.
 */

public interface ItemTouchListener {

    /**
     * 当拖拽时回调
     * 拖拽条目时实现刷新效果
     * @param fromPosition
     * @param toPosition
     * @return 是否执行了move
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * 当条目被移除时回调
     * @param position 移除的位置
     * @return
     */
    boolean onItemRemove(int position);
}
