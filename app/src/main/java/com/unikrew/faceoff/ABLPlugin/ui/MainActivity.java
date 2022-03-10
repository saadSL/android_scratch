package com.unikrew.faceoff.ABLPlugin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ofss.digx.mobile.android.allied.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void cnicActivity(View view) {
        Intent i = new Intent(view.getContext(), CnicAvailabilityActivity.class);
        startActivity(i);
    }

}