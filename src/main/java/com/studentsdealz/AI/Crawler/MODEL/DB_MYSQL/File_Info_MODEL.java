package com.studentsdealz.AI.Crawler.MODEL.DB_MYSQL;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by saran on 8/9/17.
 */
@Entity
@Table(name = "file_info")
public class File_Info_MODEL {

    @Id
    @Column(name = "file_info_id")
    private int file_info_id;

    @Column(name = "file_url")
    private String file_url;

    @Column(name = "file_name")
    private String file_name;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getFile_info_id() {
        return file_info_id;
    }

    public void setFile_info_id(int file_info_id) {
        this.file_info_id = file_info_id;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}
