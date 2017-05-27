package com.dq.dqvaccine.data;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos
 */
public class DQContract {

    public static abstract class HijosEntry{
        public static final String TABLE_NAME ="Hijos";

        public static final String ID = "id";
        public static final String CI = "cedula";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDO = "apellido";
        public static final String FECHA_NAC = "fecha_nac";
        public static final String LUGAR_NAC = "lugar_nac";
        public static final String SEXO = "sexo";
        public static final String NACIONALIDAD = "nacionalidad";
        public static final String DIRECCION = "direccion";
        public static final String DEPARTAMENTO = "departamento";
        public static final String MUNICIPIO = "municipio";
        public static final String BARRIO = "barrio";
        public static final String ID_USUARIO = "id_usuario";
        public static String _ID;
    }

    public static abstract class VacunasEntry implements BaseColumns{
        public static final String TABLE_NAME ="vacunas";

        public static final String ID = "id";
        public static final String NOMBRE_VAC = "nombre_vac";
        public static final String ID_HIJO = "id_hijo";
        public static final String EDAD = "EDAD";
        public static final String DOSIS = "dosis";
        public static final String FECHA = "fecha";
        public static final String LOTE = "lote";
        public static final String RESPONSABLE = "responsable";
        public static final String MES_APLICACION = "mes_aplicacion";
        public static final String APLICADO = "aplicado";
    }
}
