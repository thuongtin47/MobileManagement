///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.mobilemanagement.Utility;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// 
// * @author DINHVU
// */
//public class JDBC {
//
//    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//    private static String url = "jdbc:sqlserver://localhost;database = MobileManagementDB;encrypt=false";
//    private static String user = "sa";
//    private static String pass = "1211";
//
//    static {
//        try {
//            Class.forName(driver);
//
//        } catch (ClassNotFoundException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
//        Connection connection = DriverManager.getConnection(url, user, pass);
//        PreparedStatement pstmt = null;
//        if (sql.trim().startsWith("{")) {
//            pstmt = connection.prepareCall(sql);
//        } else {
//            pstmt = connection.prepareCall(sql);
//        }
//        for (int i = 0; i < args.length; i++) {
//            pstmt.setObject(i + 1, args[i]);
//        }
//        return pstmt;
//    }
//    
//
//    public static int update(String sql, Object... args) {
//        try {
//            PreparedStatement stmt = getStmt(sql, args);
//            try {
//                return stmt.executeUpdate();
//            } finally {
//                stmt.getConnection().close();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static ResultSet query(String sql, Object... args) {
//        try {
//            PreparedStatement stmt = getStmt(sql, args);
//            return stmt.executeQuery();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static Object value(String sql, Object... args) {
//        try {
//            ResultSet rs = query(sql, args);
//            if (rs.next()) {
//                return rs.getObject(0);
//            }
//            rs.getStatement().getConnection().close();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//}
package com.mobilemanagement.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC utility class for database operations.
 *
 * @author DINHVU
 */
public class JDBC {

    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String url = "jdbc:sqlserver://localhost;database=MobileManagementDB;encrypt=false";
    private static String user = "sa";
    private static String pass = "1211";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, pass);
        PreparedStatement pstmt;
        if (sql.trim().startsWith("{")) {
            pstmt = connection.prepareCall(sql);
        } else {
            pstmt = connection.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    public static int update(String sql, Object... args) {
    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement stmt = getStmt(sql, args)) {

        boolean isResultSet = stmt.execute();
        if (!isResultSet) {
            return stmt.getUpdateCount();
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error during SQL UPDATE operation.", e);
    }
    return -1; // or handle as needed
}

    public static ResultSet query(String sql, Object... args) {
        try {
            PreparedStatement stmt = getStmt(sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error during SQL query operation.", e);
        }
    }

    public static Object value(String sql, Object... args) {
        try (ResultSet rs = query(sql, args)) {
            if (rs.next()) {
                return rs.getObject(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching value from database.", e);
        }
        return null;
    }
}
