package com.studentsdealz.DataBase;

import com.impetus.client.cassandra.common.CassandraConstants;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by saran on 7/26/17.
 */
public class Cassandra {
    static EntityManagerFactory emf;
    static {
        Map<String, String> props = new HashMap<String,String>();
        props.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);

        emf = Persistence.createEntityManagerFactory("cassandraPU", props);

    }
    public static EntityManager getEntityManager()
    {
        return  emf.createEntityManager();
    }
}
