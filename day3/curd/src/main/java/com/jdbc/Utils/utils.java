package com.jdbc.Utils;
import java.sql.DriverManager;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.ResultSet;

public class utils {
    //懒得加驱动，会自己加的我记得
    private static String url="jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";
    private static String username="root";
    private static String password="1234";
    public static Connection getConnection() throws Exception {
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
    public static void close(Connection conn,Statement stmt,ResultSet rs) throws Exception{
        if(rs!=null){
            rs.close();
        }
        if(stmt!=null){
            stmt.close();
        }
        if(conn!=null){
            conn.close();
        }   
    }
}
