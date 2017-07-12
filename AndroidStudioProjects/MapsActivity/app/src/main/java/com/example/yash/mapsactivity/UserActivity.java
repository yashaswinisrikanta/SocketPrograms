package com.example.yash.mapsactivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


//import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonForRenters;
    private Button buttonForDrivers;

    //private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //firebaseAuth = FirebaseAuth.getInstance();// initialise firebase object
       /*if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
           startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }*/
        progressDialog = new ProgressDialog(this);
        buttonForDrivers = (Button) findViewById(R.id.buttonForDrivers);
        buttonForRenters = (Button) findViewById(R.id.buttonForRenters);
        buttonForDrivers.setOnClickListener(this);
        buttonForRenters.setOnClickListener(this);
    }

    private void userSelect() {

        progressDialog.setMessage("Going to user page...");
        progressDialog.show();
        progressDialog.dismiss();

                            //progressDialog.hide();﻿
                            //start the profile activityToast.makeText(SignUp.this,"Registered Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));


    }

    @Override
    public void onClick(View v) {

        if(v==buttonForRenters)
        {
            userSelect();
           // finish();
            //starting login activity
            //startActivity(new Intent(this,ProfileActivity.class));
        }

        if(v==buttonForDrivers)
        {
            userSelect();
        }

    }
}
