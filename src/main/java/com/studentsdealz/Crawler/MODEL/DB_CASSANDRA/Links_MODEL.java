package com.studentsdealz.Crawler.MODEL.DB_CASSANDRA;

import javax.persistence.*;

/**
 * Created by saran on 8/7/17.
 */
@Entity(name = "links")
@Table(name = "links", schema = "rajasthan_hackthon@cassandraPU")
public class Links_MODEL {
    @Embeddable
    public static class PrimaryKey_Links {
        @Column(name = "url_from")
        private String url_from;

        @Column(name = "url_to")
        private String url_to;

        //getters and setters


        public String getUrl_from() {
            return url_from;
        }

        public void setUrl_from(String url_from) {
            this.url_from = url_from;
        }

        public String getUrl_to() {
            return url_to;
        }

        public void setUrl_to(String url_to) {
            this.url_to = url_to;
        }
    }


    @EmbeddedId
    private PrimaryKey_Links key;



    @Column(name = "link_txt")
    private String link_txt;


    public PrimaryKey_Links getKey() {
        return key;
    }

    public void setKey(PrimaryKey_Links key) {
        this.key = key;
    }

    public String getLink_txt() {
        return link_txt;
    }

    public void setLink_txt(String link_txt) {
        this.link_txt = link_txt;
    }
}
