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
import android.view.ViewGroup;


import org.weread.callable.OnItemClickListener;
import org.weread.model.ArticleItem;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected LayoutInflater mInflater;
    protected ArrayList<T> mItems;
    protected OnItemClickListener itemOnclickListener;
    protected Context context;

    public SimpleRecyclerAdapter(Context context, ArrayList<T> items) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    public void setItemOnclickListener(OnItemClickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    public synchronized void add(T item, int position) {
        mItems.add(position, item);
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public synchronized void add(T item) {
        int pos = mItems.size();
        mItems.add(pos, item);
        notifyItemInserted(pos);
    }

    public synchronized void remove(T item) {
        int position = mItems.indexOf(item);
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public synchronized void removeAll() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public synchronized void addAll(List<T> items) {
        for (T item : items) {
            add(item);
        }
    }

    public synchronized void remove(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }
}
