package com.example.deliverable1;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class UnitTest1 {

    // Class ServiceRequest Test Cases
    @Test
    public void testGetID() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ServiceRequest serviceRequest = new ServiceRequest("id", "name", "username", "service", list, RequestCondition.ACCEPTED);
        assertEquals("getID method failed", "id", serviceRequest.getID());
    }

    @Test
    public void testGetCostumer() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ServiceRequest serviceRequest = new ServiceRequest("id", "name", "username", "service", list, RequestCondition.ACCEPTED);
        assertEquals("getCostumer method failed", "name", serviceRequest.getCostumer());
    }

    @Test
    public void testGetBranch() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ServiceRequest serviceRequest = new ServiceRequest("id", "name", "username", "service", list, RequestCondition.ACCEPTED);
        assertEquals("getBranch method failed", "username", serviceRequest.getBranch());
    }

    @Test
    public void testGetService() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ServiceRequest serviceRequest = new ServiceRequest("id", "name", "username", "service", list, RequestCondition.ACCEPTED);
        assertEquals("getService method failed", "service", serviceRequest.getService());
    }

    @Test
    public void testGetCostumerInfoForm() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ServiceRequest serviceRequest = new ServiceRequest("id", "name", "username", "service", list, RequestCondition.ACCEPTED);
        assertArrayEquals("getCostumerInfoForm method failed", list.toArray(), serviceRequest.getCostumerInfoForm().toArray());
    }

    @Test
    public void testIsAccepted() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ServiceRequest serviceRequest = new ServiceRequest("id", "name", "username", "service", list, RequestCondition.ACCEPTED);
        assertEquals("isAccepted method failed", RequestCondition.ACCEPTED, serviceRequest.isAccepted());
    }
}