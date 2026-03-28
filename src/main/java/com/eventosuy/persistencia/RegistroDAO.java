package com.eventosuy.persistencia;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity(name="Registros")
public class RegistroDAO {

	@EmbeddedId //usamos clase especifica de id
	private registroId idn; 
    private LocalDate fecha;
    private Float costo;
    @ManyToOne(cascade = CascadeType.PERSIST) @MapsId("asisId")
    private AsistenteDAO asistente;
    @ManyToOne(cascade = CascadeType.PERSIST) @MapsId("edId")
    private EdicionDAO edicion;
    private boolean usoPatrocinio;
    private String codigoPatrocinio;
    
    public RegistroDAO(LocalDate fecha, float costo, AsistenteDAO asis, String codigo, EdicionDAO edi) {
    	this.asistente = asis;
    	this.codigoPatrocinio = codigo;
    	this.costo = costo;
    	this.fecha = fecha;
    	this.usoPatrocinio = codigo != null;
    	this.edicion = edi;
    }
    
    public RegistroDAO() {}
    
}
