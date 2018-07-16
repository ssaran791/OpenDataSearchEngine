package com.studentsdealz.DataBase;

/**
 * Created by saran on 5/19/17.
 */



import com.studentsdealz.AI.Crawler.MODEL.DB_MYSQL.File_Info_MODEL;
import com.studentsdealz.AI.Indexer.MODEL.DB_MYSQL.IndexQueueMODEL_AI;
import com.studentsdealz.AI.Searcher.MODEL.DB_MYSQL.Ai_Query_Helper_MODEL;
import com.studentsdealz.AI.Suggestions.MODEL.DB_MYSQL.Suggestion_Init_MODEL;
import com.studentsdealz.AppConstants;
import com.studentsdealz.Indexer.MODEL.DB_MYSQL.Indexer_Queue_MODEL;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Created by saran on 3/9/17.
 */
public class MySql {

    //removed cassadra from the project need to work when the application became mush higher



//    public static Session getsession()
//    {
//        return  Cluster.builder().addContactPoint(Constant.DATABASE.DB_HOST).build().connect(Constant.DATABASE.DB_SCHEMA);
//    }
//    public static void insert(Session session,String table_name,LinkedHashMap<String,HashMap<String,Class>> key_values)
//    {
//
//        String prepared_startement_sql_helper_values="";
//        String prepared_startement_sql_helper_key="";
//        for(Map.Entry<String,HashMap<String,Class>> single_key_values:key_values.entrySet())
//        {
//
//            prepared_startement_sql_helper_values+="?,";
//            prepared_startement_sql_helper_key+=""+single_key_values.getKey()+",";
//
//        }
//        int total_number=0;
//        prepared_startement_sql_helper_values=prepared_startement_sql_helper_values.substring(0,prepared_startement_sql_helper_values.length()-1);
//        prepared_startement_sql_helper_key=prepared_startement_sql_helper_key.substring(0,prepared_startement_sql_helper_key.length()-1);
//        System.out.println("insert into " + table_name + " (" + prepared_startement_sql_helper_key + ") values (" + prepared_startement_sql_helper_values + ");");
//        BoundStatement prepred_query = session.prepare("insert into " + table_name + " (" + prepared_startement_sql_helper_key + ") values (" + prepared_startement_sql_helper_values + ");").bind();
//        for(Map.Entry<String,HashMap<String,Class>> single_key_values:key_values.entrySet())
//        {
//            for(Map.Entry<String,Class> single_2_key_values:single_key_values.getValue().entrySet())
//            prepred_query.set(total_number,single_2_key_values.getKey(),single_2_key_values.getValue());
//            total_number++;
//        }
//        session.execute(prepred_query);
//    }


    private static final String HIBERNATE_CONNECTION_URL_KEY = "hibernate.connection.url";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_MYSQL_DRIVER = "hibernate.connection.driver_class";
    private static final String CACHE_QUERY = "hibernate.cache.use_query_cache";
    private static final String USER_NAME = "hibernate.connection.username";
    private static final String PASSWORD = "hibernate.connection.password";
    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String C3_P0_MINSIZE = "hibernate.c3p0.min_size";
    private static final String C3_P0_MAXSIZE = "hibernate.c3p0.max_size";
    private static final String C3_P0_TIMEOUT = "hibernate.c3p0.timeout";
    private static final String C3_P0_MAX_STATEMENTS = "hibernate.c3p0.max_statements";
    private static final String C3_P0_IDLE_TEST_PERIOD = "hibernate.c3p0.idle_test_period";

    private static Configuration config;
    private static SessionFactory sessionFactory;

    static {
        initialize();
    }

    public static void initialize() {
        config = new Configuration();
        String rdsUrl = System.getenv("DB_URL");
        if (rdsUrl == null || rdsUrl.trim().equals("")) {
            rdsUrl = AppConstants.DATABASE_CONSTANTS.DB_URL;
        }else{
            AppConstants.IS_TEST=false;
        }

        config.setProperty(HIBERNATE_CONNECTION_URL_KEY, rdsUrl);
        config.setProperty(HIBERNATE_DIALECT, "org.hibernate.dialect.MySQLDialect");
        config.setProperty(HIBERNATE_MYSQL_DRIVER,  AppConstants.DATABASE_CONSTANTS.DB_DRIVER);
        //TODO: need to update while production

        if(AppConstants.IS_TEST)
            config.setProperty(SHOW_SQL, "true");
        else
            config.setProperty(SHOW_SQL, "false");


        config.setProperty(C3_P0_MINSIZE, "5");
        config.setProperty(C3_P0_MAXSIZE, "5");
        config.setProperty(C3_P0_TIMEOUT, "100");
        config.setProperty(C3_P0_MAX_STATEMENTS, "50");
        config.setProperty(C3_P0_IDLE_TEST_PERIOD, "100");
        config.setProperty(CACHE_QUERY, "false");
        config.setProperty("hibernate.jdbc.batch_size","20");


        String db_username=System.getenv("DB_USER");
        if(db_username==null || db_username.trim().equals(""))
        {
            db_username=AppConstants.DATABASE_CONSTANTS.DB_USERNAME;
        }

        String db_password=System.getenv("DB_PASS");
        if(db_password==null || db_password.trim().equals(""))
        {
            db_password=AppConstants.DATABASE_CONSTANTS.DB_PASSWORD;
        }



        config.setProperty(USER_NAME, db_username);
        config.setProperty(PASSWORD,db_password );


       //config.addAnnotatedClass(Suggest_Helper_Model.class);
        config.addAnnotatedClass(Indexer_Queue_MODEL.class);
        config.addAnnotatedClass(File_Info_MODEL.class);
        config.addAnnotatedClass(Ai_Query_Helper_MODEL.class);
        config.addAnnotatedClass(IndexQueueMODEL_AI.class);
        config.addAnnotatedClass(Suggestion_Init_MODEL.class);








        // TODO: Fix the deprecated method.
        sessionFactory = config.buildSessionFactory();

    }

    public static Session openSession() {
        return sessionFactory.openSession();
    }

    public static void closeSession(Session s) {
        if (s != null) {
            s.close();
        }

    }

    public static Transaction beginTransaction(Session s) {
        Transaction tx = null;
        if (s != null) {
            tx = s.beginTransaction();
        }
        return tx;
    }

    public static void commitTransaction(Transaction tx) {
        if (tx != null) {
            tx.commit();
        }
    }

    public static void rollbackTransaction(Transaction tx) {
        if (tx != null) {
            tx.rollback();
        }
    }

    private static boolean isBeta() {
        String environment = System.getProperty("environment");
        if (environment == null || environment.trim().equals("")) {
            return true;
        }
        return "test".equals(environment);
    }





}