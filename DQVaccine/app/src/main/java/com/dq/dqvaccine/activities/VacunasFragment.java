package com.dq.dqvaccine.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dq.dqvaccine.R;
import com.dq.dqvaccine.clases.ExpandableListAdapter;
import com.dq.dqvaccine.data.DQContract;
import com.dq.dqvaccine.data.DQbdHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO: Crear tantos DatosLoadTasks se necesiten segun mes_aplicacion
//TODO: En DatosLoadTask, hacer Vacuna(cursor) y add a ArrayList<Vacuna>
//TODO: listDataChild debe ser List<Vacuna>

public class VacunasFragment extends Fragment {

    private static final String ARG_HIJO_ID = "hijoId";
    private int mHijoId;

    private DQbdHelper mDQbdHelper;

    private ExpandableListView mVacunasList;
    private ExpandableListAdapter mVacunasAdapter;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    public VacunasFragment() {
        // Required empty public constructor
    }

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

    private class DatosLoadTask extends AsyncTask<Void, Void, ArrayList> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            Cursor cursor = mDQbdHelper.getHijoBySex("M");
            ArrayList<String> mArrayList = new ArrayList<String>();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                // The Cursor is now set to the right position
                mArrayList.add(cursor.getString(cursor.getColumnIndex(DQContract.HijosEntry.NOMBRE))
                        + " " + cursor.getString(cursor.getColumnIndex(DQContract.HijosEntry.APELLIDO)));
            }
            return  mArrayList;
        }
    }

    private class DatosLoadTask2 extends AsyncTask<Void, Void, ArrayList> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            Cursor cursor = mDQbdHelper.getHijoBySex("F");
            ArrayList<String> mArrayList = new ArrayList<String>();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                // The Cursor is now set to the right position
                mArrayList.add(cursor.getString(cursor.getColumnIndex(DQContract.HijosEntry.NOMBRE))
                        + " " + cursor.getString(cursor.getColumnIndex(DQContract.HijosEntry.APELLIDO)));
            }
            return  mArrayList;
        }



    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Masculino");
        listDataHeader.add("Femenino");
        listDataHeader.add("Femenino");

        // Adding child data
        List<String> masculino1 = new DatosLoadTask().doInBackground();


        List<String> femenino1 = new DatosLoadTask2().doInBackground();


        List<String> femenino2 = new DatosLoadTask2().doInBackground();


        listDataChild.put(listDataHeader.get(0), masculino1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), femenino1);
        listDataChild.put(listDataHeader.get(2), femenino2);
    }
}

