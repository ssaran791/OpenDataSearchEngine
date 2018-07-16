package com.studentsdealz.Crawler.MODEL.DB_MYSQL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by saran on 8/7/17.
 */
@Entity
@Table(name = "crawler_queue")
public class Crawler_Queue_MODEL {

    @Id
    @Column(name = "crawler_queue_id")
    private int crawler_queue_id;



    @Column(name = "url_to_download")
    private String url_to_download;

    @Column(name = "md5")
    private String md5;

    @Column(name = "sha1")
    private String sha1;

    public String getUrl_to_download() {
        return url_to_download;
    }

    public void setUrl_to_download(String url_to_download) {
        this.url_to_download = url_to_download;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public int getCrawler_queue_id() {
        return crawler_queue_id;
    }

    public void setCrawler_queue_id(int crawler_queue_id) {
        this.crawler_queue_id = crawler_queue_id;
    }
}
