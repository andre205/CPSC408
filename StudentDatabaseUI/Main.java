/**

 @author Tyler Andrews

 This program displays a user a list of options pertaining to a student database,
 prompts for their response, and executes the corresponding action.

 */

package com.cpsc408;

public class Main {

    public static void main(String[] args) {

        DatabaseManager db = new DatabaseManager();
        Menu m = new Menu();

        //initialize response variable and temp student id for intermediate use
        int response = -2;
        int sid;

        //keep prompting for user responses until they decide to quit, or the server goes offline
        while(DatabaseManager.serverIsOnline() && response != 0) {
            //on the very first iteration, show the user their options
            if(response == -2)
                m.displayMenu();

            //every iteration, reset their response and prompt for a new one
            response = -1;
            while (response < 0)
                response = m.promptResponse();

            //execute the corresponding functionality based on user response
            switch(response){
                case(0): //Quit
                    System.out.println("Exiting.");
                    break;
                case(1): //Print all results
                    // get ResultSet containing all records, then print it
                    db.printResultSet(db.getAllRecords());
                    break;
                case(2): //Add a student
                    // prompt for student attributes, create a student object, then add it to the database
                    db.createStudent(m.promptStudentAttributes());
                    break;
                case(3): //Update student major
                    // prompt for a student id to update, make sure it is in the database,
                    // get that student object from the database, prompt for the changes, then update it in the database
                    sid = m.promptStudentId();
                    if(db.studentExists(sid))
                        db.updateStudent(sid, m.promptStudentMajor(db.getStudent(sid)));
                    break;
                case(4): //Update student advisor
                    sid = m.promptStudentId();
                    if(db.studentExists(sid))
                        db.updateStudent(sid, m.promptStudentAdvisor(db.getStudent(sid)));
                    break;
                case(5): //Update student major and advisor
                    sid = m.promptStudentId();
                    if(db.studentExists(sid))
                        db.updateStudent(sid, m.promptStudentMajorAndAdvisor(db.getStudent(sid)));
                    break;
                case(6): //Delete student
                    // prompt for a student id to update, make sure it is in the database, delete it
                    sid = m.promptStudentId();
                    if(db.studentExists(sid))
                        db.deleteStudent(sid);
                    break;
                case(7): //Print major filtered results
                    // get ResultSet containing all records with the given major, then print it
                    db.printResultSet(db.getMajorFilteredRecords(m.promptMajor()));
                    break;
                case(8): //Print GPA filtered results
                    db.printResultSet(db.getGPAFilteredRecords(m.promptGPA()));
                    break;
                case(9): //Print advisor filtered results
                    db.printResultSet(db.getAdvisorFilteredRecords(m.promptAdvisor()));
                    break;
            }

        }

    }

}

