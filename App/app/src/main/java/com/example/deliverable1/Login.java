package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Login extends AppCompatActivity {
    DatabaseReference reff;
    protected static HashMap logInAccountInfo;
    EditText editText1, editText2;
    List<HashMap> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reff = FirebaseDatabase.getInstance().getReference("accounts");

        accounts = new ArrayList<>();
    }

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

    public void onLoginClick(View view){
        editText1 = (EditText) findViewById(R.id.text_user);
        editText2 = (EditText) findViewById(R.id.text_pass);

        String username = editText1.getText().toString();
        String password = editText2.getText().toString();

        boolean flag = true;

        if ( nameIsValid(username) && passwordIsValid(password) ) {
            for (HashMap account : accounts) {
                if ( (account.get("username").equals(username)) && (account.get("password").equals(password)) && (MainActivity.role.toString().equals(account.get("type"))) ) {

                    if (MainActivity.role.toString().equals("ADMIN")) {
                        logInAccountInfo = account;
                        flag = false;
                        Intent intent = new Intent(getApplicationContext(), Admin.class);
                        startActivity(intent);
                    }
                    else if (MainActivity.role.toString().equals("EMPLOYEE")) {
                        logInAccountInfo = account;
                        flag = false;
                        Intent intent = new Intent(getApplicationContext(), Employee.class);
                        startActivity(intent);
                    }
                    else {
                        logInAccountInfo = account;
                        flag = false;
                        Intent intent = new Intent(getApplicationContext(), Costumer.class);
                        startActivity(intent);
                    }
                }
            }
            if (flag)
                Toast.makeText(Login.this, "Username, password or selected role is not correct. Please try again or register!", Toast.LENGTH_LONG).show();
        }
    }

    public void onRegisterClick(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    //******************************************************** Helper Methods *****
    protected boolean nameIsValid(String input){
        if (input.length() == 0){
            Toast.makeText(Login.this, "Invalid input: one of the boxes is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        String[] splitted = input.split(" ");

        if ( (splitted.length != 1) ){
            Toast.makeText(Login.this, "Invalid password: your username cannot contain spaces", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    protected boolean passwordIsValid(String input){
        if (input.length() == 0){
            Toast.makeText(Login.this, "Invalid input: one or more of the boxes is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if (input.length() < 5){
            Toast.makeText(Login.this, "Invalid password: your password should contain at least 5 characters ", Toast.LENGTH_LONG).show();
            return false;
        }

        String[] splitted = input.split(" ");

        if ( (splitted.length != 1) ){
            Toast.makeText(Login.this, "Invalid password: your password cannot contain spaces", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    //******************************************************************************
}
