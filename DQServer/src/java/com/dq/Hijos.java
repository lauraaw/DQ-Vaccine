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
    @Size(max = 2147483647)
    @Column(name = "fecha_nac")
    private String fechaNac;
    @Column(name = "lugar_nac")
    private String lugarNac;
    @Size(max = 2147483647)
    @Column(name = "sexo")
    private String sexo;
    @Size(max = 2147483647)
    @Column(name = "nacionalidad")
    private String nacionalidad;
    @Size(max = 2147483647)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 2147483647)
    @Column(name = "municipio")
    private String municipio;
    @Size(max = 2147483647)
    @Column(name = "departamento")
    private String departamento;
    @Size(max = 2147483647)
    @Column(name = "barrio")
    private String barrio;
    @Column(name = "id_usuario")
    private Integer idUsuario;
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

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getLugarNac() {
        return lugarNac;
    }

    public void setLugarNac(String lugarNac) {
        this.lugarNac = lugarNac;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
    
    

    /*@XmlTransient
    public Collection<Vacunas> getVacunasCollection() {
        return vacunasCollection;
    }

    public void setVacunasCollection(Collection<Vacunas> vacunasCollection) {
        this.vacunasCollection = vacunasCollection;
    }*/

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer id_usuario) {
        this.idUsuario = id_usuario;
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
