/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.weread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.weread.callable.OnItemClickListener;

import java.util.ArrayList;

public abstract class SimpleHeaderRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected static final int VIEW_TYPE_HEADER = 0;
    protected static final int VIEW_TYPE_ITEM = 1;

    protected OnItemClickListener mClickListener;
    protected LayoutInflater mInflater;
    protected ArrayList<T> mItems;
    protected View mHeaderView;
    protected Context context;

    public SimpleHeaderRecyclerAdapter(Context context, ArrayList<T> items, View headerView) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mItems = items;
        mHeaderView = headerView;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return mItems.size();
        } else {
            return mItems.size() + 1;
        }
    }

    public T getItem(int pos) {
        return mItems.get(pos);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mHeaderView != null) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    //
    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    public void setmClickListener(OnItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    public synchronized void add(T item, int position) {
        mItems.add(position, item);
        if (mHeaderView == null) {
            mItems.add(position, item);
        } else {
            mItems.add(position - 1, item);
        }
        notifyItemInserted(position);
    }

    public synchronized void add(T item) {
        int pos = mItems.size();
        mItems.add(pos, item);
        if (mHeaderView == null) {
            notifyItemInserted(pos);
        } else {
            notifyItemInserted(pos + 1);
        }
    }

    public synchronized void remove(T item) {
        int position = mItems.indexOf(item);
        mItems.remove(position);
        if (mHeaderView == null) {
            notifyItemRemoved(position);
        } else {
            notifyItemRemoved(position - 1);
        }
    }

    public synchronized void removeAll() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public synchronized void remove(int position) {
        if (mHeaderView == null) {
            mItems.remove(position);
        } else {
            mItems.remove(position - 1);
        }
        notifyItemRemoved(position);
    }
}
