/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dq.service;

import com.dq.Usuarios;
import com.google.gson.Gson;
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
@Path("com.dq.usuarios")
public class UsuariosFacadeREST extends AbstractFacade<Usuarios> {

    @PersistenceContext(unitName = "DQPU")
    private EntityManager em;

    public UsuariosFacadeREST() {
        super(Usuarios.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Usuarios entity) {
        super.create(entity);
    }
    
    @POST
    @Path("/correo")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Usuarios correo(String jsonMail) {
        Gson obj = new Gson();
        Usuarios u = obj.fromJson(jsonMail, Usuarios.class);
        try {
            u = (Usuarios) getEntityManager()
                    .createQuery("SELECT u FROM Usuarios u WHERE u.correo = :correo")
                    .setParameter("correo", u.getCorreo()).getSingleResult();
        }
        catch (NoResultException e) {
            u = null;
        }
        return u;
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Usuarios entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Usuarios find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("/mail/{correo}")
    @Produces({MediaType.APPLICATION_JSON})
    public Usuarios findCorreo(@PathParam("correo") String correo) {
        Usuarios u;
        try {
            u = (Usuarios) getEntityManager()
                    .createQuery("SELECT u FROM Usuarios u WHERE u.correo = :correo")
                    .setParameter("correo", correo).getSingleResult();
        }
        catch (NoResultException e) {
            u = null;
        }
        return u;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuarios> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuarios> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
