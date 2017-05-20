package com.dq.dqvaccine.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.dq.dqvaccine.clases.Hijo;
import com.dq.dqvaccine.clases.Vacuna;
import com.dq.dqvaccine.data.DQContract;
import com.dq.dqvaccine.data.DQbdHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class VacunasFragment extends Fragment {

    private static final String ARG_HIJO_ID = "hijoId";
    private int mHijoId;

    private DQbdHelper mDQbdHelper;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vacunas, container, false);
        mVacunasList = (ExpandableListView) root.findViewById(R.id.lvExp);
        mDQbdHelper = new DQbdHelper(getActivity());
        prepareListData();
        mVacunasAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        mVacunasList.setAdapter(mVacunasAdapter);

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mHijoId = getArguments().getInt(ARG_HIJO_ID);
        }

        setHasOptionsMenu(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                showHijosScreen(mHijoId);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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

    private class DatosLoadTask extends AsyncTask<String, Void, ArrayList> {

        @Override
        protected ArrayList<Vacuna> doInBackground(String... par) {

            String fecha;
            ArrayList<Vacuna> mArrayList = new ArrayList<Vacuna>();
            HttpClient httpClient = new DefaultHttpClient();

            System.out.println("Aca no es");
            System.out.println(par[0]);
            System.out.println(par[1]);
            HttpGet del =
                    new HttpGet("http://10.30.30.16:8084/DQ/webresources/com.dq.vacunas/vacunasmes/" + par[1] + "/" + par[0]);

            del.setHeader("content-type", "application/json");

            try {
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());

                JSONArray jArray = new JSONArray(respStr);
                if (jArray != null) {
                    System.out.println("Aca tampoco");
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObject = jArray.getJSONObject(i);

                        int idHijo, id, dosis, mes, aplicado;
                        String nombre, edad, fecha_apl, lote, responsable;

                        idHijo = jObject.getInt("VacunasPK.idHijo");
                        id = jObject.getInt("VacunasPK.idVacuna");
                        nombre = jObject.getString("nombre_vac");

                        if (jObject.isNull("edad")){ edad = " "; }
                        else { edad = jObject.getString("edad"); }

                        dosis = jObject.getInt("dosis");

                        if (jObject.isNull("fecha")){ fecha_apl = " "; }
                        else { fecha_apl = jObject.getString("fecha"); }

                        if (jObject.isNull("lote")) { lote = " ";}
                        else { lote = jObject.getString("lote"); }

                        if (jObject.isNull("responsable")) { responsable = " "; }
                        else { responsable = jObject.getString("responsable"); }

                        mes = jObject.getInt("mesAplicacion");
                        aplicado = jObject.getInt("aplicado");

                        Vacuna v = new Vacuna(id, nombre, idHijo, edad, dosis, fecha_apl, lote, responsable, mes, aplicado);

                        Utiles ut = new Utiles();

                        del =
                                new HttpGet("http://10.30.30.16:8084/DQ/webresources/com.dq.hijos/fecha/" + mHijoId);
                        resp = httpClient.execute(del);
                        respStr = EntityUtils.toString(resp.getEntity());
                        jObject = new JSONObject(respStr);
                        String dt = jObject.getString("value");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        try {
                            date = sdf.parse(dt);
                            System.out.println(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        fecha = ut.calcularFechaAAplicar(sdf.format(date), v.getMes_aplicacion());
                        v.setFecha_apl(fecha);
                        mArrayList.add(v);
                    }
                }
            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
            }
            return  mArrayList;
        }
    }



    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Vacuna>>();

        // Adding child data
        listDataHeader.add("Al nacer");
        listDataHeader.add("Dos meses");
        listDataHeader.add("Cuatro meses");
        listDataHeader.add("Seis meses");
        listDataHeader.add("Doce meses");
        listDataHeader.add("Quince meses");
        listDataHeader.add("Dieciocho meses");
        listDataHeader.add("Cuarenta y ocho meses");

        // Adding child data
        List<Vacuna> lista = new DatosLoadTask().doInBackground("0", String.valueOf(mHijoId));

        listDataChild.put(listDataHeader.get(0), lista); // Header, Child data

        //--------
        lista = new DatosLoadTask().doInBackground("2", String.valueOf(mHijoId));

        listDataChild.put(listDataHeader.get(1), lista); // Header, Child data

        //--------
        lista = new DatosLoadTask().doInBackground("4", String.valueOf(mHijoId));

        listDataChild.put(listDataHeader.get(2), lista); // Header, Child data

        //--------
        lista = new DatosLoadTask().doInBackground("6", String.valueOf(mHijoId));

        listDataChild.put(listDataHeader.get(3), lista); // Header, Child data

        //--------
        lista = new DatosLoadTask().doInBackground("12", String.valueOf(mHijoId));

        listDataChild.put(listDataHeader.get(4), lista); // Header, Child data

        //--------
        lista = new DatosLoadTask().doInBackground("15", String.valueOf(mHijoId));

        listDataChild.put(listDataHeader.get(5), lista); // Header, Child data

        //--------
        lista = new DatosLoadTask().doInBackground("18", String.valueOf(mHijoId));

        listDataChild.put(listDataHeader.get(6), lista); // Header, Child data

        //--------
        lista = new DatosLoadTask().doInBackground("48", String.valueOf(mHijoId));

        listDataChild.put(listDataHeader.get(7), lista); // Header, Child data
    }


}


