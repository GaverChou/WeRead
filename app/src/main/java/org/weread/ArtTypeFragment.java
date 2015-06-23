package org.weread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.VolleyError;

import org.weread.adapter.ArtTypeGridAdapter;
import org.weread.api.ArticleApi;
import org.weread.callable.OnItemClickListener;
import org.weread.dao.ArticleTypeDao;
import org.weread.dao.UserInfoKeeper;
import org.weread.model.ArtcleType;
import org.weread.network.ResponseListener;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-19.
 */
public class ArtTypeFragment extends BaseFragment<MainActivity> {
    @InjectView(R.id.main_grid)
    GridView mGridView;
    ArtTypeGridAdapter adapter;

    protected ArticleTypeDao articleTypeDao;
    private static ArtTypeFragment mInstance = null;

    public synchronized static ArtTypeFragment getInstance() {
        if (mInstance == null) {
            mInstance = new ArtTypeFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, savedInstanceState, R.layout.gridview);
    }

    @Override
    protected void initData() {
        super.initData();
        articleTypeDao = new ArticleTypeDao(mActivity);
    }

    protected void initView(View view) {
        adapter = new ArtTypeGridAdapter(articleTypeDao.query());
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new ArtTypeItemListener());
    }

    class ArtTypeItemListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
            ArtcleType artcleType = (ArtcleType) adapter.getItem(pos);
            Intent intent = new Intent(mActivity, ArticleByTagActivity.class);
            intent.putExtra(ArticleByTagActivity.TITLE,
                    artcleType.getType());
            startActivity(intent);
        }
    }

    @Override
    protected void initEvent() {
        if (pullToRefreshEnabled()) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    reFreshDate();
                }
            });
            if (UserInfoKeeper.isFirstArtType(mActivity)) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshing(true);
                        reFreshDate();
                        UserInfoKeeper.setIsFirstArtType(mActivity);
                    }
                });
            }
        }
    }

    private void reFreshDate() {
        ArticleApi.getArticleTypeApi(new ResponseListener<List<ArtcleType>>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", error.toString());
                setRefreshing(false);
            }

            @Override
            public void onResponse(List<ArtcleType> response) {
                articleTypeDao.deleteAll();
                adapter.removeAll();
                articleTypeDao.addActionResult(response);
                List<ArtcleType> articleItems = articleTypeDao.query();
                adapter.addAll(articleItems);
                setRefreshing(false);
            }
        });
    }
}
