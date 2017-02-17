//package com.kencloud.partner.user.database;
//
///**
// * Created by suchismita.p on 12/9/2016.
// */
//import android.annotation.SuppressLint;
//import android.os.StrictMode;
//import android.util.Log;
//import java.sql.SQLException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class ConnectionClass {
//    String ip = "swash.database.windows.net:1433";
//    String classs ="com.microsoft.sqlserver.jdbc.SQLServerDriver";
//    String db = "SwashMaster";
//    String un = "swashDB_Admin";
//    String password = "Swash@2016";
//
//    @SuppressLint("NewApi")
//    public Connection CONN() {
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                .permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        Connection conn = null;
//        String ConnURL = null;
//        try {
//
//            Class.forName(classs);
//            ConnURL = "jdbc:sqlserver://" + ip + ";"
//                    + "databaseName=" + db + ";user=" + un + ";password="
//                    + password + ";";
//            conn = DriverManager.getConnection(ConnURL);
//        } catch (SQLException se) {
//            Log.e("ERRO", se.getMessage());
//        } catch (ClassNotFoundException e) {
//            Log.e("ERRO", e.getMessage());
//        } catch (Exception e) {
//            Log.e("ERRO", e.getMessage());
//        }
//        return conn;
//    }
//}
//
//
