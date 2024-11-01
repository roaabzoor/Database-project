package com.example.project;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Employee {
    private int ssn;
    private String fname;
    private String lname;
    private String email;
    private String epassword;
    private int salary;
    private String contactnumber;
    private String jopposition;
    private LocalDate hiredate;
    private String gender;

    public Employee(int ssn, String fname, String lname, String email, String epassword, int salary, String contactnumber, String jopposition, LocalDate hiredate, String gender) {
        this.ssn = ssn;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.epassword = epassword;
        this.salary = salary;
        this.contactnumber = contactnumber;
        this.jopposition = jopposition;
        this.gender = gender;
        this.hiredate = hiredate;
    }
    public int getSsn() {
        return ssn;
    }

    public String getEpassword() {
        return epassword;
    }

    public int getSalary() {
        return salary;
    }
    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getEmail() {
        return email;
    }
    public String getContactnumber() {
        return contactnumber;
    }
    public String getJobPosition() {
        return jopposition;
    }
    public LocalDate getHireDate() {
        return hiredate;
    }
    public String getGender() {
        return gender;
    }

    public void setFname(String fname) {

        this.fname = fname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEpassword(String epassword) {
        this.epassword = epassword;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setContactNumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public void setJobPosition(String jobPosition) {
        this.jopposition = jobPosition;
    }

    public void setHireDate(LocalDate hiredate) {
        this.hiredate = hiredate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
