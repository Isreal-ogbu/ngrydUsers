package com.example.ngrydUsers.UserModel;

public class Users {
    private String name;
    private int age;
    private double accountBalance;
    private String location;

    private String error;

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public Users(String aName, int aAge, double aAccountBalance, String aLocation){
        this.name = aName;
        this.age = aAge;
        this.accountBalance = aAccountBalance;
        this.location = aLocation;
    }
    public Users(String aName){
        this.name = aName;
        this.error = "This user does not exist!!";
    }
}
