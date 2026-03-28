package com.eventosuy.persistencia;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

//esta clase esta definida unicamente para persistir registro
//registro tiene una clave compuesta por el id de un asistente
//y el id de una edicion, usamos esta clase como id del registro

@Embeddable
public class registroId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int asisId;
	private int edId;
	
	public int getAsisId() {
		return asisId;
	}
	public void setAsisId(int asisId) {
		this.asisId = asisId;
	}
	public int getEdiId() {
		return edId;
	}
	public void setEdiId(int ediId) {
		this.edId = ediId;
	}
	
	//para usarse como clave embedded es obligatorio que implemente los siguientes metodos
	public registroId() {}
	
	@Override
	public int hashCode() {
		return Objects.hash(asisId, edId);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof registroId) {
			registroId otro = (registroId) obj;
			return this.asisId == otro.asisId && this.edId == otro.edId;
		}
		return false;
	}
	
}
