package com.example.deliverable1;

import java.util.ArrayList;

public class ServiceRequest {
    private String id;
    private String costumerUsername;
    private String branchUsername;
    private String service;
    private ArrayList<String> costumerInfoForm;
    private RequestCondition condition;

    public ServiceRequest(String id, String costumerUsername, String branchUsername, String service, ArrayList<String> costumerInfoForm, RequestCondition condition){
        this.id = id;
        this.costumerUsername = costumerUsername;
        this.branchUsername = branchUsername;
        this.service = service;
        this.costumerInfoForm = costumerInfoForm;
        this.condition = condition;
    }

    public String getID(){
        return this.id;
    }
    public String getCostumer(){
        return this.costumerUsername;
    }
    public String getBranch(){
        return this.branchUsername;
    }
    public String getService(){
        return this.service;
    }
    public ArrayList<String> getCostumerInfoForm(){
        return this.costumerInfoForm;
    }
    public RequestCondition isAccepted(){ return this.condition;}
}
