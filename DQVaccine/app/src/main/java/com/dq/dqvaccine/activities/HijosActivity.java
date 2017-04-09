package com.dq.dqvaccine.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dq.dqvaccine.R;
import com.dq.dqvaccine.clases.Notificacion;

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

        if (fragment == null) {
            fragment = HijosFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.hijos_container, fragment).commit();
        }

        //Crea las notificaciones solo la primera vez que corre la aplicacion
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            loadNotificaciones();

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }

    private void loadNotificaciones() {
        new Notificacion(this, "09/04/2017", 1);
    }
}
