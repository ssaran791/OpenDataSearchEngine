package com.studentsdealz.Crawler.DAL.DB_CASSANDRA;

import com.studentsdealz.Crawler.MODEL.DB_CASSANDRA.Crawled_html_Model;
import com.studentsdealz.DataBase.Cassandra;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by saran on 8/6/17.
 */
public class Crawled_Htmls_DAL {
    public static void storeHtml(String page_address, String original_page_address, String domain_name, Date time_of_crawling, String data)
    {
        Crawled_html_Model to_store=new Crawled_html_Model();

        to_store.setUrl(page_address);
        to_store.setUrl_original(original_page_address);
        to_store.setDomain(domain_name);
        to_store.setData(data);


        EntityManager em= Cassandra.getEntityManager();
        em.persist(to_store);
        em.close();
    }
    public static Crawled_html_Model getCrawledData(String page_url)
    {
        EntityManager em=Cassandra.getEntityManager();


        Query q = em.createQuery("Select p from crawled_htmls p where p.url = :url");

        q.setParameter("url",page_url);
        Crawled_html_Model html_object=(Crawled_html_Model) q.getSingleResult();

        return html_object;
    }
}
