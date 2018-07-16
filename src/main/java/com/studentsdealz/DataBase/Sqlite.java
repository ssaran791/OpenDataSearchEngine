package com.studentsdealz.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by saran on 8/9/17.
 */
public class Sqlite {
    public static Connection connect(String file_path) {

        // SQLite connection string
        String url = "jdbc:sqlite:"+file_path;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return conn;
    }
}
