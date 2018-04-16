/**

 @author Tyler Andrews

 This program takes a single command line argument of a csv file containing unnormalized data.

 It verifies the existance of the file, then normalizes each row of data into the database.

 It also reports to the user if any rows fail and displays the failed records.

 */

package com.cpsc408;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        if(args.length == 1) {

            File c = new File(args[0]);
            if(c.exists()){

                System.out.println("Normalizing data from " + args[0]);

                DatabaseManager db = new DatabaseManager();

                if(db.serverIsOnline()) {

                    try {

                        FileReader in = new FileReader(args[0]);
                        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

                        int count = 0;
                        int fails = 0;
                        LinkedList<dataRow> badRows = new LinkedList<>();

                        for (CSVRecord record : records) {

                            dataRow d = new dataRow(record.get(0), record.get(1), record.get(2),
                                    record.get(3), record.get(4), record.get(5),
                                    record.get(6), record.get(7), record.get(8),
                                    record.get(9), record.get(10), record.get(11),
                                    record.get(12), record.get(13), record.get(14),
                                    record.get(15), record.get(16), record.get(17),
                                    record.get(18), record.get(19));

                            //System.out.println(d.toString());

                            if (db.normalizeDataRow(d))
                                count++;
                            else {
                                fails++;
                                badRows.add(d);
                            }
                        }

                        System.out.println(count + " rows successfully normalized");

                        if (fails>0) {
                            System.out.println(fails + " rows failed.");
                            for (dataRow d : badRows){
                                System.out.println(d.toString());
                            }
                        }

                    }
                    catch (Exception e) {
                        System.out.println("File read error, please try again");
                    }
                }
            }
            else
                System.out.println("Please enter the path of an existing csv file as a command line argument.");

        }
        else
            System.out.println("Please enter the path of a csv file as a command line argument.");
    }
}
