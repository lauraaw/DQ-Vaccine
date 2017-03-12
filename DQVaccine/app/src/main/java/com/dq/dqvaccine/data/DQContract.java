package com.dq.dqvaccine.data;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos
 */
public class DQContract {

    public static abstract class HijosEntry implements BaseColumns{
        public static final String TABLE_NAME ="hijos";

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
        public static final String REFERENCIA = "referencia";
        public static final String NOMBRE_RESPONSABLE = "nombre_responsable";
        public static final String TEL = "tel";
        public static final String SEGURO = "seguro";
        public static final String ALERGIA = "alergia";
    }

}
