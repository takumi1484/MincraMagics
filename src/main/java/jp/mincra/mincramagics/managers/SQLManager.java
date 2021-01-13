package jp.mincra.mincramagics.managers;


import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MincraPlayer;

import java.sql.*;
import java.util.Map;
import java.util.UUID;

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
        String sql = "CREATE TABLE IF NOT EXISTS player (" +
                //AUTO_INCREMENT 値が指定されなくても自動で入力される。
                "id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, " +
                "name varchar(20), " +
                "uuid VARBINARY(36) NOT NULL UNIQUE," +
                "mp INT, " +
                "cooltime INT" +
                ")";
        System.out.println("[MincraMagics] テーブルを作成中です... "+sql);
        //execute the SQL stetement
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("[MincraMagics] テーブルの作成に失敗しました。");
            e.printStackTrace();
        }
    }

    //MincraPlayer型についての操作
    public void updateMincraPlayer(UUID uuid, MincraPlayer mincraPlayer){
        String sql = "UPDATE player set " +
                "name = '"+ mincraPlayer.getPlayerName() + "'" +
                "mp = "+ mincraPlayer.getPlayerMP() +
                "cooltime = "+ mincraPlayer.getPlayerCooltime() +
                "WHERE uuid = '" + mincraPlayer.getPlayerUUID() + "'";

        try {
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("[MincraMagics] SQLのアップデートに失敗しました。 " +
                    "name=" + mincraPlayer.getPlayerName());
            e.printStackTrace();
        }
    }

    public MincraPlayer getMincraPlayer(UUID uuid) {
        MincraPlayer mincraPlayer = new MincraPlayer();

        String sql = "SELECT name, uuid, mp, cooltime FROM player " +
                "WHERE uuid = '"+ uuid +"'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                mincraPlayer.setPlayerName(rs.getString("name"));
                mincraPlayer.setPlayerMP(rs.getInt("mp"));
                mincraPlayer.setPlayerCooltime(rs.getInt("cooltime"));
            }

            return mincraPlayer;
        }catch (SQLException e){
            System.out.println("[MincraMagics] データを取得に失敗しました。");
            e.printStackTrace();

            return null;
        }
    }
}