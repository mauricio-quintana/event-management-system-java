package com.eventosuy.logica.evento;

import java.time.LocalDate;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import com.eventosuy.logica.contenedores.DuplaFoto;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTEvento {
	
	private String nombre;
	private String sigla;
	private String descripcion;
	private LocalDate fechaAlta;
	private Set<String> categorias;
	private Set<DuplaFoto> ediciones;
	private byte[] foto;
	private String fechaAltaAsString;
	private boolean finalizado;
	
	public DTEvento(String nombre, String sigla, String descripcion, LocalDate fechaAlta, Set<String> categorias, Set<DuplaFoto> ediciones, byte[] foto, boolean fin) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.fechaAlta = fechaAlta;
		this.categorias = categorias;
		this.ediciones = ediciones; //deben ser first = foto y second = nombre edicion
		this.foto = foto;
		fechaAltaAsString = fechaAlta.toString();
		setFinalizado(fin);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	
	public Set<String> getCategorias(){
		return categorias;
	}
	
	public Set<DuplaFoto> getEdiciones(){
		return ediciones;
	}
	
	public byte[] getFoto() {
		return this.foto;
	}
	
	public String getFechaAltaAsString() {
		return fechaAltaAsString;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
}
