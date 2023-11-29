package com.example.deliverable1;

import org.junit.Test;
import static org.junit.Assert.*;

public class UnitTest3 {

    // Class Account Test Cases
    @Test
    public void testGetID(){
        Account account = new Account("id", "firstName", "lastName", "username", "password", AccountTypes.ADMIN);
        assertEquals("getID method failed", "id", account.getID());
    }

    @Test
    public void testGetFirstName(){
        Account account = new Account("id", "firstName", "lastName", "username", "password", AccountTypes.ADMIN);
        assertEquals("getFirstName method failed", "firstName", account.getFirstName());
    }

    @Test
    public void testGetLastName(){
        Account account = new Account("id", "firstName", "lastName", "username", "password", AccountTypes.ADMIN);
        assertEquals("getLastName method failed", "lastName", account.getLastName());
    }

    @Test
    public void testGetUsername(){
        Account account = new Account("id", "firstName", "lastName", "username", "password", AccountTypes.ADMIN);
        assertEquals("getUsername method failed", "username", account.getUsername());
    }

    @Test
    public void testGetPassword(){
        Account account = new Account("id", "firstName", "lastName", "username", "password", AccountTypes.ADMIN);
        assertEquals("getPassword method failed", "password", account.getPassword());
    }

    @Test
    public void testGetType(){
        Account account = new Account("id", "firstName", "lastName", "username", "password", AccountTypes.ADMIN);
        assertEquals("getType method failed", AccountTypes.ADMIN, account.getType());
    }
}