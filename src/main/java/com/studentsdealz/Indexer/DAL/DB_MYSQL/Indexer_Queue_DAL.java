package com.studentsdealz.Indexer.DAL.DB_MYSQL;

import com.studentsdealz.DataBase.MySql;
import com.studentsdealz.Indexer.MODEL.DB_MYSQL.Indexer_Queue_MODEL;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saran on 8/7/17.
 */
public class Indexer_Queue_DAL {
    public static List<Indexer_Queue_MODEL> getToIndexDatas(int limit)
    {
        Session session = null;
        Transaction tx = null;
        List<Indexer_Queue_MODEL> list_to_index = new ArrayList<Indexer_Queue_MODEL>();

        try {
            session = MySql.openSession();
            tx = MySql.beginTransaction(session);
            String sql = "SELECT * FROM indexer_queue limit "+limit+" ;";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Indexer_Queue_MODEL.class);
            list_to_index = query.list();
            MySql.commitTransaction(tx);
        } catch (Exception e) {
            e.printStackTrace();
            MySql.rollbackTransaction(tx);
        } finally {
            MySql.closeSession(session);
        }
        return list_to_index;
    }
}
