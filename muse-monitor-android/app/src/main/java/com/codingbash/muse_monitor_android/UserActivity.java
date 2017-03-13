package com.codingbash.muse_monitor_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class UserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        addButtonClickListener();
    }

    private void addButtonClickListener(){
        Button patientButton = (Button) findViewById(R.id.patient_button);
        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPatientActivity();
            }
        });
    }

    private void goToPatientActivity(){
        Intent i = new Intent(this, PatientActivity.class);
        startActivity(i);
    }
}
