package org.weread;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gc.materialdesign.widgets.ProgressDialog;

import org.weread.callable.SwipeViewCallback;
import org.weread.widget.RefreshLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by mrgaver on 15-5-28.
 */
public abstract class BaseFragment<T> extends Fragment implements SwipeViewCallback {
    @Optional
    @InjectView(R.id.listview_refresh)
    protected RefreshLayout mSwipeRefreshLayout;
    protected T mActivity;
    protected int mBaseColor;

    protected ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (T) getActivity();
        mBaseColor = getResources().getColor(R.color.primary);
        setHasOptionsMenu(true);
    }

    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState, int viewID) {
        View view = inflater.inflate(viewID, container, false);
        ButterKnife.inject(this, view);
        initData();
        initView(view);
        initEvent();
        return view;
    }

    protected void initData() {
    }

    protected void initView(View view) {
    }

    protected void initEvent() {
        if (pullToRefreshEnabled()) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            setRefreshing(false);
                        }

                    }, 2000);
                }

            });
            mSwipeRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {

                @Override
                public void onLoad() {
                    mSwipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 加载完后调用该方法
                            mSwipeRefreshLayout.setLoading(false);
                        }
                    }, 1500);
                }
            });
        }
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    public void setProgressDismiss() {
        if (progressDialog != null) progressDialog.dismiss();
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
