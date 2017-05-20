/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dq;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laura
 */
@Entity
@Table(name = "\"Hijos\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hijos.findAll", query = "SELECT h FROM Hijos h")
    , @NamedQuery(name = "Hijos.findByIdHijo", query = "SELECT h FROM Hijos h WHERE h.idHijo = :idHijo")
    , @NamedQuery(name = "Hijos.findByCi", query = "SELECT h FROM Hijos h WHERE h.ci = :ci")
    , @NamedQuery(name = "Hijos.findByNombre", query = "SELECT h FROM Hijos h WHERE h.nombre = :nombre")
    , @NamedQuery(name = "Hijos.findByApellido", query = "SELECT h FROM Hijos h WHERE h.apellido = :apellido")
    , @NamedQuery(name = "Hijos.findByFechaNac", query = "SELECT h FROM Hijos h WHERE h.fechaNac = :fechaNac")
    , @NamedQuery(name = "Hijos.findByLugarNac", query = "SELECT h FROM Hijos h WHERE h.lugarNac = :lugarNac")
    , @NamedQuery(name = "Hijos.findBySexo", query = "SELECT h FROM Hijos h WHERE h.sexo = :sexo")
    , @NamedQuery(name = "Hijos.findByNacionalidad", query = "SELECT h FROM Hijos h WHERE h.nacionalidad = :nacionalidad")
    , @NamedQuery(name = "Hijos.findByDireccion", query = "SELECT h FROM Hijos h WHERE h.direccion = :direccion")
    , @NamedQuery(name = "Hijos.findByMunicipio", query = "SELECT h FROM Hijos h WHERE h.municipio = :municipio")})
public class Hijos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_hijo")
    private Integer idHijo;
    @Column(name = "ci")
    private Integer ci;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "fecha_nac")
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Size(max = 2147483647)
    @Column(name = "lugar_nac")
    private String lugarNac;
    @Column(name = "sexo")
    private Character sexo;
    @Size(max = 2147483647)
    @Column(name = "nacionalidad")
    private String nacionalidad;
    @Size(max = 2147483647)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 2147483647)
    @Column(name = "municipio")
    private String municipio;
    @Column(name = "id_usuario")
    private Integer id_usuario;
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hijos")
    private Collection<Vacunas> vacunasCollection;
    */
    public Hijos() {
    }

    public Hijos(Integer idHijo) {
        this.idHijo = idHijo;
    }

    public Integer getIdHijo() {
        return idHijo;
    }

    public void setIdHijo(Integer idHijo) {
        this.idHijo = idHijo;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
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

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getLugarNac() {
        return lugarNac;
    }

    public void setLugarNac(String lugarNac) {
        this.lugarNac = lugarNac;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /*@XmlTransient
    public Collection<Vacunas> getVacunasCollection() {
        return vacunasCollection;
    }

    public void setVacunasCollection(Collection<Vacunas> vacunasCollection) {
        this.vacunasCollection = vacunasCollection;
    }*/

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHijo != null ? idHijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hijos)) {
            return false;
        }
        Hijos other = (Hijos) object;
        if ((this.idHijo == null && other.idHijo != null) || (this.idHijo != null && !this.idHijo.equals(other.idHijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dq.service.Hijos[ idHijo=" + idHijo + " ]";
    }
    
}
