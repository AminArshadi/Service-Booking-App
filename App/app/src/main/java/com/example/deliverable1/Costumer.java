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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Costumer extends AppCompatActivity {
    DatabaseReference reff1, reff2, reff3, reff4;
    List<HashMap> services, branchProfiles, serviceRequests, rates;
    private Dialog dialog;
    private EditText editText1, editText2, editText3, editText4;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costumer);

        // welcoming the customer
        TextView intro = (TextView) findViewById(R.id.customer_page_intro);
        intro.setText("Welcome, " + Login.logInAccountInfo.get("firstName") + "!");
        //

        reff1 = FirebaseDatabase.getInstance().getReference("services");
        reff2 = FirebaseDatabase.getInstance().getReference("branch profiles");
        reff3 = FirebaseDatabase.getInstance().getReference("service requests");
        reff4 = FirebaseDatabase.getInstance().getReference("rates");

        services = new ArrayList<>();
        branchProfiles = new ArrayList<>();
        serviceRequests = new ArrayList<>();
        rates = new ArrayList<>();

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
        reff4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rates.clear();
                for (DataSnapshot accountSnapshot : snapshot.getChildren()) {
                    rates.add((HashMap) accountSnapshot.getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //Notifications' button///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onViewNotificationsClick(View view) {
        dialog.setContentView(R.layout.veiw_service_popup);
        dialog.show();

        TextView textView = (TextView) dialog.findViewById(R.id.popupListTextView);
        textView.setText("Notifications:");

        listView = (ListView) dialog.findViewById(R.id.veiwServiceListVeiw);
        ArrayList<String> arrayList = new ArrayList<>();

        for (HashMap serviceRequest : serviceRequests) {
            if ( serviceRequest.get("costumer").toString().equals(Login.logInAccountInfo.get("username")) && serviceRequest.get("accepted").toString().equals("ACCEPTED") ){
                arrayList.add("\n\nGood news! Your request for " + serviceRequest.get("service").toString() + " has been accepted by " + serviceRequest.get("branch").toString() + " branch.");
                // deleting the service requests after notifying the costumer
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("service requests").child(serviceRequest.get("id").toString());
                dR.removeValue();
                //
            }
            else if ( serviceRequest.get("costumer").toString().equals(Login.logInAccountInfo.get("username")) && serviceRequest.get("accepted").toString().equals("DECLINED") ){
                arrayList.add("\n\nUnfortunately, your request for " + serviceRequest.get("service").toString() + " has been declined by " + serviceRequest.get("branch").toString() + " branch.");
                // deleting the service requests after notifying the costumer
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("service requests").child(serviceRequest.get("id").toString());
                dR.removeValue();
                //
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Branches' button///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onViewAllBranches(View view) {
        dialog.setContentView(R.layout.veiw_service_popup);
        dialog.show();

        TextView textView = (TextView) dialog.findViewById(R.id.popupListTextView);
        textView.setText("List of Branches:");

        listView = (ListView) dialog.findViewById(R.id.veiwServiceListVeiw);
        ArrayList<String> arrayList = new ArrayList<>();

        for (HashMap branchProfile : branchProfiles) {
            int totalRate = 0;
            int counter = 0;
            for (HashMap rate : rates) {
                if ( branchProfile.get("username").toString().equals(rate.get("branchUsername")) ) {
                    totalRate += (Long)rate.get("rate");
                    counter++;
                }
            }
            arrayList.add("\n\nBranch Name: \n" + branchProfile.get("username").toString()
                    + "\nAddress: \n" + branchProfile.get("address").toString()
                    + "\nPhone Number: \n" + branchProfile.get("phoneNumber").toString()
                    + "\nWorking Hours: \n" + branchProfile.get("workingHours").toString()
                    + "\nBranch Services: \n" + branchProfile.get("branchServices").toString()
                    + "\nBranch Rate: \n" + totalRate / (double)counter);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public void onSearchForBranchClick(View view) {
        dialog.setContentView(R.layout.customer_search_for_branch_popup);
        dialog.show();
    }

    public void onSearchClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.branchAddressEditText);
        editText2 = (EditText) dialog.findViewById(R.id.fromEditText);
        editText3 = (EditText) dialog.findViewById(R.id.toEditText);
        editText4 = (EditText) dialog.findViewById(R.id.typeOfServiceEditText);
        String address = editText1.getText().toString();
        String from = editText2.getText().toString();
        String to = editText3.getText().toString();
        String typeOfService = editText4.getText().toString();

        listView = (ListView) dialog.findViewById(R.id.veiwSearchResult);
        ArrayList<String> arrayList = new ArrayList<>();

        Pattern VALID_address = Pattern.compile("^[0-9]+[-][0-9]+[ ][A-Za-z]+[ ][A-Za-z]+[-][A-Z][0-9][A-Z][0-9][A-Z][0-9]$", Pattern.CASE_INSENSITIVE);
        Pattern VALID_time = Pattern.compile("^[012][0-9][:][0-5][0-9]$", Pattern.CASE_INSENSITIVE);
        Pattern VALID_typeOfService = Pattern.compile("^[A-Za-z0-9 ]+$", Pattern.CASE_INSENSITIVE);

        Matcher matcher1 = VALID_address.matcher(address.trim());
        Matcher matcher2 = VALID_time.matcher(from.trim());
        Matcher matcher3 = VALID_time.matcher(to.trim());
        Matcher matcher4 = VALID_typeOfService.matcher(typeOfService.trim());

        // validating input
        if ( address.length() == 0 && (from.length() == 0 || to.length() == 0) && typeOfService.length() == 0 ) {
            Toast.makeText(Costumer.this, "Provide enough information before searching", Toast.LENGTH_LONG).show();
            return;
        }

        if ( (from.length() != 0 && to.length() == 0) || (from.length() == 0 && to.length() != 0) ) {
            Toast.makeText(Costumer.this, "Invalid working hours", Toast.LENGTH_LONG).show();
            return;
        }

        if ( address.length() != 0 && !matcher1.find() ) {
            Toast.makeText(Costumer.this, "Invalid address", Toast.LENGTH_LONG).show();
            return;
        }

        if ( from.length() != 0 && !matcher2.find() ) {
            Toast.makeText(Costumer.this, "Invalid working hours", Toast.LENGTH_LONG).show();
            return;
        }

        if ( to.length() != 0 && !matcher3.find() ) {
            Toast.makeText(Costumer.this, "Invalid working hours", Toast.LENGTH_LONG).show();
            return;
        }

        if ( typeOfService.length() != 0 && !matcher4.find() ) {
            Toast.makeText(Costumer.this, "Invalid service type", Toast.LENGTH_LONG).show();
            return;
        }
        //

        boolean flag = true;

        for (HashMap branchProfile : branchProfiles) {
            if ( address.length() != 0 && branchProfile.get("address").toString().equals(address) ) {
                flag = false;
                int totalRate = 0;
                int counter = 0;
                for (HashMap rate : rates) {
                    if ( branchProfile.get("username").toString().equals(rate.get("branchUsername")) ) {
                        totalRate += (Long)rate.get("rate");
                        counter++;
                    }
                }
                arrayList.add("\n\nBranch Name: \n" + branchProfile.get("username").toString()
                        + "\nAddress: \n" + branchProfile.get("address").toString()
                        + "\nPhone Number: \n" + branchProfile.get("phoneNumber").toString()
                        + "\nWorking Hours: \n" + branchProfile.get("workingHours").toString()
                        + "\nBranch Services: \n" + branchProfile.get("branchServices").toString()
                        + "\nBranch Rate: \n" + totalRate / (double)counter);
            }
            else if ( from.length() != 0 && to.length() != 0 && branchProfile.get("workingHours").toString().equals(from + "-" + to) ) {
                flag = false;
                int totalRate = 0;
                int counter = 0;
                for (HashMap rate : rates) {
                    if ( branchProfile.get("username").toString().equals(rate.get("branchUsername")) ) {
                        totalRate += (Long)rate.get("rate");
                        counter++;
                    }
                }
                arrayList.add("\n\nBranch Name: \n" + branchProfile.get("username").toString()
                        + "\nAddress: \n" + branchProfile.get("address").toString()
                        + "\nPhone Number: \n" + branchProfile.get("phoneNumber").toString()
                        + "\nWorking Hours: \n" + branchProfile.get("workingHours").toString()
                        + "\nBranch Services: \n" + branchProfile.get("branchServices").toString()
                        + "\nBranch Rate: \n" + totalRate / (double)counter);
            }
            else if ( typeOfService.length() != 0 ) {
                for (String service : (ArrayList<String>)branchProfile.get("branchServices")){
                    if (service.equals(typeOfService)){
                        flag = false;
                        int totalRate = 0;
                        int counter = 0;
                        for (HashMap rate : rates) {
                            if ( branchProfile.get("username").toString().equals(rate.get("branchUsername")) ) {
                                totalRate += (Long)rate.get("rate");
                                counter++;
                            }
                        }
                        arrayList.add("\n\nBranch Name: \n" + branchProfile.get("username").toString()
                                    + "\nAddress: \n" + branchProfile.get("address").toString()
                                    + "\nPhone Number: \n" + branchProfile.get("phoneNumber").toString()
                                    + "\nWorking Hours: \n" + branchProfile.get("workingHours").toString()
                                    + "\nBranch Services: \n" + branchProfile.get("branchServices").toString()
                                    + "\nBranch Rate: \n" + totalRate / (double)counter);
                    }
                }
            }
            if (flag)
                Toast.makeText(Costumer.this, "No result found", Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //service request's button///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onSubmitServiceRequestClick(View view) {
        dialog.setContentView(R.layout.submit_service_request_popup);
        dialog.show();
    }

    public void onInfoFormEditTextClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.serviceNameEditText);
        String serviceName = editText1.getText().toString();
        TextView textView = (TextView) dialog.findViewById(R.id.infoFormTextView);

        for (HashMap service : services) {
            if ( service.get("serviceName").toString().equals(serviceName) ) {
                textView.setText( service.get("costumerInfoForm").toString() );
                return;
            }
        }
        textView.setText("Entered Service name is invalid.");
    }

    public void onSubmitClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.branchUsernameEditText);
        editText2 = (EditText) dialog.findViewById(R.id.serviceNameEditText);
        editText3 = (EditText) dialog.findViewById(R.id.infoFormEditText);
        String branchName = editText1.getText().toString();
        String serviceName = editText2.getText().toString();
        String infoForm = editText3.getText().toString();

        // validating input
        if ( branchName.length() == 0 || serviceName.length() == 0 || infoForm.length() == 0 ) {
            Toast.makeText(Costumer.this, "Fill out all the boxes", Toast.LENGTH_LONG).show();
            return;
        }

        boolean isBranchNameValid = false;
        boolean doesBranchOfferService = false;
        boolean isServiceNameValid = false;

        for (HashMap branchProfile : branchProfiles) {
            if ( branchProfile.get("username").toString().equals(branchName) ) {
                isBranchNameValid = true;
                ArrayList<String> branchServices = (ArrayList<String>) branchProfile.get("branchServices");

                for (int i=0; i < branchServices.size(); i++) {
                    if ( branchServices.get(i).equals(serviceName) ) {
                        doesBranchOfferService = true;
                    }
                }
                break;
            }
        }
        if (!isBranchNameValid) {
            Toast.makeText(Costumer.this, "Branch does not exist", Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<String> savedInfoForm = new ArrayList<>();

        for (HashMap service : services) {
            if ( service.get("serviceName").toString().equals(serviceName) ) {
                savedInfoForm = (ArrayList<String>) service.get("costumerInfoForm");
                isServiceNameValid = true;
                break;
            }
        }

        if (!isServiceNameValid) {
            Toast.makeText(Costumer.this, "Service does not exist", Toast.LENGTH_LONG).show();
            return;
        }

        if (!doesBranchOfferService) {
            Toast.makeText(Costumer.this, "Branch does not offer the specified service", Toast.LENGTH_LONG).show();
            return;
        }

        String[] splittedInfoForm = infoForm.split(",");

        if (savedInfoForm.size() != splittedInfoForm.length) {
            Toast.makeText(Costumer.this, "Provide all the required information of the form", Toast.LENGTH_LONG).show();
            return;
        }
        //

        ArrayList<String> tmp = new ArrayList<>();
        Collections.addAll(tmp, splittedInfoForm);

        for (HashMap serviceRequest : serviceRequests) {
            if ( serviceRequest.get("costumer").toString().equals(Login.logInAccountInfo.get("username").toString()) && serviceRequest.get("branch").toString().equals(branchName) && serviceRequest.get("service").toString().equals(serviceName) && serviceRequest.get("accepted").toString().equals("REVIEW") ) {
                // when the costumer tries to submit the exact same request
                if ( serviceRequest.get("costumerInfoForm").toString().equals(tmp.toString()) ) {
                    Toast.makeText(Costumer.this, "You have already submitted this request. Please check your notifications for updates.", Toast.LENGTH_LONG).show();
                    return;
                }
                //
                // when the costumer tries to submit the same request with a different information form
                reff3.child(serviceRequest.get("id").toString()).child("costumerInfoForm").setValue(tmp);
                Toast.makeText(Costumer.this, "Your information form is successfully updated for this request.", Toast.LENGTH_LONG).show();
                return;
                //
            }
        }
        String id = reff3.push().getKey();
        ServiceRequest serviceRequest = new ServiceRequest(id, Login.logInAccountInfo.get("username").toString(), branchName, serviceName, tmp, RequestCondition.REVIEW);
        reff3.child(id).setValue(serviceRequest);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Rate' button///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onRateButton(View view) {
        dialog.setContentView(R.layout.rate_popup);
        dialog.show();
    }

    public void onOneClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.reviewEditText);
        editText2 = (EditText) dialog.findViewById(R.id.branchNameEditText);
        String review = editText1.getText().toString();
        String branchName = editText2.getText().toString();

        boolean isBranchNameValid = false;

        for (HashMap branchProfile : branchProfiles) {
            if ( branchProfile.get("username").toString().equals(branchName) ) {
                isBranchNameValid = true;
                break;
            }
        }

        if (!isBranchNameValid) {
            Toast.makeText(Costumer.this, "Branch does not exist", Toast.LENGTH_LONG).show();
            return;
        }

        for (HashMap rate : rates) {
            if ( rate.get("branchUsername").equals(branchName) && rate.get("costumerUsername").equals(Login.logInAccountInfo.get("username").toString()) ) {
                String id = rate.get("id").toString();
                Rate newRate = new Rate(id, Login.logInAccountInfo.get("username").toString(), branchName, 1, review);
                reff4.child(id).setValue(newRate);
                Toast.makeText(Costumer.this, "Your previous rating has been updated", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;
            }
        }

        String id = reff4.push().getKey();
        Rate rate = new Rate(id, Login.logInAccountInfo.get("username").toString(), branchName, 1, review);
        reff4.child(id).setValue(rate);

        Toast.makeText(Costumer.this, "Thank you for rating!", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
    public void onTwoClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.reviewEditText);
        editText2 = (EditText) dialog.findViewById(R.id.branchNameEditText);
        String review = editText1.getText().toString();
        String branchName = editText2.getText().toString();

        boolean isBranchNameValid = false;

        for (HashMap branchProfile : branchProfiles) {
            if ( branchProfile.get("username").toString().equals(branchName) ) {
                isBranchNameValid = true;
                break;
            }
        }

        if (!isBranchNameValid) {
            Toast.makeText(Costumer.this, "Branch does not exist", Toast.LENGTH_LONG).show();
            return;
        }

        for (HashMap rate : rates) {
            if ( rate.get("branchUsername").equals(branchName) && rate.get("costumerUsername").equals(Login.logInAccountInfo.get("username").toString()) ) {
                String id = rate.get("id").toString();
                Rate newRate = new Rate(id, Login.logInAccountInfo.get("username").toString(), branchName, 2, review);
                reff4.child(id).setValue(newRate);
                Toast.makeText(Costumer.this, "Your previous rating has been updated", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;
            }
        }

        String id = reff4.push().getKey();
        Rate rate = new Rate(id, Login.logInAccountInfo.get("username").toString(), branchName, 2, review);
        reff4.child(id).setValue(rate);

        Toast.makeText(Costumer.this, "Thank you for rating!", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
    public void onThreeClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.reviewEditText);
        editText2 = (EditText) dialog.findViewById(R.id.branchNameEditText);
        String review = editText1.getText().toString();
        String branchName = editText2.getText().toString();

        boolean isBranchNameValid = false;

        for (HashMap branchProfile : branchProfiles) {
            if ( branchProfile.get("username").toString().equals(branchName) ) {
                isBranchNameValid = true;
                break;
            }
        }

        if (!isBranchNameValid) {
            Toast.makeText(Costumer.this, "Branch does not exist", Toast.LENGTH_LONG).show();
            return;
        }

        for (HashMap rate : rates) {
            if ( rate.get("branchUsername").equals(branchName) && rate.get("costumerUsername").equals(Login.logInAccountInfo.get("username").toString()) ) {
                String id = rate.get("id").toString();
                Rate newRate = new Rate(id, Login.logInAccountInfo.get("username").toString(), branchName, 3, review);
                reff4.child(id).setValue(newRate);
                Toast.makeText(Costumer.this, "Your previous rating has been updated", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;
            }
        }

        String id = reff4.push().getKey();
        Rate rate = new Rate(id, Login.logInAccountInfo.get("username").toString(), branchName, 3, review);
        reff4.child(id).setValue(rate);

        Toast.makeText(Costumer.this, "Thank you for rating!", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
    public void onFourClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.reviewEditText);
        editText2 = (EditText) dialog.findViewById(R.id.branchNameEditText);
        String review = editText1.getText().toString();
        String branchName = editText2.getText().toString();

        boolean isBranchNameValid = false;

        for (HashMap branchProfile : branchProfiles) {
            if ( branchProfile.get("username").toString().equals(branchName) ) {
                isBranchNameValid = true;
                break;
            }
        }

        if (!isBranchNameValid) {
            Toast.makeText(Costumer.this, "Branch does not exist", Toast.LENGTH_LONG).show();
            return;
        }

        for (HashMap rate : rates) {
            if ( rate.get("branchUsername").equals(branchName) && rate.get("costumerUsername").equals(Login.logInAccountInfo.get("username").toString()) ) {
                String id = rate.get("id").toString();
                Rate newRate = new Rate(id, Login.logInAccountInfo.get("username").toString(), branchName, 4, review);
                reff4.child(id).setValue(newRate);
                Toast.makeText(Costumer.this, "Your previous rating has been updated", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;
            }
        }

        String id = reff4.push().getKey();
        Rate rate = new Rate(id, Login.logInAccountInfo.get("username").toString(), branchName, 4, review);
        reff4.child(id).setValue(rate);

        Toast.makeText(Costumer.this, "Thank you for rating!", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
    public void onFiveClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.reviewEditText);
        editText2 = (EditText) dialog.findViewById(R.id.branchNameEditText);
        String review = editText1.getText().toString();
        String branchName = editText2.getText().toString();

        boolean isBranchNameValid = false;

        for (HashMap branchProfile : branchProfiles) {
            if ( branchProfile.get("username").toString().equals(branchName) ) {
                isBranchNameValid = true;
                break;
            }
        }

        if (!isBranchNameValid) {
            Toast.makeText(Costumer.this, "Branch does not exist", Toast.LENGTH_LONG).show();
            return;
        }

        for (HashMap rate : rates) {
            if ( rate.get("branchUsername").equals(branchName) && rate.get("costumerUsername").equals(Login.logInAccountInfo.get("username").toString()) ) {
                String id = rate.get("id").toString();
                Rate newRate = new Rate(id, Login.logInAccountInfo.get("username").toString(), branchName, 5, review);
                reff4.child(id).setValue(newRate);
                Toast.makeText(Costumer.this, "Your previous rating has been updated", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;
            }
        }

        String id = reff4.push().getKey();
        Rate rate = new Rate(id, Login.logInAccountInfo.get("username").toString(), branchName, 5, review);
        reff4.child(id).setValue(rate);

        Toast.makeText(Costumer.this, "Thank you for rating!", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
