package com.studentsdealz.AI.Searcher.MODEL.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by saran on 8/12/17.
 */
public class Ai_Response_MODEL {
    private String title;

    private Map<String,String> ai_result;


    private String download_id;

    public String getDownload_id() {
        return download_id;
    }

    public void setDownload_id(String download_id) {
        this.download_id = download_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getAi_result() {
        return ai_result;
    }

    public void setAi_result(Map<String, String> ai_result) {
        this.ai_result = ai_result;
    }

    @Override
    public String toString() {
        return "Ai_Response_MODEL{" +
                "title='" + title + '\'' +
                ", ai_result=" + ai_result +
                '}';
    }
}
