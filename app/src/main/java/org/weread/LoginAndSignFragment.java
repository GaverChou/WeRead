package org.weread;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.weread.widget.SimpleTextViewPagerIndicator;

/**
 * Created by GaverChou on 2015-06-20.
 */
public class LoginAndSignFragment extends BaseFragment<MainActivity> {
    protected String[] mTitles = null;
    protected SimpleTextViewPagerIndicator mIndicator;
    protected ViewPager mViewPager;
    protected FragmentPagerAdapter mAdapter;
    protected Fragment[] mFragments = null;
    public static LoginAndSignFragment mInstance = null;

    public synchronized static LoginAndSignFragment instance() {
        if (mInstance == null) {
            mInstance = new LoginAndSignFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_signin, container, false);
        initViews(view);
        initDatas();
        initEvents();
        return view;
    }

    protected void initEvents() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });
        if (mIndicator != null) {
            for (int i = 0, count = mIndicator.textViews.size(); i < count; i++) {
                final int index = i;
                mIndicator.textViews.get(i).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViewPager.setCurrentItem(index);
                            }
                        });
            }
        }
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
        mIndicator = (SimpleTextViewPagerIndicator) view
                .findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) view
                .findViewById(R.id.id_stickynavlayout_viewpager);
        mTitles = getResources().getStringArray(R.array.loginsinin_tit_list);
        mFragments = new Fragment[mTitles.length];
        mIndicator.setTitles(mTitles);
        mFragments[0] = SigninFragment.getInstance();
        mFragments[1] = LoginFragment.getInstance();
    }
}