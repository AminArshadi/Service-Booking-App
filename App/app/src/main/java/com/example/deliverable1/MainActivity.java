package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // Instance variables
    protected static AccountTypes role;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /// Buttons
    public void adminLog(View view) {
        role = AccountTypes.ADMIN;
        openLogin();
    }

    public void employeeLog(View view) {
        role = AccountTypes.EMPLOYEE;
        openLogin();
    }

    public void costumerLog(View view) {
        role = AccountTypes.CUSTOMER;
        openLogin();
    }
    ///

    //// Action after clicking on buttons
    public void openLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    ////
}
