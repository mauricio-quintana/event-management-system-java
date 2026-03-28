package com.eventosuy.excepciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class AsistenteErrorRegistroException extends Exception {
	
	public AsistenteErrorRegistroException(String string) {
		super(string);
	}
	
}
