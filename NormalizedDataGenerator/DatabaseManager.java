/**

@author Tyler Andrews

This class handles all communication with the SQL server. (StudentDB)

It has the following functionality:
Create and provide singleton SQL server connection
Retrieve records
Add records
Delete records
Check if records exist
Print records

 */

package com.cpsc408;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseManager {

    public static final String DRIVER_LOC = "com.mysql.jdbc.Driver";
    public static final String CONN_URL = "jdbc:mysql://localhost:3306/StudentDB";
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
                //var2.printStackTrace();
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

    //select * from student database and return the ResultSet
    public ResultSet getAllRecords(){
        try {
            System.out.println("Displaying all student records.");
            PreparedStatement p = conn.prepareStatement("SELECT * FROM Student");
            return p.executeQuery();
        }
        catch(Exception e){
            return null;
        }
    }

    //select * from student database where major is equal to the user's filter and return the ResultSet
    public ResultSet getMajorFilteredRecords(String filter){
        try {
            System.out.println("Displaying all student records where major is " + filter + ".");
            PreparedStatement p = conn.prepareStatement("SELECT * FROM Student WHERE Major = ?");
            p.setString(1,filter);
            return p.executeQuery();
        }
        catch(Exception e){
            return null;
        }
    }

    //select * from student database where advisor is equal to the user's filter and return the ResultSet
    public ResultSet getAdvisorFilteredRecords(String filter){
        try {
            System.out.println("Displaying all student records where advisor is " + filter + ".");
            PreparedStatement p = conn.prepareStatement("SELECT * FROM Student WHERE FacultyAdvisor = ?");
            p.setString(1,filter);
            return p.executeQuery();
        }
        catch(Exception e){
            return null;
        }
    }

    //select * from student database where GPA is similar to the user's filter and return the ResultSet
    public ResultSet getGPAFilteredRecords(float gpa){
        try {
            System.out.println("Displaying all student records where gpa is " + gpa + ".");
            PreparedStatement p = conn.prepareStatement("SELECT * FROM Student WHERE GPA LIKE ?");
            p.setFloat(1,gpa);
            return p.executeQuery();
        }
        catch(Exception e){
            return null;
        }
    }

    //display column labels followed by the given ResultSet in a clean format (corresponding to the database value sizes)
    public Boolean printResultSet(ResultSet r){
        String format = "%-10d %-25s %-25s %.2f %-10s %-25s\n";
        String format2 = "%-10s %-25s %-25s %-4s %-10s %-25s\n";


        try{
            if(!r.next()){
                System.out.println("No results.");
                return false;
            }

            System.out.printf(format2,"ID", "First Name", "Last Name", "GPA", "Major", "Faculty Advisor");
            while(r.next())
            {
                Integer id = r.getInt("StudentId");
                String fn = r.getString("FirstName");
                String ln = r.getString("LastName");
                Float GPA = r.getFloat("GPA");
                String maj = r.getString("Major");
                String adv = r.getString("FacultyAdvisor");

                System.out.printf(format,id,fn,ln,GPA,maj,adv);
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //given a student, add them to the database
    public Boolean createStudent(Student s){
        try{
            PreparedStatement p = conn.prepareStatement("INSERT INTO Student(FirstName, LastName, GPA, Major, FacultyAdvisor) VALUES (?,?,?,?,?);");
            p.setString(1, s.getFirstName());
            p.setString(2, s.getLastName());
            p.setFloat(3, s.getGPA());
            p.setString(4, s.getMajor());
            p.setString(5, s.getAdvisor());
            p.executeUpdate();
            System.out.println("Student added to database.");
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //given a student id and a modified student object, update the database record at that id to contain the new student values
    public boolean updateStudent(int id, Student s){
        try{
            PreparedStatement p = conn.prepareStatement("UPDATE Student SET Major = ?, FacultyAdvisor = ? WHERE StudentId = ?");
            p.setString(1, s.getMajor());
            p.setString(2, s.getAdvisor());
            p.setInt(3, id);
            p.executeUpdate();
            System.out.println("Student updated.");
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //given a student id, check if there is a record in the database with that id
    public boolean studentExists(int id){
        try{
            PreparedStatement p = conn.prepareStatement("SELECT * FROM Student WHERE StudentId = ?");
            p.setInt(1, id);
            ResultSet r = p.executeQuery();
            if (r.next())
                return true;
            else {
                System.out.println("That student does not exist. Returning to menu.");
                return false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //assuming the student id is in the database, return a student object populated with the database values at that id
    public Student getStudent(int id){
        try{
            Student s = new Student();
            PreparedStatement p = conn.prepareStatement("SELECT * FROM Student WHERE StudentId = ?");
            p.setInt(1, id);
            ResultSet r = p.executeQuery();
            if (r.next()){
                s.setFirstName(r.getString("FirstName"));
                s.setLastName(r.getString("LastName"));
                s.setAdvisor(r.getString("FacultyAdvisor"));
                s.setGPA(r.getFloat("GPA"));
                s.setMajor(r.getString("Major"));
                return s;
            }

            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    //assuming the student id is in the database, delete the database record at that id
    public Boolean deleteStudent(int id){
        try{
            Student s = new Student();
            PreparedStatement p = conn.prepareStatement("DELETE FROM Student WHERE StudentId = ?");
            p.setInt(1, id);
            p.executeUpdate();
            System.out.println("Student deleted.");
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
