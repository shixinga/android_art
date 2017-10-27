package one.com.v4scroll;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;

/**
 * 包含三种实现滑动效果的方法[本质都是改变布局参数的方式]，都在自定义的MyViewCanMove的onTouchEvent()里面
 * Created by shixing on 2017/6/21.
 */

public class MyViewCanMove extends View {
    private static final String TAG = "MyViewCanMove";
    private int mLastX;
    private int mLastY;
    public MyViewCanMove(Context context) {
        super(context);
    }

    public MyViewCanMove(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewCanMove(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyViewCanMove(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout: "+ left+"," + top + "," + right + "," + bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: down");
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: move");
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                //方法一：
                layout(getLeft()+offsetX,getTop()+offsetY,getRight()+offsetX,getBottom()+offsetY);//会调用onLayout()
                //layout(getLeft()+offsetX,getTop()+offsetY,getRight()+offsetX,getBottom()+offsetY);等同于下面的2行注释代码

                //方法二：
//                //同时对left和right进行位移
//                offsetLeftAndRight(offsetX);
//                //同时对top和bottom进行位移
//                offsetTopAndBottom(offsetY);

                //方法三：
                //相对于父控件的布局，
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offsetX;
//                layoutParams.topMargin = getTop() + offsetY;
//                setLayoutParams(layoutParams);


                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                
                break;
        }
        //默认为false,即不会触发MotionEvent.ACTION_MOVE，所以要返回true，这次滑动才有效果
        Log.d(TAG, "onTouchEvent: "+super.onTouchEvent(event));
        return true;
    }
}
