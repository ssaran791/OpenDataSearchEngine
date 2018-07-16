package com.studentsdealz.Searcher.MODEL;

/**
 * Created by saran on 8/11/17.
 */
public class SingleSearchResponseJSON {
    private String title;
    private String url_to_display;
    private String url_to_link;
    private String  description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl_to_display() {
        return url_to_display;
    }

    public void setUrl_to_display(String url_to_display) {
        this.url_to_display = url_to_display;
    }

    public String getUrl_to_link() {
        return url_to_link;
    }

    public void setUrl_to_link(String url_to_link) {
        this.url_to_link = url_to_link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
