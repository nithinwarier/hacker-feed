package com.playo.hackerfeed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.playo.hackerfeed.model.HackerFeed;
import com.playo.hackerfeed.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vichu on 11/10/17.
 */

public class HackerFeedAdapter extends RecyclerView.Adapter<HackerFeedAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context context;

    @NonNull
    private List<HackerFeed> mValues = new ArrayList<>();

    private OnLoadMoreListener onLoadMoreListener;

    private boolean isLoading;
    int lastLoadItemCount = 0;
    private int visibleThreshold = 5;

    public HackerFeedAdapter(Context context, RecyclerView recyclerView, List<HackerFeed> items) {
        this.context = context;
        if (items != null && !items.isEmpty()) {
            mValues.addAll(items);
        }

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && lastLoadItemCount!= totalItemCount
                        && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        lastLoadItemCount = totalItemCount;
                        onLoadMoreListener.onLoadMore();
                        isLoading = true;
                    }
                }
            }
        });
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void setData(@Nullable List<HackerFeed> items) {
        mValues.clear();
        if (items != null && !items.isEmpty())
            mValues.addAll(items);

        notifyDataSetChanged();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        this.isLoading = false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(layoutInflater.inflate(R.layout.item_type_feed_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView title = (TextView) holder.itemView.findViewById(R.id.tv_title);
        TextView url = (TextView) holder.itemView.findViewById(R.id.tv_url);

        final HackerFeed feed = mValues.get(position);

        title.setText(feed.getTitle());
        url.setText(feed.getUrl());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    int index = (int)v.getTag();
                    String url = mValues.get(index).getUrl();
                    if (url != null && url.length() > 0) {
                        if (Utils.isOnline(context)) {
                            i.setData(Uri.parse(url));
                            context.startActivity(i);
                        } else {
                            showToast("No network connection available");
                        }
                    } else {
                        showToast("No url found");
                    }
                }
            });
        }
    }

    private void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
