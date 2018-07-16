package com.studentsdealz.AI.Indexer.MODEL.DB_MYSQL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by saran on 8/14/17.
 */
@Entity
@Table(name = "ai_index_queue")
public class IndexQueueMODEL_AI {
    @Id
    @Column(name = "download_id")
    private int download_id;


    public int getDownload_id() {
        return download_id;
    }

    public void setDownload_id(int download_id) {
        this.download_id = download_id;
    }
}
