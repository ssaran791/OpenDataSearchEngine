package com.studentsdealz.Crawler.DAL.DB_CASSANDRA;

import com.studentsdealz.Crawler.MODEL.DB_CASSANDRA.Crawled_html_Model;
import com.studentsdealz.Crawler.MODEL.DB_CASSANDRA.Links_MODEL;
import com.studentsdealz.DataBase.Cassandra;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saran on 8/7/17.
 */
public class Links_DAL {
    public static void insertLinksRelation(String from_url, HashMap<String,String> to_url_datas)
    {
        //to_url_datas containg key - as to url ,  value - as to url text

        EntityManager em= Cassandra.getEntityManager();

        for(Map.Entry<String,String> single_to_url_record:to_url_datas.entrySet())
        {
            Links_MODEL to_link_data=new Links_MODEL();

            Links_MODEL.PrimaryKey_Links link_relation=new Links_MODEL.PrimaryKey_Links();

            //setting from and to url
            link_relation.setUrl_from(from_url);
            link_relation.setUrl_to(single_to_url_record.getKey());


            to_link_data.setKey(link_relation);

            //setting the link text
            to_link_data.setLink_txt(single_to_url_record.getValue());

            em.persist(to_link_data);
        }
        em.close();
    }
    public static HashMap<String,String > getLinksRelation(String to_page_url)
    {
        EntityManager em=Cassandra.getEntityManager();

        HashMap<String,String> list_of_ref_pages_return=new HashMap<>();

        Query q = em.createQuery("Select p from links p where p.key.url_to = :url");

        q.setParameter("url",to_page_url);


        List<Links_MODEL> list_of_ref_page=q.getResultList();

        for (Links_MODEL single_link:list_of_ref_page)
        {
            list_of_ref_pages_return.put(single_link.getKey().getUrl_from(),single_link.getLink_txt());
        }


        em.close();

        return list_of_ref_pages_return;
    }
}
