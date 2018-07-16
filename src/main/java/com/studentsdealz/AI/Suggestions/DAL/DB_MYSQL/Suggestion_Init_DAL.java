package com.studentsdealz.AI.Suggestions.DAL.DB_MYSQL;

import com.studentsdealz.AI.Searcher.MODEL.DB_MYSQL.Ai_Query_Helper_MODEL;
import com.studentsdealz.AI.Suggestions.MODEL.DB_MYSQL.Suggestion_Init_MODEL;
import com.studentsdealz.DataBase.MySql;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saran on 8/17/17.
 */
public class Suggestion_Init_DAL {
    public static List<Suggestion_Init_MODEL> getAllSuggestionQuery()
    {
        List<Suggestion_Init_MODEL> returing=new ArrayList<>();

        Session session = null;
        Transaction tx = null;

        try {
            session = MySql.openSession();
            tx = MySql.beginTransaction(session);
            String sql = "SELECT * FROM suggestion_init;";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Suggestion_Init_MODEL.class);
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
