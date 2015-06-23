package org.weread;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.weread.callable.SwipeViewCallback;
import org.weread.widget.RefreshLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by GaverChou on 2015-06-19.
 */
public abstract class BaseActivity extends AppCompatActivity implements SwipeViewCallback {
    @Optional
    @InjectView(R.id.main_toolbar)
    protected Toolbar mToolbar;
    protected int mBaseColor;
    @Optional
    @InjectView(R.id.listview_refresh)
    protected RefreshLayout mSwipeRefreshLayout;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        init();
    }

    protected void init() {
        initData();
        initView();
        initEvent();
    }

    protected void initEvent() {

    }

    protected void initData() {
        mBaseColor = getResources().getColor(R.color.primary);
    }

    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(null);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(refreshing);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean pullToRefreshEnabled() {
        return mSwipeRefreshLayout != null;
    }

    @Override
    public int[] getPullToRefreshColorResources() {
        return new int[]{R.color.primary};
    }
}
