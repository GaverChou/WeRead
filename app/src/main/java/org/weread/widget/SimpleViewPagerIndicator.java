package org.weread.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.weread.util.DensityUtil;

public class SimpleViewPagerIndicator extends RadioGroup {

    private int inditorColor = 0xAA244e71;

    private int mTabCount;
    private int mIndicatorColor = inditorColor;
    private float mTranslationX;
    private Paint mPaint = new Paint();
    private int mTabWidth;
    private RadioButton[] mRadioBtns;

    public SimpleViewPagerIndicator(Context context) {
        this(context, null);
    }

    public SimpleViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(mIndicatorColor);
        mPaint.setStrokeWidth(DensityUtil.dip2px(context, 2.0F));
    }

    public void setmRadioBtns(RadioButton[] mRadioBtns) {
        this.mRadioBtns = mRadioBtns;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w / mTabCount;
    }

    public void setTabCount(int count) {
        mTabCount = count;
    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mTranslationX, getHeight() - DensityUtil.dip2px(getContext(), 1));
        canvas.drawLine(0, 0, mTabWidth, 0, mPaint);
        canvas.restore();
    }

    public void scroll(int position, float offset) {
        /**
         * <pre>
         *  0-1:position=0 ;1-0:postion=0;
         * </pre>
         */
        if (mRadioBtns != null && mRadioBtns.length == mTabCount) {
            mRadioBtns[position].setChecked(true);
        }
        mTranslationX = getWidth() / mTabCount * (position + offset);
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
