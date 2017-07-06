package com.dq.dqvaccine.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.dq.dqvaccine.R;

public class VacunasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getIntent().getIntExtra(HijosActivity.EXTRA_HIJO_ID, 0);

        setContentView(R.layout.activity_vacunas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        VacunasFragment fragment = (VacunasFragment)
                getSupportFragmentManager().findFragmentById(R.id.vacunas_container);

        if (fragment == null) {
            fragment = VacunasFragment.newInstance(id);
            getSupportFragmentManager().beginTransaction().add(R.id.vacunas_container, fragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacunas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
