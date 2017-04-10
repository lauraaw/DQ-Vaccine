package com.dq.dqvaccine.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dq.dqvaccine.R;
import com.dq.dqvaccine.Utiles;
import com.dq.dqvaccine.clases.Hijo;
import com.dq.dqvaccine.clases.HijosCursorAdapter;
import com.dq.dqvaccine.clases.Notificacion;
import com.dq.dqvaccine.data.DQContract.HijosEntry;
import com.dq.dqvaccine.data.DQbdHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class HijosFragment extends Fragment {

    private DQbdHelper mDQbdHelper;

    private ListView mHijosList;
    private HijosCursorAdapter mHijosAdapter;


    public HijosFragment() {
        // Required empty public constructor
    }

    public static HijosFragment newInstance() {
        return new HijosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hijos, container, false);

        mHijosList = (ListView) root.findViewById(R.id.hijos_list);
        mHijosAdapter = new HijosCursorAdapter(getActivity(), null);
        mHijosList.setAdapter(mHijosAdapter);

        mHijosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mHijosAdapter.getItem(i);
                int currentHijoId = currentItem.getInt(
                        currentItem.getColumnIndex(HijosEntry.ID));
                showVacunasScreen(currentHijoId);
            }
        });


        getActivity().deleteDatabase(DQbdHelper.DATABASE_NAME);

        mDQbdHelper = new DQbdHelper(getActivity());

        loadHijos();

        //Crea las notificaciones solo la primera vez que corre la aplicacion
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            loadNotificaciones();

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        return root;
    }

    private void loadNotificaciones() {
        Utiles util = new Utiles();
        Cursor cursor = mDQbdHelper.getHijoById("1");
        ArrayList<Integer> meses = getMesesNoAplicados("1");
        cursor.moveToFirst();
        Hijo hijo = new Hijo(cursor);
        for (int i = 0; i < meses.size(); i++) {
            String fecha = util.calcularFechaAAplicar(hijo.getFecha_nac(), meses.get(i));
            new Notificacion(getActivity(),
                    util.calcularNotificacion(fecha),
                    hijo.getId(),
                    hijo.getNombre() + " " + hijo.getApellido(),
                    meses.get(i));
        }
        cursor.close();

        cursor = mDQbdHelper.getHijoById("2");
        meses = getMesesNoAplicados("2");
        cursor.moveToFirst();
        hijo = new Hijo(cursor);
        for (int i = 0; i < meses.size(); i++) {
            String fecha = util.calcularFechaAAplicar(hijo.getFecha_nac(), meses.get(i));
            new Notificacion(getActivity(),
                    util.calcularNotificacion(fecha),
                    hijo.getId(),
                    hijo.getNombre() + " " + hijo.getApellido(),
                    meses.get(i));
        }
        cursor.close();

        cursor = mDQbdHelper.getHijoById("3");
        meses = getMesesNoAplicados("3");
        cursor.moveToFirst();
        hijo = new Hijo(cursor);
        for (int i = 0; i < meses.size(); i++) {
            String fecha = util.calcularFechaAAplicar(hijo.getFecha_nac(), meses.get(i));
            new Notificacion(getActivity(),
                    util.calcularNotificacion(fecha),
                    hijo.getId(),
                    hijo.getNombre() + " " + hijo.getApellido(),
                    meses.get(i));
        }
        cursor.close();
    }

    private ArrayList<Integer> getMesesNoAplicados(String idHijo) {
        Cursor cursor = mDQbdHelper.getNoAplicadas(idHijo);
        ArrayList<Integer> lista = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getInt(8));
            } while (cursor.moveToNext());
        }
        HashSet<Integer> hs = new HashSet<Integer>(lista);
        lista.clear();
        lista.addAll(hs);

        Collections.sort(lista);
        return lista;
    }


    private void showVacunasScreen(int currentHijoId) {
        Intent intent = new Intent(getActivity(), VacunasActivity.class);
        intent.putExtra(HijosActivity.EXTRA_HIJO_ID, currentHijoId);
        startActivity(intent);
    }


    private void loadHijos() {
        new HijosLoadTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private class HijosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDQbdHelper.getAllHijos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mHijosAdapter.swapCursor(cursor);
            } else if (cursor.isAfterLast()){
                cursor.close();
            }
        }


    }}

