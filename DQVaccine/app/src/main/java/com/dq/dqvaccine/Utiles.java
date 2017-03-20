package com.dq.dqvaccine;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utiles {

    public Utiles(){

    }

    public String calcularFechaAAplicar(String dt, int dias) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, dias);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        return dt;
    }

    public boolean vencido(String dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        Date fecha = new Date();
        try {
            fecha = sdf.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date hoy = new Date();
        return fecha.after(hoy);
    }
}
