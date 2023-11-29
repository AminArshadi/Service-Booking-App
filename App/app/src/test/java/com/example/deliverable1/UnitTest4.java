package com.example.deliverable1;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class UnitTest4 {

    // Class BranchProfile Test Cases
    @Test
    public void testGetID(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        BranchProfile branchProfile = new BranchProfile("id", "username", "address", 1234567890, "workingHours", list);
        assertEquals("getID method failed", "id", branchProfile.getID());
    }

    @Test
    public void testGetUsername(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        BranchProfile branchProfile = new BranchProfile("id", "username", "address", 1234567890, "workingHours", list);
        assertEquals("getID method failed", "username", branchProfile.getUsername());
    }

    @Test
    public void testGetAddress(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        BranchProfile branchProfile = new BranchProfile("id", "username", "address", 1234567890, "workingHours", list);
        assertEquals("getAddress method failed", "address", branchProfile.getAddress());
    }

    @Test
    public void testGetPhoneNumber(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        BranchProfile branchProfile = new BranchProfile("id", "username", "address", 1234567890, "workingHours", list);
        assertEquals("getPhoneNumber method failed", 1234567890, branchProfile.getPhoneNumber());
    }

    @Test
    public void testGetWorkingHours(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        BranchProfile branchProfile = new BranchProfile("id", "username", "address", 1234567890, "workingHours", list);
        assertEquals("getWorkingHours method failed", "workingHours", branchProfile.getWorkingHours());
    }

    @Test
    public void testGetBranchServices(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        BranchProfile branchProfile = new BranchProfile("id", "username", "address", 1234567890, "workingHours", list);
        assertArrayEquals("getBranchServices method failed", list.toArray(), branchProfile.getBranchServices().toArray());
    }
}
