/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dq.service;

import com.dq.Hijos;
import com.dq.Usuarios;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Laura
 */
@Stateless
@Path("com.dq.hijos")
public class HijosFacadeREST extends AbstractFacade<Hijos> {

    @PersistenceContext(unitName = "DQPU")
    private EntityManager em;

    public HijosFacadeREST() {
        super(Hijos.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Hijos entity) {
        super.create(entity);
    }
    
    @POST
    @Path("/hijos")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public List<Hijos> hijos(String jsonID) {
        Gson obj = new Gson();
        Usuarios u = obj.fromJson(jsonID, Usuarios.class);
        List<Hijos> l;
        l = getEntityManager()
                    .createQuery("SELECT u FROM Hijos u WHERE u.idUsuario = :id")
                    .setParameter("id", u.getId()).getResultList();
        return l;
    }
    
    @POST
    @Path("/hijo")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Hijos hijo(String jsonID) {
        Gson obj = new Gson();
        Hijos h = obj.fromJson(jsonID, Hijos.class);
        try {
            h = (Hijos) getEntityManager()
                        .createQuery("SELECT u FROM Hijos u WHERE u.idHijo = :id")
                        .setParameter("id", h.getIdHijo()).getSingleResult();
            }
        catch (NoResultException e) {
            h = null;
        }
        return h;
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Hijos entity) {
        super.edit(entity);
    }
    

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Hijos find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("/hijoss/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Hijos> findHijos(@PathParam("id") int id) {
        List<Hijos> l;
        l = getEntityManager()
                    .createQuery("SELECT u FROM Hijos u WHERE u.id_usuario = :id")
                    .setParameter("id", id).getResultList();
        return l;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Hijos> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Hijos> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        em = Persistence.createEntityManagerFactory("DQPU").createEntityManager();
        return em;
    }
    
}
