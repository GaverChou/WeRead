package org.weread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.weread.adapter.ArticleListAdapter;
import org.weread.api.ArticleApi;
import org.weread.model.Article;
import org.weread.network.ResponseListener;
import org.weread.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-20.
 */
public class ArticleByTagActivity extends BaseActivity {

    public final static String TITLE = "title";
    @InjectView(R.id.art_by_tag_tit_tv)
    TextView mTitleTv;
    @InjectView(R.id.main_listview)
    ListView listView;
    @InjectView(R.id.recycleview_loadmore)
    View mRefresh;
    ArticleListAdapter adapter;
    private String mTitle;
    private int pageNow = 1;
    public final static int pageSize = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_by_tag);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(TITLE);
    }

    @Override
    protected void initView() {
        super.initView();
        mRefresh.setBackgroundColor(0xffffffff);
        mSwipeRefreshLayout.setmRefreshView(mRefresh);
        adapter = new ArticleListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ArticleByTagActivity.this, ArtDetailActivity.class);
                intent.putExtra(ArticleByTagActivity.TITLE,
                        ((Article) adapter.getItem(i)).getTitle());
                startActivity(intent);
            }
        });
        mTitleTv.setText(mTitle);
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

            mSwipeRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
                @Override
                public void onLoad() {
                    loadMoreData();
                }
            });
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    setRefreshing(true);
                    reFreshDate();
                }
            });
        }
    }

    protected void reFreshDate(){
        ArticleApi.getArticleByPageApi(mTitle, 1, pageSize, new ResponseListener<List<Article>>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", error.toString());
                setRefreshing(false);
            }

            @Override
            public void onResponse(List<Article> response) {
                pageNow = 1;
                if (response != null && response.size() > 0) {
                    adapter.removeAll();
                    adapter.addAll(response);
                }
                setRefreshing(false);
            }
        });
    }

    protected void loadMoreData(){
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArticleApi.getArticleByPageApi(mTitle, pageNow + 1, pageSize, new ResponseListener<List<Article>>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", error.toString());
                        mSwipeRefreshLayout.setLoading(false);
                    }

                    @Override
                    public void onResponse(List<Article> response) {
                        pageNow++;
                        adapter.addAll(response);
                        mSwipeRefreshLayout.setLoading(false);
                    }
                });
            }
        }, 1500);
    }
}
