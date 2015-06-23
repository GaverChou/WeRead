package org.weread;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.gc.materialdesign.widgets.SnackBar;

import org.weread.api.ArticleApi;
import org.weread.dao.ArticleAllDao;
import org.weread.model.Article;
import org.weread.network.NetworkImageLoader;
import org.weread.network.ResponseListener;

import java.text.SimpleDateFormat;

import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-20.
 */
public class ArtDetailActivity extends BaseActivity {
    public final static String TITLE = "title";
    @InjectView(R.id.art_detail_thumb_imgv)
    NetworkImageView thumb;
    @InjectView(R.id.art_detail_tit_tv)
    TextView mTitleTv;
    @InjectView(R.id.art_detail_date_tv)
    TextView mDateTv;
    @InjectView(R.id.webview)
    WebView webView;
    private String mTitleStr;

    protected ArticleAllDao articleAllDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_detail);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        mTitleStr = intent.getStringExtra(TITLE);
        articleAllDao = new ArticleAllDao(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDefaultFontSize(16);
        webSettings.setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        webView.setBackgroundColor(getResources().getColor(R.color.bg_theme)); // 设置背景色
    }

    @Override
    protected void initView() {
        super.initView();
        Article article = articleAllDao.queryArticleByTitle(mTitleStr);
        if (article.getCreateTime() == null) {
            ArticleApi.getArticleByTitle(mTitleStr, new ResponseListener<Article>() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new SnackBar(ArtDetailActivity.this,
                            "服务器连接异常！").show();
                }

                @Override
                public void onResponse(Article response) {
                    mTitleTv.setText(response.getTitle());
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    mDateTv.setText(sdf.format(response.getCreateTime()));
                    NetworkImageLoader.loadIntoNetworkImageView(thumb, response.getImgurl(), R.mipmap.profile_bg);
                    webView.loadData(response.getContent(), "text/html; charset=UTF-8", null);
                }
            });
        } else {
            mTitleTv.setText(article.getTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            mDateTv.setText(sdf.format(article.getCreateTime()));
            NetworkImageLoader.loadIntoNetworkImageView(thumb, article.getImgurl(), R.mipmap.profile_bg);
            webView.loadData(article.getContent(), "text/html; charset=UTF-8", null);
        }
        webView.setVisibility(View.VISIBLE);
    }
}
