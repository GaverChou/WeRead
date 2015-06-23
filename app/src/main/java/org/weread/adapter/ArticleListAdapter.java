package org.weread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.weread.R;
import org.weread.api.ArticleApi;
import org.weread.dao.CollectionDao;
import org.weread.model.ArtcleType;
import org.weread.model.Article;
import org.weread.model.NewsItem;
import org.weread.network.ResponseListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-05.
 */
public class ArticleListAdapter extends BaseAdapter {

    private ArrayList<Article> mItems = new ArrayList<>();
    private boolean isDetail;
    private CollectionDao collectionDao;

    public ArticleListAdapter(Context context) {
        collectionDao = new CollectionDao(context);
    }

    public void setDetail(boolean isDetail) {
        this.isDetail = isDetail;
    }

    public ArticleListAdapter(Context context, ArrayList<Article> mItems) {
        this.mItems = mItems;
        collectionDao = new CollectionDao(context);
    }

    public synchronized void add(Article item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public synchronized void removeAll() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public synchronized void addAll(List<Article> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final Article article = mItems.get(pos);
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(parent
                    .getContext());
            convertView = layoutInflator.inflate(
                    R.layout.news_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(article.getTitle());
        holder.sumary.setText(article.getSumary());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        holder.from.setText(sdf.format(article.getCreateTime()));
        holder.jvli.setText(article.getCollectCount() + "收藏");
        if (collectionDao.queryArticleByTitle(article.getTitle()) == null) {
            holder.likeBtn.setImageResource(R.mipmap.ic_heart_outline_grey);
        } else {
            holder.likeBtn.setImageResource(R.mipmap.ic_heart_red);
        }
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (collectionDao.queryArticleByTitle(article.getTitle()) == null) {
                    collectionDao.addActionResult(article);
                    ArticleApi.updateCollection(article.getTitle(), new ResponseListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                        @Override
                        public void onResponse(Object response) {

                        }
                    });
                    holder.jvli.setText((article.getCollectCount() + 1) + "收藏");
                    holder.likeBtn.setImageResource(R.mipmap.ic_heart_red);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.news_item_tit_tv)
        TextView title;
        @InjectView(R.id.news_item_jvli_tv)
        TextView jvli;
        @InjectView(R.id.news_item_from_tv)
        TextView from;
        @InjectView(R.id.news_item_sumary_tv)
        TextView sumary;
        @InjectView(R.id.news_item_like_btn)
        ImageButton likeBtn;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }
    }
}
