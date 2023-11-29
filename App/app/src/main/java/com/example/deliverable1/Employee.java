package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee extends AppCompatActivity {

    DatabaseReference reff1, reff2, reff3;
    List<HashMap> services, branchProfiles, serviceRequests;
    private Dialog dialog;
    private EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        // welcoming the employee
        TextView intro = (TextView) findViewById(R.id.customer_page_intro);
        intro.setText("Welcome, " + Login.logInAccountInfo.get("firstName") + "!");
        //

        reff1 = FirebaseDatabase.getInstance().getReference("services");
        reff2 = FirebaseDatabase.getInstance().getReference("branch profiles");
        reff3 = FirebaseDatabase.getInstance().getReference("service requests");

        services = new ArrayList<>();
        branchProfiles = new ArrayList<>();
        serviceRequests = new ArrayList<>();

        dialog = new Dialog(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        reff1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services.clear();
                for (DataSnapshot accountSnapshot : snapshot.getChildren()) {
                    services.add((HashMap) accountSnapshot.getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchProfiles.clear();
                for (DataSnapshot accountSnapshot : snapshot.getChildren()) {
                    branchProfiles.add((HashMap) accountSnapshot.getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        reff3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceRequests.clear();
                for (DataSnapshot accountSnapshot : snapshot.getChildren()) {
                    serviceRequests.add((HashMap) accountSnapshot.getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //Profile's buttons///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onEditBranchProfileClick(View view) {
        dialog.setContentView(R.layout.edit_branch_profile_popup);
        dialog.show();

        String address = "";
        String phoneNumber = "";
        String workingHours = "";
        for (HashMap branchProfile : branchProfiles) {
            if (Login.logInAccountInfo.get("username").toString().equals(branchProfile.get("username").toString())) {
                address = branchProfile.get("address").toString();
                phoneNumber = branchProfile.get("phoneNumber").toString();
                workingHours = branchProfile.get("workingHours").toString();
            }
        }

        if (address.length() != 0) {
            editText1 = (EditText) dialog.findViewById(R.id.unitNumberEditText);
            editText2 = (EditText) dialog.findViewById(R.id.buildingNumberEditText);
            editText3 = (EditText) dialog.findViewById(R.id.streetNameEditText);
            editText4 = (EditText) dialog.findViewById(R.id.postalCodeEditText);

            editText1.setText(address.split("-")[0]);
            editText2.setText(address.split("-")[1].split(" ")[0]);
            String tmp = address.split("-")[1].split(" ")[1] + " " + address.split("-")[1].split(" ")[2];
            editText3.setText(tmp);
            editText4.setText(address.split("-")[2]);
        }

        if (!phoneNumber.equals("0")) {
            editText5 = (EditText) dialog.findViewById(R.id.phoneNumberEditText);

            editText5.setText(phoneNumber);
        }

        if (workingHours.length() != 0) {
            editText6 = (EditText) dialog.findViewById(R.id.FromEditText);
            editText7 = (EditText) dialog.findViewById(R.id.ToEditText);

            editText6.setText(workingHours.split("-")[0]);
            editText7.setText(workingHours.split("-")[1]);
        }
    }

    public void onUpdateProfileClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.unitNumberEditText);
        editText2 = (EditText) dialog.findViewById(R.id.buildingNumberEditText);
        editText3 = (EditText) dialog.findViewById(R.id.streetNameEditText);
        editText4 = (EditText) dialog.findViewById(R.id.postalCodeEditText);
        editText5 = (EditText) dialog.findViewById(R.id.phoneNumberEditText);
        editText6 = (EditText) dialog.findViewById(R.id.FromEditText);
        editText7 = (EditText) dialog.findViewById(R.id.ToEditText);
        String unitNumber = editText1.getText().toString();
        String buildingNumber = editText2.getText().toString();
        String streetName = editText3.getText().toString();
        String postalCode = editText4.getText().toString();
        String phoneNumber = editText5.getText().toString();
        String from = editText6.getText().toString();
        String to = editText7.getText().toString();

        String id = "";
        for (HashMap branchProfile : branchProfiles) {
            if ( Login.logInAccountInfo.get("username").toString().equals(branchProfile.get("username").toString()) ) {
                id = branchProfile.get("id").toString();
                break;
            }
        }

        Pattern VALID_unitNumber = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
        Pattern VALID_buildingNumber = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
        Pattern VALID_streetName = Pattern.compile("^[A-Za-z ]+[ ][A-Za-z ]+$", Pattern.CASE_INSENSITIVE);
        Pattern VALID_postalCode = Pattern.compile("^[A-Z][0-9][A-Z][0-9][A-Z][0-9]$", Pattern.CASE_INSENSITIVE);
        Pattern VALID_phoneNumber = Pattern.compile("^[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$", Pattern.CASE_INSENSITIVE);
        Pattern VALID_from = Pattern.compile("^[012][0-9][:][0-5][0-9]$", Pattern.CASE_INSENSITIVE);
        Pattern VALID_to = Pattern.compile("^[012][0-9][:][0-5][0-9]$", Pattern.CASE_INSENSITIVE);

        Matcher matcher1 = VALID_unitNumber.matcher(unitNumber.trim());
        Matcher matcher2 = VALID_buildingNumber.matcher(buildingNumber.trim());
        Matcher matcher3 = VALID_streetName.matcher(streetName.trim());
        Matcher matcher4 = VALID_postalCode.matcher(postalCode.trim());
        Matcher matcher5 = VALID_phoneNumber.matcher(phoneNumber.trim());
        Matcher matcher6 = VALID_from.matcher(from.trim());
        Matcher matcher7 = VALID_to.matcher(to.trim());

        if ( matcher1.find() && matcher2.find() && matcher3.find() && matcher4.find() )
            reff2.child(id).child("address").setValue( unitNumber + "-" + buildingNumber + " " + streetName + "-" + postalCode );
        else{
            Toast.makeText(Employee.this, "Invalid address", Toast.LENGTH_LONG).show();
            return;
        }

        if ( matcher5.find() )
            reff2.child(id).child("phoneNumber").setValue( phoneNumber );
        else{
            Toast.makeText(Employee.this, "Invalid phone number", Toast.LENGTH_LONG).show();
            return;
        }

        if (matcher6.find() && matcher7.find()) {

            if (Double.parseDouble(from.split(":")[0]) > 24 || Double.parseDouble(to.split(":")[0]) > 24) {
                Toast.makeText(Employee.this, "Invalid working hours", Toast.LENGTH_LONG).show();
                return;
            }
            if ( Double.parseDouble(from.split(":")[0]) > Double.parseDouble(to.split(":")[0]) ) {
                Toast.makeText(Employee.this, "Invalid working hours", Toast.LENGTH_LONG).show();
                return;
            }
            // when the hour part of from and to are the same and the minute part of the two is not valid
            if ( Double.parseDouble(from.split(":")[0]) == Double.parseDouble(to.split(":")[0]) && Double.parseDouble(from.split(":")[1]) > Double.parseDouble(to.split(":")[1]) ) {
                Toast.makeText(Employee.this, "Invalid working hours", Toast.LENGTH_LONG).show();
                return;
            }
            //

            reff2.child(id).child("workingHours").setValue( from + "-" + to );
        }

        else{
            Toast.makeText(Employee.this, "Invalid working hours", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(Employee.this, "Profile updated", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Services' buttons///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onViewServiceClick(View view) {
        dialog.setContentView(R.layout.veiw_service_popup);
        dialog.show();

        TextView textView = (TextView) dialog.findViewById(R.id.popupListTextView);
        textView.setText("List of Services:");

        listView = (ListView) dialog.findViewById(R.id.veiwServiceListVeiw);
        ArrayList<String> arrayList = new ArrayList<>();

        for (HashMap service : services) {
            arrayList.add("\n\nService Name: \n" + service.get("serviceName").toString()
                    + "\nHourly Rate: \n" + "$" + service.get("hourlyRate").toString()
                    + "\nCostumer Information Form: \n" + service.get("costumerInfoForm").toString() );
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public void onViewBranchServiceClick(View view) {

        dialog.setContentView(R.layout.veiw_service_popup);
        dialog.show();

        TextView textView = (TextView) dialog.findViewById(R.id.popupListTextView);
        textView.setText("List of Services:");

        listView = (ListView) dialog.findViewById(R.id.veiwServiceListVeiw);
        ArrayList<String> arrayList = new ArrayList<>();

        ArrayList<String> branchServices = new ArrayList<>();
        for (HashMap ch : branchProfiles) {

            if ( Login.logInAccountInfo.get("username").toString().equals(ch.get("username").toString()) ){
                branchServices = (ArrayList<String>) ch.get("branchServices");
            }
        }

        for (String branchService : branchServices) {
            for (HashMap service : services) {
                if( service.get("serviceName").toString().equals(branchService) ) {
                    arrayList.add("\n\nService Name: \n" + service.get("serviceName").toString()
                            + "\nHourly Rate: \n" + "$" + service.get("hourlyRate").toString()
                            + "\nCostumer Information Form: \n" + service.get("costumerInfoForm").toString() );
                }
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public void onAddServiceToBranchClick(View view) {
        dialog.setContentView(R.layout.add_service_to_branch_popup);
        dialog.show();
    }

    public void onAddSeviceClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.addServiceNameEditText2);
        String serviceName = editText1.getText().toString();

        boolean flag = true;

        // validating the inputs
        if (serviceName.length() == 0){
            Toast.makeText(Employee.this, "Please fill out all the boxes", Toast.LENGTH_LONG).show();
            return;
        }
        //

        // checking if the service has been created by admin
        for (HashMap service : services) {

            if ( service.get("serviceName").equals(serviceName) ) {
                flag = false;
            }
        }
        if (flag){
            Toast.makeText(Employee.this, "Service does not exist", Toast.LENGTH_LONG).show();
            return;
        }
        //

        // finding the current employee's branch profile and getting its branchServices list
        HashMap branchProfile = new HashMap();
        ArrayList<String> branchServices = new ArrayList<>();
        for (HashMap ch : branchProfiles) {

            if ( Login.logInAccountInfo.get("username").toString().equals(ch.get("username").toString()) ){
                branchProfile = ch;
                branchServices = (ArrayList<String>) ch.get("branchServices");
            }
        }
        //

        ArrayList<String> newBranchServices;

        if ( branchServices.get(0).equals("") && branchServices.toArray().length == 1 ) {
            newBranchServices = new ArrayList<>(1);
            newBranchServices.add(serviceName);
        }
        else{
            newBranchServices = new ArrayList<>( branchServices.toArray().length + 1 );
            for (String branchService : branchServices) {
                // checking if the branch already has the service to be added
                if ( branchService.equals(serviceName) ) {
                    Toast.makeText(Employee.this, "Service already exist", Toast.LENGTH_LONG).show();
                    return;
                }//
                newBranchServices.add(branchService);
            }
            newBranchServices.add(serviceName);
        }
        String id = branchProfile.get("id").toString();
        reff2.child(id).child("branchServices").setValue( newBranchServices );

        Toast.makeText(Employee.this, "Service added successfully", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }

    public void onDeleteSeviceClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.addServiceNameEditText2);
        String serviceName = editText1.getText().toString();

        boolean flag = true;

        // validating the inputs
        if (serviceName.length() == 0){
            Toast.makeText(Employee.this, "Please fill out all the boxes", Toast.LENGTH_LONG).show();
            return;
        }
        //

        // checking if the service has been created by admin
        for (HashMap service : services) {
            if ( service.get("serviceName").equals(serviceName) ) {
                flag = false;
            }
        }
        if (flag){
            Toast.makeText(Employee.this, "Service does not exist", Toast.LENGTH_LONG).show();
            return;
        }
        //

        // finding the current employee's branch profile and getting its branchServices list
        HashMap branchProfile = new HashMap();
        ArrayList<String> branchServices = new ArrayList<>();
        for (HashMap ch : branchProfiles) {
            if ( Login.logInAccountInfo.get("username").toString().equals(ch.get("username").toString()) ){
                branchProfile = ch;
                branchServices = (ArrayList<String>) ch.get("branchServices");
            }
        }
        //

        ArrayList<String> newBranchServices;

        if ( (!branchServices.get(0).equals("")) && (branchServices.toArray().length == 1) ) {
            newBranchServices = new ArrayList<>(1);
            newBranchServices.add("");
        }
        else if ( branchServices.get(0).equals("") && branchServices.toArray().length == 1 ) {
            Toast.makeText(Employee.this, "Branch does not offer any service currently. Add a service before attempting to delete", Toast.LENGTH_LONG).show();
            return;
        }

        else{
            newBranchServices = new ArrayList<>( branchServices.toArray().length - 1 );
            for (String branchService : branchServices) {
                if ( ! branchService.equals(serviceName) ) {
                    try{
                        newBranchServices.add(branchService);
                    }
                    catch (Exception e){
                        Toast.makeText(Employee.this, "Service does not exist", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        }

        String id = branchProfile.get("id").toString();
        reff2.child(id).child("branchServices").setValue( newBranchServices );

        Toast.makeText(Employee.this, "Service deleted successfully", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Service requests' buttons///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onViewServiceRequestsClick(View view) {
        dialog.setContentView(R.layout.veiw_service_popup);
        dialog.show();

        TextView textView = (TextView) dialog.findViewById(R.id.popupListTextView);
        textView.setText("List of Requests:");

        listView = (ListView) dialog.findViewById(R.id.veiwServiceListVeiw);
        ArrayList<String> arrayList = new ArrayList<>();

        for (HashMap serviceRequest : serviceRequests) {
            if ( serviceRequest.get("branch").equals(Login.logInAccountInfo.get("username")) && serviceRequest.get("accepted").toString().equals("REVIEW") ) {
                arrayList.add("\n\nCostumer's username: \n" + serviceRequest.get("costumer").toString()
                            + "\nService: \n" + serviceRequest.get("service").toString()
                            + "\nCostumer Information Form: \n" + serviceRequest.get("costumerInfoForm").toString() );
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public void onAcceptDeclineServiceRequest(View view) {
        dialog.setContentView(R.layout.accept_decline_service_request_popup);
        dialog.show();
    }

    public void onAcceptServiceRequestClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.costumerUsernameEditText);
        editText2 = (EditText) dialog.findViewById(R.id.addServiceNameEditText2);
        String costumerUsername = editText1.getText().toString();
        String serviceName = editText2.getText().toString();

        // validating the inputs
        if (costumerUsername.length() == 0 || serviceName.length() == 0){
            Toast.makeText(Employee.this, "Please fill out the boxes", Toast.LENGTH_LONG).show();
            return;
        }
        //

        for (HashMap serviceRequest : serviceRequests) {
            if ( serviceRequest.get("branch").equals(Login.logInAccountInfo.get("username")) && serviceRequest.get("costumer").toString().equals(costumerUsername) && serviceRequest.get("service").toString().equals(serviceName) && serviceRequest.get("accepted").toString().equals("REVIEW") ) {
                String id = serviceRequest.get("id").toString();
                reff3.child(id).child("accepted").setValue( RequestCondition.ACCEPTED );

                Toast.makeText(Employee.this, "Service request accepted", Toast.LENGTH_LONG).show();

                dialog.dismiss();
                return;
            }
        }
        Toast.makeText(Employee.this, "Service request does not exist", Toast.LENGTH_LONG).show();
    }

    public void onDeclineServiceRequestClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.costumerUsernameEditText);
        editText2 = (EditText) dialog.findViewById(R.id.addServiceNameEditText2);
        String costumerUsername = editText1.getText().toString();
        String serviceName = editText2.getText().toString();

        // validating the inputs
        if (costumerUsername.length() == 0 || serviceName.length() == 0){
            Toast.makeText(Employee.this, "Please fill out the boxes", Toast.LENGTH_LONG).show();
            return;
        }
        //

        for (HashMap serviceRequest : serviceRequests) {
            if ( serviceRequest.get("branch").equals(Login.logInAccountInfo.get("username")) && serviceRequest.get("costumer").toString().equals(costumerUsername) && serviceRequest.get("service").toString().equals(serviceName) && serviceRequest.get("accepted").toString().equals("REVIEW") ) {
                String id = serviceRequest.get("id").toString();

                reff3.child(id).child("accepted").setValue( RequestCondition.DECLINED );

                Toast.makeText(Employee.this, "Service request declined", Toast.LENGTH_LONG).show();

                dialog.dismiss();
                return;
            }
        }
        Toast.makeText(Employee.this, "Service request does not exist", Toast.LENGTH_LONG).show();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
