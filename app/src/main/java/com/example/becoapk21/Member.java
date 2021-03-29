package com.example.becoapk21;

public class Member {

    private String password;
    private String email;
    private String fname;


    public Member() {//constructor


    }


    public Member(String email, String password, String fname) {//constructor
        this.password = password;
        this.email = email;
        this.fname = fname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

}
