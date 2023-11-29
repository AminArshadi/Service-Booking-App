package com.example.deliverable1;

public class Account {
    private String ID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private AccountTypes type;

    public Account(String id, String firstName, String lastName, String username, String password, AccountTypes type) {
        this.ID = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getID(){
        return this.ID;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public AccountTypes getType(){
        return this.type;
    }
}
