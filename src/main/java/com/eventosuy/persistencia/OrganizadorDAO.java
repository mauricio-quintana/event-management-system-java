package com.eventosuy.persistencia;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity(name="Organizadores")
public class OrganizadorDAO extends UsuarioDAO {

	private String descripcion;
	private String link;
	@OneToMany(mappedBy="organizador")
	private Set<EdicionDAO> ediciones;
	
	public OrganizadorDAO(String nickname, String nombre, String email, String descripcion, String link) {
		super(nickname, nombre, email);
		this.descripcion = descripcion;
		this.link = link;
		ediciones = new HashSet<EdicionDAO>();
	}
	
	public OrganizadorDAO() {}
	
	public void addEdicion(EdicionDAO edi) {
		ediciones.add(edi);
	}
}
