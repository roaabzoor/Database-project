package com.example.project;

public class Adopter {
    private int adopterid;
    private String fname;
    private String lname;
    private String email;
    private String address;
    private int age;
    private String contactnumber;

    public Adopter(int adopterid, String fname, String lname, String email, String address, int age, String contactnumber) {
        this.adopterid = adopterid;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.address = address;
        this.age = age;
        this.contactnumber = contactnumber;
    }

    public int getAdopterid() {
        return adopterid;
    }

    public String getFname() { return fname; }
    public String getLname() { return lname; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public int getAge() { return age; }
    public String getContactnumber() { return contactnumber; }
}