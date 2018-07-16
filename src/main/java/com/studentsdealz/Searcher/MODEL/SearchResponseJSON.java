package com.studentsdealz.Searcher.MODEL;

import java.util.List;

/**
 * Created by saran on 8/11/17.
 */
public class SearchResponseJSON {
    private String total_results;
    private List<SingleSearchResponseJSON> searchResults;


    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    public List<SingleSearchResponseJSON> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SingleSearchResponseJSON> searchResults) {
        this.searchResults = searchResults;
    }




}
