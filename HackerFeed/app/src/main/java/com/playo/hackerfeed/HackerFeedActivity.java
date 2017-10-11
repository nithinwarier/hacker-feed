package com.playo.hackerfeed;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.playo.hackerfeed.client.HackerFeedClient;
import com.playo.hackerfeed.model.HackerFeed;
import com.playo.hackerfeed.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HackerFeedActivity extends AppCompatActivity {

    final int PAGE_SIZE = 16;

    private String category;
    private ProgressBar progressBar;
    private TextView tvError;
    private RecyclerView recyclerView;
    private int page = 1;
    private HackerFeedAdapter adapter;
    private List<HackerFeed> feedEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacker_feed);

        category = getIntent().getStringExtra(LauncherActivity.ARG_CATEGORY);
        setTitle(category);

        init();
    }

    private void init() {
        progressBar = (ProgressBar) findViewById(R.id.pb_loader);
        progressBar.setVisibility(View.VISIBLE);
        tvError = (TextView) findViewById(R.id.txv_error);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new HackerFeedAdapter(this, recyclerView, feedEntries);

        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (feedEntries.size() >= 16) {
                    if (Utils.isOnline(HackerFeedActivity.this)) {
                        progressBar.setVisibility(View.VISIBLE);
                        new HackerFeedTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ++page);
                    } else {
                        showConnectionError();
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);

        if (Utils.isOnline(this)) {
            progressBar.setVisibility(View.VISIBLE);
            fetchFeeds();
        } else {
            showConnectionError();
        }
    }

    private void showConnectionError() {
        progressBar.setVisibility(View.GONE);
        showConnectionSettingsView();
    }

    protected void showConnectionSettingsView() {
        ConnectionDialogFragment connectionDialog = new ConnectionDialogFragment();
        connectionDialog.setPositiveClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        connectionDialog.setNegativeClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });
        connectionDialog.show(getSupportFragmentManager(), "connection-dialog");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void fetchFeeds() {
        HackerFeedTask downloadFeedTask = new HackerFeedTask();
        downloadFeedTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, page);
    }

    class HackerFeedTask extends AsyncTask<Integer, Void, List<HackerFeed>> {

        @Override
        protected List<HackerFeed> doInBackground(Integer... params) {
            if (!Utils.isOnline(HackerFeedActivity.this)) {
                return Collections.EMPTY_LIST;
            }
            if (category == null || category.length() == 0)
                return Collections.EMPTY_LIST;

            int page = params.length > 0 ? params[0] : 1;
            HackerFeedClient hackerClient = new HackerFeedClient();
            String url = HackerFeedClient.FEED_URL + category + HackerFeedClient.FEED_URL_PAGINATION + String.valueOf(page);
            return hackerClient.getHackerFeed(url);
        }

        @Override
        protected void onPostExecute(List<HackerFeed> hackerFeeds) {
            super.onPostExecute(hackerFeeds);
            progressBar.setVisibility(View.GONE);
            if (hackerFeeds.isEmpty()) {
                showError("No feed items found");
                return;
            }

            feedEntries.addAll(hackerFeeds);
            adapter.setData(feedEntries);

            adapter.setLoaded();
        }
    }

    private void showError(String msg) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(msg);
    }
}
