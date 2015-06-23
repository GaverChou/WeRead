package org.weread;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.weread.callable.ViewPageChangeListener;
import org.weread.widget.SimpleViewPagerIndicator;


/**
 * Created by GaverChou on 2015-05-29.
 */
public class BaseIndicatorFragment<T> extends BaseFragment {
    protected SimpleViewPagerIndicator mIndicator;
    protected ViewPager mViewPager;
    protected FragmentPagerAdapter mAdapter;
    protected Fragment[] mFragments = null;

    protected void initEvents() {
        mViewPager.setOnPageChangeListener(new ViewPageChangeListener(mIndicator));
    }

    protected void onInit() {

    }

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    protected void initDatas() {
        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    protected void initViews(View view) {
        super.initView(view);
        mIndicator = (SimpleViewPagerIndicator) view
                .findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) view
                .findViewById(R.id.id_stickynavlayout_viewpager);
    }
}
