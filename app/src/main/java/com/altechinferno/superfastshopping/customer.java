package com.altechinferno.superfastshopping;

public class customer {

    String fullName,Email,Phonenumber,Password;

    public customer() {

    }

    public customer(String fullName, String email, String phonenumber, String password) {
        this.fullName = fullName;
        Email = email;
        Phonenumber = phonenumber;
        Password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
