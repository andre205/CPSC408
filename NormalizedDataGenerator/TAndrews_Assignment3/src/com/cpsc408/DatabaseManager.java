/**

@author Tyler Andrews

This class handles all communication with the SQL server. (A3)

Given a dataRow object, it normalizes it across all 6 tables, using generated keys for consistency.

 */

package com.cpsc408;

import java.sql.*;

public class DatabaseManager {

    public static final String DRIVER_LOC = "com.mysql.jdbc.Driver";
    public static final String CONN_URL = "jdbc:mysql://localhost:3306/A3";
    public static final String CONN_FLAGS = "?autoReconnect=true&useSSL=false";
    public static final String CONN_USER = "root";
    public static final String CONN_PASS = "p";

    public static Connection conn = null;

    //initialize connection when manager is created
    public DatabaseManager() {
        getSQLConnection();
    }

    //return or initialize singleton sql connection
    public static Connection getSQLConnection() {

        if (conn == null){

            try {
                Class.forName(DRIVER_LOC);
                conn = DriverManager.getConnection(CONN_URL + CONN_FLAGS, CONN_USER, CONN_PASS);
            }
            catch (Exception var2) {
                var2.printStackTrace();
                return null;
            }
        }

        return conn;
    }

    //check if the server is online
    public static Boolean serverIsOnline(){
        if (getSQLConnection() != null) {
            return true;
        }
        else{
            System.out.println("SQL Server offline. Exiting application.");
            return false;
        }
    }

    //given a dataRow, insert the data into the normalized tables
    public Boolean normalizeDataRow(dataRow d){
        try{
            PreparedStatement userInfo_st = conn.prepareStatement("INSERT INTO UserInfo(username, firstName , lastName , telephone , address , zip, dob )" +
                    "VALUES (?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);


            PreparedStatement gameInfo_st = conn.prepareStatement("INSERT INTO GameInfo( name,releaseDate, description)"+
                    " VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);

            PreparedStatement userInventory_st = conn.prepareStatement("INSERT INTO UserInventory(uid, gid,purchaseDate )"+
                    " VALUES (?, ?,?);");

            PreparedStatement userSpecs_st = conn.prepareStatement("INSERT INTO UserSpecs(uid , cpu, cpuFreq  , graphicsCard , manufacturer , ramSize , ramType , resultion)" +
                    "VALUES (?,?,?,?,?,?,?,?);");

            PreparedStatement userTickets_st = conn.prepareStatement("INSERT INTO UserTickets(uid , gid )"+
                    " VALUES(?,?);",Statement.RETURN_GENERATED_KEYS);

            PreparedStatement ticketInfo_st = conn.prepareStatement("INSERT INTO TicketInfo(tid , text, level )" +
                    "VALUES(?,?,?);");


            userInfo_st.setString(1, d.getUsername().trim().substring(0, Math.min(40,d.getUsername().length())));
            userInfo_st.setString(2, d.getFirstName().trim().substring(0, Math.min(40,d.getFirstName().length())));
            userInfo_st.setString(3, d.getLastName().trim().substring(0,Math.min(40,d.getLastName().length())));
            userInfo_st.setString(4, d.getTelephone().trim().substring(0,Math.min(40,d.getTelephone().length())));
            userInfo_st.setString(5, d.getAddress().trim().substring(0,Math.min(40,d.getAddress().length())));
            userInfo_st.setString(6, d.getZip().trim().substring(0,Math.min(10, d.getZip().length())));
            userInfo_st.setString(7, d.getDob().trim().substring(0,Math.min(40, d.getDob().length())));
            userInfo_st.executeUpdate();
            ResultSet rs_uid = userInfo_st.getGeneratedKeys();
            int uid = 0;
            if(rs_uid.next())
                uid = rs_uid.getInt(1);

            gameInfo_st.setString(1, d.getGameName().trim().substring(0,Math.min(40, d.getGameName().length())));
            gameInfo_st.setString(2, d.getReleaseDate().trim().substring(0,Math.min(40, d.getReleaseDate().length())));
            gameInfo_st.setString(3, d.getDescription().trim().substring(0,Math.min(200, d.getDescription().length())));
            gameInfo_st.executeUpdate();
            ResultSet rs_gid = gameInfo_st.getGeneratedKeys();
            int gid = 0;
            if(rs_gid.next())
                gid = rs_gid.getInt(1);

            userSpecs_st.setInt(1, uid);
            userSpecs_st.setString(2,d.getCpu().trim().substring(0,Math.min(40,d.getCpu().length())));
            userSpecs_st.setString(3,d.getCpuFreq().trim().substring(0,Math.min(40,d.getCpuFreq().length())));
            userSpecs_st.setString(4,d.getGraphicsCard().trim().substring(0,Math.min(40,d.getGraphicsCard().length()-1)));
            userSpecs_st.setString(5,d.getManufacturer().trim().substring(0,Math.min(40,d.getManufacturer().length())));
            userSpecs_st.setString(6,d.getRamSize().trim().substring(0,Math.min(40,d.getRamSize().length())));
            userSpecs_st.setString(7,d.getRamType().trim().substring(0,Math.min(40,d.getRamType().length())));
            userSpecs_st.setString(8,d.getResolution().trim().substring(0,Math.min(40,d.getResolution().length())));
            userSpecs_st.executeUpdate();

            userTickets_st.setInt(1,uid);
            userTickets_st.setInt(2,gid);
            userTickets_st.executeUpdate();
            ResultSet rs_tid = userTickets_st.getGeneratedKeys();
            int tid = 0;
            if(rs_tid.next())
                tid = rs_tid.getInt(1);

            ticketInfo_st.setInt(1,tid);
            ticketInfo_st.setString(2,d.getText().trim().substring(0,Math.min(300,d.getText().length())));
            ticketInfo_st.setString(3,d.getLevel().trim().substring(0,Math.min(20,d.getLevel().length())));
            ticketInfo_st.executeUpdate();

            userInventory_st.setInt(1,uid);
            userInventory_st.setInt(2,gid);
            userInventory_st.setString(3,d.getPurchaseDate().trim().substring(0,Math.min(40,d.getPurchaseDate().length())));
            userInventory_st.executeUpdate();

            return true;
        }
        catch(Exception e)
        {
            //e.printStackTrace();
            return false;
        }
    }


}
