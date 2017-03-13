package com.codingbash.muse_monitor_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.choosemuse.libmuse.MuseManagerAndroid;

public class UserActivity extends AppCompatActivity {

    private MuseManagerAndroid museManagerAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        museManagerAndroid = MuseManagerAndroid.getInstance();
        museManagerAndroid.setContext(this);
    }
}
