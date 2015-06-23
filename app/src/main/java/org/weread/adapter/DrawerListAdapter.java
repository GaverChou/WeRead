package org.weread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.weread.R;
import org.weread.model.DrawerSelectItem;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by mrgaver on 15-5-28.
 */
public class DrawerListAdapter extends SimpleRecyclerAdapter<DrawerSelectItem> {

    public DrawerListAdapter(Context context, ArrayList<DrawerSelectItem> mInfos) {
        super(context, mInfos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NOIconViewHolder(mInflater.inflate(R.layout.drawer_item_noicon, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof NOIconViewHolder) {
            NOIconViewHolder tmp = (NOIconViewHolder) viewHolder;
            tmp.mTitle.setText(mItems.get(position).mTitleId);
            if ((position + 1) % 4 == 0) {
                tmp.divider.setVisibility(View.VISIBLE);
            }
        }
    }

    class NOIconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.drawer_item_noicon_tit_tv)
        TextView mTitle;
        @InjectView(R.id.drawer_item_divider)
        View divider;

        public NOIconViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemOnclickListener != null) {
                itemOnclickListener.onItemClick(view, getPosition(),mItems.get(getPosition()));
            }
        }
    }
}
