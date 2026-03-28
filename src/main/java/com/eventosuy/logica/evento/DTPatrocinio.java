package com.eventosuy.logica.evento;
import java.time.LocalDate;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTPatrocinio {
	
	private String nombreInstitucion;
	private String nombreEdicion;
	private String nombreTipoRegistro;
	private String codigo;
	private LocalDate fechaRealizado;
	private Float monto;
	private EnumPatrocinio nivel;
	private Integer cantidadGratuitos;
	private String fechaRealizadoAsString;
	
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}
	
	public String getNombreEdicion() {
		return this.nombreEdicion;
	}
	
	public String getNombreTipoRegistro() {
		return this.nombreTipoRegistro;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public LocalDate getDTFecha() {
		return fechaRealizado;
	}
	
	public Float getMonto() {
		return monto;
	}
	
	public EnumPatrocinio getNivel() {
		return nivel;
	}
	
	public Integer getCantidadGratuitos() {
		return cantidadGratuitos;
	}
	
	public DTPatrocinio(String nombreIntitucion, String nombreEdicion, String codigo, LocalDate fecha, Float monto, EnumPatrocinio nivel, Integer cantGratuitos, String nombreTipoRegistro) {
		this.nombreInstitucion=nombreIntitucion;
		this.nombreEdicion = nombreEdicion;
		this.codigo = codigo;
		this.fechaRealizado = fecha;
		this.monto = monto;
		this.nivel = nivel;
		this.cantidadGratuitos = cantGratuitos;
		this.nombreTipoRegistro = nombreTipoRegistro;
		fechaRealizadoAsString = fecha.toString();
	}
	
	public String getDTFechaAsString() {
		return fechaRealizadoAsString;
	}
}
