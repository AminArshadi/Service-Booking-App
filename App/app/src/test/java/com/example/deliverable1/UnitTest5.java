package com.example.deliverable1;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class UnitTest5 {

    // Class Service Test Cases
    @Test
    public void getID(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Service service = new Service("id", "serviceName", 1.1, list);
        assertEquals("getID method failed", "id", service.getID());
    }

    @Test
    public void getServiceName(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Service service = new Service("id", "serviceName", 1.1, list);
        assertEquals("getServiceName method failed", "serviceName", service.getServiceName());
    }

    @Test
    public void getHourlyRate(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Service service = new Service("id", "serviceName", 1.1, list);
        assertEquals(1.1, service.getHourlyRate(), 0.001);
    }

    @Test
    public void getCostumerInfoForm(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Service service = new Service("id", "serviceName", 1.1, list);
        assertArrayEquals("getCostumerInfoForm method failed", list.toArray(), service.getCostumerInfoForm().toArray());
    }
}
