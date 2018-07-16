package com.studentsdealz.Indexer.DAL.DB_CASSANDRA;

import com.studentsdealz.DataBase.Cassandra;
import com.studentsdealz.Indexer.MODEL.DB_CASSANDRA.Index_Meta_Data_MODEL;

import javax.persistence.EntityManager;
import java.util.Date;

/**
 * Created by saran on 8/7/17.
 */
public class Index_Meta_Data_DAL {
    public static void insertMetaData(String url,String original_url,String title,String description,String sector, Date last_index_date){
        Index_Meta_Data_MODEL meta_datas=new Index_Meta_Data_MODEL();

        meta_datas.setUrl(url);
        meta_datas.setTitle(title);
        meta_datas.setOriginal_url(original_url);
        meta_datas.setDescription(description);
        meta_datas.setLast_index_date(last_index_date);

        EntityManager em= Cassandra.getEntityManager();
        em.persist(meta_datas);

        em.close();
    }
}
