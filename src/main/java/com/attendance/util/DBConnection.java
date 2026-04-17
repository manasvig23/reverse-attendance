package com.attendance.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL  = "jdbc:mysql://localhost:3306/attendance_db";
    private static final String USER = "root";
    private static final String PASS = "root123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    public static void main(String[] args) {
        try {
            Connection con = getConnection();
            if (con != null) {
                System.out.println("Connection successful!");
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
