package com.dq.dqvaccine.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.dq.dqvaccine.R;
import com.dq.dqvaccine.clases.ExpandableListAdapter;
import com.dq.dqvaccine.clases.VacunasCursorAdapter;
import com.dq.dqvaccine.data.DQContract;
import com.dq.dqvaccine.data.DQbdHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VacunasFragment extends Fragment {

    private DQbdHelper mDQbdHelper;

    private ExpandableListView mVacunasList;
    private ExpandableListAdapter mVacunasAdapter;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    public VacunasFragment() {
        // Required empty public constructor
    }

    public static VacunasFragment newInstance() {
        return new VacunasFragment();
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

        /*
        mVacunasList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mHijosAdapter.getItem(i);
                int currentHijoId = currentItem.getInt(
                        currentItem.getColumnIndex(HijosEntry.ID));
                System.out.println(currentHijoId);
                showDetailScreen(currentHijoId);
            }
        });
        */

        getActivity().deleteDatabase(DQbdHelper.DATABASE_NAME);


        return root;
    }

    /*
    private void showDetailScreen(int currentHijoId) {
        Intent intent = new Intent(getActivity(), HijosDetalleActivity.class);
        intent.putExtra(HijosActivity.EXTRA_HIJO_ID, currentHijoId);
        startActivity(intent);
    }
    */


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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

