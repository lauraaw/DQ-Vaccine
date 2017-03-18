package com.dq.dqvaccine.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dq.dqvaccine.R;
import com.dq.dqvaccine.clases.VacunasCursorAdapter;
import com.dq.dqvaccine.data.DQContract.HijosEntry;
import com.dq.dqvaccine.data.DQbdHelper;


public class VacunasFragment extends Fragment {

    private DQbdHelper mDQbdHelper;

    private ListView mVacunasList1;
    private ListView mVacunasList2;
    private VacunasCursorAdapter mVacunasAdapter1;
    private VacunasCursorAdapter mVacunasAdapter2;
    //private SimpleCursorAdapter mHijosAdapter;


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

        mVacunasList1 = (ListView) root.findViewById(R.id.vacunas_list1);
        mVacunasList2 = (ListView) root.findViewById(R.id.vacunas_list2);
        mVacunasAdapter1 = new VacunasCursorAdapter(getActivity(), null);
        mVacunasAdapter2 = new VacunasCursorAdapter(getActivity(), null);

        mVacunasList1.setAdapter(mVacunasAdapter1);
        mVacunasList2.setAdapter(mVacunasAdapter2);

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

        mDQbdHelper = new DQbdHelper(getActivity());
        /*SQLiteDatabase db = mDQbdHelper.getWritableDatabase();
        mDQbdHelper.insertarDatos(db);*/

        loadDatos();
        return root;
    }

    /*
    private void showDetailScreen(int currentHijoId) {
        Intent intent = new Intent(getActivity(), HijosDetalleActivity.class);
        intent.putExtra(HijosActivity.EXTRA_HIJO_ID, currentHijoId);
        startActivity(intent);
    }
    */

    private void loadDatos() {
        new DatosLoadTask().execute();
        new DatosLoadTask2().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private class DatosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDQbdHelper.getHijoBySex("M");
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mVacunasAdapter1.swapCursor(cursor);
            } else {
                // Mostrar emtpty state
            }
        }


    }

    private class DatosLoadTask2 extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDQbdHelper.getHijoBySex("F");
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mVacunasAdapter2.swapCursor(cursor);
            } else {
                // Mostrar emtpty state
            }
        }


    }
}

