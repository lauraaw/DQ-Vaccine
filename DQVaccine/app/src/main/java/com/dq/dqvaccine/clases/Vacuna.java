package com.dq.dqvaccine.clases;

public class Vacuna {
    private int id;
    private String nombre_vac;
    private int id_hijo;
    private String edad;
    private int dosis;
    private String fecha;
    private String lote;
    private String responsable;
    private int mes_aplicacion;
    private int aplicado;
    private String fecha_apl;
    private int vencido;

    public Vacuna(int id, String nombre_vac, int id_hijo, String edad, int dosis, String fecha,
                  String lote, String responsable, int mes_aplicacion, int aplicado, String fecha_apl,
                  int vencido) {
        this.id = id;
        this.nombre_vac = nombre_vac;
        this.id_hijo = id_hijo;
        this.edad = edad;
        this.dosis = dosis;
        this.fecha = fecha;
        this.lote = lote;
        this.responsable = responsable;
        this.mes_aplicacion = mes_aplicacion;
        this.aplicado = aplicado;
        this.fecha_apl = fecha_apl;
        this.vencido = vencido;
    }

    public int getId() {
        return id;
    }

    public String getNombre_vac() {
        return nombre_vac;
    }

    public int getId_hijo() {
        return id_hijo;
    }

    public String getEdad() {
        return edad;
    }

    public int getDosis() {
        return dosis;
    }

    public String getFecha() {
        return fecha;
    }

    public String getLote() {
        return lote;
    }

    public String getResponsable() {
        return responsable;
    }

    public int getMes_aplicacion() {
        return mes_aplicacion;
    }

    public void setFecha_apl(String fecha) {
        this.fecha_apl = fecha;
    }

    public String getFecha_apl() { return fecha_apl;}

    public int getAplicado() { return  aplicado;}

    public int getVencido() { return vencido; }

    public void setVencido(int vencido) { this.vencido = vencido; }
}
