package org.weread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import org.weread.adapter.GoodArtAdapter;
import org.weread.api.ArticleApi;
import org.weread.callable.ABaseLinearLayoutManager;
import org.weread.callable.OnItemClickListener;
import org.weread.callable.OnRecyclerViewScrollLocationListener;
import org.weread.dao.ArticleAllDao;
import org.weread.dao.UserInfoKeeper;
import org.weread.model.Article;
import org.weread.model.ArticleItem;
import org.weread.network.ResponseListener;
import org.weread.widget.RefreshLayout;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-19.
 */
public class GoodArticleFragment extends BaseFragment<MainActivity> {
    @InjectView(R.id.main_list)
    RecyclerView mListView;
    GoodArtAdapter adapter;
    @InjectView(R.id.recycleview_loadmore)
    View loadMore;
    private int pageNow = 1;
    public final static int pageSize = 3;
    protected ArticleAllDao articleAllDao;
    private static GoodArticleFragment mInstance = null;

    public synchronized static GoodArticleFragment getInstance() {
        if (mInstance == null) {
            mInstance = new GoodArticleFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, savedInstanceState, R.layout.recycleview);
    }

    @Override
    protected void initData() {
        super.initData();
        articleAllDao = new ArticleAllDao(mActivity);
    }

    protected void initView(View view) {
        loadMore.setVisibility(View.GONE);
        mListView.setHasFixedSize(true);
        final ABaseLinearLayoutManager layoutManager = new ABaseLinearLayoutManager(mActivity);
        layoutManager.setOnRecyclerViewScrollLocationListener(mListView, new OnRecyclerViewScrollLocationListener() {
            @Override
            public void onBottomWhenScrollIdle(RecyclerView recyclerView) {
                loadMoreData();
            }

            @Override
            public void onTopWhenScrollIdle(RecyclerView recyclerView) {

            }
        });
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);
//        mListView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new GoodArtAdapter(mActivity);
        List<ArticleItem> articleItemResults = articleAllDao.queryArticleItem();
        adapter.addAll(articleItemResults);
        mListView.setAdapter(adapter);
        adapter.setItemOnclickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object obj) {
                Intent intent = new Intent(mActivity, ArtDetailActivity.class);
                intent.putExtra(ArticleByTagActivity.TITLE,
                        ((ArticleItem) obj).mArtTit);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initEvent() {
        if (pullToRefreshEnabled()) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    reFreshData();
                }
            });
            if (UserInfoKeeper.isFirstGood(mActivity)) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshing(true);
                        reFreshData();
                        UserInfoKeeper.setIsFirstGood(mActivity);
                    }
                });
            }
        }
    }

    public void reFreshData() {

        ArticleApi.getArticleAllByPageApi(1, pageSize, new ResponseListener<List<Article>>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", error.toString());
                setRefreshing(false);
            }

            @Override
            public void onResponse(List<Article> response) {
                pageNow = 1;
                if (response != null && response.size() > 0) {
                    articleAllDao.deleteAll();
                    adapter.removeAll();
                    try {
                        articleAllDao.addActionResult(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    List<ArticleItem> articleItems = articleAllDao.queryArticleItem();
                    adapter.addAll(articleItems);
                }
                setRefreshing(false);
            }
        });
    }

    boolean canLoadMore = true;

    protected synchronized void loadMoreData() {
        if (canLoadMore) {
            canLoadMore = false;
            loadMore.setVisibility(View.VISIBLE);
            ArticleApi.getArticleAllByPageApi(pageNow + 1, pageSize, new ResponseListener<List<Article>>() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG", error.toString());
                    loadMore.setVisibility(View.GONE);
                    canLoadMore = true;
                }

                @Override
                public void onResponse(List<Article> response) {
                    pageNow++;
                    try {
                        articleAllDao.addActionResult(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    List<ArticleItem> articleItems = articleAllDao.queryArticleItem();
                    adapter.addAll(articleItems);
                    loadMore.setVisibility(View.GONE);
                    canLoadMore = true;
                }
            });
        }
    }
}