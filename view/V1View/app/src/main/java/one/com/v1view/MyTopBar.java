package one.com.v1view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.R.attr.format;
import static android.R.attr.name;
import static android.content.ContentValues.TAG;
import static android.os.Build.VERSION_CODES.M;
import static one.com.v1view.R.attr.titleText;

/**
 * Created by shixing on 2017/6/19.
 */

public class MyTopBar extends RelativeLayout {
    private Button mLeftButton,mRightButton;
    private TextView mTv_title;

    private String mLeftText;
    private int mLeftColor;
    private Drawable mLeftBackground;

    private String mRightText;
    private int mRightColor;
    private Drawable mRightBackground;


    private String mTitleText;
    private int mTitleTextColor;
    private float  mTitleTextSize;

    private static final String TAG = "MyTopBar";

    public interface IButtonClickListener {
        void leftButtonClick();
        void rightButtonClick();
    }

    private IButtonClickListener mIbuttoneClickListener;

    public void setmIbuttoneClickListener(IButtonClickListener mIbuttoneClickListener) {
        this.mIbuttoneClickListener = mIbuttoneClickListener;
    }

    public MyTopBar(Context context) {
        super(context);

    }

    public MyTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MyTopBar(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        init(context,attrs);
        Log.d(TAG, "MyTopBar: ");
    }

    public MyTopBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    
    public void init(Context context,AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.MyTopBar);
        mTitleText = ta.getString(R.styleable.MyTopBar_titleText);
        mLeftText = ta.getString(R.styleable.MyTopBar_leftText);
        mRightText = ta.getString(R.styleable.MyTopBar_rightText);
        mTitleTextColor = ta.getColor(R.styleable.MyTopBar_titleColor,0xff0000);
        mLeftColor = ta.getColor(R.styleable.MyTopBar_leftTextColor,0xff0000);
        mRightColor = ta.getColor(R.styleable.MyTopBar_rightTextColor,0xff0000);

        //注意不是ta.getFloat()
        mTitleTextSize = ta.getDimension(R.styleable.MyTopBar_titleTextSize,8);
        mLeftBackground = ta.getDrawable(R.styleable.MyTopBar_leftBackground);
        mRightBackground = ta.getDrawable(R.styleable.MyTopBar_rightBackground);

        ta.recycle();
        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTv_title = new TextView(context);



        //设值
        mTv_title.setText(mTitleText);
        mTv_title.setTextColor(mTitleTextColor);
        mTv_title.setTextSize(mTitleTextSize);
        mTv_title.setGravity(Gravity.CENTER);

        mLeftButton.setText(mLeftText);
        mLeftButton.setTextColor(mLeftColor);
        mLeftButton.setBackground(mLeftBackground);

        mRightButton.setText(mRightText);
        mRightButton.setTextColor(mRightColor);
        mRightButton.setBackground(mRightBackground);

        setBackgroundColor(0xff5678);
        //布局
        LayoutParams leftLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        LayoutParams rightRp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightRp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        LayoutParams titleLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLp.addRule(RelativeLayout.CENTER_IN_PARENT);

        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mIbuttoneClickListener.leftButtonClick();
            }
        });

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mIbuttoneClickListener.rightButtonClick();
            }
        });
        addView(mLeftButton,leftLp);
        addView(mRightButton,rightRp);
        addView(mTv_title,titleLp);
        Log.d(TAG, "MyTopBar: ");
    }
}
