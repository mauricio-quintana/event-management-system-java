package com.eventosuy.logica.evento;
import java.time.LocalDate;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTEdicion {

	private String nombre;
	private String pais;
	private String ciudad;
	private LocalDate fechaAlta;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String sigla;
	private String organizador;
	private byte[] foto;
	private String video;
	private Set<String> tipoRegistros;
	private Set<DTRegistro> registros;
	private Set<String> patrocinios;
	private EnumEstadoEdicion estado;
	private String fechaAltaAsString;
	private String fechaInicioAsString;
	private String fechaFinAsString;
	private String evento;
	
	public DTEdicion(String nombre, String pais, String ciudad, LocalDate fechaInicio, LocalDate fechaFin,
			LocalDate fechaAlta, String sigla, String organizador, Set<String> tipoRegistros, Set<DTRegistro> registros,
			Set<String> patrocinios, byte[] foto, EnumEstadoEdicion estado, String video, String nomEve) {
		super();
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.fechaAlta = fechaAlta;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.sigla = sigla;
		this.organizador = organizador;
		this.tipoRegistros = tipoRegistros;
		this.registros = registros;
		this.patrocinios = patrocinios;
		this.foto = foto;
		this.estado = estado;
		this.video = video;
		fechaAltaAsString = fechaAlta.toString();
		fechaInicioAsString = fechaInicio.toString();
		fechaFinAsString = fechaFin.toString();
		this.setEvento(nomEve);
	}

	public String getNombre() {
		return nombre;
	}

	

	public String getPais() {
		return pais;
	}

	
	public String getCiudad() {
		return ciudad;
	}

	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	
	public LocalDate getFechaFin() {
		return fechaFin;
	}

	

	public String getSigla() {
		return sigla;
	}

	

	public String getOrganizador() {
		return organizador;
	}

	
	public Set<String> getTipoRegistros() {
		return tipoRegistros;
	}



	public Set<DTRegistro> getRegistros() {
		return registros;
	}

	

	public Set<String> getPatrocinios() {
		return patrocinios;
	}

	public byte[] getFoto() {
		return this.foto;
	}
	
	public EnumEstadoEdicion getEstado() {
		return this.estado;
	}
	
	public String getVideo() {
		return this.video;
	}
	
	public String getFechaInicioString() {
		return fechaInicioAsString;
	}
	
	public String getFechaAltaString() {
		return fechaAltaAsString;
	}
	
	public String getFechaFinString() {
		return fechaFinAsString;
	}
	
	public boolean isArchivada() {
		return estado == EnumEstadoEdicion.Archivada;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}
}
