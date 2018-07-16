package com.studentsdealz.CommonJOBS;

import com.studentsdealz.Crawler.MODEL.DB_CASSANDRA.Crawled_html_Model;
import com.studentsdealz.DataBase.Cassandra;
import com.studentsdealz.DataBase.MySql;
import com.studentsdealz.Indexer.MODEL.DB_MYSQL.Indexer_Queue_MODEL;
import com.studentsdealz.Utils.HashUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by saran on 8/8/17.
 */
public class CrawledPageToIndexQueue {
    //this will add all the crawled pages to the indexer queue in mysql
    public static void main(String... args)
    {
        List<Crawled_html_Model> crawled_url_datas=(getCrawledUrls(10000));
        List<String> new_crawl_url=new ArrayList<>();

        for(Crawled_html_Model single_crawled_url:crawled_url_datas)
        {
            insertIntoIndexerQueue(single_crawled_url.getUrl());
        }
        System.out.println("Finished start to index the datas");
       // (new_crawl_url);
    }

    //dal layer
    public static List<Crawled_html_Model> getCrawledUrls(int max_result)
    {
        List<Crawled_html_Model> returing_data=null;
        EntityManager em= Cassandra.getEntityManager();


        Query q = em.createQuery("Select p.url from crawled_htmls p");
        q.setMaxResults(max_result);

        returing_data= q.getResultList();

        return returing_data;
    }
    public static void insertIntoIndexerQueue(String url)
    {

        Session session = null;
        Transaction tx = null;
        try {
            session = MySql.openSession();
            tx = MySql.beginTransaction(session);

//            Indexer_Queue_MODEL to_insert_record=new Indexer_Queue_MODEL();
//            to_insert_record.setUrl("");


                Indexer_Queue_MODEL to_insert_record=new Indexer_Queue_MODEL();
                to_insert_record.setUrl(url);
                to_insert_record.setMd5(HashUtils.MD5(url));
                to_insert_record.setSha1(HashUtils.SHA1(url));
                session.saveOrUpdate(to_insert_record);







            MySql.commitTransaction(tx);
        } catch (Exception e) {
            e.printStackTrace();
            MySql.rollbackTransaction(tx);
        } finally {
            MySql.closeSession(session);
        }
    }
}
