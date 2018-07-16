package com.studentsdealz.Crawler.MODEL.DB_CASSANDRA;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by saran on 7/26/17.
 */
@Entity(name = "crawled_htmls")
@Table(name = "crawled_htmls", schema = "rajasthan_hackthon@cassandraPU")
public class Crawled_html_Model {
    @Id
    @Column(name = "url")
    private String url;

    @Column(name = "data")
    private String data;

    @Column(name = "url_original")
    private String url_original;

    @Column(name = "domain")
    private String domain;

    @Column(name = "last_updated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_updated_time;


    public Date getLast_updated_time() {
        return last_updated_time;
    }

    public void setLast_updated_time(Date last_updated_time) {
        this.last_updated_time = last_updated_time;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl_original() {
        return url_original;
    }

    public void setUrl_original(String url_original) {
        this.url_original = url_original;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Crawled_html_Model{" +
                "url='" + url + '\'' +
                ", data='" + data + '\'' +
                ", url_original='" + url_original + '\'' +
                ", domain='" + domain + '\'' +
                ", last_updated_time=" + last_updated_time +
                '}';
    }
}
