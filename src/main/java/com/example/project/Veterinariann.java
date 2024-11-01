package com.example.project;

public class Veterinariann {

    private int veterinarianid;
    private String fname;
    private String lname;
    private String email;
    private String address;
    private int salary;
    private String contactnumber;
    private String specialization;

    public Veterinariann(int veterinarianid, String fname, String lname, String email, String address, int salay, String contactnumber,String specialization) {
        this.veterinarianid = veterinarianid;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.address = address;
        this.salary = salay;
        this.contactnumber = contactnumber;
        this.specialization = specialization;
    }
    public int getVeterinarianId() {
        return veterinarianid;
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

    public String getAddress() {
        return address;
    }

    public int getSalary() {
        return salary;
    }

    public String getContactNumber() {
        return contactnumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setVeterinarianId(int veterinarianId) {
        this.veterinarianid = veterinarianId;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setContactNumber(String contactNumber) {
        this.contactnumber = contactNumber;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
