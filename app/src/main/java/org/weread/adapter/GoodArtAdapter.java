package org.weread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.weread.R;
import org.weread.model.ArticleItem;
import org.weread.network.NetworkImageLoader;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by GaverChou on 2015-05-27.
 */
public class GoodArtAdapter extends SimpleRecyclerAdapter<ArticleItem> {
    public GoodArtAdapter(Context context) {
        super(context, new ArrayList<ArticleItem>());
    }

    public GoodArtAdapter(Context context, ArrayList<ArticleItem> titleList) {
        super(context, titleList);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewHolder(mInflater.inflate(R.layout.article_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CardViewHolder) {
            CardViewHolder holder = (CardViewHolder) viewHolder;
            ArticleItem lArticleItem = mItems.get(position);
            holder.artTitTv.setText(lArticleItem.mArtTit);
            holder.artDateTv.setText(lArticleItem.mCreateTime);
            holder.artSumaryTv.setText(lArticleItem.mSumary);
            NetworkImageLoader.loadIntoNetworkImageView(holder.artThumb, lArticleItem.mImgUrl, R.mipmap.profile_bg);
        }
    }

    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.art_item_tit_tv)
        TextView artTitTv;
        @InjectView(R.id.art_item_date_tv)
        TextView artDateTv;
        @InjectView(R.id.art_item_sumary_tv)
        TextView artSumaryTv;
        @InjectView(R.id.art_item_img)
        NetworkImageView artThumb;

        public CardViewHolder(View convertView) {
            super(convertView);
            ButterKnife.inject(this, convertView);
            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemOnclickListener != null) {
                itemOnclickListener.onItemClick(view, getPosition(),mItems.get(getPosition()));
            }
        }
    }
}
