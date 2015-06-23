package org.weread;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-19.
 */
public class HomeFragment extends BaseIndicatorFragment implements View.OnClickListener{
    @InjectView(R.id.home_home_btn)
    RadioButton mHomeBtn;
    @InjectView(R.id.home_label_btn)
    RadioButton mLabelBtn;
    @InjectView(R.id.home_discover_btn)
    RadioButton mDiscoverBtn;
    @InjectView(R.id.home_me_btn)
    RadioButton mMeBtn;
    public static HomeFragment mInstance = null;

    public synchronized static HomeFragment instance() {
        if (mInstance == null) {
            mInstance = new HomeFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inditor, container, false);
        ButterKnife.inject(this,view);
        initViews(view);
        initDatas();
        initEvents();
        return view;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        RadioButton[] radioButtons= new RadioButton[4];
        radioButtons[0] = mHomeBtn;
        radioButtons[1] = mLabelBtn;
        radioButtons[2] = mDiscoverBtn;
        radioButtons[3] = mMeBtn;
        mIndicator.setTabCount(4);
        mIndicator.setmRadioBtns(radioButtons);
        mFragments = new Fragment[4];
        mFragments[0] = GoodArticleFragment.getInstance();
        mFragments[1] = ArtTypeFragment.getInstance();
        mFragments[2] = NewsFragment.getInstance();
        mFragments[3] = MeFragment.getInstance();
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mHomeBtn.setOnClickListener(this);
        mLabelBtn.setOnClickListener(this);
        mDiscoverBtn.setOnClickListener(this);
        mMeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.home_home_btn:
                mHomeBtn.setChecked(true);
                mViewPager.setCurrentItem(0);break;
            case R.id.home_label_btn:
                mLabelBtn.setChecked(true);
                mViewPager.setCurrentItem(1);break;
            case R.id.home_discover_btn:
                mDiscoverBtn.setChecked(true);
                mViewPager.setCurrentItem(2);break;
            case R.id.home_me_btn:
                mMeBtn.setChecked(true);
                mViewPager.setCurrentItem(3);break;
        }
    }
}