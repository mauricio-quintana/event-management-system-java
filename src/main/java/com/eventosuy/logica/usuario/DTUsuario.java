package com.eventosuy.logica.usuario;

import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({DTAsistente.class, DTOrganizador.class})
@XmlRootElement(name = "dtu")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTUsuario {
	
	private String nick;
	private String nomb;
	private String emai;
	private String pass;
	private byte[] foto;
	private Set<String> seguidos;
	private Set<String> seguidores;
	
	//constructor por defecto vacio para poder usar la anotacion
	public DTUsuario() {} 
	
	public DTUsuario(String nic, String nom, String ema, String pass, byte[] foto, Set<String> seguidos, Set<String> seguidores) {
		this.nick=nic;
		this.nomb=nom;
		this.emai=ema;
		this.pass = pass;
		this.foto = foto;
		this.seguidos = seguidos;
		this.seguidores = seguidores;
	}
	
	
	public String getNickname() {
		return this.nick;
	}
	
	public String getNombre() {
		return this.nomb;
	}
	
	public String getEmail() {
		return this.emai;
	}
	
	public String getContrasena() {
		return pass;
	}
	
	public byte[] getFoto() {
		return foto;
	}

	public Set<String> getSeguidos() {
		return seguidos;
	}

	public void setSeguidos(Set<String> seguidos) {
		this.seguidos = seguidos;
	}

	public Set<String> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(Set<String> seguidores) {
		this.seguidores = seguidores;
	}
	
}
