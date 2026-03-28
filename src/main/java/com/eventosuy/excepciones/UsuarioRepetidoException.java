package com.eventosuy.excepciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioRepetidoException extends Exception {

    public UsuarioRepetidoException(String string) {
        super(string);
    }

}
