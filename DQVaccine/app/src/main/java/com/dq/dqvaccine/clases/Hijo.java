package com.dq.dqvaccine.clases;

import android.content.ContentValues;

import com.dq.dqvaccine.data.DQContract.HijosEntry;

import java.util.Date;



/**
 * Created by Laura on 3/12/2017.
 */

public class Hijo {
    private int id;
    private int ci;
    private String nombre;
    private String apellido;
    private String fecha_nac;
    private String lugar_nac;
    private String sexo;
    private String nacionalidad;
    private String direccion;
    private String departamento;
    private String municipio;
    private String barrio;
    private String referencia;
    private String nombre_resp;
    private String tel;
    private String seguro;
    private String alergias;

    public Hijo(int id, int ci, String nombre, String apellido,
                 String fecha_nac, String lugar_nac, String sexo,
                 String nacionalidad, String direccion,
                 String departamento, String municipio,
                 String barrio, String referencia,
                 String nombre_resp, String tel,
                 String seguro, String alergias){
        this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nac = fecha_nac;
        this.lugar_nac = lugar_nac;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.departamento = departamento;
        this.municipio = municipio;
        this.barrio = barrio;
        this.referencia = referencia;
        this.nombre_resp = nombre_resp;
        this.tel = tel;
        this.seguro = seguro;
        this.alergias = alergias;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(HijosEntry.ID, id);
        values.put(HijosEntry.CI, ci);
        values.put(HijosEntry.NOMBRE, nombre);
        values.put(HijosEntry.APELLIDO, apellido);
        values.put(HijosEntry.FECHA_NAC, fecha_nac);
        values.put(HijosEntry.LUGAR_NAC, lugar_nac);
        values.put(HijosEntry.SEXO, sexo);
        values.put(HijosEntry.NACIONALIDAD, nacionalidad);
        values.put(HijosEntry.DIRECCION, direccion);
        values.put(HijosEntry.DEPARTAMENTO, departamento);
        values.put(HijosEntry.MUNICIPIO, municipio);
        values.put(HijosEntry.BARRIO, barrio);
        values.put(HijosEntry.REFERENCIA, referencia);
        values.put(HijosEntry.NOMBRE_RESPONSABLE, nombre_resp);
        values.put(HijosEntry.TEL, tel);
        values.put(HijosEntry.SEGURO, seguro);
        values.put(HijosEntry.ALERGIA, alergias);
        return values;
    }

    public int getId() {
        return id;
    }

    public int getCi() {
        return ci;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public String getLugar_nac() {
        return lugar_nac;
    }

    public String getSexo() {
        return sexo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getNombre_resp() {
        return nombre_resp;
    }

    public String getTel() {
        return tel;
    }

    public String getSeguro() {
        return seguro;
    }

    public String getAlergias() {
        return alergias;
    }
}
