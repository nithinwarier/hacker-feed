package com.playo.hackerfeed.feed;

import com.playo.hackerfeed.model.HackerFeed;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by vichu on 11/10/17.
 */

public class HackerFeedParser {

    public List<HackerFeed> parse(InputStream in) {
        try {
            List<HackerFeed> hackerFeedList = new ArrayList<>();
            String feed = readStreamToString(in);
            JSONObject feedJson = new JSONObject(feed);

            //error check
            if (feedJson.has("error")) {
                return Collections.EMPTY_LIST;
            }

            JSONArray feedArray = feedJson.getJSONArray("hits");
            for (int i=0; i<feedArray.length(); i++) {
                JSONObject feedJsonObj = feedArray.getJSONObject(i);

                HackerFeed hackerFeed = new HackerFeed();
                if (feedJsonObj.has("title"))
                    hackerFeed.setTitle(feedJsonObj.getString("title"));
                if (feedJsonObj.has("url"))
                    hackerFeed.setUrl(feedJsonObj.getString("url"));
                if (feedJsonObj.has("author"))
                    hackerFeed.setAuthor(feedJsonObj.getString("author"));
                if (feedJsonObj.has("points"))
                    hackerFeed.setPoints(feedJsonObj.getInt("points"));

                hackerFeedList.add(hackerFeed);
            }

            return hackerFeedList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Collections.EMPTY_LIST;
    }

    public String readStreamToString(InputStream inputStream) throws IOException {
        BufferedInputStream bufferedInputStream = null;
        InputStreamReader reader = null;
        try {
            bufferedInputStream = new BufferedInputStream(inputStream);
            reader = new InputStreamReader(bufferedInputStream);
            StringBuilder stringBuilder = new StringBuilder();

            final int bufferSize = 1024 * 2;
            char[] buffer = new char[bufferSize];
            int n = 0;
            while ((n = reader.read(buffer)) != -1) {
                stringBuilder.append(buffer, 0, n);
            }

            return stringBuilder.toString();
        } finally {
            closeQuietly(bufferedInputStream);
            closeQuietly(reader);
        }
    }

    public void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }


}
