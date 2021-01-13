package jp.mincra.mincramagics.managers;


import jp.mincra.mincramagics.MincraMagics;

import java.sql.*;
import java.util.Map;

public class SQLManager {

    private Connection conn;
    private Statement stmt;

    public Statement getStatement(){
        return stmt;
    }


    public void getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Map<String, String> propertyMap = MincraMagics.getPropertyManager().getPropertyMap();
            String url = propertyMap.get("MySQL_url")+"?user=" +
                    propertyMap.get("MySQL_user")+"&password=" +
                    propertyMap.get("MySQL_password");
            System.out.println("[MincraMagics] MySQLに接続中です...");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            // 例外処理
            System.out.println("[MincraMagics] MySQLの接続に失敗しました。");
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            System.out.println("[MincraMagics] MySQLから切断します...");
            conn.close();
        } catch (SQLException e){
            System.out.println("[MincraMagics] MySQLの切断に失敗しました。");
            e.printStackTrace();
        }
    }

    //必要なテーブルを作成
    public void createRequiredTables() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS player (" +
                //AUTO_INCREMENT 値が指定されなくても自動で入力される。
                "id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, " +
                "name varchar(20), " +
                "UUID VARBINARY(32) NOT NULL," +
                "mp INT, " +
                "cooltime INT" +
                ")";
        System.out.println("[MincraMagics] テーブルを作成中です... "+createTableSQL);
        //execute the SQL stetement
        try {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("[MincraMagics] テーブルの作成に失敗しました。");
            e.printStackTrace();
        }
    }

    //MincraPlayer型についての操作
}