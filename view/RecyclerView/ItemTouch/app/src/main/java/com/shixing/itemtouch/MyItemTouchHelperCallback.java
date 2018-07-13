package com.shixing.itemtouch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by shixing on 2018/5/15.
 */

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private ItemTouchListener mMoveListener;

    public MyItemTouchHelperCallback(ItemTouchListener moveListener) {
        this.mMoveListener = moveListener;
    }

    private static final String TAG = "MainActivity";
    //Callback被回调时先调用的，用来判断当前是什么动作，如判断方向（我要监听那个方向的拖动）
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //方向:up/down/left/right
        int up = ItemTouchHelper.UP; //0x0001
        int down = ItemTouchHelper.DOWN; //0x0010
        //上下滑
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //左还是右划
        int swipFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int flags = makeMovementFlags(dragFlags, swipFlags);
        Log.d(TAG, "getMovementFlags: " + flags);
        return flags;
    }

    //允许长按拖拽
    @Override
    public boolean isLongPressDragEnabled() {
        Log.d(TAG, "isLongPressDragEnabled: ");
        return true; //false表示不可长按
    }

    //当上下移动时被回调
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder target) {
        if (srcHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        Log.d(TAG, "onMove: srcHolder.layoutPosition()=" + srcHolder.getLayoutPosition()
            + ", target.layoutPosition()=" + target.getLayoutPosition()
            + ", srcHolder.getAdapterPosition()=" + srcHolder.getAdapterPosition()
            + ", target.getAdapterPosition()=" + target.getAdapterPosition()
            );
        return mMoveListener.onItemMove(srcHolder.getAdapterPosition(), target.getAdapterPosition());
    }
    //当侧滑时被回调
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(TAG, "onSwiped: ");
        //监听侧滑：1.删除数据，2。调用adapter进行remove更新
        mMoveListener.onItemRemove(viewHolder.getAdapterPosition());
    }


    /**
     *
     * @param viewHolder 选中状态下的可移动条目
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //判断选中状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //松手时恢复itemView的背景色
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //恢复
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        //加下面代码的原因是recyclerView会复用itemView，所以你已经在移除时把该itemview设为透明，所以会有bug
//        //透明度动画
//        viewHolder.itemView.setAlpha(1);
//        //比例缩小动画
//        viewHolder.itemView.setScaleX(1);
//        viewHolder.itemView.setScaleY(1);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //dx:水平方向移动的偏移量(负时往左，正时往右)
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
            //透明度动画
            viewHolder.itemView.setAlpha(alpha);
            //比例缩小动画




            viewHolder.itemView.setScaleX(alpha);
            viewHolder.itemView.setScaleY(alpha);
        }
        if (viewHolder.itemView.getAlpha() == 0) { //里面的代码和放到85-90行的效果一样
            //        //透明度动画
            viewHolder.itemView.setAlpha(1);
            //比例缩小动画
            viewHolder.itemView.setScaleX(1);
            viewHolder.itemView.setScaleY(1);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
