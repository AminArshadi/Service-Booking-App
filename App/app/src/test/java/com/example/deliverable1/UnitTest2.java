package com.example.deliverable1;

import org.junit.Test;
import static org.junit.Assert.*;

public class UnitTest2 {

    // Class Rate Test Cases
    @Test
    public void testGetID() {
        Rate rate = new Rate("id", "costumerUsername", "branchUsername", 5, "review");
        assertEquals("getID method failed", "id", rate.getID());
    }

    @Test
    public void testGetCostumerUsername() {
        Rate rate = new Rate("id", "costumerUsername", "branchUsername", 5, "review");
        assertEquals("getCostumerUsername method failed", "costumerUsername", rate.getCostumerUsername());
    }

    @Test
    public void testGetBranchUsername() {
        Rate rate = new Rate("id", "costumerUsername", "branchUsername", 5, "review");
        assertEquals("getBranchUsername method failed", "branchUsername", rate.getBranchUsername());
    }

    @Test
    public void testGetRate() {
        Rate rate = new Rate("id", "costumerUsername", "branchUsername", 5, "review");
        assertEquals("getRate method failed", 5, rate.getRate());
    }

    @Test
    public void testGetReview() {
        Rate rate = new Rate("id", "costumerUsername", "branchUsername", 5, "review");
        assertEquals("getReview method failed", "review", rate.getReview());
    }
}
