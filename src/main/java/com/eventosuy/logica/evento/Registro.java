package com.eventosuy.logica.evento;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import com.eventosuy.logica.usuario.Asistente;
import com.eventosuy.persistencia.AsistenteDAO;
import com.eventosuy.persistencia.EdicionDAO;
import com.eventosuy.persistencia.RegistroDAO;

public class Registro {
    private LocalDate fecha;
    private Float costo;
    private TipoRegistro tipoRegistro;
    private Asistente asistente;
    private Edicion edicion;
    private String nombreEvento;
    private boolean asistencia;
    
    private String codigoPatrocinio;
    
    public Registro(LocalDate fecha, Float costo, TipoRegistro tipo, Asistente asis, Edicion edicion, String nombreEvento, String codigo) {
        this.fecha = fecha;
        this.costo = costo;
        this.tipoRegistro = tipo;
        this.asistente = asis;
        this.edicion = edicion;
        this.nombreEvento = nombreEvento;
        this.setCodigoPatrocinio(codigo);
        this.asistencia = false;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Float getCosto() {
        return costo;
    }
    
    public TipoRegistro getTipoRegistro() {
    	return tipoRegistro;
    }
    
    public Asistente getAsistente() {
    	return asistente;
    }

    /*public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }
    
    public void setTipoRegistro(TipoRegistro tipo) {
    	this.tipoRegistro = tipo;
    }
    
    public void setAsistente(Asistente asis) {
    	this.asistente = asis;
    }*/
    public boolean getAsistencia() {
    	return this.asistencia;
    }
    
    public void setAsistencia(boolean asistencia) {
    	this.asistencia=asistencia;
    }
    
    public String obtenerNombreRegistro() {
    	return edicion.getNombre();
    }
    
    public DTRegistro obtenerDetallesRegistroDeUsuario() {
    	String nombreTipoRegistro = tipoRegistro.getNombre();
    	String nickNameAsis = asistente.getNickname();
    	return new DTRegistro(nickNameAsis, nombreTipoRegistro, this.getFecha(), costo, this.edicion.getNombre(), this.nombreEvento, this.costo==0, this.getAsistencia());
    }
    
    public String getNicknameAsistente() {
    	return asistente.getNickname();
    }
    
    public Boolean edicionDistinta(String nombreEdicion) {
    	if (!nombreEdicion.equals(tipoRegistro.obtenerNombreEdicion())) {
    		return true;
    	} else {
    		return false;
    	}
    }

	public String getCodigoPatrocinio() {
		return codigoPatrocinio;
	}

	public void setCodigoPatrocinio(String codigoPatrocinio) {
		this.codigoPatrocinio = codigoPatrocinio;
	}
	
	public RegistroDAO persistirRegistro(EntityManager man, EdicionDAO edicion) {
		AsistenteDAO asistentePersistido = asistente.persistirAsistente(man);
		RegistroDAO registroPersistido = new RegistroDAO(fecha, costo, asistentePersistido, codigoPatrocinio, edicion); 
		man.persist(registroPersistido); 
		asistentePersistido.addRegistro(registroPersistido);
		man.persist(asistentePersistido);
		return registroPersistido;
	}
	
}

