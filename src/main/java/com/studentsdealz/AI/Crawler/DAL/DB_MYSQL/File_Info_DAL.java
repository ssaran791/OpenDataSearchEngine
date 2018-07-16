package com.studentsdealz.AI.Crawler.DAL.DB_MYSQL;

import com.studentsdealz.AI.Crawler.MODEL.DB_MYSQL.File_Info_MODEL;
import com.studentsdealz.DataBase.MySql;
import com.studentsdealz.Indexer.MODEL.DB_MYSQL.Indexer_Queue_MODEL;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saran on 8/9/17.
 */
public class File_Info_DAL {
    public static List<File_Info_MODEL> getToDownloadFiles(int limit)
    {
        List<File_Info_MODEL> returing_data=new ArrayList<>();

        Session session = null;
        Transaction tx = null;

        try {
            session = MySql.openSession();
            tx = MySql.beginTransaction(session);
            String sql = "SELECT * FROM file_info limit "+limit+" ;";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(File_Info_MODEL.class);
            returing_data = query.list();
            MySql.commitTransaction(tx);
        } catch (Exception e) {
            e.printStackTrace();
            MySql.rollbackTransaction(tx);
        } finally {
            MySql.closeSession(session);
        }

        return returing_data;
    }
}
