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
import android.widget.ListView;

import com.dq.dqvaccine.R;
import com.dq.dqvaccine.clases.HijosCursorAdapter;
import com.dq.dqvaccine.data.DQContract.HijosEntry;
import com.dq.dqvaccine.data.DQbdHelper;


public class HijosFragment extends Fragment {

    private DQbdHelper mDQbdHelper;

    private ListView mHijosList;
    private HijosCursorAdapter mHijosAdapter;
    //private SimpleCursorAdapter mHijosAdapter;


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
        /*mHijosAdapter = new SimpleCursorAdapter(
                getActivity(), // Context context
                android.R.layout.two_line_list_item, // int layout
                mDQbdHelper.getAllHijos(), // Cursor c
                new String[]{HijosEntry.NOMBRE + " " + HijosEntry.APELLIDO, HijosEntry.CI}, // String[] from
                new int[]{android.R.id.text1, android.R.id.text2}, // int[] to
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER // int flags
        );*/
        mHijosList.setAdapter(mHijosAdapter);

        mDQbdHelper = new DQbdHelper(getActivity());
        /*SQLiteDatabase db = mDQbdHelper.getWritableDatabase();
        mDQbdHelper.insertarDatos(db);*/

        loadHijos();
        return root;
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
            } else {
                // Mostrar empty state
            }
        }


    }}

