/**
 * Created by Samuel on 18/3/2017.
 */
package com.dq.dqvaccine.clases;
import android.content.ContentValues;
import android.database.Cursor;

import com.dq.dqvaccine.data.DQContract.ResponsablesEntry;

public class Responsable {
    private int id;
    private int ci;
    private String nombre;
    private String apellido;
    private String correo;
    private String fecha_nac;
    private String lugar_nac;

    public Responsable(int id, int ci, String nombre,
                       String apellido, String correo,
                       String fecha_nac, String lugar_nac) {
        this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fecha_nac = fecha_nac;
        this.lugar_nac = lugar_nac;
    }

    public Responsable(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex(ResponsablesEntry.ID));
        ci = cursor.getInt(cursor.getColumnIndex(ResponsablesEntry.CI));
        nombre = cursor.getString(cursor.getColumnIndex(ResponsablesEntry.NOMBRE));
        apellido = cursor.getString(cursor.getColumnIndex(ResponsablesEntry.APELLIDO));
        correo = cursor.getString(cursor.getColumnIndex(ResponsablesEntry.CORREO));
        fecha_nac = cursor.getString(cursor.getColumnIndex(ResponsablesEntry.FECHA_NAC));
        lugar_nac = cursor.getString(cursor.getColumnIndex(ResponsablesEntry.LUGAR_NAC));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ResponsablesEntry.ID, id);
        values.put(ResponsablesEntry.CI, ci);
        values.put(ResponsablesEntry.NOMBRE, nombre);
        values.put(ResponsablesEntry.APELLIDO, apellido);
        values.put(ResponsablesEntry.CORREO, correo);
        values.put(ResponsablesEntry.FECHA_NAC, fecha_nac);
        values.put(ResponsablesEntry.LUGAR_NAC, lugar_nac);
        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getLugar_nac() {
        return lugar_nac;
    }

    public void setLugar_nac(String lugar_nac) {
        this.lugar_nac = lugar_nac;
    }
}