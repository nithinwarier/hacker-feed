package com.playo.hackerfeed.client;

import android.content.Context;

import com.playo.hackerfeed.feed.HackerFeedParser;
import com.playo.hackerfeed.model.HackerFeed;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * Created by vichu on 11/10/17.
 */

public class HackerFeedClient {


    private static final String TAG = "HackerFeedClient";
    public static final String FEED_URL = "https://hn.algolia.com/api/v1/search?query=";
    public static final String FEED_URL_PAGINATION = "&page=";
    /**
     * Network connection timeout, in milliseconds.
     */
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 15000;  // 15 seconds
    private static final int NET_READ_TIMEOUT_MILLIS = 10000;  // 10 seconds

    public List<HackerFeed> getHackerFeed(String url) {
        InputStream stream = null;
        try {
            final URL location = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) location.openConnection();

            conn.setReadTimeout(NET_READ_TIMEOUT_MILLIS /* milliseconds */);
            conn.setConnectTimeout(NET_CONNECT_TIMEOUT_MILLIS /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();

            stream = conn.getInputStream();

            return new HackerFeedParser().parse(stream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return Collections.EMPTY_LIST;
    }


}
