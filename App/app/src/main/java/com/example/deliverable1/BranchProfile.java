package com.example.deliverable1;

import java.util.ArrayList;
import java.util.HashMap;

public class BranchProfile {

    private String ID;
    private String username;
    private String address;
    private int phoneNumber;
    private String workingHours;
    private ArrayList<String> branchServices;

    public BranchProfile(String id, String username, String address, int phoneNumber, String workingHours, ArrayList<String> branchServices){
        this.ID = id;
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.workingHours = workingHours;
        this.branchServices = branchServices;
    }

    public String getID(){
        return this.ID;
    }
    public String getUsername(){ return this.username; }
    public String getAddress(){ return this.address; }
    public int getPhoneNumber(){ return this.phoneNumber; }
    public String getWorkingHours(){
        return this.workingHours;
    }
    public ArrayList<String> getBranchServices(){
        return this.branchServices;
    }

}
