package com.studentsdealz.AI.Searcher.DAL.DB_MYSQL;

import com.studentsdealz.AI.Searcher.MODEL.DB_MYSQL.Ai_Query_Helper_MODEL;
import com.studentsdealz.DataBase.MySql;
import com.studentsdealz.Indexer.MODEL.DB_MYSQL.Indexer_Queue_MODEL;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saran on 8/10/17.
 */
public class Ai_Query_Helper_DAL {
    public static List<Ai_Query_Helper_MODEL> getAllQuery()
    {
        List<Ai_Query_Helper_MODEL> returing=new ArrayList<>();

        Session session = null;
        Transaction tx = null;

        try {
            session = MySql.openSession();
            tx = MySql.beginTransaction(session);
            String sql = "SELECT * FROM ai_query_helper;";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Ai_Query_Helper_MODEL.class);
            returing = query.list();
            MySql.commitTransaction(tx);
        } catch (Exception e) {
            e.printStackTrace();
            MySql.rollbackTransaction(tx);
        } finally {
            MySql.closeSession(session);
        }

        return returing;
    }
}
