package com.dq.dqvaccine.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//TODO: importar las clases de las vacunas y responsables.
import com.dq.dqvaccine.clases.Hijo;
import com.dq.dqvaccine.clases.Responsable;
import com.dq.dqvaccine.data.DQContract.HijosEntry;
import com.dq.dqvaccine.data.DQContract.ResponsablesEntry;
import com.dq.dqvaccine.data.DQContract.VacunasEntry;

public class DQbdHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DQ.db";

    public DQbdHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + ResponsablesEntry.TABLE_NAME + " ("
                + ResponsablesEntry._ID + " INTEGER PRIMARY KEY,"
                + ResponsablesEntry.ID + " INTEGER NOT NULL,"
                + ResponsablesEntry.CI + " INTEGER NOT NULL,"
                + ResponsablesEntry.NOMBRE + " TEXT NOT NULL,"
                + ResponsablesEntry.APELLIDO + " TEXT NOT NULL,"
                + ResponsablesEntry.CORREO + " TEXT NOT NULL,"
                + ResponsablesEntry.FECHA_NAC + " TEXT NOT NULL,"
                + ResponsablesEntry.LUGAR_NAC + " TEXT,"
                + "UNIQUE (" + ResponsablesEntry.ID + "))"
        );

        db.execSQL("CREATE TABLE " + HijosEntry.TABLE_NAME + " ("
                + HijosEntry._ID + " INTEGER PRIMARY KEY,"
                + HijosEntry.ID + " INTEGER NOT NULL,"
                + HijosEntry.CI + " INTEGER NOT NULL,"
                + HijosEntry.NOMBRE + " TEXT NOT NULL,"
                + HijosEntry.APELLIDO + " TEXT NOT NULL,"
                + HijosEntry.FECHA_NAC + " TEXT NOT NULL,"
                + HijosEntry.LUGAR_NAC + " TEXT,"
                + HijosEntry.SEXO + " TEXT NOT NULL,"
                + HijosEntry.NACIONALIDAD + " TEXT NOT NULL,"
                + HijosEntry.DIRECCION + " TEXT,"
                + HijosEntry.DEPARTAMENTO + " TEXT,"
                + HijosEntry.MUNICIPIO + " TEXT,"
                + HijosEntry.BARRIO + " TEXT,"
                + HijosEntry.REFERENCIA + " TEXT,"
                + HijosEntry.NOMBRE_RESPONSABLE + " TEXT NOT NULL,"
                + HijosEntry.TEL + " TEXT,"
                + HijosEntry.SEGURO + " TEXT,"
                + HijosEntry.ALERGIA + " TEXT,"
                + HijosEntry.ID_RESP + " INTEGER NOT NULL,"
                + "UNIQUE (" + HijosEntry.ID + "),"
                + "FOREIGN KEY (" + HijosEntry.ID_RESP + ") REFERENCES "
                + ResponsablesEntry.TABLE_NAME + "(" + ResponsablesEntry.ID + "))"
        );

        db.execSQL("CREATE TABLE " + VacunasEntry.TABLE_NAME + " ("
                + VacunasEntry._ID + " INTEGER PRIMARY KEY,"
                + VacunasEntry.ID + " INTEGER NOT NULL,"
                + VacunasEntry.NOMBRE_VAC + " TEXT NOT NULL,"
                + VacunasEntry.ID_HIJO + " INTEGER NOT NULL,"
                + VacunasEntry.EDAD + " TEXT,"
                + VacunasEntry.DOSIS + " INTEGER,"
                + VacunasEntry.FECHA + " TEXT,"
                + VacunasEntry.LOTE + " TEXT,"
                + VacunasEntry.RESPONSABLE + " TEXT,"
                + VacunasEntry.MES_APLICACION + " INTEGER,"
                + "UNIQUE (" + VacunasEntry.ID + "),"
                + "FOREIGN KEY (" + VacunasEntry.ID_HIJO + ") REFERENCES "
                + HijosEntry.TABLE_NAME + "(" + HijosEntry.ID + "))"
        );

        insertarDatos(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertarDatos( SQLiteDatabase sqLiteDatabase) {

        //Datos de los Responsables

        insertarResponsable(sqLiteDatabase, new Responsable(101, 2589631, "Maria", "Villalba",
                "mariavi01@gmail.com", "09/02/1988", "San Lorenzo"));

        insertarResponsable(sqLiteDatabase, new Responsable(102, 1986700, "Miguel", "Benitez",
                "maba85@gmail.com", "13/12/1985", "Asunción"));

        insertarResponsable(sqLiteDatabase, new Responsable(103, 2444888, "Mercedes", "Ibarra",
                "merceiba@gmail.com", "19/03/1990", "Luque"));

        insertarResponsable(sqLiteDatabase, new Responsable(104, 2589631, "Victor", "Galeano",
                "victorgale@gmail.com", "10/06/1985", "Asunción"));

        //Datos de los Hijos

        insertarHijos(sqLiteDatabase, new Hijo(1, 7777778, "Teo", "Villalba", "10/02/2017",
                "San Lorenzo", "M", "Paraguaya", "10 de Agosto 123", "Central", "San Lorenzo",
                "San Isidro", null, "Maria Villalba", "0981111222", null, null, 101));

        insertarHijos(sqLiteDatabase, new Hijo(2, 7777779, "Sol", "Villalba", "10/02/2017",
                "San Lorenzo", "F", "Paraguaya", "10 de Agosto 123", "Central", "San Lorenzo",
                "San Isidro", null, "Maria Villalba", "0981111222", null, null, 101));

        insertarHijos(sqLiteDatabase, new Hijo(3, 7777650, "Fernando", "Benítez", "18/11/2016",
                "Asunción", "M", "Paraguaya", "10 de Agosto 125", "Central", "San Lorenzo",
                "San Isidro", null, "Miguel Benítez", "0981123456", null, null, 102));

        insertarHijos(sqLiteDatabase, new Hijo(4, 7777681, "Sarah", "Duarte", "10/09/2016",
                "Luque", "F", "Paraguaya", "Comandante Peralta 259", "Central", "Luque",
                "San Juan", null, "Mercedes Ibarra", "0971650859", null, null, 103));

        insertarHijos(sqLiteDatabase, new Hijo(5, 7777100, "Larissa", "Galeano", "15/03/2016",
                "Asunción", "F", "Paraguaya", "Paz del Chaco 820", "Central", "Asunción",
                "Santa María", null, "Victor Galeano", "0972333999", null, null, 104));
    }

    public long insertarResponsable(SQLiteDatabase db, Responsable responsable){
        return db.insert(
                ResponsablesEntry.TABLE_NAME,
                null,
                responsable.toContentValues());
    }


    public long insertarHijos(SQLiteDatabase db, Hijo hijo){
        return db.insert(
                HijosEntry.TABLE_NAME,
                null,
                hijo.toContentValues());
    }

    public Cursor getAllHijos() {
        return getReadableDatabase()
                .query(
                        HijosEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getHijoById(String hijoId) {
        Cursor c = getReadableDatabase().query(
                HijosEntry.TABLE_NAME,
                null,
                HijosEntry.ID + " = ?",
                new String[]{hijoId},
                null,
                null,
                null);
        return c;
    }

    public Cursor getHijoBySex(String hijoSexo) {
        Cursor c = getReadableDatabase().query(
                HijosEntry.TABLE_NAME,
                null,
                HijosEntry.SEXO + " = ?",
                new String[]{hijoSexo},
                null,
                null,
                null);
        return c;
    }
}
