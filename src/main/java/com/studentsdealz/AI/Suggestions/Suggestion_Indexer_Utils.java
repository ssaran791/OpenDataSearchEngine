package com.studentsdealz.AI.Suggestions;

import com.studentsdealz.AI.Indexer.Sqlite_Helper;
import com.studentsdealz.AppConstants;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by saran on 8/17/17.
 */
public class Suggestion_Indexer_Utils {
    public static ArrayList<String> suggest_possible_index(String download_id, String query, String col_name) throws Exception
    {
        ArrayList<String> all_possible_query=new ArrayList<>();
        Sqlite_Helper sqlite_helper=new Sqlite_Helper();
        sqlite_helper.setSqlite_path(AppConstants.IndexerConstant_AI.STORAGE_OF_CONVERTED_FILES+download_id+".db");

        String col_id=sqlite_helper.col_name(col_name);

        ArrayList<String> all_values=sqlite_helper.getGroupByValues(col_id);

        for(String single_col_value:all_values)
        {
            all_possible_query.add(query.replaceFirst(new String("*"),single_col_value).toLowerCase().trim());
        }


        //System.out.println(all_values);
        return all_possible_query;

    }

    public static ArrayList<String> suggest_possible_index_nw(String download_id, String query, String col_names) throws Exception{

        String[] cols=col_names.split(",");
        //System.out.println(cols[0]);
        ArrayList<String> all_possible_query=new ArrayList<>();
        ArrayList<String> final_all_possible_query_1=new ArrayList<>();
        Sqlite_Helper sqlite_helper=new Sqlite_Helper();
        sqlite_helper.setSqlite_path(AppConstants.IndexerConstant_AI.STORAGE_OF_CONVERTED_FILES+download_id+".db");

        String col_id=sqlite_helper.col_name(cols[0]);
        //System.out.println(col_id);
        ArrayList<String> all_values=sqlite_helper.getGroupByValues(col_id);

        for(String single_col_value:all_values)
        {
            all_possible_query.add(query.replaceFirst(Pattern.quote("*"),single_col_value.trim()).toLowerCase().trim());
        }



            try {
                if(cols[1]!=null) {
                    ArrayList<String> res = sqlite_helper.getAllCols(cols[0]);

                    for (String single_all_possible_qry : all_possible_query)
                        for (String single_col_va : res) {
                            final_all_possible_query_1.add(single_all_possible_qry.replace("*", single_col_va.toLowerCase().trim()).toLowerCase().trim());
                        }

                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
                return all_possible_query;
            }



        //System.out.println(all_values);
        return final_all_possible_query_1;
    }
}
