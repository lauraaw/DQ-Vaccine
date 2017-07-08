package com.dq.dqvaccine;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//TODO: Crear vencido

public class Utiles {

    public Utiles(){

    }

    public boolean enTiempo(String dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = new Date();
        try {
            fecha = sdf.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date hoy = new Date();
        return fecha.before(hoy);
    }

    public String calcularNotificacion(String dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_YEAR, -2);
        dt = sdf.format(c.getTime(  ));  // dt is now the new date
        System.out.println(dt);
        return dt;
    }

    public boolean vencido(String dt){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.MONTH, 1);
        Date fecha = c.getTime();
        Date hoy = new Date();
        return fecha.before(hoy);
    }

    public abstract class Path{

        public static final String ip = "10.30.30.16";
        public static final String correoPath = "http://" + ip + ":8084/DQ/webresources/com.dq.usuarios/correo";
        public static final String hijosPath = "http://" + ip + ":8084/DQ/webresources/com.dq.hijos/hijos";
        public static final String hijoPath = "http://" + ip + ":8084/DQ/webresources/com.dq.hijos/hijo";
        public static final String vacunasPath = "http://" + ip + ":8084/DQ/webresources/com.dq.vacunas/vacunas";
        public static final String vacunasnoPath = "http://" + ip + ":8084/DQ/webresources/com.dq.vacunas/vacunasnoapl";

    }

}

