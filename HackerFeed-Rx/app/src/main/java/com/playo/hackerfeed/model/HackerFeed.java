package com.playo.hackerfeed.model;

/**
 * Created by vichu on 11/10/17.
 */

public class HackerFeed {

    private String id;
    private String title;
    private String url;
    private String author;
    private String time;
    private int points;

    public String getId() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
