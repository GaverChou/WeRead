package org.weread.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.weread.R;
import org.weread.util.DensityUtil;

import java.util.ArrayList;

public class SimpleTextViewPagerIndicator extends LinearLayout {

    private int textColor = 0xFFFFFFFF;
    private int inditorColor = 0xAA244e71;

    private String[] mTitles;
    private int mTabCount;
    private int mIndicatorColor = inditorColor;
    private int mFocusTextColor = 0xffc9def9;
    private float mTranslationX;
    private Paint mPaint = new Paint();
    private int mTabWidth;

    public SimpleTextViewPagerIndicator(Context context) {
        this(context, null);
    }

    public SimpleTextViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mPaint.setColor(mIndicatorColor);
        mPaint.setStrokeWidth(DensityUtil.dip2px(context, 2.0F));
    }

    public SimpleTextViewPagerIndicator(Context context, AttributeSet attrs,
                                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Indicator, defStyleAttr, 0);
        int n = arr.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = arr.getIndex(i);
            switch (attr) {
                case R.styleable.Indicator_indicatorColor:
                    mIndicatorColor = arr.getInteger(attr, inditorColor);
                    break;
            }
        }
        arr.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w / mTabCount;
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
        mTabCount = titles.length;
        generateTitleView();

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
        mTranslationX = getWidth() / mTabCount * (position + offset);
        for (int i = 0; i < textViews.size(); i++) {
            if (position == i) {
                textViews.get(i).setTextColor(textColor);
            } else {
                textViews.get(i).setTextColor(mFocusTextColor);
            }
        }
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public ArrayList<TextView> textViews = new ArrayList<TextView>();

    private void generateTitleView() {
        if (getChildCount() > 0)
            this.removeAllViews();
        int count = mTitles.length;

        setWeightSum(count);
        for (int i = 0; i < count; i++) {
            TextView tv = new TextView(getContext());
            LayoutParams lp = new LayoutParams(0,
                    LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(textColor);
            tv.setText(mTitles[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            tv.setLayoutParams(lp);
            textViews.add(tv);
            addView(tv);
        }
    }
}
