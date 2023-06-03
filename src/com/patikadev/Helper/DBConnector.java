package com.patikadev.Helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
    private Connection connect = null;

    public Connection connectDB(){
        try {
            this.connect = DriverManager.getConnection(Config.DB_URL,Config.USER,Config.PASSWORD);
        } catch (Exception e) {
            e.getMessage();
        }

        return this.connect;
    }

    public static Connection getInstance(){
        DBConnector db = new DBConnector();
        return db.connectDB();
    }
}
