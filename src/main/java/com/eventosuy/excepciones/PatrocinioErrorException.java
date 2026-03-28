package com.eventosuy.excepciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class PatrocinioErrorException extends Exception {
	
	public PatrocinioErrorException(String string) {
		super(string);
	}
	
}
