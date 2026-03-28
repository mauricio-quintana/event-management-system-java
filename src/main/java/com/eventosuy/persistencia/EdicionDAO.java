package com.eventosuy.persistencia;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.eventosuy.logica.evento.DTEdicion;
import com.eventosuy.logica.evento.DTRegistro;
import com.eventosuy.logica.evento.EnumEstadoEdicion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name="Ediciones")
public class EdicionDAO {

	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	private int idn; //primary key autogenerada
    private String nombre;
    private String sigla;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaAlta;
    private String ciudad;
    private String pais;
    private LocalDate fechaArchivo;
    private String nombreEvento;
    
    @OneToMany(mappedBy="edicion", cascade = CascadeType.PERSIST) //misma relacion que definida en Registro
    private Set<RegistroDAO> registros;
    
    @ManyToOne(cascade = CascadeType.PERSIST) @JoinColumn(name="ORG_ID") //uso como foreign key el id de organizador
    private OrganizadorDAO organizador;
    
    public EdicionDAO(String nombre, String sigla, LocalDate fechaIni, LocalDate fechaFin, LocalDate fechaAlta, String ciudad, String pais, OrganizadorDAO org, String nombreEvento) {
    	this.setCiudad(ciudad);
    	this.setFechaAlta(fechaAlta);
    	this.setFechaFin(fechaFin);
    	this.setFechaInicio(fechaIni);
    	this.setNombre(nombre);
    	this.organizador = org;
    	this.pais = pais;
    	this.registros = new HashSet<RegistroDAO>();
    	this.setSigla(sigla);
    	this.fechaArchivo = LocalDate.now();
    	this.nombreEvento = nombreEvento;
    }
    
    public EdicionDAO() {}
    
    public void addRegistro(RegistroDAO regis) {
    	this.registros.add(regis);
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public DTEdicion toDTE() {
		Set<DTRegistro> DTregistros = new HashSet<DTRegistro>();
		return new DTEdicion(nombre, pais, ciudad, fechaInicio, fechaFin, fechaAlta, sigla, organizador.getNickname(), null, DTregistros, null, null, EnumEstadoEdicion.Archivada, null, nombreEvento);
	}
 }
