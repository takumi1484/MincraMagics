package jp.mincra.mincramagics.managers;


import jp.mincra.mincramagics.MincraMagics;
import jp.mincra.mincramagics.container.MincraPlayer;

import java.sql.*;
import java.util.Map;
import java.util.UUID;

public class SQLManager {

    private Connection conn;
    private Statement stmt;


    public void getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
//            PropertyManager propertyManager = MincraMagics.getPropertyManager();
            String url = MincraMagics.getPropertyManager().getProperty("MySQL_url")+"?user=" +
                    MincraMagics.getPropertyManager().getProperty("MySQL_user")+"&password=" +
                    MincraMagics.getPropertyManager().getProperty("MySQL_password");
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
                "mp_value FLOAT, " +
                "cooltime_value FLOAT, " +
                "cooltime_max FLOAT" +
                ")";
        System.out.println("[MincraMagics] テーブルの作成を試行します...");
        //execute the SQL stetement
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("[MincraMagics] テーブルの作成に失敗しました。");
            e.printStackTrace();
        }
    }


    //MincraPlayer型についての操作
    public void updateMincraPlayer(MincraPlayer mincraPlayer){
        String sql = "UPDATE player set " +
                "name = '" + mincraPlayer.getPlayerName() + "', " +
                "mp_value = " + mincraPlayer.getPlayerMP_value() + ", " +
                "cooltime_value = " + mincraPlayer.getPlayerCooltime_value() + ", " +
                "cooltime_max = " + mincraPlayer.getPlayerCooltime_max() +
                " WHERE uuid = '" + mincraPlayer.getPlayerUUID() + "'";

        try {
            stmt.executeUpdate(sql);
            System.out.println("[MincraMagics] SQLのアップデートに成功しました。 name=" +
                    mincraPlayer.getPlayerName());
        } catch (SQLException e) {
            System.out.println("[MincraMagics] SQLのアップデートに失敗しました。 name=" +
                    mincraPlayer.getPlayerName());
            e.printStackTrace();
        }
    }

    public void insertMincraPlayer(UUID uuid, MincraPlayer mincraPlayer){
        String sql = "SELECT EXISTS(SELECT * FROM player WHERE uuid = '" + uuid + "')";

        //insert
        if (existsRecord(sql)){
            sql = "INSERT INTO player (name, uuid, mp_value, cooltime_value, cooltime_max) VALUES ('" +
                    mincraPlayer.getPlayerName() + "', '" +
                    mincraPlayer.getPlayerUUID() + "', " +
                    mincraPlayer.getPlayerMP_value() + ", " +
                    mincraPlayer.getPlayerCooltime_value() + ", " +
                    mincraPlayer.getPlayerCooltime_max() + ")";
            try {
                stmt.executeUpdate(sql);
                System.out.println("[MincraMagics] レコードの追加に成功しました。 name=" +
                        mincraPlayer.getPlayerName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                mincraPlayer.setPlayerName(rs.getString("name"));
                mincraPlayer.setPlayerMP_value(rs.getFloat("mp_value"));
                mincraPlayer.setPlayerCooltime_value(rs.getFloat("cooltime_value"));
                mincraPlayer.setPlayerCooltime_max(rs.getFloat("cooltime_max"));
            }

            return mincraPlayer;
        }catch (SQLException e){
            System.out.println("[MincraMagics] データを取得に失敗しました。");
            e.printStackTrace();

            return null;
        }
    }

    public void saveMincraPlayer(){
        System.out.println("[MincraMagics] 全プレイヤーのデータをSQLに保存します...");
        Map<UUID, MincraPlayer> map = MincraMagics.getPlayerManager().getMincraPlayerMap();

        for(Map.Entry<UUID, MincraPlayer> entry : map.entrySet()) {
            updateMincraPlayer(entry.getValue());
        }
    }


    //Util
    public boolean existsRecord(String sql){
        //レコードの存在チェック
        int i = 0;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                i = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i == 0;
    }
}