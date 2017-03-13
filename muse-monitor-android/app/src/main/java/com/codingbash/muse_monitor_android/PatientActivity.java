package com.codingbash.muse_monitor_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PatientActivity extends AppCompatActivity {

    private static final String PREFS_NAME ="MUSE-MONITOR-PREFERENCES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        addButtonClickListeners();
    }

    private void addButtonClickListeners(){
        // TODO: Fill in current patient ID from preferences into EditText
        Button submitButton = (Button) findViewById(R.id.patient_id_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patientId = ((EditText) findViewById(R.id.patient_id_field)).getText().toString();
                // TODO: Validate Patient ID
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("patientId", patientId);
                editor.commit();

                goToStreamingActivity();
            }
        });
    }

    private void goToStreamingActivity(){
        Intent i = new Intent(this, StreamingActivity.class);
        startActivity(i);
    }
}
