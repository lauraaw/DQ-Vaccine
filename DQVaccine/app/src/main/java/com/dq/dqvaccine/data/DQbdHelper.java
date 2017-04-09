package com.dq.dqvaccine.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.dq.dqvaccine.clases.Hijo;
import com.dq.dqvaccine.clases.Responsable;
import com.dq.dqvaccine.clases.Vacuna;
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
                //+ VacunasEntry._ID + " INTEGER PRIMARY KEY,"
                + VacunasEntry.ID + " INTEGER NOT NULL,"
                + VacunasEntry.NOMBRE_VAC + " TEXT NOT NULL,"
                + VacunasEntry.ID_HIJO + " INTEGER NOT NULL,"
                + VacunasEntry.EDAD + " TEXT,"
                + VacunasEntry.DOSIS + " INTEGER,"
                + VacunasEntry.FECHA + " TEXT,"
                + VacunasEntry.LOTE + " TEXT,"
                + VacunasEntry.RESPONSABLE + " TEXT,"
                + VacunasEntry.MES_APLICACION + " INTEGER NOT NULL,"
                + VacunasEntry.APLICADO + " INTEGER NOT NULL,"
                //+ "UNIQUE (" + VacunasEntry.ID + "),"
                + " PRIMARY KEY(" + VacunasEntry.ID + ", " + VacunasEntry.ID_HIJO + "), "
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

        insertarResponsable(sqLiteDatabase, new Responsable(901, 2589631, "Maria", "Villalba",
                "mariavi01@gmail.com", "09/02/1988", "San Lorenzo"));

        insertarResponsable(sqLiteDatabase, new Responsable(902, 1986700, "Miguel", "Benitez",
                "maba85@gmail.com", "13/12/1985", "Asunción"));

        insertarResponsable(sqLiteDatabase, new Responsable(903, 2444888, "Mercedes", "Ibarra",
                "merceiba@gmail.com", "19/03/1990", "Luque"));

        insertarResponsable(sqLiteDatabase, new Responsable(904, 2589631, "Victor", "Galeano",
                "victorgale@gmail.com", "10/06/1985", "Asunción"));

        //Datos de los Hijos

        insertarHijos(sqLiteDatabase, new Hijo(1, 7777778, "Teo", "Villalba", "10/02/2017",
                "San Lorenzo", "M", "Paraguaya", "10 de Agosto 123", "Central", "San Lorenzo",
                "San Isidro", null, "Maria Villalba", "0981111222", null, null, 901));

        insertarHijos(sqLiteDatabase, new Hijo(2, 7777779, "Sol", "Villalba", "10/02/2017",
                "San Lorenzo", "F", "Paraguaya", "10 de Agosto 123", "Central", "San Lorenzo",
                "San Isidro", null, "Maria Villalba", "0981111222", null, null, 901));

        insertarHijos(sqLiteDatabase, new Hijo(3, 7777650, "Fernando", "Benítez", "18/11/2016",
                "Asunción", "M", "Paraguaya", "10 de Agosto 125", "Central", "San Lorenzo",
                "San Isidro", null, "Miguel Benítez", "0981123456", null, null, 902));

        insertarHijos(sqLiteDatabase, new Hijo(4, 7777681, "Sarah", "Duarte", "10/09/2016",
                "Luque", "F", "Paraguaya", "Comandante Peralta 259", "Central", "Luque",
                "San Juan", null, "Mercedes Ibarra", "0971650859", null, null, 903));

        insertarHijos(sqLiteDatabase, new Hijo(5, 7777100, "Larissa", "Galeano", "15/03/2016",
                "Asunción", "F", "Paraguaya", "Paz del Chaco 820", "Central", "Asunción",
                "Santa María", null, "Victor Galeano", "0972333999", null, null, 904));

        //Datos de las vacunas

        insertarVacuna(sqLiteDatabase, new Vacuna(100, "BCG (Tuberculosis)", 3 , "0 meses", 1,
                "18/11/2016", "H018653", "Lic. Beatriz Orué", 0, 1));

        insertarVacuna(sqLiteDatabase, new Vacuna(101, "Rotavirus", 3 , "2 meses", 1,
                "20/01/2017", "H018900", "Lic. Beatriz Orué", 2, 1));

        insertarVacuna(sqLiteDatabase, new Vacuna(102, "IPV", 3, "2 meses", 1,
                "20/01/2017", "I032950", "Lic. Beatriz Orué", 2, 1));

        insertarVacuna(sqLiteDatabase, new Vacuna(103, "PCV 10 Valente", 3, "2 meses", 1,
                "25/01/2017", "P993446", "Lic. Oscar Duré", 2, 1));

        insertarVacuna(sqLiteDatabase, new Vacuna(104, "Pentavalente", 3, "2 meses", 1,
                "25/01/2017", "H015963", "Lic. Oscar Duré", 2, 1));

        insertarVacuna(sqLiteDatabase, new Vacuna(105, "OPV/IPV", 3, "4 meses", 1,
                "18/03/2017", "P233456", "Lic. Norma Aquino", 4, 1));

        insertarVacuna(sqLiteDatabase, new Vacuna(106, "Rotavirus", 3, "4 meses", 2,
                "18/03/2017", "H018901", "Lic. Norma Aquino", 4, 1));

        insertarVacuna(sqLiteDatabase, new Vacuna(107, "PCV 10 Valente", 3, " ", 2,
                " ", " ", " ", 4, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(108, "Pentavalente", 3, " ", 2,
                " ", " ", " ", 4, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(109, "OPV/IPV", 3, " ", 2,
                " ", " ", " ", 6, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(110, "Pentavalente", 3, " ", 3,
                " ", " ", " ", 6, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(111, "Influenza 1RA", 3, " ", 1,
                " ", " ", " ", 6, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(112, "Influenza 2RA", 3, " ", 1,
                " ", " ", " ", 6, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(113, "SPR", 3, " ", 1,
                " ", " ", " ", 12, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(114, "PCV 10 REF.", 3, " ", 1,
                " ", " ", " ", 12, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(115, "AA", 3, " ", 1,
                " ", " ", " ", 12, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(116, "Influenza.", 3, " ", 1,
                " ", " ", " ", 12, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(117, "V.V.Z.", 3, " ", 1,
                " ", " ", " ", 15, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(118, "V.H.A.", 3, " ", 1,
                " ", " ", " ", 15, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(119, "OPV/IPV", 3, " ", 3,
                " ", " ", " ", 18, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(120, "D.P.T.", 3, " ", 1,
                " ", " ", " ", 18, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(121, "OPV/IPV", 3, " ", 4,
                " ", " ", " ", 48, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(122, "D.P.T.", 3, " ", 2,
                " ", " ", " ", 48, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(123, "SPR", 3, " ", 2,
                " ", " ", " ", 48, 0));

        insertarTodo(sqLiteDatabase, 1);
        insertarTodo(sqLiteDatabase, 2);
        insertarTodo(sqLiteDatabase, 4);
        insertarTodo(sqLiteDatabase, 5);
    }

    private void insertarTodo(SQLiteDatabase sqLiteDatabase, int i) {
        insertarVacuna(sqLiteDatabase, new Vacuna(100, "BCG (Tuberculosis)", i, " ", 1,
                " ", " ", " ", 0, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(101, "Rotavirus", i , " ", 1,
                " ", " ", " ", 2, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(102, "IPV", i, " ", 1,
                " ", " ", " ", 2, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(103, "PCV 10 Valente", i, " ", 1,
                " ", " ", " ", 2, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(104, "Pentavalente", i, " ", 1,
                " ", " ", " ", 2, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(105, "OPV/IPV", i, " ", 1,
                " ", " ", " ", 4, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(106, "Rotavirus", i, " ", 2,
                " ", " ", " ", 4, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(107, "PCV 10 Valente", i, " ", 2,
                " ", " ", " ", 4, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(108, "Pentavalente", i, " ", 2,
                " ", " ", " ", 4, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(109, "OPV/IPV", i, " ", 2,
                " ", " ", " ", 6, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(110, "Pentavalente", i, " ", 3,
                " ", " ", " ", 6, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(111, "Influenza 1RA", i, " ", 1,
                " ", " ", " ", 6, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(112, "Influenza 2RA", i, " ", 1,
                " ", " ", " ", 6, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(113, "SPR", i, " ", 1,
                " ", " ", " ", 12, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(114, "PCV 10 REF.", i, " ", 1,
                " ", " ", " ", 12, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(115, "AA", i, " ", 1,
                " ", " ", " ", 12, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(116, "Influenza.", i, " ", 1,
                " ", " ", " ", 12, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(117, "V.V.Z.", i, " ", 1,
                " ", " ", " ", 15, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(118, "V.H.A.", i, " ", 1,
                " ", " ", " ", 15, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(119, "OPV/IPV", i, " ", 3,
                " ", " ", " ", 18, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(120, "D.P.T.", i, " ", 1,
                " ", " ", " ", 18, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(121, "OPV/IPV", i, " ", 4,
                " ", " ", " ", 48, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(122, "D.P.T.", i, " ", 2,
                " ", " ", " ", 48, 0));

        insertarVacuna(sqLiteDatabase, new Vacuna(123, "SPR", i, " ", 2,
                " ", " ", " ", 48, 0));
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

    public long insertarVacuna(SQLiteDatabase db, Vacuna vacuna){
        return db.insert(
                VacunasEntry.TABLE_NAME,
                null,
                vacuna.toContentValues());
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

    public Cursor getVacunasByMes(String vacunaMes, String hijoId) {
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM "
                + VacunasEntry.TABLE_NAME + " WHERE " +
                VacunasEntry.MES_APLICACION + " = ? AND " +
                VacunasEntry.ID_HIJO + " = ?"
                , new String[]{vacunaMes, hijoId});
        return c;
    }
}
