package com.example.socialmediaconsolidator.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.socialmediaconsolidator.Data.ContactRecyclerViewAdapter;
import com.example.socialmediaconsolidator.Data.DatabaseHandler;
import com.example.socialmediaconsolidator.Model.Contact;
import com.example.socialmediaconsolidator.R;
import com.example.socialmediaconsolidator.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private User loggedUser;
    private String name;
    private String email;
    private FirebaseDatabase database;
    private Switch fbSwitch;
    private Switch twSwitch;
    private Switch igSwitch;
    private EditText contactNameEditText;
    private Contact newContact;

    private DatabaseHandler newDBHandler;
    private DatabaseHandler dbHandler;
    private Contact myContact;
    private ArrayList<Contact> contactList = new ArrayList<>();

    RecyclerView recyclerView;
    ContactRecyclerViewAdapter contactRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createContact();
            }
        });

        name = getIntent().getStringExtra("usersName");
        email = getIntent().getStringExtra("usersEmail");

        FirebaseApp firebaseApp = FirebaseApp.getInstance();

        loggedUser = new User(name, email);
        loggedUser.setUseFacebook(true);
        database = FirebaseDatabase.getInstance(firebaseApp, "https://social-media-app-e8fe0.firebaseio.com/");

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newDBHandler = new DatabaseHandler(FeedActivity.this);

        refreshData();

    }

    private void refreshData() {

        contactList.clear();

        dbHandler = new DatabaseHandler(getApplicationContext());
        ArrayList<Contact> contactsFromDB = dbHandler.getContacts();

        for (int i = 0; i < contactsFromDB.size(); i++) {

            String contactUN = contactsFromDB.get(i).getUsername();
            boolean contactFB = contactsFromDB.get(i).isFacebook();
            boolean contactTW = contactsFromDB.get(i).isTwitter();
            boolean contactIG = contactsFromDB.get(i).isInstagram();
            int contactId = contactsFromDB.get(i).getContactID();

            myContact = new Contact();
            myContact.setUsername(contactUN);
            myContact.setContactID(contactId);
            myContact.setFacebook(contactFB);
            myContact.setTwitter(contactTW);
            myContact.setInstagram(contactIG);

            contactList.add(myContact);
        }

        dbHandler.close();

        contactRecyclerViewAdapter = new ContactRecyclerViewAdapter(this, contactList);
        recyclerView.setAdapter(contactRecyclerViewAdapter);
        contactRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }


    public void createContact() {

        newContact = new Contact();

        alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.contact_dialog, null);

        Button okButton = view.findViewById(R.id.okButtonId);
        contactNameEditText = view.findViewById(R.id.contactNameEditText);
        fbSwitch = view.findViewById(R.id.facebookSwitchId);
        twSwitch = view.findViewById(R.id.twitterSwitchId);
        igSwitch = view.findViewById(R.id.instagramSwitchId);

        alertDialogBuilder.setView(view);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newContact.setUsername(contactNameEditText.getText().toString());
                newContact.setFacebook(false);
                newContact.setTwitter(false);
                newContact.setInstagram(false);

                fbSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            newContact.setFacebook(true);

                        } else {
                            newContact.setFacebook(false);
                        }
                    }
                });

                twSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            newContact.setTwitter(true);

                        } else {
                            newContact.setTwitter(false);
                        }
                    }
                });

                igSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            newContact.setInstagram(true);

                        } else {
                            newContact.setInstagram(false);
                        }
                    }
                });

                newDBHandler.addContact(newContact);
                newDBHandler.close();
                alertDialog.dismiss();
                refreshData();
            }
        });

    }
}
