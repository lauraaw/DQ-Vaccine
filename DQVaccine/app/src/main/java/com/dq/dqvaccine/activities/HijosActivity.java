package com.dq.dqvaccine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dq.dqvaccine.R;

public class HijosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HijosFragment fragment = (HijosFragment)
                getSupportFragmentManager().findFragmentById(R.id.hijos_container);

        if (fragment == null) {
            fragment = HijosFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.hijos_container, fragment).commit();
        }
    }
}
