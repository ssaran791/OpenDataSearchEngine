package com.studentsdealz.AI.Indexer;

import com.studentsdealz.DataBase.Sqlite;
import com.uwyn.jhighlight.fastutil.Hash;
import org.stringtemplate.v4.ST;

import java.io.File;
import java.sql.*;
import java.util.*;

/**
 * Created by saran on 8/9/17.
 */
public class Sqlite_Helper {
    private static String create_col_info_table="CREATE TABLE  IF NOT EXISTS col_info ( id integer PRIMARY KEY AUTOINCREMENT,  col_name text );";
    private List<String> columns=null;
    private String sqlite_path=null;
    private String sqlite_insert_prepared_qry="";
    PreparedStatement insert_data;
    private int col_size;
    private TreeMap<Integer,String> col_infos_treemap=new TreeMap<>();

    private HashMap<String,String> all_cols=new HashMap<>();

    Connection conn = Sqlite.connect(sqlite_path);
    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        col_size=columns.size();
        this.columns = columns;
    }

    public String getSqlite_path() {
        return sqlite_path;
    }

    public void setSqlite_path(String sqlite_path) {
        this.sqlite_path = sqlite_path;
        conn = Sqlite.connect(sqlite_path);
    }

    public void exec_init()
    {
        //first deleting old index records
        delete_exisiting_datas();

        conn = Sqlite.connect(sqlite_path);
//        String create_table_query="CREATE TABLE IF NOT EXISTS column_infos(id int primary key,name text);";
//        CREATE TABLE  IF NOT EXISTS col_info (
//            id integer PRIMARY KEY AUTOINCREMENT,
//            col_name text
//    );
//
//        CREATE TABLE IF NOT EXISTS  Data (
//            c1 text,
//            c2 text,
//            c3 text
//    );

        PreparedStatement insertIntoColInfo=null;

        //creating the column info table and inserting the values
        try (Statement stmt  = conn.createStatement();){

            //creating the tables
            stmt.execute(create_col_info_table);

            //adding the col info to the col tables
            insertIntoColInfo=conn.prepareStatement("insert into col_info(id,col_name) values (?,?);");

            int i=1;
            for(String col_name:columns)
            {
                col_infos_treemap.put(i,col_name);
                insertIntoColInfo.setInt(1,i);
                insertIntoColInfo.setString(2,col_name);
                insertIntoColInfo.execute();
                i++;
            }

            sqlite_insert_prepared_qry="insert into Data (";
            String sqlite_insert_prepared_qry_hlp = "(";
      String create_data_table=" CREATE TABLE IF NOT EXISTS  Data ( ";
        for(Map.Entry<Integer,String> cols:col_infos_treemap.entrySet())
        {
            create_data_table+="c"+cols.getKey()+" text,";
            sqlite_insert_prepared_qry+="c"+cols.getKey()+" ,";
            sqlite_insert_prepared_qry_hlp+="? ,";
        }
            create_data_table=create_data_table.substring(0,create_data_table.length()-1);
            sqlite_insert_prepared_qry=sqlite_insert_prepared_qry.substring(0,sqlite_insert_prepared_qry.length()-1);

            sqlite_insert_prepared_qry+=") values ";


            sqlite_insert_prepared_qry_hlp=sqlite_insert_prepared_qry_hlp.substring(0,sqlite_insert_prepared_qry_hlp.length()-1);
            sqlite_insert_prepared_qry_hlp+=")";
            sqlite_insert_prepared_qry+=" "+sqlite_insert_prepared_qry_hlp;

        create_data_table+=" );";
            stmt.execute(create_data_table);

        System.out.println(sqlite_insert_prepared_qry);



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void insert(List<String> datas) throws Exception
    {
        if(datas.size()==col_size){
            try (Statement stmt  = conn.createStatement();) {
                insert_data = conn.prepareStatement(sqlite_insert_prepared_qry);
                int i = 1;
                for (String data : datas) {
                    insert_data.setString(i, data);
                    i++;
                }
                insert_data.execute();

            }
        }else {
            System.out.println("Column count mismatching skipting datas");
        }

    }
    private void delete_exisiting_datas()
    {
        try{

            File file = new File(sqlite_path);

            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
            }

        }catch(Exception e){

            e.printStackTrace();

        }
    }

    public String col_name(String key_value)
    {
        String result="";
        try {

            Statement st=conn.createStatement();
            ResultSet col_resutls=st.executeQuery("select * from col_info;");
            // col_resutls.next();
            while(col_resutls.next()) {
                if (col_resutls.getString("col_name").trim().equals(key_value))
                    result = "c" + col_resutls.getInt("id");

                all_cols.put("c" + col_resutls.getInt("id"), col_resutls.getString("col_name"));
            }
           // System.out.println("res="+all_cols);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public  TreeMap<Integer,String> col_names(String key_value)
    {
        String result="";
        TreeMap<Integer,String> cols=new TreeMap<>();
        try {
            Statement st=conn.createStatement();
            ResultSet col_resutls=st.executeQuery("select * from col_info where col_name like '%"+key_value+"%';");
            // col_resutls.next();
            while(col_resutls.next()) {


                cols.put(col_resutls.getInt("id"), col_resutls.getString("col_name"));
            }

            return cols;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public TreeMap<String,String> getAIResults(String sql_query) throws Exception
    {
        if(all_cols.size()==0){
            col_name("");
        }
        TreeMap<String,String> ai_result=new TreeMap<>();

        Statement ai_query_statement=conn.createStatement();

        ResultSet results=ai_query_statement.executeQuery(sql_query);

        if(results.next())
        for(Map.Entry<String,String> col:all_cols.entrySet()){
            ai_result.put(col.getValue(),results.getString(col.getKey()));
        }







        return ai_result;

    }


    public ArrayList<String> getGroupByValues(String col_id) throws Exception
    {
        System.out.println("col_id="+col_id);
        ArrayList<String> possible_col_value=new ArrayList<>();

        Statement st=conn.createStatement();
        ResultSet res=st.executeQuery("select "+col_id+" from Data group by "+col_id);

        while (res.next())
        {
            possible_col_value.add(res.getString(col_id));
        }
        return possible_col_value;
    }

    public ArrayList<String> getAllCols(String except) throws Exception
    {
        ArrayList<String> list_of_cols=new ArrayList<>();

        String possible_col_sql="select col_name from col_info where trim(col_name) not like '"+except+"';";

        Statement st=conn.createStatement();
        ResultSet resultSet=st.executeQuery(possible_col_sql);


        while (resultSet.next())
        {
            list_of_cols.add(resultSet.getString("col_name"));
        }
        return list_of_cols;

    }

}
