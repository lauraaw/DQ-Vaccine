package com.dq.dqvaccine.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dq.dqvaccine.R;

public class VacunasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        VacunasFragment fragment = (VacunasFragment)
                getSupportFragmentManager().findFragmentById(R.id.vacunas_container);

        if (fragment == null) {
            fragment = VacunasFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.vacunas_container, fragment).commit();
        }

    }

}
