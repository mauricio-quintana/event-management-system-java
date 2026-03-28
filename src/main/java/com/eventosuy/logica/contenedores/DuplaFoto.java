package com.eventosuy.logica.contenedores;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DuplaFoto {
    private byte[] first;
    private String second;

    public DuplaFoto(byte[] str1, String str2) {
        this.first = str1;
        this.second = str2;
    }

    public byte[] getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

}