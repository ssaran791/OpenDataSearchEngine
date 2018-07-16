package com.studentsdealz.AI.Searcher.MODEL.DB_MYSQL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by saran on 8/10/17.
 */
@Entity
@Table(name = "ai_query_helper")
public class Ai_Query_Helper_MODEL {

    @Id
    @Column(name = "ai_id")
    private int ai_id;

    @Column(name = "download_id")
    private String download_id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "variables")
    private String variables;

    @Column(name = "To_Display")
    private String To_Display;

    @Column(name = "Title")
    private String Title;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getAi_id() {
        return ai_id;
    }

    public void setAi_id(int ai_id) {
        this.ai_id = ai_id;
    }

    public String getDownload_id() {
        return download_id;
    }

    public void setDownload_id(String download_id) {
        this.download_id = download_id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getTo_Display() {
        return To_Display;
    }

    public void setTo_Display(String to_Display) {
        To_Display = to_Display;
    }
}
