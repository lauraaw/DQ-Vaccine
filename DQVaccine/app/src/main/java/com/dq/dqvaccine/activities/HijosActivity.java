package com.dq.dqvaccine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dq.dqvaccine.MainActivity;
import com.dq.dqvaccine.R;

//TODO: Crear las notificaciones

public class HijosActivity extends AppCompatActivity {

    public static final String EXTRA_HIJO_ID = "extra_hijo_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HijosFragment fragment = (HijosFragment)
                getSupportFragmentManager().findFragmentById(R.id.hijos_container);

        int id = getIntent().getIntExtra(MainActivity.EXTRA_USUARIO_ID, 0);

        if (fragment == null) {
            fragment = HijosFragment.newInstance(id);
            getSupportFragmentManager().beginTransaction().add(R.id.hijos_container, fragment).commit();
        }

    }

}
