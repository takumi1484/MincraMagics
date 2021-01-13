package jp.mincra.mincramagics.managers;


import jp.mincra.mincramagics.MincraMagics;

import java.sql.*;
import java.util.Map;

public class MySQLManager {

    public void getConnection(){


        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Map<String, String> propertyMap = MincraMagics.getPropertyManager().getPropertyMap();

            con = DriverManager.getConnection(
                    propertyMap.get("MySQL_url") + "?useSSL=false",
                    propertyMap.get("MySQL_user"),
                    propertyMap.get("MySQL_password")
            );

            pstmt = con.prepareStatement("select * from menu");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("tenpo_name"));
                System.out.println(rs.getInt("id"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
