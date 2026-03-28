package com.eventosuy.logica.evento;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTTipoRegistro {
	
	private String nombre;
	private Integer cupo;
	private Float costo;
	private String descripcion;
	
	public DTTipoRegistro(String nombre, Integer cupo, Float costo, String descripcion) {
		this.nombre = nombre;
		this.cupo = cupo;
		this.costo = costo;
		this.descripcion = descripcion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Integer getCupo() {
		return cupo;
	}
	
	public Float getCosto() {
		return costo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
}
