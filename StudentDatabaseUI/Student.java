/**

 @author Tyler Andrews

 This is a simple student class.

 It stores the following student attributes:
 First Name
 Last Name
 GPA
 Major
 Faculty Advisor

 */

package com.cpsc408;

public class Student {

    private String firstName;
    private String lastName;
    private float GPA;
    private String major;
    private String advisor;

    public Student(){
        firstName = null;
        lastName = null;
        GPA = 0;
        major = null;
        advisor = null;
    }

    public void printStudent() {
        String format = "%-25s %-25s %.2f %-10s %-25s\n";
        String format2 = "%-25s %-25s %-4s %-10s %-25s\n";

        System.out.printf(format2,"First Name", "Last Name", "GPA", "Major", "Faculty Advisor");
        System.out.printf(format,this.firstName,this.lastName,this.GPA,this.major,this.advisor);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getGPA() {
        return GPA;
    }

    public void setGPA(float GPA) {
        this.GPA = GPA;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }
}
