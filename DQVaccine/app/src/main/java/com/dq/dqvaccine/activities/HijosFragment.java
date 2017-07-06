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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class HijosFragment extends Fragment {

    private ListView mHijosList;
    private HijosCursorAdapter mHijosAdapter;
    private int mUsuarioId;
    private ArrayList<Hijo> hijos;



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
            cargarNotificaciones();

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        return root;
    }

    private void showVacunasScreen(int currentHijoId) {
        Intent intent = new Intent(getActivity(), VacunasActivity.class);
        intent.putExtra(HijosActivity.EXTRA_HIJO_ID, currentHijoId);
        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    private void loadHijos() {
        hijos = new ArrayList<>();
        new HijosLoadTask().execute();
    }

    private void cargarNotificaciones() {
        new MesesNoAplicados().execute();
    }

    private class HijosLoadTask extends AsyncTask<Void, Void, Wrapper> {


        AlertDialog alertDialog;


        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(getActivity()).create();
        }

        protected Wrapper doInBackground(Void... params) {

            boolean resul = true;

            MatrixCursor mc = new MatrixCursor(new String[] {"_id", HijosEntry.NOMBRE, HijosEntry.APELLIDO,
            HijosEntry.FECHA_NAC});

            HttpClient httpClient = new DefaultHttpClient();

            HttpPost post = new HttpPost(Utiles.Path.hijosPath);

            post.setHeader("content-type", "application/json");


            try {
                JSONObject parm = new JSONObject();
                parm.put("id", mUsuarioId);
                StringEntity entity = new StringEntity(parm.toString());
                post.setEntity(entity);
                HttpResponse resp = httpClient.execute(post);
                String respStr = EntityUtils.toString(resp.getEntity());

                JSONArray jArray = new JSONArray(respStr);
                if (jArray != null) {
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObject = jArray.getJSONObject(i);
                        int id = jObject.getInt("idHijo");
                        String nombre = jObject.getString("nombre");
                        String apellido = jObject.getString("apellido");
                        String fecha_nac = jObject.getString("fechaNac");
                        mc.addRow(new Object[] {id, nombre, apellido, fecha_nac});
                        hijos.add(new Hijo(id, nombre, apellido, fecha_nac));
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


    private class MesesNoAplicados extends AsyncTask<Void, Void, Boolean> {



        protected Boolean doInBackground(Void... params) {

            int mes;
            String fecha_apl;
            ArrayList<Integer> meses;
            ArrayList<String> fechas;
            Utiles util = new Utiles();
            HttpClient httpClient;
            HttpPost post;
            String parm;

            for (Hijo hijo: hijos
                 ) {

                meses = new ArrayList<>();
                fechas = new ArrayList<>();
                httpClient = new DefaultHttpClient();


                try {
                    post = new HttpPost(Utiles.Path.vacunasnoPath);
                    parm = "{'vacunasPK':{'idHijo':" + hijo.getId() + "}, 'aplicado': 0}";
                    StringEntity entity = new StringEntity(parm);
                    post.setEntity(entity);

                    post.setHeader("content-type", "application/json");
                    HttpResponse resp = httpClient.execute(post);
                    String respStr = EntityUtils.toString(resp.getEntity());

                    JSONArray jArray = new JSONArray(respStr);
                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jObject = jArray.getJSONObject(i);
                            mes = jObject.getInt("mesAplicacion");
                            fecha_apl = jObject.getString("fechaApl");
                            if (!meses.contains(mes)){
                                meses.add(mes);
                                fechas.add(fecha_apl);
                            }
                        }
                    }
                } catch (Exception ex) {
                    Log.e("ServicioRest", "Error!", ex);
                }

                for (int i = 0 ; i < meses.size() ; i++) {
                    new Notificacion(getActivity(),
                            util.calcularNotificacion(fechas.get(i)),
                            hijo.getId(),
                            hijo.getNombre() + " " + hijo.getApellido(),
                            meses.get(i));
                }

            }
            return true;
        }

    }

}

