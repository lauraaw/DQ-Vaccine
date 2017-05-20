package com.dq.dqvaccine.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dq.dqvaccine.MainActivity;
import com.dq.dqvaccine.R;
import com.dq.dqvaccine.Utiles;
import com.dq.dqvaccine.clases.Hijo;
import com.dq.dqvaccine.clases.HijosCursorAdapter;
import com.dq.dqvaccine.clases.Notificacion;
import com.dq.dqvaccine.data.DQContract.HijosEntry;
import com.dq.dqvaccine.data.DQbdHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class HijosFragment extends Fragment {

    private DQbdHelper mDQbdHelper;

    private ListView mHijosList;
    private HijosCursorAdapter mHijosAdapter;
    private int mUsuarioId;


    public HijosFragment() {
        // Required empty public constructor
    }

    //Se obtiene el id del usuario para mostrar a sus hijos
    public static HijosFragment newInstance(int usuarioId) {
        HijosFragment fragment = new HijosFragment();
        Bundle args = new Bundle();
        args.putInt(MainActivity.EXTRA_USUARIO_ID, usuarioId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUsuarioId = getArguments().getInt(MainActivity.EXTRA_USUARIO_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hijos, container, false);

        mHijosList = (ListView) root.findViewById(R.id.hijos_list);
        mHijosAdapter = new HijosCursorAdapter(getActivity(), null);
        mHijosList.setAdapter(mHijosAdapter);

        //Se obtiene el id del hijo presionado
        mHijosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mHijosAdapter.getItem(i);
                int currentHijoId = currentItem.getInt(
                        currentItem.getColumnIndex("_id"));
                showVacunasScreen(currentHijoId);
            }
        });

        loadHijos();

        //Crea las notificaciones solo la primera vez que corre la aplicacion
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!prefs.getBoolean("firstTime", false)) {
            // one time code
            loadNotificaciones();

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        return root;
    }

    //TODO: CAMBIAR PARA QUE SEA SOLO LOS HIJOS QUE CORRESPONDE
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

    private class HijosLoadTask extends AsyncTask<Void, Void, Wrapper> {


        AlertDialog alertDialog;


        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(getActivity()).create();
        }

        protected Wrapper doInBackground(Void... params) {

            boolean resul = true;

            MatrixCursor mc = new MatrixCursor(new String[] {"_id", HijosEntry.NOMBRE, HijosEntry.APELLIDO});

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet del =
                    new HttpGet("http://10.30.30.16:8084/DQ/webresources/com.dq.hijos/hijos/" + mUsuarioId);

            del.setHeader("content-type", "application/json");


            try {
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());

                JSONArray jArray = new JSONArray(respStr);
                if (jArray != null) {
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObject = jArray.getJSONObject(i);
                        int id = jObject.getInt("idHijo");
                        String nombre = jObject.getString("nombre");
                        String apellido = jObject.getString("apellido");
                        mc.addRow(new Object[] {id, nombre, apellido});
                    }
                }
                else {
                    resul = false;
                }
            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                resul = false;
            }

            Wrapper w = new Wrapper();
            w.cursor = mc;
            w.result = resul;
            return w;
        }

        protected void onPostExecute(Wrapper w) {

            if (w.result) {
                if (w.cursor != null && w.cursor.getCount() > 0) {
                    mHijosAdapter.swapCursor(w.cursor);
                } else if (w.cursor.isAfterLast()){
                    w.cursor.close();
                }
            }
            else{
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Correo no encontrado en la base de datos");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }


    }

    public class Wrapper
    {
        public Boolean result;
        public Cursor cursor;
    }

}

