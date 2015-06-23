package org.weread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import org.weread.adapter.ArtTypeGridAdapter;
import org.weread.adapter.NewsListAdapter;
import org.weread.dao.CollectionDao;
import org.weread.model.ArtTypeItem;
import org.weread.model.Article;
import org.weread.model.ArticleItem;
import org.weread.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-19.
 */
public class NewsFragment extends BaseFragment<MainActivity> {
    @InjectView(R.id.main_listview)
    ListView mListView;
    @InjectView(R.id.recycleview_loadmore)
    View mRefresh;
    NewsListAdapter adapter;

    private CollectionDao collectionDao;
    private static NewsFragment mInstance = null;

    public synchronized static NewsFragment getInstance() {
        if (mInstance == null) {
            mInstance = new NewsFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, savedInstanceState, R.layout.listview);
    }

    @Override
    protected void initData() {
        super.initData();
        collectionDao = new CollectionDao(mActivity);
    }

    protected void initView(View view) {
        mRefresh.setBackgroundColor(0xffffffff);
        mSwipeRefreshLayout.setmRefreshView(mRefresh);
        List<ArticleItem> articleItems = collectionDao.queryArticleItem();
        adapter = new NewsListAdapter(articleItems);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, ArtDetailActivity.class);
                intent.putExtra(ArticleByTagActivity.TITLE,
                        ((ArticleItem) adapter.getItem(i)).mArtTit);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        if (pullToRefreshEnabled()) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<ArticleItem> articleItems = collectionDao.queryArticleItem();
                            adapter.removeAll();
                            adapter.addAll(articleItems);
                            setRefreshing(false);
                        }

                    }, 2000);
                }

            });
        }
    }
}
