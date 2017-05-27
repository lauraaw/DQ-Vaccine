/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dq;

import com.dq.Hijos;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laura
 */
@Entity
@Table(name = "\"Vacunas\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vacunas.findAll", query = "SELECT v FROM Vacunas v")
    , @NamedQuery(name = "Vacunas.findByIdVacuna", query = "SELECT v FROM Vacunas v WHERE v.vacunasPK.idVacuna = :idVacuna")
    , @NamedQuery(name = "Vacunas.findByIdHijo", query = "SELECT v FROM Vacunas v WHERE v.vacunasPK.idHijo = :idHijo")
    , @NamedQuery(name = "Vacunas.findByNombreVac", query = "SELECT v FROM Vacunas v WHERE v.nombreVac = :nombreVac")
    , @NamedQuery(name = "Vacunas.findByEdad", query = "SELECT v FROM Vacunas v WHERE v.edad = :edad")
    , @NamedQuery(name = "Vacunas.findByDosis", query = "SELECT v FROM Vacunas v WHERE v.dosis = :dosis")
    , @NamedQuery(name = "Vacunas.findByFecha", query = "SELECT v FROM Vacunas v WHERE v.fecha = :fecha")
    , @NamedQuery(name = "Vacunas.findByLote", query = "SELECT v FROM Vacunas v WHERE v.lote = :lote")
    , @NamedQuery(name = "Vacunas.findByResponsable", query = "SELECT v FROM Vacunas v WHERE v.responsable = :responsable")
    , @NamedQuery(name = "Vacunas.findByMesAplicacion", query = "SELECT v FROM Vacunas v WHERE v.mesAplicacion = :mesAplicacion")
    , @NamedQuery(name = "Vacunas.findByAplicado", query = "SELECT v FROM Vacunas v WHERE v.aplicado = :aplicado")
    , @NamedQuery(name = "Vacunas.findByFechaApl", query = "SELECT v FROM Vacunas v WHERE v.fechaApl = :fechaApl")})
public class Vacunas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VacunasPK vacunasPK;
    @Size(max = 2147483647)
    @Column(name = "nombre_vac")
    private String nombreVac;
    @Size(max = 2147483647)
    @Column(name = "edad")
    private String edad;
    @Column(name = "dosis")
    private Integer dosis;
    @Size(max = 2147483647)
    @Column(name = "fecha")
    private String fecha;
    @Size(max = 2147483647)
    @Column(name = "lote")
    private String lote;
    @Size(max = 2147483647)
    @Column(name = "responsable")
    private String responsable;
    @Column(name = "mes_aplicacion")
    private Integer mesAplicacion;
    @Column(name = "aplicado")
    private Integer aplicado;
    @Size(max = 2147483647)
    @Column(name = "fecha_apl")
    private String fechaApl;
   /* @JoinColumn(name = "id_hijo", referencedColumnName = "id_hijo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Hijos hijos;*/

    public Vacunas() {
    }

    public Vacunas(VacunasPK vacunasPK) {
        this.vacunasPK = vacunasPK;
    }

    public Vacunas(int idVacuna, int idHijo) {
        this.vacunasPK = new VacunasPK(idVacuna, idHijo);
    }
    
    public Vacunas(int mes){
        this.mesAplicacion = mes;
    }

    public VacunasPK getVacunasPK() {
        return vacunasPK;
    }

    public void setVacunasPK(VacunasPK vacunasPK) {
        this.vacunasPK = vacunasPK;
    }

    public String getNombreVac() {
        return nombreVac;
    }

    public void setNombreVac(String nombreVac) {
        this.nombreVac = nombreVac;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Integer getDosis() {
        return dosis;
    }

    public void setDosis(Integer dosis) {
        this.dosis = dosis;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Integer getMesAplicacion() {
        return mesAplicacion;
    }

    public void setMesAplicacion(Integer mesAplicacion) {
        this.mesAplicacion = mesAplicacion;
    }

    public Integer getAplicado() {
        return aplicado;
    }

    public void setAplicado(Integer aplicado) {
        this.aplicado = aplicado;
    }

    public String getFechaApl() {
        return fechaApl;
    }

    public void setFechaApl(String fechaApl) {
        this.fechaApl = fechaApl;
    }

    /*public Hijos getHijos() {
        return hijos;
    }

    public void setHijos(Hijos hijos) {
        this.hijos = hijos;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vacunasPK != null ? vacunasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacunas)) {
            return false;
        }
        Vacunas other = (Vacunas) object;
        if ((this.vacunasPK == null && other.vacunasPK != null) || (this.vacunasPK != null && !this.vacunasPK.equals(other.vacunasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dq.service.Vacunas[ vacunasPK=" + vacunasPK + " ]";
    }
    
}
