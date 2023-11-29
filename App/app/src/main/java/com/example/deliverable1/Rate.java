package com.example.deliverable1;

public class Rate {

    private String id;
    private String costumerUsername;
    private String branchUsername;
    private int rate;
    private String review;

    public Rate(String id, String costumerUsername, String branchUsername, int rate, String review){
        this.id = id;
        this.costumerUsername = costumerUsername;
        this.branchUsername = branchUsername;
        this.rate = rate;
        this.review = review;
    }

    public String getID(){
        return this.id;
    }
    public String getCostumerUsername(){
        return this.costumerUsername;
    }
    public String getBranchUsername(){
        return this.branchUsername;
    }
    public int getRate(){
        return this.rate;
    }
    public String getReview(){
        return this.review;
    }

}
