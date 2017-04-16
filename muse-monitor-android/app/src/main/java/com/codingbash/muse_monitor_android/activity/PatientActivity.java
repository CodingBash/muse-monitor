package com.codingbash.muse_monitor_android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codingbash.muse_monitor_android.R;
import com.codingbash.muse_monitor_android.model.Patient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class PatientActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MUSE-MONITOR-PREFERENCES";

    private static final String TAG = "PATIENT_ACTIVITY";

    public static final String SEARCH_PATIENT_BY_ID_URL = "http://muse-monitor-patientservice.herokuapp.com/patients/{patientId}";

    public static final String SEARCH_PATIENT_BY_NAME_URL = "http://muse-monitor-patientservice.herokuapp.com/patients/name/{patientName}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        initUI();
    }

    public void initUI() {
        Button searchPatientButton = (Button) findViewById(R.id.search_patient_button);
        searchPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText patientIdEditText = (EditText) findViewById(R.id.search_patient_id_edittext);
                String inputPatientId = StringUtils.trim(patientIdEditText.getText().toString());
                EditText patientNameEditText = (EditText) findViewById(R.id.search_patient_name_edittext);
                String inputPatientName = StringUtils.trim(patientNameEditText.getText().toString());

                boolean patientIdEntered = StringUtils.isNotBlank(inputPatientId);
                boolean patientNameEntered = StringUtils.isNotBlank(inputPatientName);

                if (patientIdEntered) {
                    Log.i(TAG, "Patient ID entered");
                    new PatientSearchByIdTask().execute(inputPatientId, String.valueOf(patientNameEntered), inputPatientName);
                    //checkPatientId(inputPatientId, patientNameEntered, inputPatientName);
                } else if (patientNameEntered) {
                    Log.i(TAG, "Patient name entered");
                    new PatientSearchByNameTask().execute(inputPatientName);
                    // checkPatientName(inputPatientName);
                } else {
                    Log.i(TAG, "No input entered");
                    Context context = getApplicationContext();
                    CharSequence text = "No ID or name entered";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }

    private void goToStreamingActivity() {
        Intent i = new Intent(this, StreamingActivity.class);
        startActivity(i);
    }

    private ResponseEntity<Patient> checkPatientId(String inputPatientId, boolean patientNameEntered, String inputPatientName) {
        /*
                    Modularize
                     */
        HostnameVerifier verifier = new NullHostnameVerifier();
        MySimpleClientHttpRequestFactory factory = new MySimpleClientHttpRequestFactory(verifier);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpAuthentication authHeader = new HttpBasicAuthentication("admin",
                "password");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAuthorization(authHeader);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        String requestUrl = SEARCH_PATIENT_BY_ID_URL.replace("{patientId}", inputPatientId);
        ResponseEntity<Patient> response = restTemplate.getForEntity(requestUrl, Patient.class);
        return response;
        /*
        ResponseEntity<Patient> response = restTemplate.getForEntity(requestUrl, Patient.class);
        if(response.getStatusCode() == HttpStatus.OK){
            String responsePatientId = response.getBody().getMongoId();

            // Modularize

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("patientId", responsePatientId);
            editor.commit();

            // Change Activity
            goToStreamingActivity();

        } else {
            Context context = getApplicationContext();
            CharSequence text = "Error - 0 patients found for ID";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            if(patientNameEntered){
                checkPatientName(inputPatientName);
            }
        }
        */
    }

    private ResponseEntity<Patient> checkPatientName(String inputPatientName) {
         /*
                    Modularize
                     */
        HostnameVerifier verifier = new NullHostnameVerifier();
        MySimpleClientHttpRequestFactory factory = new MySimpleClientHttpRequestFactory(verifier);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpAuthentication authHeader = new HttpBasicAuthentication("admin",
                "password");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAuthorization(authHeader);
        ResponseEntity<Patient> response = restTemplate.exchange(SEARCH_PATIENT_BY_NAME_URL, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), Patient.class, inputPatientName);
        System.out.println();
        return response;
        /*
        ResponseEntity<Patient> response = restTemplate.getForEntity(requestUrl, Patient.class, urlParameters);
        if(response.getStatusCode() == HttpStatus.OK){
            String responsePatientId = response.getBody().getMongoId();

            // Modularize

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("patientId", responsePatientId);
            editor.commit();

            // Change Activity
            goToStreamingActivity();
        } else {
            // TODO: Throw error - 0 or 2+ found for name
            Context context = getApplicationContext();
            CharSequence text = "Error - 0 or 2+ patients found for name";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        */
    }

    private class PatientSearchByIdTask extends AsyncTask<String, Void, ResponseEntity<Patient>> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Patient ID executing");
        }

        @Override
        protected ResponseEntity<Patient> doInBackground(String... params) {
            try {
                ResponseEntity<Patient> response = checkPatientId(params[0], Boolean.getBoolean(params[1]), params[2]);
                if (response.getStatusCode() == HttpStatus.OK) {
                    String responsePatientId = response.getBody().getMongoId();

                    // Modularize

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("patientId", responsePatientId);
                    editor.commit();

                    // Change Activity
                    goToStreamingActivity();
                    return response;

                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Error - 0 patients found for ID";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    if (Boolean.getBoolean(params[1])) {
                        checkPatientName(params[2]);
                    }
                }
            } catch (Exception e) {
                TODO:
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<Patient> result) {
            if (result != null) {
                Log.i(TAG, "Patient Id Result: " + result.getBody().toString());
            } else {
                Log.i(TAG, "Patient Name Result: null");
            }
        }

    }

    private class PatientSearchByNameTask extends AsyncTask<String, Void, ResponseEntity<Patient>> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Patient Name executing");
        }

        @Override
        protected ResponseEntity<Patient> doInBackground(String... params) {
            try {
                ResponseEntity<Patient> response = checkPatientName(params[0]);
                if (response != null) {
                    Log.i(TAG, "Patient Name Result: " + response.getStatusCode().toString() + " " + response.getBody().toString());
                } else {
                    Log.i(TAG, "Patient Name Result: null");
                }
                if (response.getStatusCode() == HttpStatus.OK) {
                    String responsePatientId = response.getBody().getMongoId();

                    // Modularize

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("patientId", responsePatientId);
                    editor.commit();

                    // Change Activity
                    goToStreamingActivity();
                    return response;
                } else {
                    // TODO: Throw error - 0 or 2+ found for name
                    Context context = getApplicationContext();
                    CharSequence text = "Error - 0 or 2+ patients found for name";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            } catch (Exception e) {
                TODO:
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<Patient> result) {
            if (result != null) {
                Log.i(TAG, "Patient Name Result: " + result.getBody().toString());
            } else {
                Log.i(TAG, "Patient Name Result: null");
            }
        }

    }


    public class MySimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

        private final HostnameVerifier verifier;

        public MySimpleClientHttpRequestFactory(HostnameVerifier verifier) {
            this.verifier = verifier;
        }

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            if (connection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) connection).setHostnameVerifier(verifier);
            }
            super.prepareConnection(connection, httpMethod);
        }

    }

    public class NullHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
