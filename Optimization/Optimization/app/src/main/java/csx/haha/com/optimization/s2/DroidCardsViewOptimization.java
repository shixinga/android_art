package csx.haha.com.optimization.s2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import csx.haha.com.optimization.R;

public class DroidCardsViewOptimization extends View {
    //图片与图片之间的间距
    private int mCardSpacing = 100;
    //图片与左侧距离的记录
    private int mCardLeft = 10;

    private List<DroidCard> mDroidCards = new ArrayList<DroidCard>();

    private Paint paint = new Paint();

    public DroidCardsViewOptimization(Context context) {
        super(context);
        initCards();
    }

    public DroidCardsViewOptimization(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCards();
    }



    /**
     * 初始化卡片集合
     */
    protected void initCards(){
        Resources res = getResources();
        mDroidCards.add(new DroidCard(res,R.drawable.alex,mCardLeft));

        mCardLeft+=mCardSpacing;
        mDroidCards.add(new DroidCard(res, R.drawable.claire,mCardLeft));

        mCardLeft+=mCardSpacing;
        mDroidCards.add(new DroidCard(res,R.drawable.kathryn,mCardLeft));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mDroidCards.size() - 1; ++i) {
            drawDroidCardByClip(canvas, mDroidCards.get(i), i);
        }
        drawDroidCard(canvas, mDroidCards.get(mDroidCards.size() - 1));
        invalidate();
    }

    /**
     * 虽然会少量的增加cpu的计算（增加了方法调用）
     * 但是能够减轻GPU的压力
     * @param canvas
     * @param droidCard
     * @param i
     */
    private void drawDroidCardByClip(Canvas canvas, DroidCard droidCard, int i) {
        canvas.save();
        canvas.clipRect(droidCard.x, 0f, mDroidCards.get(i + 1).x, droidCard.height);
        canvas.drawBitmap(droidCard.bitmap,droidCard.x,0f, paint);
        canvas.restore();
    }

    /**
     * 绘制DroidCard
     * @param canvas
     * @param c
     */
    private void drawDroidCard(Canvas canvas, DroidCard c) {
        canvas.drawBitmap(c.bitmap,c.x,0f,paint);
    }
}
