package com.example.DotaMarketPlace.Model;

// Object
public class User {

    private static String uFirstName , uLastName ,  uPhoneNumber , uPassword , ucPassword , uGender , uUsername;

    // Constructor
    public User(String FirstName, String LastName,String Username , String Password, String cPassword,String PhoneNumber, String Gender) {
        this.uFirstName = FirstName;
        this.uLastName = LastName;
        this.uUsername = Username;
        this.uPassword = Password;
        this.ucPassword = cPassword;
        this.uPhoneNumber = PhoneNumber;
        this.uGender = Gender;
    }

    public static String getFirstName() {
        return uFirstName;
    }

    public static void setFirstName(String FirstName) {
        User.uFirstName = FirstName;
    }

    public static String getLastName() {
        return uLastName;
    }

    public static void setLastName(String LastName) {
        User.uLastName = LastName;
    }

    public static String getUsername(){
        return uUsername;
    }

    public static void setUsername(String Username) {
        User.uUsername = Username;
    }


    public static String getPassword() {
        return uPassword;
    }

    public static void setPassword(String Password) {
        User.uPassword = Password;
    }

    public static String getcPassword() {
        return ucPassword;
    }

    public static void setUcPassword(String cPassword) {
        User.ucPassword = cPassword;
    }

    public static String getPhoneNumber() {
        return uPhoneNumber;
    }

    public static void setPhoneNumber(String PhoneNumber) {
        User.uPhoneNumber = PhoneNumber;
    }

    public static String getGender() {
        return uGender;
    }

    public static void setGender(String Gender) {
        User.uGender = Gender;
    }

}
