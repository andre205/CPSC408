/**

 @author Tyler Andrews

 This class handles all user input and menu output.

 It has the following functionality:
 Show the user the application functionality
 Prompt for their response
 Prompt for student attributes

 */

package com.cpsc408;

import java.util.Scanner;

public class Menu {

    //print all program options
    public void displayMenu(){

        System.out.println("Would you like to:");
        System.out.println("1. Display all students and their attributes?");
        System.out.println("2. Create a student?");
        System.out.println("3. Update a student's major?");
        System.out.println("4. Update a student's advisor?");
        System.out.println("5. Update both a student's major and advisor?");
        System.out.println("6. Delete a student?");
        System.out.println("7. Search for a student by Major?");
        System.out.println("8. Search for a student by GPA?");
        System.out.println("9. Search for a student by Advisor?");
        System.out.println("----");
        System.out.println("0. Quit?");
        System.out.println("Type any non-integer response to redisplay this menu.");

    }

    //ask for an integer response until the user provides one, then return it
    public int promptResponse(){
        System.out.println("Please enter a response between 0-9");

        Scanner in = new Scanner(System.in);

        if (in.hasNextInt()){
            int i = in.nextInt();
            if(i < 10)
                return i;
            else
                return -1;
        }
        else
            displayMenu();
            return -1;
    }

    //ask the user for student attributes, create a student object out of them, then return it
    //for gpa, make sure the user enters a float
    public Student promptStudentAttributes(){
        Scanner in = new Scanner(System.in);

        Student s = new Student();

        float gpa = -1;
        String st;
        System.out.println("Enter the student's first name");
        s.setFirstName(in.nextLine());
        System.out.println("Enter the student's last name");
        s.setLastName(in.nextLine());
        System.out.println("Enter the student's GPA (rounded to 2 decimal places");
        while(gpa < 0){
            if(in.hasNextLine())
            {
                st = in.nextLine();
                try{
                    gpa = Float.valueOf(st);
                }
                catch(Exception e)
                {
                    System.out.println("Please enter a number value between 0 - 4.0");
                    gpa = -1;
                }
                if (gpa < 4.0 && gpa > 0)
                    s.setGPA(gpa);
                else {
                    System.out.println("Please enter a number value between 0 - 4.0");
                    gpa = -1;
                }
            }
        }
        System.out.println("Enter the student's major");
        s.setMajor(in.nextLine());
        System.out.println("Enter the student's faculty advisor");
        s.setAdvisor(in.nextLine());

        return s;

    }

    //prompt the user to enter an integer until they do, then return it
    public int promptStudentId(){

        Scanner in = new Scanner(System.in);
        int r = -1;

        System.out.println("Please enter the ID of the student that you would like to update.");
        while (r<0){
            if(in.hasNextInt()){
                r = in.nextInt();
            }
        }
        return r;
    }

    //given a student object, print it, then ask the user for a new major, update the object and return it
    public Student promptStudentMajor(Student s){
        System.out.println("Current student information:");
        s.printStudent();

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the student's new major.");
        s.setMajor(in.nextLine());
        return s;
    }

    public Student promptStudentAdvisor(Student s){
        System.out.println("Current student information:");
        s.printStudent();

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the student's new faculty advisor.");
        s.setAdvisor(in.nextLine());
        return s;
    }

    public Student promptStudentMajorAndAdvisor(Student s){
        System.out.println("Current student information:");
        s.printStudent();

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the student's new major.");
        s.setMajor(in.nextLine());
        System.out.println("Please enter the student's new faculty advisor.");
        s.setAdvisor(in.nextLine());
        return s;
    }

    //ask the user to enter a major, then return it
    public String promptMajor(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a major to filter by.");
        return in.nextLine();
    }

    //ask the user to enter a GPA until they do, then return it
    public Float promptGPA(){
        Scanner in = new Scanner(System.in);
        float g = (float) -1.0;
        String st;
        System.out.println("Please enter a GPA to filter by.");
        while(g < 0){
            if(in.hasNextLine())
            {
                st = in.nextLine();
                try{
                    g = Float.valueOf(st);
                }
                catch(Exception e)
                {
                    System.out.println("Please enter a number value between 0 - 4.0");
                    g = -1;
                }
                if (g < 4.0 && g > 0)
                    return g;
                else {
                    System.out.println("Please enter a number value between 0 - 4.0");
                    g = -1;
                }
            }
        }
        return null;
    }

    //ask the user to enter an advisor, then return it
    public String promptAdvisor(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter an advisor to filter by.");
        return in.nextLine();
    }

}
