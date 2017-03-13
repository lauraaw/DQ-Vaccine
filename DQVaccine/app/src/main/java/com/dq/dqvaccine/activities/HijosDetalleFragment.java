package com.dq.dqvaccine.activities;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dq.dqvaccine.R;
import com.dq.dqvaccine.clases.Hijo;
import com.dq.dqvaccine.data.DQbdHelper;


public class HijosDetalleFragment extends Fragment {
    private static final String ARG_HIJO_ID = "hijoId";
    private int mHijoId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mCedula;
    private TextView mFechaNac;
    private TextView mSexo;
    private TextView mLugarNac;
    private TextView mNacionalidad;
    private TextView mDepartamento;
    private TextView mMunicipio;
    private TextView mBarrio;
    private TextView mDireccion;
    private TextView mAlergias;


    private DQbdHelper mDQbdHelper;


    public HijosDetalleFragment() {
        // Required empty public constructor
    }

    public static HijosDetalleFragment newInstance(int hijoId) {
        HijosDetalleFragment fragment = new HijosDetalleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_HIJO_ID, hijoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mHijoId = getArguments().getInt(ARG_HIJO_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hijos_detalle, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mCedula = (TextView) root.findViewById(R.id.tv_cedula);
        mFechaNac = (TextView) root.findViewById(R.id.tv_fecha_nac);
        mSexo = (TextView) root.findViewById(R.id.tv_sexo);
        mLugarNac = (TextView) root.findViewById(R.id.tv_lugar_nac);
        mNacionalidad = (TextView) root.findViewById(R.id.tv_nac);
        mDepartamento = (TextView) root.findViewById(R.id.tv_departamento);
        mMunicipio = (TextView) root.findViewById(R.id.tv_municipio);
        mBarrio = (TextView) root.findViewById(R.id.tv_barrio);
        mDireccion = (TextView) root.findViewById(R.id.tv_direccion);
        mAlergias = (TextView) root.findViewById(R.id.tv_alergia);

        mDQbdHelper = new DQbdHelper(getActivity());

        loadHijo();

        return root;
    }

    private void loadHijo() {
        new GetHijoByIdTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Acciones
    }

    private void showLawyer(Hijo hijo) {
        String nombre = hijo.getNombre() + " " + hijo.getApellido();
        mCollapsingView.setTitle(nombre);
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + ""))
                .centerCrop()
                .into(mAvatar);
        System.out.println(nombre);
        mCedula.setText(String.valueOf(hijo.getCi()));
        mFechaNac.setText(hijo.getFecha_nac());
        mSexo.setText(hijo.getSexo());
        mLugarNac.setText(hijo.getLugar_nac());
        mNacionalidad.setText(hijo.getNacionalidad());
        mDepartamento.setText(hijo.getDepartamento());
        mMunicipio.setText(hijo.getMunicipio());
        mBarrio.setText(hijo.getBarrio());
        mDireccion.setText(hijo.getDireccion());
        mAlergias.setText(hijo.getAlergias());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private class GetHijoByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDQbdHelper.getHijoById(String.valueOf(mHijoId));
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new Hijo(cursor));
            } else {
                showLoadError();
            }
        }

    }

}