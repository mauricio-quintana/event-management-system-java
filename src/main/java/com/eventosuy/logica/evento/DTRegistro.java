package com.eventosuy.logica.evento;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTRegistro {
	
	private String nickname;
	private String nombre;
	private LocalDate fechaRegistrado;
	private Float costo;
	private String nombreEdicion;
	private String nombreEvento;
	private boolean patrocinado;
	private String fechaRegistradoAsString;
	private boolean asistencia;
	
	public DTRegistro(String nic, String nom, LocalDate fecha, Float cost, String nomb, String nombreEvento, boolean pat, boolean asistio) {
		this.nickname = nic;
		this.nombre = nom;
		this.fechaRegistrado = fecha;
		this.costo = cost;
		this.nombreEdicion = nomb;
		this.nombreEvento = nombreEvento;
		this.patrocinado = pat;
		fechaRegistradoAsString = fecha.toString();
		this.setAsistencia(asistio);
	}
	
	public String getNicknameAsistente() {
		return nickname;
	}
	
	public String getNombreTipoRegistro() {
		return nombre;
	}
	
	public LocalDate getFechaRegistrado() {
		return fechaRegistrado;
	}
	
	public Float getCosto() {
		return costo;
	}
	
	public String getNombreEdicion() {
		return this.nombreEdicion;
	}
	
	public String getNombreEvento() {
		return this.nombreEvento;
	}
	
	public boolean usaCodigopatrocinio() {
		return patrocinado;
	}
	
	public String getFechaRegistradoAsString() {
		return fechaRegistradoAsString;
	}

	public boolean getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(boolean asistencia) {
		this.asistencia = asistencia;
	}
}
