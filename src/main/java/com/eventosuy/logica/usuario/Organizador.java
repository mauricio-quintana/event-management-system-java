package com.eventosuy.logica.usuario;


import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import com.eventosuy.logica.contenedores.Dupla;
import com.eventosuy.logica.evento.DTEdicion;
import com.eventosuy.logica.evento.Edicion;
import com.eventosuy.logica.evento.EnumEstadoEdicion;
import com.eventosuy.persistencia.EdicionDAO;
import com.eventosuy.persistencia.OrganizadorDAO;

public class Organizador extends Usuario {
	private String descripcion;
	private String link;
	private Map<String, Edicion> ediciones;
	
	public Organizador(String nick, String nombre, String email, String descripcion, String link, String pass, byte[] foto) {
        super(nick, nombre, email, pass, foto);
        this.descripcion = descripcion;
        this.link = link;
        this.ediciones = new HashMap<>();
    }
	
	public String getDescripcion() {
        return this.descripcion;
    }
	
	public String getLink() {
        return this.link;
    }
	
	public void setDescripcion(String desc) {
        this.descripcion = desc;
    }
	
	public void setLink(String link) {
        this.link = link;
    }
	
	@Override
    public DTOrganizador getDTUsuario() { 
		Set<Dupla> ediAcep = new HashSet<>();
		Set<Dupla> ediIng = new HashSet<>();
		Set<Dupla> ediRec = new HashSet<>();
		Set<Dupla> ediArc = new HashSet<>();
		for (Map.Entry<String, Edicion> it: ediciones.entrySet()) { //ediciones en memoria
			switch (it.getValue().getEstado()) {
			case EnumEstadoEdicion.Confirmada: ediAcep.add(it.getValue().obtenerEventoEdicion());
				break;
			case EnumEstadoEdicion.Ingresada: ediIng.add(it.getValue().obtenerEventoEdicion());
				break;
			case EnumEstadoEdicion.Rechazada: ediRec.add(it.getValue().obtenerEventoEdicion());
				break;
			default:
				break;
			}
		}
		EntityManagerFactory fab = Persistence.createEntityManagerFactory("EventosUY");
		EntityManager man = fab.createEntityManager();
		Query consulta = man.createQuery("SELECT e FROM Ediciones e JOIN e.organizador o WHERE o.nickname = ?1");
		consulta.setParameter(1, this.getNickname());
		for (Object ediArch : consulta.getResultList().toArray()) {
			DTEdicion dte = ((EdicionDAO) ediArch).toDTE();
			ediArc.add(new Dupla(dte.getEvento(), dte.getNombre()));
		}
		man.close();
		fab.close();
        return new DTOrganizador(getNickname(), getNombre(), getEmail(), getContrasena(), getFoto(), this.getDescripcion(), this.getLink(), ediAcep, ediIng, ediRec, this.getSeguidos(), this.getSeguidores(), ediArc);
    }
	
	public void asociarEdicion(Edicion edicion) {
        if (edicion != null) {
            ediciones.put(edicion.getNombre(), edicion);
        }
    }
	
	public OrganizadorDAO persistirOrganizador(EntityManager man) {
		OrganizadorDAO organizadorPersistido;
		Query consulta = man.createQuery("SELECT o FROM Organizadores o WHERE o.nickname =?1");
		consulta.setParameter(1, this.getNickname());
		List<OrganizadorDAO> res = consulta.getResultList();
		if (res.isEmpty()) {
			organizadorPersistido = new OrganizadorDAO(this.getNickname(), this.getNombre(), this.getEmail(), descripcion, link);
			man.persist(organizadorPersistido);
		} else organizadorPersistido = res.getFirst();
		return organizadorPersistido;
	}
	
	public void removerEdicion(String nombre) {
		ediciones.remove(nombre);
	}
}