package com.example.deliverable1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

public class UnitTest10 {

    // Class Register Test Cases
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void testNameIsValid(){

        Register register = new Register();

        boolean actual1 = register.nameIsValid("Arshadi");
        assertTrue("nameIsValid method failed", actual1);

        boolean actual2 = register.nameIsValid("Amin");
        assertTrue("nameIsValid method failed", actual2);
    }

    @Test
    public void testPasswordIsValid(){

        Register register = new Register();

        boolean actual1 = register.passwordIsValid("012345");
        assertTrue("passwordIsValid method failed", actual1);

        boolean actual2 = register.passwordIsValid("1234567890");
        assertTrue("passwordIsValid method failed", actual2);
    }
}
