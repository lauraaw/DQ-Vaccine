package com.dq.dqvaccine.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dq.dqvaccine.R;
import com.dq.dqvaccine.Utiles;
import com.dq.dqvaccine.clases.ExpandableListAdapter;
import com.dq.dqvaccine.clases.Vacuna;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VacunasFragment extends Fragment {

    private static final String ARG_HIJO_ID = "hijoId";
    private int mHijoId;

    private ExpandableListView mVacunasList;
    private ExpandableListAdapter mVacunasAdapter;

    List<String> listDataHeader;
    HashMap<String, List<Vacuna>> listDataChild;


    public VacunasFragment() {
        // Required empty public constructor
    }

    //Se obtiene el id del hijo para mostrar sus vacunas
    public static VacunasFragment newInstance(int hijoId) {
        VacunasFragment fragment = new VacunasFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_HIJO_ID, hijoId);
        fragment.setArguments(args);
        return fragment;
    }

    //Crea la lista expandible
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vacunas, container, false);
        mVacunasList = (ExpandableListView) root.findViewById(R.id.lvExp);
        preparar();

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preparar();
            }
        });

        return root;
    }

    //ID del hijo
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mHijoId = getArguments().getInt(ARG_HIJO_ID);
        }

        setHasOptionsMenu(true);

    }

    //Al hacer clic en el boton de info de hijo
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                showHijosScreen(mHijoId);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Cambia de intent y pasa a la actividad de hijos detalle
    private void showHijosScreen(int currentHijoId) {
        Intent intent = new Intent(getActivity(), HijosDetalleActivity.class);
        intent.putExtra(HijosActivity.EXTRA_HIJO_ID, currentHijoId);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void preparar() {
        new CargarVacunasTask().execute();
    }

    private class CargarVacunasTask extends AsyncTask<Object, Object, Boolean> {


        @Override
        protected Boolean doInBackground(Object... par) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse resp;
            String respStr;

            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<Vacuna>>();

            listDataHeader.add("Al nacer");

            listDataHeader.add("Dos meses");
            listDataHeader.add("Cuatro meses");
            listDataHeader.add("Seis meses");
            listDataHeader.add("Doce meses");
            listDataHeader.add("Quince meses");
            listDataHeader.add("Dieciocho meses");
            listDataHeader.add("Cuarenta y ocho meses");
            try {
                HttpPost post = new HttpPost(Utiles.Path.vacunasPath);

                post.setHeader("content-type", "application/json");
                JSONObject parm = new JSONObject();
                parm.put("idHijo", mHijoId);
                StringEntity entity = new StringEntity(parm.toString());
                post.setEntity(entity);

                resp = httpClient.execute(post);
                respStr = EntityUtils.toString(resp.getEntity());

                listDataChild.put(listDataHeader.get(0), recorrer(respStr, 0));

                listDataChild.put(listDataHeader.get(1), recorrer(respStr, 2));

                listDataChild.put(listDataHeader.get(2), recorrer(respStr, 4));

                listDataChild.put(listDataHeader.get(3), recorrer(respStr, 6));

                listDataChild.put(listDataHeader.get(4), recorrer(respStr, 12));

                listDataChild.put(listDataHeader.get(5), recorrer(respStr, 15));

                listDataChild.put(listDataHeader.get(6), recorrer(respStr, 18));

                listDataChild.put(listDataHeader.get(7), recorrer(respStr, 48));
            } catch (Exception e) {
                Log.e("ServicioRest", "Error!", e);
            }

            return true;
        }

        protected void onPostExecute(Boolean t){
            mVacunasAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
            mVacunasList.setAdapter(mVacunasAdapter);
        }
    }


    public ArrayList<Vacuna> recorrer(String json, int month) {
        JSONObject jObject;
        JSONArray jArray = null;
        ArrayList<Vacuna> mArrayList = new ArrayList<Vacuna>();
        int idHijo, id, dosis, mes, aplicado, vencido;
        String nombre, edad, fecha_apl, lote, responsable, fecha;
        Utiles u = new Utiles();
        try {
            jArray = new JSONArray(json);
            if (jArray != null) {
                for (int i = 0; i < jArray.length(); i++) {
                    jObject = jArray.getJSONObject(i);
                    mes = jObject.getInt("mesAplicacion");

                    if (mes == month) {
                        idHijo = jObject.getJSONObject("vacunasPK").getInt("idHijo");
                        id = jObject.getJSONObject("vacunasPK").getInt("idVacuna");
                        nombre = jObject.getString("nombreVac");

                        if (jObject.isNull("edad")) {
                            edad = " ";
                        } else {
                            edad = jObject.getString("edad");
                        }

                        dosis = jObject.getInt("dosis");

                        if (jObject.isNull("fecha")) {
                            fecha = " ";
                        } else {
                            fecha = jObject.getString("fecha");
                        }

                        if (jObject.isNull("lote")) {
                            lote = " ";
                        } else {
                            lote = jObject.getString("lote");
                        }

                        if (jObject.isNull("responsable")) {
                            responsable = " ";
                        } else {
                            responsable = jObject.getString("responsable");
                        }

                        aplicado = jObject.getInt("aplicado");
                        fecha_apl = jObject.getString("fechaApl");

                        if (u.vencido(fecha_apl)) {
                            vencido = 1;
                        }
                        else {
                            vencido = 0;
                        }

                        Vacuna v = new Vacuna(id, nombre, idHijo, edad, dosis, fecha, lote,
                                responsable, mes, aplicado, fecha_apl, vencido);

                        mArrayList.add(v);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return mArrayList;
    }

}


