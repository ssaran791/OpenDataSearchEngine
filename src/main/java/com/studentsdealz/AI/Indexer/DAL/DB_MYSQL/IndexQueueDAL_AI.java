package com.studentsdealz.AI.Indexer.DAL.DB_MYSQL;

import com.studentsdealz.AI.Indexer.MODEL.DB_MYSQL.IndexQueueMODEL_AI;
import com.studentsdealz.DataBase.MySql;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saran on 8/14/17.
 */
public class IndexQueueDAL_AI {
    public static List<IndexQueueMODEL_AI> getAllToIndexID()
    {
        Session session = null;
        Transaction tx = null;
        List<IndexQueueMODEL_AI> list_to_index = new ArrayList<IndexQueueMODEL_AI>();

        try {
            session = MySql.openSession();
            tx = MySql.beginTransaction(session);
            String sql = "SELECT * FROM ai_index_queue;";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(IndexQueueMODEL_AI.class);
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
