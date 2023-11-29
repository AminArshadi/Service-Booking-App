package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // Instance variables
    DatabaseReference reff, reff2;
    EditText editText1, editText2, editText3, editText4;
    List<HashMap> accounts;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Type Spinner
        Spinner spinner = findViewById(R.id.Account_Types);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //
        reff = FirebaseDatabase.getInstance().getReference("accounts");
        reff2 = FirebaseDatabase.getInstance().getReference("branch profiles");

        accounts = new ArrayList<>();
    }

    ///// Spinner /////
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if ( text.equals("Employee") ){
            MainActivity.role = AccountTypes.EMPLOYEE;
        }
        else {
            MainActivity.role = AccountTypes.CUSTOMER;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
    //////////////////

    @Override
    protected void onStart() {
        super.onStart();
        // attaching value event listener
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clearing the previous artist list
                accounts.clear();
                //iterating through all the nodes
                for ( DataSnapshot accountSnapshot : snapshot.getChildren() ) {
                    //getting product
                    //Account account = accountSnapshot.getValue(Account.class);
                    //adding product to the list
                    accounts.add( (HashMap)accountSnapshot.getValue() );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void onRegisterClick(View view) {
        openRegister();
    }

    public boolean openRegister() {
        //getting the values from EditTexts
        editText1 = (EditText) findViewById(R.id.editTextTextPersonName);
        editText2 = (EditText) findViewById(R.id.editTextTextPersonName2);
        editText3 = (EditText) findViewById(R.id.Username_Field);
        editText4 = (EditText) findViewById(R.id.Password_Field);

        String firstName = editText1.getText().toString();
        String lastName = editText2.getText().toString();
        String username = editText3.getText().toString();
        String password = editText4.getText().toString();

        // check if the account already exists
        for (HashMap account : accounts) {
            if ( account.get("username").equals(username) ) {
                Toast.makeText(Register.this, "This username already exists", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        addAccount( firstName, lastName, username, password);
        return true;
    }

    private void addAccount(String firstName, String lastName, String username, String password) {
        if ( nameIsValid(firstName) && nameIsValid(lastName) && nameIsValid(username) && passwordIsValid(password) ) {
            //getting a unique id
            String id = reff.push().getKey();
            //creating a new product with the given info
            Account account = new Account(id, firstName, lastName, username, password, MainActivity.role);
            //saving the product into the database
            reff.child(id).setValue(account);
            // adding a unique branch profile upon creating the employee
            if ( MainActivity.role.toString().equals("EMPLOYEE") ) {
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("");

                String id2 = reff2.push().getKey();
                BranchProfile branchProfile = new BranchProfile(id2, username, "", 0, "",  tmp);
                reff2.child(id2).setValue(branchProfile);
            }
            //

            Toast.makeText(Register.this, "Account created successfully", Toast.LENGTH_LONG).show();

            // going back to the login page after creating the new account
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }
    }

    //******************************************************** Helper Methods *****
    protected boolean nameIsValid(String input){
        if (input.length() == 0){
            Toast.makeText(Register.this, "Invalid input: one of the boxes is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        String[] splitted = input.split(" ");

        if ( (splitted.length != 1) ){
            Toast.makeText(Register.this, "Invalid password: your username cannot contain spaces", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    protected boolean passwordIsValid(String input){
        if (input.length() == 0){
            Toast.makeText(Register.this, "Invalid input: one or more of the boxes is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if (input.length() < 5){
            Toast.makeText(Register.this, "Invalid password: your password should contain at least 5 characters ", Toast.LENGTH_LONG).show();
            return false;
        }

        String[] splitted = input.split(" ");

        if ( (splitted.length != 1) ){
            Toast.makeText(Register.this, "Invalid password: your password cannot contain spaces", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    //******************************************************************************
}
