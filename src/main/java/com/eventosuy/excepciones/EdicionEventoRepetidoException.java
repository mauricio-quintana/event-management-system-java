package com.eventosuy.excepciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class EdicionEventoRepetidoException extends Exception {
	
	public EdicionEventoRepetidoException(String string) {
		super(string);
	}
	
}
