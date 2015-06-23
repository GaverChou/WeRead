package org.weread;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.weread.callable.ViewPageChangeListener;
import org.weread.widget.SimpleViewPagerIndicator;

import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-20.
 */
public class LoginAndSigninActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_signin);
    }
}
