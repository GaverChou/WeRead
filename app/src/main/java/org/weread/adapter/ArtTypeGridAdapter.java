package org.weread.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.weread.R;
import org.weread.model.ArtTypeItem;
import org.weread.model.ArtcleType;
import org.weread.network.NetworkImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaverChou on 2015-06-05.
 */
public class ArtTypeGridAdapter extends BaseAdapter {

    private List<ArtcleType> mItems = new ArrayList<>();
    private boolean isDetail;

    public ArtTypeGridAdapter() {
    }

    public void setDetail(boolean isDetail) {
        this.isDetail = isDetail;
    }

    public ArtTypeGridAdapter(List<ArtcleType> mItems) {
        this.mItems = mItems;
    }

    public synchronized void add(ArtcleType item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public synchronized void removeAll() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public synchronized void addAll(List<ArtcleType> items) {
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
        ArtcleType artcleType = mItems.get(pos);
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(parent
                    .getContext());
            holder = new ViewHolder();
            convertView = layoutInflator.inflate(
                    R.layout.article_type_item, null);
            holder.thumb = (NetworkImageView) convertView.findViewById(R.id.art_type_item_img);
            holder.title = (TextView) convertView.findViewById(R.id.art_item_type_tit_tv);
            holder.count = (TextView) convertView.findViewById(R.id.art_item_type_count_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(artcleType.getType());
        holder.count.setText(artcleType.getTypeCount()+"篇");
        NetworkImageLoader.loadIntoNetworkImageView(holder.thumb, artcleType.getTypeUrl(), R.mipmap.profile_bg);
        return convertView;
    }

    class ViewHolder {
        NetworkImageView thumb;
        TextView title;
        TextView count;
    }
}
