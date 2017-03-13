package com.dq.dqvaccine.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dq.dqvaccine.clases.Hijo;
import com.dq.dqvaccine.data.DQContract.HijosEntry;

public class DQbdHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DQ.db";

    public DQbdHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS" + HijosEntry.TABLE_NAME + " ("
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
                + "UNIQUE (" + HijosEntry.ID + "))"
        );

        insertarDatos(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertarDatos( SQLiteDatabase sqLiteDatabase) {
        Log.d("HEY", "Crea:" );
        insertarHijos(sqLiteDatabase, new Hijo(1, 7777778, "Teo", "Villalba", "10/02/2017",
                "San Lorenzo", "M", "Paraguaya", "10 de Agosto 123", "Central", "San Lorenzo",
                "San Isidro", null, "Maria Villalba", "0981111222", null, null));

        insertarHijos(sqLiteDatabase, new Hijo(2, 7777779, "Sol", "Villalba", "10/02/2017",
                "San Lorenzo", "F", "Paraguaya", "10 de Agosto 123", "Central", "San Lorenzo",
                "San Isidro", null, "Maria Villalba", "0981111222", null, null));
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
                HijosEntry.ID + " = ",
                new String[]{hijoId},
                null,
                null,
                null);
        return c;
    }
}
