package com.memosystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utils {
    private static final String URL = "jdbc:mysql://localhost:3306/memo_system?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}