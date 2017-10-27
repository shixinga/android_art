package one.com.v2listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 * Created by shixing on 2017/6/20.
 */

public class FlexableListview extends ListView {
    private int mMaxOverDistance = 50;
    private Context mContext;
    public FlexableListview(Context context) {
        super(context);
    }

    public FlexableListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FlexableListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FlexableListview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mMaxOverDistance = (int) (displayMetrics.density * mMaxOverDistance);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverDistance, isTouchEvent);
    }
}
