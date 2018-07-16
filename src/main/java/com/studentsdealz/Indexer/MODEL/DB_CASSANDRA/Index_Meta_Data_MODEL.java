package com.studentsdealz.Indexer.MODEL.DB_CASSANDRA;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by saran on 8/7/17.
 */
@Entity(name = "index_meta_data")
@Table(name = "index_meta_data", schema = "rajasthan_hackthon@cassandraPU")
public class Index_Meta_Data_MODEL {

    @Id
    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;


    @Column(name = "description")
    private String description;

    @Column(name = "last_index_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_index_date;

    @Column(name = "original_url")
    private String original_url;

    @Column(name = "sector")
    private String sector;



    //all getters and setters


    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLast_index_date() {
        return last_index_date;
    }

    public void setLast_index_date(Date last_index_date) {
        this.last_index_date = last_index_date;
    }
}
