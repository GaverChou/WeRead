package org.weread.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.weread.R;
import org.weread.model.ArtTypeItem;
import org.weread.model.Article;
import org.weread.model.ArticleItem;
import org.weread.model.NewsItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-05.
 */
public class NewsListAdapter extends BaseAdapter {

    private List<ArticleItem> mItems = new ArrayList<>();
    private boolean isDetail;

    public NewsListAdapter() {
    }

    public void setDetail(boolean isDetail) {
        this.isDetail = isDetail;
    }

    public NewsListAdapter(List<ArticleItem> mItems) {
        this.mItems = mItems;
    }
    public synchronized void add(ArticleItem item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public synchronized void removeAll() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public synchronized void addAll(List<ArticleItem> items) {
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
        ViewHolder holder = null;
         ArticleItem article = mItems.get(pos);
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(parent
                    .getContext());
            convertView = layoutInflator.inflate(
                    R.layout.news_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }  holder.title.setText(article.mArtTit);
        holder.sumary.setText(article.mSumary);
        holder.from.setText(article.mCreateTime);
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
            likeBtn.setVisibility(View.INVISIBLE);
        }
    }
}
