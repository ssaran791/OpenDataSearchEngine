package com.studentsdealz.Indexer.MODEL.DB_MYSQL;

import javax.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by saran on 8/7/17.
 */
@Entity
@Table(name = "indexer_queue")
public class Indexer_Queue_MODEL {

    @Id
    @Column(name = "indexer_queue_id")
    private int indexer_queue_id;

    @Column(name = "url")
    private String url;

    @Column(name = "md5")
    private String md5;

    @Column(name = "sha1")
    private String sha1;

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

    public int getIndexer_queue_id() {
        return indexer_queue_id;
    }

    public void setIndexer_queue_id(int indexer_queue_id) {
        this.indexer_queue_id = indexer_queue_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
