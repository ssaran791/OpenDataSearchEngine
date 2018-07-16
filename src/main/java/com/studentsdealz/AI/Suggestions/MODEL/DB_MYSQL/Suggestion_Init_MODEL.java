package com.studentsdealz.AI.Suggestions.MODEL.DB_MYSQL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by saran on 8/16/17.
 */
@Entity
@Table(name = "suggestion_init")
public class Suggestion_Init_MODEL {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "download_id")
    private int download_id;

    @Column(name = "keyword")
    private String keyword;


    @Column(name = "cols")
    private String cols;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDownload_id() {
        return download_id;
    }

    public void setDownload_id(int download_id) {
        this.download_id = download_id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }
}
