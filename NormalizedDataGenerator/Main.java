package com.cpsc408;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {


        if(args.length == 2) {

            DatabaseManager db = new DatabaseManager();

            if(db.serverIsOnline()) {

                try {

                    Reader in = new FileReader("path");
                    Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

                    PreparedStatement st = db.getSQLConnection().prepareStatement("INSERT INTO blah(type) VALUES(?,?,?) , Statement.RETURN_GENERATED_KEYS");


                    for (CSVRecord record : records) {

                        String blah = record.get(0);

                        ResultSet rs_key = st.getGeneratedKeys();

                        if (rs_key.next()) {

                            int c_id = rs_key.getInt(1);


                        }


                    }

                    //for a prepared statement, call    in prepares statement, do   INSERT INTO ....  (?,?,?)..  , Statement.RETURN_GENERATED_KEYS


                } catch (Exception e) {

                }

            }

            else
            {
                System.out.println("Server is offline. Exiting application.");
            }



        }
        else
            System.out.println("Please enter the path of a csv file as a command line argument.");


    }
}
