package com.example.deliverable1;

import java.util.ArrayList;

public class Service {
    private String ID;
    private String serviceName;
    private double hourlyRate;
    private ArrayList<String> costumerInfoForm;

    public Service(String id, String serviceName, double hourlyRate, ArrayList<String> costumerInfoForm){
        this.ID = id;
        this.serviceName = serviceName;
        this.hourlyRate = hourlyRate;
        this.costumerInfoForm = costumerInfoForm;
    }

    public String getID(){
        return this.ID;
    }
    public String getServiceName(){
        return this.serviceName;
    }
    public double getHourlyRate(){
        return this.hourlyRate;
    }
    public ArrayList<String> getCostumerInfoForm(){
        return this.costumerInfoForm;
    }

}
