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

public class Admin extends AppCompatActivity {

    // edit account's pop-up
    DatabaseReference reff1, reff2, reff3, reff4, reff5;
    List<HashMap> accounts, services, branchProfiles, serviceRequests, rates;
    private Dialog dialog;
    private EditText editText1, editText2, editText3;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // welcoming the admin
        TextView intro = (TextView) findViewById(R.id.admin_page_intro);
        intro.setText("Welcome, " + Login.logInAccountInfo.get("firstName") + "!");
        //

        reff1 = FirebaseDatabase.getInstance().getReference("accounts");
        reff2 = FirebaseDatabase.getInstance().getReference("services");
        reff3 = FirebaseDatabase.getInstance().getReference("branch profiles");
        reff4 = FirebaseDatabase.getInstance().getReference("service requests");
        reff5 = FirebaseDatabase.getInstance().getReference("rates");

        accounts = new ArrayList<>();
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
                accounts.clear();
                for ( DataSnapshot accountSnapshot : snapshot.getChildren() ) {
                    accounts.add( (HashMap)accountSnapshot.getValue() );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services.clear();
                for ( DataSnapshot accountSnapshot : snapshot.getChildren() ) {
                    services.add( (HashMap)accountSnapshot.getValue() );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        reff3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchProfiles.clear();
                for ( DataSnapshot accountSnapshot : snapshot.getChildren() ) {
                    branchProfiles.add( (HashMap)accountSnapshot.getValue() );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        reff4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceRequests.clear();
                for ( DataSnapshot accountSnapshot : snapshot.getChildren() ) {
                    serviceRequests.add( (HashMap)accountSnapshot.getValue() );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        reff5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rates.clear();
                for ( DataSnapshot accountSnapshot : snapshot.getChildren() ) {
                    rates.add( (HashMap)accountSnapshot.getValue() );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

//Services' buttons///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onVeiwServiceClick(View view) {
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

    public void onAddServiceClick(View view) {
        dialog.setContentView(R.layout.add_service);
        dialog.show();
    }

    // this button is inside add_service popup
    public void onCreateServiceClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.service_name_edit_text2);
        editText2 = (EditText) dialog.findViewById(R.id.hourly_rate_edit_text2);
        editText3 = (EditText) dialog.findViewById(R.id.costumer_info_edit_text2);
        String serviceName = editText1.getText().toString();
        String rate = editText2.getText().toString();
        String costumerInfo = editText3.getText().toString();

        double parsedRate;

        // validating the inputs
        if (serviceName.length() == 0 || rate.length() == 0 || costumerInfo.length() == 0){
            Toast.makeText(Admin.this, "Please fill out all the boxes", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            parsedRate = Double.parseDouble(rate);
        }
        catch (Exception e){
            Toast.makeText(Admin.this, "Please fill out hourly rate box with a number", Toast.LENGTH_LONG).show();
            return;
        }
        //

        for (HashMap service : services) {
            if ( service.get("serviceName").equals(serviceName) ) {
                Toast.makeText(Admin.this, "Service already exist", Toast.LENGTH_LONG).show();
                return;
            }
        }

        ArrayList<String> list = new ArrayList<>( costumerInfo.split(",").length );

        for (String ch : costumerInfo.split(",")){
            list.add(ch.trim());
        }

        addService(serviceName, parsedRate, list);
        dialog.dismiss();
    }

    public void onEditServiceClick(View view) {
        dialog.setContentView(R.layout.edit_service_popup);
        dialog.show();
    }

    // this button is inside edit_service_popup popup
    public void onUpdateServiceClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.service_name_edit_text);
        editText2 = (EditText) dialog.findViewById(R.id.hourly_rate_edit_text);
        editText3 = (EditText) dialog.findViewById(R.id.costumer_info_edit_text);
        String serviceName = editText1.getText().toString();
        String rate = editText2.getText().toString();
        String costumerInfo = editText3.getText().toString();

        double parsedRate;

        // validating the inputs
        if (serviceName.length() == 0 || rate.length() == 0 || costumerInfo.length() == 0){
            Toast.makeText(Admin.this, "Please fill out all the boxes", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            parsedRate = Double.parseDouble(rate);
        }
        catch (Exception e){
            Toast.makeText(Admin.this, "Please fill out hourly rate box with a number", Toast.LENGTH_LONG).show();
            return;
        }
        //

        boolean found = false;
        String id = null;

        for (HashMap service : services) {
            if ( service.get("serviceName").equals(serviceName) ) {
                id = service.get("id").toString();
                found = true;
                break;
            }
        }

        if (!found) {
            Toast.makeText(Admin.this, "Service does not exist", Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<String> list = new ArrayList<>( costumerInfo.split(",").length );

        for (String ch : costumerInfo.split(",")) {
            list.add(ch.trim());
        }

        updateService(id,serviceName, parsedRate, list);
        dialog.dismiss();
    }

    // this button is inside edit_service_popup popup
    public void onDeleteServiceClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.service_name_edit_text);
        String serviceName = editText1.getText().toString();

        // validating the inputs
        if (serviceName.length() == 0){
            Toast.makeText(Admin.this, "Please fill out service name box", Toast.LENGTH_LONG).show();
            return;
        }
        //

        for (HashMap service : services) {
            if ( service.get("serviceName").equals(serviceName) ) {

                deleteService( service.get("id").toString() );
                deleteServiceFromAllBranches(service);

                dialog.dismiss();
                return;
            }
        }
        Toast.makeText(Admin.this, "Service does not exist", Toast.LENGTH_LONG).show();
    }

    private void addService(String serviceName, double rate, ArrayList<String> costumerInfo) {
            //getting a unique id
            String id = reff2.push().getKey();
            //creating a new product with the given info
            Service service = new Service(id, serviceName, rate, costumerInfo);
            //saving the product into the database
            reff2.child(id).setValue(service);

            Toast.makeText(Admin.this, "Service created successfully", Toast.LENGTH_LONG).show();
    }

    private void updateService(String id, String serviceName, double rate, ArrayList<String> costumerInfo) {
        Service updatedService = new Service(id, serviceName, rate, costumerInfo);
        reff2.child(id).setValue(updatedService);
    }

    private void deleteService(String id) {
        // getting the specified id (existing id)
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(id);
        // removing the product with the Id of id
        dR.removeValue();
        Toast.makeText(Admin.this, "Service deleted successfully ", Toast.LENGTH_LONG).show();
    }

    // after a service is deleted from the Services, this method is called to delete that service from all branchProfiles' branchServices list
    private void deleteServiceFromAllBranches(HashMap service) {
        ArrayList<String> branchServices;
        ArrayList<String> newBranchServices;
        String id = "";

        for (HashMap branchProfile : branchProfiles) {
            branchServices = (ArrayList<String>) branchProfile.get("branchServices");
            id = branchProfile.get("id").toString();

            if ( branchServices.get(0).equals("") && branchServices.toArray().length == 1 ){}

            else if ( (branchServices.toArray().length == 1) && (branchServices.get(0).equals(service.get("serviceName"))) ) {
                newBranchServices = new ArrayList<>(1);
                newBranchServices.add("");
                reff3.child(id).child("branchServices").setValue( newBranchServices );
            }

            else{
                newBranchServices = new ArrayList<>( branchServices.toArray().length - 1 );

                for (String branchService : branchServices) {
                    if ( ! branchService.equals(service.get("serviceName")) ) {
                        try{
                            newBranchServices.add(branchService);
                        }
                        catch (Exception e){
                            // the service we want to delete does not exist in the current branchProfile's branchServices list, so we move on to check the next branchProfile's branchServices list.
                            break;
                        }
                    }
                }
                reff3.child(id).child("branchServices").setValue( newBranchServices );
            }
        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Accounts' buttons/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onVeiwCostumersClick(View view) {
        dialog.setContentView(R.layout.veiw_service_popup);
        dialog.show();

        TextView textView = (TextView) dialog.findViewById(R.id.popupListTextView);
        textView.setText("List of Customers:");

        listView = (ListView) dialog.findViewById(R.id.veiwServiceListVeiw);
        ArrayList<String> arrayList = new ArrayList<>();

        for (HashMap account : accounts) {
            if( account.get("type").toString().equals("CUSTOMER") ) {
                arrayList.add("\n\nFirst Name: " + account.get("firstName").toString()
                              + "\nLast Name: " + account.get("lastName").toString()
                              + "\nUsername: " + account.get("username").toString()
                                + "\nPassword: " + account.get("password").toString() );
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public void onVeiwEmployeesClick(View view) {
        dialog.setContentView(R.layout.veiw_service_popup);
        dialog.show();

        TextView textView = (TextView) dialog.findViewById(R.id.popupListTextView);
        textView.setText("List of Employees:");

        listView = (ListView) dialog.findViewById(R.id.veiwServiceListVeiw);
        ArrayList<String> arrayList = new ArrayList<>();

        for (HashMap account : accounts) {
            if( account.get("type").toString().equals("EMPLOYEE") ) {
                arrayList.add("\n\nFirst Name: " + account.get("firstName").toString()
                               + "\nLast Name: " + account.get("lastName").toString()
                                + "\nUsername: " + account.get("username").toString()
                                + "\nPassword: " + account.get("password").toString() );
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public void onEditAccountClick(View view) {
        dialog.setContentView(R.layout.delete_account_popup);
        dialog.show();
    }

    public void onDeleteAccountClick(View view) {
        editText1 = (EditText) dialog.findViewById(R.id.addServiceNameEditText2);
        String username = editText1.getText().toString();

        // validating the inputs
        if (username.length() == 0){
            Toast.makeText(Admin.this, "Please fill out username boxes", Toast.LENGTH_LONG).show();
            return;
        }
        //

        for (HashMap account : accounts) {
            if (username.equals("admin")){
                Toast.makeText(Admin.this, "Admin account cannot be deleted", Toast.LENGTH_LONG).show();
                return;
            }

            else if ( account.get("username").equals(username) ) {
                // deleting the corresponding branch profile, if the account belongs to employee
                if ( account.get("type").toString().equals("EMPLOYEE") ) {
                    for (HashMap branchProfile : branchProfiles) {
                        if ( account.get("username").equals(branchProfile.get("username")) ) {
                            deleteBranchProfile( branchProfile.get("id").toString() );
                        }
                    }
                }
                //

                // deleting the corresponding service request
                for (HashMap serviceRequest : serviceRequests) {
                    if ( account.get("username").equals(serviceRequest.get("branch")) || account.get("username").equals(serviceRequest.get("costumer")) ) {
                        deleteServiceRequest( serviceRequest.get("id").toString() );
                    }
                }
                //

                // deleting the corresponding rate
                for (HashMap rate : rates) {
                    if ( account.get("username").equals(rate.get("branchUsername")) || account.get("username").equals(rate.get("costumerUsername")) ) {
                        deleteRate( rate.get("id").toString() );
                    }
                }
                //

                deleteAccount( account.get("id").toString() );
                dialog.dismiss();
                return;
            }
        }

        Toast.makeText(Admin.this, "The username does not exist", Toast.LENGTH_LONG).show();
    }

    private void deleteAccount(String id) {
        // getting the specified id (existing id)
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("accounts").child(id);
        // removing the product with the Id of id
        dR.removeValue();

        Toast.makeText(Admin.this, "Account deleted successfully", Toast.LENGTH_LONG).show();
    }

    private void deleteBranchProfile(String id) {
        // getting the specified id (existing id)
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("branch profiles").child(id);
        // removing the product with the Id of id
        dR.removeValue();
    }

    private void deleteServiceRequest(String id) {
        // getting the specified id (existing id)
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("service requests").child(id);
        // removing the product with the Id of id
        dR.removeValue();
    }

    private void deleteRate(String id) {
        // getting the specified id (existing id)
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("rates").child(id);
        // removing the product with the Id of id
        dR.removeValue();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
