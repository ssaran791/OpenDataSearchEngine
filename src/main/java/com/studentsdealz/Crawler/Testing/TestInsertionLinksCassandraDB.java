package com.studentsdealz.Crawler.Testing;

import com.studentsdealz.Crawler.DAL.DB_CASSANDRA.Links_DAL;

import java.util.HashMap;

/**
 * Created by saran on 8/7/17.
 */
public class TestInsertionLinksCassandraDB {
    public static void main(String... args)
    {
        HashMap<String ,String> to_links_data=new HashMap<>();

        to_links_data.put("google.com","google");
        to_links_data.put("yahoo.com","Yahoo Search");
     Links_DAL.insertLinksRelation("test.com",to_links_data);
    }
}
