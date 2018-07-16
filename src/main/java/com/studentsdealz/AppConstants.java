package com.studentsdealz;

/**
 * Created by saran on 8/5/17.
 */
public class AppConstants {
    public static boolean IS_TEST=true;
        public final static String BASE_FOLDER="/home/saran/rajasthan_hacking_data/";
    public class CrawlerConstants{
        public static final String STORAGE_FOLDER=BASE_FOLDER+"";
        public static final int NO_OF_CRAWLING_THREADS=10;
        public static final String USER_AGENT="Studentsdealz 1.0";
    }
    public class CrawlerConstants_AI{
        public static final String STORAGE_FOLDER=BASE_FOLDER+"ExcelFileDownload/";
        public static final int NO_OF_CRAWLING_THREADS=10;
        public static final String USER_AGENT="Studentsdealz 1.0";
    }
    public class DATABASE_CONSTANTS{
        public static final String DB_URL = "jdbc:mysql://localhost/rajastha_hackthon"; // jdbc:mysql://saran.cvwcrraexqjk.ap-southeast-1.rds.amazonaws.com/rajastha_hackthon :3306
        public static final String DB_USERNAME = "root";
        public static final String DB_PASSWORD = "";
        public static final String DB_DRIVER = "org.gjt.mm.mysql.Driver";
    }
    public class IndexerConstants{
        public static final String INDEX_PATH=BASE_FOLDER+"Search_Index/RajasthanHackthonIndex/";
    }
    public class IndexerConstant_AI{
        public static final String STORAGE_OF_CONVERTED_FILES=BASE_FOLDER+"Ai_Index/";
        public static final String STORAGE_OF_SUGGEST_INDEX=BASE_FOLDER+"SuggestIndex/";
        public static final int num_of_suggestion_to_return=10;
    }
    public class SearcherConstants{

    }
}
