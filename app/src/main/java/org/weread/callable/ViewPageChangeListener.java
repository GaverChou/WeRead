package org.weread.callable;

import android.support.v4.view.ViewPager;

import org.weread.widget.SimpleViewPagerIndicator;

/**
 * Created by GaverChou on 2015-06-20.
 */
public class ViewPageChangeListener implements ViewPager.OnPageChangeListener {

    protected SimpleViewPagerIndicator mIndicator;
    public ViewPageChangeListener(SimpleViewPagerIndicator mIndicator) {
        this.mIndicator = mIndicator;
    }
    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        if (mIndicator != null) {
            mIndicator.scroll(position, positionOffset);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
