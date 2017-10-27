package one.com.v4scroll;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * scrollBy和scrollTo方式实现滑动的效果，都在自定义的MyViewCanMove的onTouchEvent()里面
 * Created by shixing on 2017/6/21.
 */

public class MyViewCanMove2 extends View {
    private static final String TAG = "MyViewCanMove2";
    private int mLastX;
    private int mLastY;
    public MyViewCanMove2(Context context) {
        super(context);
    }

    public MyViewCanMove2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewCanMove2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyViewCanMove2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        Log.d(TAG, "onLayout: "+ left+"," + top + "," + right + "," + bottom );
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
                Log.d(TAG, "onTouchEvent: down:getX()=" + getX() + ",getY()=" + getY() + ",transactionX=" + getTranslationX()
                        + ",transactionY=" + getTranslationY() + ",event.getX()=" + x + "event.getY()=" + y
                        +",event.getRawX()=" + event.getRawX() + ",event.getRawY()=" +event.getRawY());
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent move:getX()=" + getX() + ",getY()=" + getY() + ",transactionX=" + getTranslationX()
                        + ",transactionY=" + getTranslationY() + ",event.getX()=" + x + "event.getY()=" + y
                +",event.getRawX()=" + event.getRawX() + ",event.getRawY()=" +event.getRawY());
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                //scrollBy()会调用onDraw(),scrollBy()本质是调用scrollTo()
                //自定义View中，scrollBy(offsetX,offsetY);会移动该View里面的内容，而不是移动该View本身
                //自定义ViewGroup中，scrollBy(offsetX,offsetY);会移动该ViewGroup里面的所有的子View
                //所以下面注释的代码是错的
//                scrollBy(offsetX,offsetY);

                //下面的方法不会调用该自定义的View的onDraw()、onLayout()
                ((View)getParent()).scrollBy(-offsetX,-offsetY);

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
