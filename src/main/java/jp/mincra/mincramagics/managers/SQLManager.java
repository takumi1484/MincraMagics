package jp.mincra.mincramagics.managers;


import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MincraPlayer;

import java.sql.*;
import java.util.Map;
import java.util.UUID;

public class SQLManager {

    private Connection conn;
    private String url = MincraMagics.getPropertyManager().getProperty("MySQL_url")+"?user=" +
            MincraMagics.getPropertyManager().getProperty("MySQL_user")+"&password=" +
            MincraMagics.getPropertyManager().getProperty("MySQL_password");


    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            System.out.println("[MincraMagics] MySQLの接続に失敗しました。 URL: " + url);
            e.printStackTrace();
            return null;
        }
    }

    public boolean isExistTable(String tableName) {
        boolean tExists = false;
        try (ResultSet rs = getConnection().getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tExists;
    }

    public boolean isExistsRecord(String sql){
        //レコードの存在チェック
        int i = 0;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                i = rs.getInt(1);
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i == 0;
    }
    
    public void createTable(String query, String tableName) {
        try {
            if (isExistTable(tableName)){
                Statement stmt = getConnection().createStatement();
                stmt.execute(query);
                stmt.close();
                System.out.println("[MincraMagics] テーブルの作成に成功しました。 テーブル名: " + tableName);
            } else {
                System.out.println("[MincraMagics] テーブルは既に存在します。 テーブル名: " + tableName);
            }
        } catch (SQLException e) {
            System.out.println("[MincraMagics] テーブルの作成に失敗しました。 \nテーブル名: " + tableName + "\nクエリ文: " + query);
            e.printStackTrace();
        }
    }

    public void updateRecord(String query){
        try {
            Statement stmt = getConnection().createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRecord(String query) {
            try {
                Statement stmt = getConnection().createStatement();
                stmt.executeUpdate(query);
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    //MincraPlayer型についての操作
    public void updateMincraPlayer(MincraPlayer mincraPlayer){
        String query = "UPDATE player set " +
                "name = '" + mincraPlayer.getPlayerName() + "', " +
                "mp_value = " + mincraPlayer.getPlayerMP_value() + ", " +
                "cooltime_value = " + mincraPlayer.getPlayerCooltime_value() + ", " +
                "cooltime_max = " + mincraPlayer.getPlayerCooltime_max() +
                " WHERE uuid = '" + mincraPlayer.getPlayerUUID() + "'";
        updateRecord(query);
    }

    public void insertMincraPlayer(UUID uuid, MincraPlayer mincraPlayer){
        String query = "SELECT EXISTS(SELECT * FROM player WHERE uuid = '" + uuid + "')";

        //insert
        if (isExistsRecord(query)){
            query = "INSERT INTO player (name, uuid, mp_value, cooltime_value, cooltime_max) VALUES ('" +
                    mincraPlayer.getPlayerName() + "', '" +
                    mincraPlayer.getPlayerUUID() + "', " +
                    mincraPlayer.getPlayerMP_value() + ", " +
                    mincraPlayer.getPlayerCooltime_value() + ", " +
                    mincraPlayer.getPlayerCooltime_max() + ")";
            insertRecord(query);
        } else {
            System.out.println("[MincraMagics] レコードが既にplayerテーブルに存在しています。 name=" +
                    mincraPlayer.getPlayerName() + " UUID: " + mincraPlayer.getPlayerUUID());
        }
    }

    public MincraPlayer getMincraPlayer(UUID uuid) {
        MincraPlayer mincraPlayer = new MincraPlayer();
        mincraPlayer.setPlayerUUID(uuid);

        String sql = "SELECT name, uuid, mp_value, cooltime_value, cooltime_max FROM player WHERE uuid = '"+ uuid +"'";

        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                mincraPlayer.setPlayerName(rs.getString("name"));
                mincraPlayer.setPlayerMP_value(rs.getFloat("mp_value"));
                mincraPlayer.setPlayerCooltime_value(rs.getFloat("cooltime_value"));
                mincraPlayer.setPlayerCooltime_max(rs.getFloat("cooltime_max"));
            }
            stmt.close();
            rs.close();
            return mincraPlayer;
        }catch (SQLException e){
            System.out.println("[MincraMagics] データを取得に失敗しました。");
            e.printStackTrace();

            return null;
        }
    }

    public void saveMincraPlayer(){
        System.out.println("[MincraMagics] 全プレイヤーのデータをSQLに保存します...");

        for(Map.Entry<UUID, MincraPlayer> entry : MincraMagics.getPlayerManager().getMincraPlayerMap().entrySet()) {
            updateMincraPlayer(entry.getValue());
        }
    }
}